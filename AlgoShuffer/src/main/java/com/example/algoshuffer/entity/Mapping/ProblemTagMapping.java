package com.example.algoshuffer.entity.Mapping;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.ProblemTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "problem_tag_mapping")
public class ProblemTagMapping implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonIgnore
    private Problem problem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "boj_tag_id", nullable = false)
    @JsonIgnore
    private ProblemTag problemTag;
}