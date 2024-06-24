package com.example.algoshuffer.restController;

import com.example.algoshuffer.SolvedAPI;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.service.Mapping.UserProblemMappingService;
import com.example.algoshuffer.service.ProblemService;
import com.example.algoshuffer.service.ProblemTagService;
import com.example.algoshuffer.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("solved")
public class SolvedAPIController {
    private final ProblemTagService problemTagService;
    private final ProblemService problemService;
    private final UserService userService;
    private final UserProblemMappingService userProblemMappingService;

    //24-06-03날자상 206개의 tag존재 5page까지 가져오면 됨
    @GetMapping("/tag")
    public void fetchAndSaveProblemTag() throws IOException, InterruptedException {
        for (int page = 1; page < 6; page++){
            HttpResponse<String> response = SolvedAPI.solvedacAPIRequest(SolvedAPI.getTag(page));
            JsonObject tags = JsonParser.parseString(response.body()).getAsJsonObject();
            problemTagService.saveProblemTags(tags);
        }
    }

    //문제를 1000번문제부터 31980번째 문제까지 50개씩 묶어서 가져옴
    @GetMapping("/problem")
    public void fetchAndSaveProblem() throws IOException, InterruptedException {
        int start = 1000;
        int end = 31980;
        int batchSize = 50;

        for (int i = start; i <= end; i += batchSize) {
            List<Integer> problems = new ArrayList<>();
            for (int j = i; j < i + batchSize && j <= end; j++) {
                problems.add(j);
            }

            String uri = SolvedAPI.getProblemByArray(problems);
            HttpResponse<String> response = SolvedAPI.solvedacAPIRequest(uri);
            JsonArray JsonProblems = JsonParser.parseString(response.body()).getAsJsonArray();
            problemService.saveProblems(JsonProblems);
        }
    }

    //user를 입력하면 user이름을 받아와서 user가 푼문제를 저장
    @GetMapping("/user/{username}")
    public ResponseEntity<String> fetchAndSaveUser(@PathVariable String username) throws IOException, InterruptedException{
        String uri = SolvedAPI.getUserByName(username);
        HttpResponse<String> response = SolvedAPI.solvedacAPIRequest(uri);
        JsonObject originJson = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject JsonUser = originJson.getAsJsonArray("items").get(0).getAsJsonObject();
        if (originJson.get("count").getAsInt() == 1){

            User user = userService.findByName(username);
            if (user == null){
                user = new User();
                user.setUsername(username);
            }
            user.setRank(JsonUser.get("rank").getAsInt());
            user.setRating(JsonUser.get("rating").getAsInt());
            userService.saveUser(user);

            int endPage = (JsonUser.get("solvedCount").getAsInt() / 50) + 1;
            for (int page = 1; page <= endPage; page++){
                uri = SolvedAPI.getUserSolvedProblemByName(username, page);
                response = SolvedAPI.solvedacAPIRequest(uri);
                originJson = JsonParser.parseString(response.body()).getAsJsonObject();
                userProblemMappingService.saveUserProblemMapping(originJson, user);
            }
            return ResponseEntity.ok("User fetched successfully.");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}