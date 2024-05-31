package com.example.algoshuffer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Problem {
    @Id
    private Long problemid;

    private String title;

    private int level;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @ManyToMany
    private Set<User> solvedByUsers = new HashSet<>();
}
