package com.femlite.app.model.realm;

import com.femlite.app.model.Exercise;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The Realm model of a {@link Exercise}.
 */

public class RealmExercise extends RealmObject implements Exercise {

    @PrimaryKey
    private String id;

    private String workoutKey;

    private int order;
    
    private String name;

    private String photoStartPositionUrl;

    private String photoMidPositionUrl;

    private String videoUrl;

    private int layout;

    public RealmExercise() {
    }

    public RealmExercise(Exercise exercise) {
        this.id = exercise.getId();
        this.workoutKey = exercise.getWorkoutKey();
        this.order = exercise.getOrder();
        this.name = exercise.getName();
        this.photoStartPositionUrl = exercise.getPhotoStartPositionUrl();
        this.photoMidPositionUrl = exercise.getPhotoMidPositionUrl();
        this.videoUrl = exercise.getVideoUrl();
        this.layout = exercise.getLayout();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getWorkoutKey() {
        return workoutKey;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhotoStartPositionUrl() {
        return photoStartPositionUrl;
    }

    @Override
    public String getPhotoMidPositionUrl() {
        return photoMidPositionUrl;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public int getLayout() {
        return layout;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWorkoutKey(String workoutKey) {
        this.workoutKey = workoutKey;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoStartPositionUrl(String photoStartPositionUrl) {
        this.photoStartPositionUrl = photoStartPositionUrl;
    }

    public void setPhotoMidPositionUrl(String photoMidPositionUrl) {
        this.photoMidPositionUrl = photoMidPositionUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}
