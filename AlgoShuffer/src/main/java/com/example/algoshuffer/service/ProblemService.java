package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.ProblemTag;
import com.example.algoshuffer.repository.ProblemRepository;
import com.example.algoshuffer.service.Mapping.ProblemTagMappingService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProblemService{
    private final ProblemRepository problemRepository;
    private final ProblemTagMappingService problemTagMappingService;
    private final ProblemTagService problemTagService;

    public Problem findById(Long id){
        return problemRepository.findById(id).orElse(null);
    }

    public List<Problem> findByIds(Set<Long> problemsIds){
        return problemRepository.findAllById(problemsIds);
    }

    public void saveProblem(Problem problem){
        problemRepository.save(problem);
    }

    //문제들을 저장하는 메소드
    public void saveProblems(JsonArray jsonProblems) {
        List<Problem> problemsToSave = new ArrayList<>();
        List<ProblemTagMapping> mappingsToSave = new ArrayList<>();

        for (int i = 0; i < jsonProblems.size(); i++) {
            if (!jsonProblems.get(i).isJsonNull()) {
                JsonObject jsonProblem = jsonProblems.get(i).getAsJsonObject();

                Long problemId = jsonProblem.get("problemId").getAsLong();

                // 문제의 중복 여부 확인
                if (!problemRepository.existsByProblemId(problemId)) {
                    Problem problem = new Problem();
                    problem.setProblemId(problemId);
                    problem.setLevel(jsonProblem.get("level").getAsInt());
                    problem.setAcceptedUserCount(jsonProblem.get("acceptedUserCount").getAsLong());

                    JsonArray titles = jsonProblem.getAsJsonArray("titles");
                    for (int j = 0; j < titles.size(); j++) {
                        JsonObject title = titles.get(j).getAsJsonObject();
                        if (title.get("language").getAsString().equals("ko")) {
                            problem.setTitle(title.get("title").getAsString());
                            break;
                        } else {
                            problem.setTitle(title.get("title").getAsString());
                        }
                    }

                    problemsToSave.add(problem);

                    // 문제의 태그들 처리
                    JsonArray tags = jsonProblem.getAsJsonArray("tags");
                    for (int n = 0; n < tags.size(); n++) {
                        JsonObject tag = tags.get(n).getAsJsonObject();
                        Long tagId = tag.get("bojTagId").getAsLong();

                        ProblemTag problemTag = problemTagService.findById(tagId);
                        if (problemTag != null) {
                            ProblemTagMapping problemTagMapping = new ProblemTagMapping();
                            problemTagMapping.setProblem(problem);
                            problemTagMapping.setProblemTag(problemTag);

                            mappingsToSave.add(problemTagMapping);
                        }
                    }
                }
            }
        }

        // 문제와 매핑을 한 번에 저장
        problemRepository.saveAll(problemsToSave);
        problemTagMappingService.saveAll(mappingsToSave);
    }
}