package com.example.algoshuffer.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int level;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @ManyToMany
    private Set<User> solvedByUsers = new HashSet<>();
}
