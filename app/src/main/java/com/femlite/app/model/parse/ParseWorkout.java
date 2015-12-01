package com.femlite.app.model.parse;

import com.femlite.app.model.Workout;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Workout")
public class ParseWorkout extends ParseObject implements Workout {

    public ParseWorkout() {
    }

    public String getKey() {
        return getString("Key");
    }

    public String getTitle() {
        return getString("Title");
    }

    public String getType() {
        return getString("Type");
    }

    public String getInfluencer() {
        return getString("Influencer");
    }

    public int getDurationMin() {
        return getInt("Duration");
    }

    public int getCalories() {
        return getInt("Calories");
    }

    public int getNumExercises() {
        return getInt("NumExercise");
    }

    public String getDescription() {
        return getString("Description");
    }

    public String getVideoPlaceholderPhotoUrl() {
        return getString("VideoPlaceholderPhotoUrl");
    }

    public String getPhotoUrl() {
        return getString("PhotoUrl");
    }

    @Override
    public String getId() {
        return getObjectId();
    }
}
