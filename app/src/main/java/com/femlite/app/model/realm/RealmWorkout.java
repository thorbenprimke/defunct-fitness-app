package com.femlite.app.model.realm;

import com.femlite.app.model.Workout;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thorben on 11/15/15.
 */
public class RealmWorkout extends RealmObject implements Workout {

    @PrimaryKey
    private String id;

    private String key;

    private String title;

    private String photoUrl;

    private String videoPlaceholderPhotoUrl;

    private String influencer;

    private int numExercises;

    private int durationMin;

    private int calories;

    private String description;

    private String category;

    private String videoUrl;

    public RealmWorkout() {
    }

    public RealmWorkout(Workout workout) {
        title = workout.getTitle();
        photoUrl = workout.getPhotoUrl();
        videoPlaceholderPhotoUrl = workout.getVideoPlaceholderPhotoUrl();
        influencer = workout.getInfluencer();
        id = workout.getId();
        key = workout.getKey();
        durationMin = workout.getDurationMin();
        calories = workout.getCalories();
        description = workout.getDescription();
        category = workout.getCategory();
        numExercises = workout.getNumExercises();
        videoUrl = workout.getVideoUrl();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getVideoPlaceholderPhotoUrl() {
        return videoPlaceholderPhotoUrl;
    }

    @Override
    public String getInfluencer() {
        return influencer;
    }

    @Override
    public int getNumExercises() {
        return numExercises;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int getDurationMin() {
        return durationMin;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setVideoPlaceholderPhotoUrl(String videoPlaceholderPhotoUrl) {
        this.videoPlaceholderPhotoUrl = videoPlaceholderPhotoUrl;
    }

    public void setInfluencer(String influencer) {
        this.influencer = influencer;
    }

    public void setNumExercises(int numExercises) {
        this.numExercises = numExercises;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
