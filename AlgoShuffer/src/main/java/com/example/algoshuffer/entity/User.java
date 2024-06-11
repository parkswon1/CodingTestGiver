package com.example.algoshuffer.entity;

import com.example.algoshuffer.entity.Mapping.UserProblemMapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user")
    private Set<UserProblemMapping> solvedProblems = new HashSet<>();

    public User(String username){
        this.username = username;
    }
}