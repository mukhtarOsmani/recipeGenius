package com.maop.recipeGenius.entity;

import lombok.Data;

@Data
public class RecipeRequest {

    private String mealType;

    private int servingSize;

    private String preferences;

    private String ingredients;

    private String avoidIngredients;

    private String difficultyLevel;

    private String timeConstraint;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAvoidIngredients() {
        return avoidIngredients;
    }

    public void setAvoidIngredients(String avoidIngredients) {
        this.avoidIngredients = avoidIngredients;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getTimeConstraint() {
        return timeConstraint;
    }

    public void setTimeConstraint(String timeConstraint) {
        this.timeConstraint = timeConstraint;
    }
}
