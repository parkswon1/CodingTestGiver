package com.example.algoshuffer.repository;

import com.example.algoshuffer.entity.ProblemTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTagRepository extends JpaRepository<ProblemTag, Long> {
}
