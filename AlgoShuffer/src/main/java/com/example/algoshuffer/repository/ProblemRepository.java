package com.example.algoshuffer.repository;

import com.example.algoshuffer.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>{
    boolean existsByProblemId(Long problemId);
}
