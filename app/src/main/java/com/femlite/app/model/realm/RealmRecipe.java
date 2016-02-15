package com.femlite.app.model.realm;

import com.femlite.app.model.Recipe;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by tester on 1/17/16.
 *
 * TODO (thorben) - make realm compatible :D
 */
public class RealmRecipe implements Recipe {

    private String id;
    private String title;
    private String description;
    private String mealTypeInternal;
    private String foodTypeInternal;
    private String ingredientsInternal;

    private List<String> mealType;

    private List<String> foodType;

    private List<String> ingredients;

    public RealmRecipe() {
        Gson gson = new Gson();
//        gson.from
    }

    public RealmRecipe(Recipe recipe) {
        title = recipe.getTitle();
        description = recipe.getDescription();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getMealType() {
        if (mealType == null) {

        }
        return mealType;
    }

    @Override
    public List<String> getFoodType() {
        return foodType;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }
}
