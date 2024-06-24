package com.example.algoshuffer.restController;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.service.ProblemService;
import com.example.algoshuffer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final UserService userService;
    private final ProblemService problemService;

    @GetMapping("/tag/{username}/{tagId}")
    public List<Problem> getProblemByTag(@PathVariable String username,@PathVariable Long tagId){
        User user = userService.findByName(username);
        List<Problem> tagProblem = problemService.findByUserAndTag(user, tagId);
        return tagProblem;
    }
}
