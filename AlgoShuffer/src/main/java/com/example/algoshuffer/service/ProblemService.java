package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.ProblemTag;
import com.example.algoshuffer.repository.Mapping.ProblemTagMappingRepository;
import com.example.algoshuffer.repository.ProblemRepository;
import com.example.algoshuffer.repository.ProblemTagRepository;
import com.example.algoshuffer.service.Mapping.ProblemTagMappingService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public void saveProblem(Problem problem){
        problemRepository.save(problem);
    }

    //문제들을 저장하는 메소드
    public void saveProblems(JsonArray JsonProblems){
        //문제의 정보를 Problem리파지토리를 통해서 저장
        for (int i = 0; i < JsonProblems.size(); i++){
            if (!JsonProblems.get(i).isJsonNull()){
                JsonObject jsonProblem = JsonProblems.get(i).getAsJsonObject();

                Problem problem = new Problem();
                problem.setProblemId(jsonProblem.get("problemId").getAsLong());
                problem.setLevel(jsonProblem.get("level").getAsInt());
                problem.setAcceptedUserCount(jsonProblem.get("acceptedUserCount").getAsLong());

                JsonArray titles = jsonProblem.getAsJsonArray("titles");
                for (int j = 0; j < titles.size(); j++){
                    JsonObject title = titles.get(j).getAsJsonObject();
                    if (title.get("language").getAsString().equals("ko")){
                        problem.setTitle(title.get("title").getAsString());
                        break;
                    }else {
                        problem.setTitle(title.get("title").getAsString());
                    }
                }

                problemRepository.save(problem);

                //문제의 Tag들을 problemTagMappingTable에 저장
                JsonArray tags = jsonProblem.getAsJsonArray("tags");
                for (int n = 0; n < tags.size(); n++) {
                    JsonObject tag = tags.get(n).getAsJsonObject();
                    Long tagId = tag.get("bojTagId").getAsLong();

                    //problemTagService에서 tagid가 있는지 검사 있어야 저장함
                    ProblemTag problemTag = problemTagService.findById(tagId);
                    if (problemTag != null) {
                        ProblemTagMapping problemTagMapping = new ProblemTagMapping();
                        problemTagMapping.setProblem(problem);
                        problemTagMapping.setProblemTag(problemTag);
                        problemTagMappingService.saveProblemTagMapping(problemTagMapping);
                    }
                }
            }
        }
    }
}