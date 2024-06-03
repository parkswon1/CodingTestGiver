package com.example.algoshuffer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Problem {
    @Id
    @Column(name = "problem_id")
    private Long problemid;

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private int level;

    @ManyToMany
    @Column(name = "tags")
    private Set<ProblemTag> bojTag = new HashSet<>();

    @ManyToMany
    private Set<User> solvedByUsers = new HashSet<>();
}
