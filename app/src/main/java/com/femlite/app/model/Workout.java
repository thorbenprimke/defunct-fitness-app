package com.femlite.app.model;

/**
 * Created by thorben on 11/16/15.
 */
public interface Workout {

    String getTitle();

    String getPhotoUrl();

    String getVideoPlaceholderPhotoUrl();

    String getInfluencer();

    int getNumExercises();

    String getId();

    String getKey();

    int getDurationMin();

    int getCalories();

    String getDescription();

    String getCategory();

    String getVideoUrl();
}
