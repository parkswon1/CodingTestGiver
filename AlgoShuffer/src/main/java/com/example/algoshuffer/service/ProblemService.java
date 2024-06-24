package com.example.algoshuffer.service;

import com.example.algoshuffer.Solved;
import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.ProblemTag;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.repository.ProblemRepository;
import com.example.algoshuffer.service.Mapping.ProblemTagMappingService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<Problem> findByIds(Set<Long> problemsIds){
        return problemRepository.findAllById(problemsIds);
    }

    //특정 태그를 기준으로 사용자가 푼문제의 티어을 계산해서 넘겨주는 메서드
    public int userTagAverageLevel(Long userId, Long tagId){
        List<Problem> solvedProblems = problemRepository.findSolvedProblemsByUserAndTag(userId, tagId);
        return Solved.tierCalculator(Solved.tagRatingCalculator(solvedProblems));
    }

    //사용자 id랑 tagid를 받아서 5개의 문제를 반환
    public List<Problem> findByUserAndTag(User user, Long tagId){
        int userTagTier = userTagAverageLevel(user.getId(), tagId);
        int userTier = Solved.tierCalculator(user.getRating());
        int averageTier = (userTagTier + userTier)/2;
        Pageable pageable = PageRequest.of(0, 5);
        List<Problem> recommendedProblems = problemRepository.findProblemsByTagExcludingUserSolved(user.getId(), tagId, averageTier, pageable);
        return recommendedProblems;
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