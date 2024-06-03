package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.ProblemTag;
import com.example.algoshuffer.repository.ProblemTagRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemTagService {
    private final ProblemTagRepository problemTagRepository;

    //tag전부를 받아와서 저장하는 메소드
    @Transactional
    public ProblemTag saveProblemTag(JsonObject jsonObject){
        ProblemTag problemTag = new ProblemTag();
        problemTag.setBojTagId(jsonObject.get("bojTagId").getAsLong());
        problemTag.setKey(jsonObject.get("key").getAsString());

        JsonArray displayNames = jsonObject.getAsJsonArray("displayNames");
        for (int i = 0; i < displayNames.size(); i++){
            JsonObject displayName = displayNames.get(i).getAsJsonObject();
            if (displayName.get("language").getAsString().equals("ko")){
                problemTag.setDisplayName(displayName.get("name").getAsString());
                problemTag.setDisplayNameShort(displayName.get("short").getAsString());
                break;
            }
        }
        return problemTagRepository.save(problemTag);
    }
}
