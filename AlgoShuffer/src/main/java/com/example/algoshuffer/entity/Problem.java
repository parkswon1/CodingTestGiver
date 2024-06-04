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
@Table(name = "problems")
public class Problem {
    @Id
    @Column(name = "problem_id")
    private Long problemId;

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private int level;

    @Column(name = "accepted_user_count")
    private Long acceptedUserCount;
}