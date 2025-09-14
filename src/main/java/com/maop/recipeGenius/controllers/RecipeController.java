package com.maop.recipeGenius.controllers;

import com.maop.recipeGenius.entity.Recipe;
import com.maop.recipeGenius.entity.RecipeRequest;
import com.maop.recipeGenius.services.RecipeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/generate")
    public Recipe generateRecipe(@RequestBody RecipeRequest request) {
        return recipeService.generateRecipe(request);
    }
}
