package com.example.algoshuffer.entity;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "problem_tag")
@ToString
public class ProblemTag {
    @Id
    @Column(name = "boj_tag_id")
    private Long bojTagId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_name_sort")
    private String displayNameShort;

    @Column(name = "key")
    private String key;

    @OneToMany(mappedBy = "problemTag")
    private Set<ProblemTagMapping> problemTagMappings = new HashSet<>();
}
