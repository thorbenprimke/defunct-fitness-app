package com.femlite.app.model.parse;

import com.femlite.app.model.Exercise;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Exercise")
public class ParseExercise extends ParseObject implements Exercise {

    @Override
    public String getId() {
        return getObjectId();
    }

    @Override
    public String getWorkoutKey() {
        return getString("WorkoutKey");
    }

    @Override
    public int getOrder() {
        return getInt("Order");
    }

    @Override
    public String getName() {
        return getString("Name");
    }

    @Override
    public String getPhotoStartPositionUrl() {
        return getString("PhotoStartPositionUrl");
    }

    @Override
    public String getPhotoMidPositionUrl() {
        return getString("PhotoMidPositionUrl");
    }

    @Override
    public String getVideoUrl() {
        return getString("VideoUrl");
    }

    @Override
    public int getLayout() {
        return getInt("Layout");
    }
}
