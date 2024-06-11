package com.example.algoshuffer.repository.Mapping;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTagMappingRepository extends JpaRepository<ProblemTagMapping, Long> {
}