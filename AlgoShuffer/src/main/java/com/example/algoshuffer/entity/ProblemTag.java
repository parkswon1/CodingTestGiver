package com.example.algoshuffer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ProblemTag {
    @Id
    @Column(name = "boj_tag_id")
    private Long bojTagId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_name_short")
    private String displayNameShort;

    private String key;

    @ManyToMany
    private Set<Problem> problems = new HashSet<>();
}
