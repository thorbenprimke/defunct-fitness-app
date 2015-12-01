package com.femlite.app.model.realm;

import com.femlite.app.model.Workout;

import io.realm.RealmObject;

/**
 * Created by thorben on 11/15/15.
 */
public class RealmWorkout extends RealmObject {

    private String title;

    private String photoUrl;

    private String videoPlaceholderPhotoUrl;

    private String influencer;

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVideoPlaceholderPhotoUrl() {
        return videoPlaceholderPhotoUrl;
    }

    public void setVideoPlaceholderPhotoUrl(String videoPlaceholderPhotoUrl) {
        this.videoPlaceholderPhotoUrl = videoPlaceholderPhotoUrl;
    }

    public String getInfluencer() {
        return influencer;
    }

    public void setInfluencer(String influencer) {
        this.influencer = influencer;
    }
}
