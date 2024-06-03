package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.ProblemTag;
import com.example.algoshuffer.repository.ProblemTagRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemTagService {
    private final ProblemTagRepository problemTagRepository;

    //tag전부를 받아와서 저장하는 메소드
    //item을 통해서 배열을 만들고
    //item의 bojtagid로 primarykey값을 만든다.
    public void saveProblemTags(JsonObject jsonObject){
        JsonArray items = jsonObject.get("items").getAsJsonArray();

        for (int i = 0; i < items.size(); i++){
            JsonObject item = items.get(i).getAsJsonObject();
            ProblemTag problemTag = new ProblemTag();
            problemTag.setBojTagId(item.get("bojTagId").getAsLong());
            problemTag.setKey((item.get("key").getAsString()));

            JsonArray displayNames = item.getAsJsonArray("displayNames");
            for (int j = 0; j < displayNames.size(); j++){
                JsonObject displayName = displayNames.get(j).getAsJsonObject();
                if (displayName.get("language").getAsString().equals("ko")){
                    problemTag.setDisplayName(displayName.get("name").getAsString());
                    problemTag.setDisplayNameShort(displayName.get("short").getAsString());
                    break;
                }
            }
            problemTagRepository.save(problemTag);
        }
    }
}
