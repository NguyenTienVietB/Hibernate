package com.javatraining.model;

import java.util.Set;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @OrderBy("title")
    private Set<Post> posts;
}