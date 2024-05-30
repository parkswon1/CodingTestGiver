package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.example.algoshuffer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProblemService problemService;

    //id로 user를 찾는 메소드
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //id로 user가 있는지 찾고 user가 푼문제를 넘겨주는 메소드
    public Set<Problem> findAllSolvedProblems(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSolvedProblems();
    }

    //name으로 회원 가입할때 name이 있으면 미성공 0 없다면 생성후 1
    public int createUserByName(User user){
        if (userRepository.findByName(user.getUsername()) == 1){
            new RuntimeException("User already exitst");
            return 0;
        }else{
            userRepository.save(user);
            return 1;
        }
    }

    //id로 user가 있는지 확인하고 문제를 넣어주는데 문제가 등록되지 않은 문제면 새로 생성 이후 사용자 푼문제에 넣어줌
    public void addUserSolvedProblem(Long id, Set<Problem> problems){
        User user = findById(id);
        for (Problem problem : problems){
            if (problemService.findById(problem.getId()) == null){
                problemService.createProblem(problem);
            }
            user.getSolvedProblems().add(problem);
        }
    }
}
