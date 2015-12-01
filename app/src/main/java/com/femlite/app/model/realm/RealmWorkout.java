package com.femlite.app.model.realm;

import com.femlite.app.model.Workout;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thorben on 11/15/15.
 */
public class RealmWorkout extends RealmObject implements Workout {


    public RealmWorkout() {
    }

    public RealmWorkout(Workout workout) {
        title = workout.getTitle();
        photoUrl = workout.getPhotoUrl();
        videoPlaceholderPhotoUrl = workout.getVideoPlaceholderPhotoUrl();
        influencer = workout.getInfluencer();
        id = workout.getId();
    }

    private String title;

    private String photoUrl;

    private String videoPlaceholderPhotoUrl;

    private String influencer;

    private int numExercises;

    @PrimaryKey
    private String id;

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
}
