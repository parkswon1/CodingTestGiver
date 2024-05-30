package com.example.algoshuffer.service;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProblemService{
    private final ProblemRepository problemRepository;

    public Problem findById(Long id){
        return problemRepository.findById(id).orElse(null);
    }

    public void createProblem(Problem problem){
        problemRepository.save(problem);
    }
}
