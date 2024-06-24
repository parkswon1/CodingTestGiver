package com.example.algoshuffer.restController;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.service.ProblemService;
import com.example.algoshuffer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//문제를 추천해주는 컨트롤러들을 모은 컨트롤러
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final UserService userService;
    private final ProblemService problemService;

    //user의 이름과 tagid를 입력하면 문제를 5개 반환
    @GetMapping("/{username}/tag/{tagId}")
    public List<Problem> getProblemByUsernameAndTag(@PathVariable String username,@PathVariable Long tagId){
        User user = userService.findByName(username);
        return problemService.findProblemByUserAndTag(user, tagId);
    }

    //user의 이름을 입력하면 문제를 10 개 반환
    @GetMapping("/{username}")
    public List<Problem> getProblemByUesrname(@PathVariable String username){
        User user = userService.findByName(username);
        return problemService.findProblemByUser(user);
    }
}