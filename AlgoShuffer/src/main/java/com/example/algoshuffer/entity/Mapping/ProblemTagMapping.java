package com.example.algoshuffer.entity.Mapping;

import com.example.algoshuffer.entity.Problem;
import com.example.algoshuffer.entity.ProblemTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "problem_tag_mapping")
public class ProblemTagMapping implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "boj_tag_id")
    private ProblemTag problemTag;

    @Id
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
