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

    @GetMapping("/tag")
    public void fetchAndSaveProblemTag() throws IOException, InterruptedException {
        JsonObject tags = SolvedAPI.solvedacAPIRequest(SolvedAPI.getTag());
        problemTagService.saveProblemTag(tags);
    }
}
