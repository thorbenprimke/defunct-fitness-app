package com.femlite.app.model.parse;

import com.femlite.app.model.Recipe;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by tester on 1/17/16.
 */
@ParseClassName("Recipes")
public class ParseRecipe extends ParseObject implements Recipe {

    @Override
    public String getTitle() {
        return getString("Title");
    }

    @Override
    public String getDescription() {
        return getString("Description");
    }

    @Override
    public List<String> getMealType() {
        return getList("MealType");
    }

    @Override
    public List<String> getFoodType() {
        return getList("FoodType");
    }

    @Override
    public List<String> getIngredients() {
        return getList("Ingredients");
    }
}
