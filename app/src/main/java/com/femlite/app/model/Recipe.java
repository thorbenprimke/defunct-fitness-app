package com.femlite.app.model;

import java.util.List;

/**
 * Created by tester on 1/17/16.
 */
public interface Recipe {

    String getTitle();

    String getDescription();

    List<String> getMealType();

    List<String> getFoodType();

    List<String> getIngredients();
}
