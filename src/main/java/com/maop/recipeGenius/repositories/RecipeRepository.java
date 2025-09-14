package com.maop.recipeGenius.repositories;

import com.maop.recipeGenius.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}