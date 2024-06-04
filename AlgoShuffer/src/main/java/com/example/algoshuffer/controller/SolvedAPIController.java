package com.example.algoshuffer.controller;

import com.example.algoshuffer.SolvedAPI;
import com.example.algoshuffer.service.ProblemTagService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("solved")
public class SolvedAPIController {
    private final ProblemTagService problemTagService;

    //24-06-03날자상 206개의 tag존재 5page까지 가져오면 됨
    @GetMapping("/tag")
    public void fetchAndSaveProblemTag() throws IOException, InterruptedException {
        for (int page = 1; page < 6; page++){
            JsonObject tags = SolvedAPI.solvedacAPIRequest(SolvedAPI.getTag(page));
            problemTagService.saveProblemTags(tags);
        }
    }
}
