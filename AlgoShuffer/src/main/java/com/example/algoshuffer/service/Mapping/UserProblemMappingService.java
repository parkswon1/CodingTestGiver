package com.example.algoshuffer.service.Mapping;

import com.example.algoshuffer.entity.Mapping.UserProblemMapping;
import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.repository.Mapping.UserProblemMappingRepository;
import com.example.algoshuffer.service.ProblemService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProblemMappingService {
    private final UserProblemMappingRepository userProblemMappingRepository;
    private final ProblemService problemService;

    //user객체랑 problem객체가 연관되도록 매핑(매핑이 기존에 있는지 검사는 로직으로 중복 방지)
    public void saveUserProblemMapping(JsonObject jsonObject, User user) {
        JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();

        // 받은 문제 ID들 추출
        Set<Long> problemIds = new HashSet<>();
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonProblem = jsonArray.get(i).getAsJsonObject();
            problemIds.add(jsonProblem.get("problemId").getAsLong());
        }

        // User가 이미 푼 문제들 가져오기
        List<UserProblemMapping> existingMappings = userProblemMappingRepository.findByUser(user);
        Set<Long> existingProblemIds = existingMappings.stream()
                .map(mapping -> mapping.getProblem().getProblemId())
                .collect(Collectors.toSet());

        //새로운 푼 문제들 추출
        Set<Long> newProblemIds = problemIds.stream()
                .filter(problemId -> !existingProblemIds.contains(problemId))
                .collect(Collectors.toSet());

        //새로운 문제들 DB에서 한번에 가져오기
        Map<Long, Problem> problemMap = problemService.findByIds(newProblemIds).stream()
                .collect(Collectors.toMap(Problem::getProblemId, problem -> problem));

        List<UserProblemMapping> newMappings = new ArrayList<>();
        for (Long problemId : newProblemIds){
            Problem problem = problemMap.get(problemId);
            if (problem != null){
                UserProblemMapping userProblemMapping = new UserProblemMapping();
                userProblemMapping.setProblem(problem);
                userProblemMapping.setUser(user);
                newMappings.add(userProblemMapping);
            }
        }

        if (!newMappings.isEmpty()){
            userProblemMappingRepository.saveAll(newMappings);
        }
    }
}
