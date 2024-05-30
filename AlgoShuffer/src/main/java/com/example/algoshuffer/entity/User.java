package com.example.algoshuffer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToMany
    private Set<Problem> solvedProblems = new HashSet<>();

    public User(String username){
        this.username = username;
    }
}
