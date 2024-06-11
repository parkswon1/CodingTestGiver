package com.example.algoshuffer.controller;

import com.example.algoshuffer.SolvedAPI;
import com.example.algoshuffer.service.ProblemService;
import com.example.algoshuffer.service.ProblemTagService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.GetMapping;
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
    public static JsonParser parser = new JsonParser();

    //24-06-03날자상 206개의 tag존재 5page까지 가져오면 됨
    @GetMapping("/tag")
    public void fetchAndSaveProblemTag() throws IOException, InterruptedException {
        for (int page = 1; page < 6; page++){
            HttpResponse<String> response = SolvedAPI.solvedacAPIRequest(SolvedAPI.getTag(page));
            JsonObject tags = parser.parse(response.body()).getAsJsonObject();
            problemTagService.saveProblemTags(tags);
        }
    }

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
            JsonArray JsonProblems = parser.parse(response.body()).getAsJsonArray();
            problemService.saveProblems(JsonProblems);
        }
    }
}
