package com.example.algoshuffer.repository;

import com.example.algoshuffer.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
