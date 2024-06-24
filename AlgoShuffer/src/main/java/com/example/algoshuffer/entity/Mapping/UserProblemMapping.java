package com.example.algoshuffer.entity.Mapping;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_problem_mapping")
public class UserProblemMapping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonIgnore
    private Problem problem;
}
