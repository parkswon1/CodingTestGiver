package com.example.algoshuffer.entity;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "problems")
public class Problem {
    @Id
    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "level", nullable = true)
    private int level;

    @Column(name = "accepted_user_count")
    private Long acceptedUserCount;

    @OneToMany(mappedBy = "problem")
    private Set<ProblemTagMapping> problemTagMappings = new HashSet<>();
}