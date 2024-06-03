package com.example.algoshuffer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
