package com.maop.recipeGenius.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recipes")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(columnDefinition = "jsonb")
    private String ingredients; // JSON string for list with measurements

    @Column(columnDefinition = "jsonb")
    private String instructions; // JSON string for steps

    @Column(columnDefinition = "jsonb")
    private String nutritionalInfo; // JSON string for calories, macros

    @Column
    private Integer prepTime;

    @Column
    private Integer cookTime;

    @Column
    private Integer servingSize;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}