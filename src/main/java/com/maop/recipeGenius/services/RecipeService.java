package com.maop.recipeGenius.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maop.recipeGenius.entity.Recipe;
import com.maop.recipeGenius.entity.RecipeRequest;
import com.maop.recipeGenius.repositories.RecipeRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.image.ImageResponse;

@Service
public class RecipeService {

    private final OpenAiChatModel chatModel;
    private final OpenAiImageModel imageModel;
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public RecipeService(OpenAiChatModel chatModel, OpenAiImageModel imageModel, RecipeRepository recipeRepository, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
        this.recipeRepository = recipeRepository;
        this.objectMapper = objectMapper;
    }

    public Recipe generateRecipe(RecipeRequest request) {

        String prompt = String.format(
                "Generate a %s recipe for %d people that aligns with the following preferences: %s. " +
                        "Use only these ingredients: %s. Avoid: %s. " +
                        "Include a title, brief description (2-3 sentences), ingredients with measurements, " +
                        "step-by-step instructions, estimated prep/cooking time, and nutritional info per serving. " +
                        "Ensure the recipe is %s and can be completed in %s. Suggest substitutions if needed. " +
                        "Output in JSON format with keys: title, description, ingredients (array of objects with name and measurement), " +
                        "instructions (array of strings), prepTime, cookTime, nutritionalInfo (object with calories, protein, carbs, fat).",
                request.getMealType(), request.getServingSize(), request.getPreferences(),
                request.getIngredients(), request.getAvoidIngredients(),
                request.getDifficultyLevel(), request.getTimeConstraint()
        );

        String textResponse = chatModel.call(prompt);

        Recipe recipe = parseRecipeFromJson(textResponse);

        String imagePrompt = "Generate a high-quality, appetizing image of the recipe titled '" + recipe.getTitle() + "'. Make it look delicious and representative of the dish.";
        ImageResponse imageResponse = imageModel.call(
                new ImagePrompt(imagePrompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-3")
                                .quality("hd")
                                .N(1)
                                .height(1024)
                                .width(1024)
                                .build())
        );

        if (!imageResponse.getResults().isEmpty()) {
            Image image = imageResponse.getResult().getOutput();
            recipe.setImageUrl(image.getUrl());
        }

        return recipeRepository.save(recipe);
    }

    private Recipe parseRecipeFromJson(String json) {
        try {
            Recipe recipe = new Recipe();
            recipe.setTitle("Sample Recipe");
            recipe.setDescription("A delicious sample dish.");
            recipe.setIngredients(objectMapper.writeValueAsString(new String[]{"1 cup flour", "2 eggs"}));
            recipe.setInstructions(objectMapper.writeValueAsString(new String[]{"Mix ingredients", "Cook for 10 mins"}));
            recipe.setNutritionalInfo(objectMapper.writeValueAsString(new ObjectMapper().createObjectNode()
                    .put("calories", 200)
                    .put("protein", 10)
                    .put("carbs", 30)
                    .put("fat", 5)));
            recipe.setPrepTime(15);
            recipe.setCookTime(10);
            return recipe;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse recipe JSON", e);
        }
    }
}