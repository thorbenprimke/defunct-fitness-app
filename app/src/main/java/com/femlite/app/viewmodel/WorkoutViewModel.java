package com.femlite.app.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.femlite.app.model.Workout;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * View Model for {@link Workout}.
 */
public class WorkoutViewModel implements Workout {

    private Workout workout;

    public WorkoutViewModel(Workout workout) {
        this.workout = workout;
    }

    public String getTitle() {
        return workout.getTitle();
    }

    public String getPhotoUrl() {
        return workout.getPhotoUrl();
    }

    public String getVideoPlaceholderPhotoUrl() {
        return workout.getVideoPlaceholderPhotoUrl();
    }

    @Override
    public int getNumExercises() {
        return workout.getNumExercises();
    }

    @Override
    public String getId() {
        return workout.getId();
    }

    @Override
    public String getInfluencer() {
        return workout.getInfluencer();
    }

    @BindingAdapter("bind:squareUrl")
    public static void setImageUrl(ImageView view, String url) {
        final Context context = view.getContext();
        Glide.with(context)
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/influencer/" + url)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(view);
    }

    @BindingAdapter("bind:coverUrl")
    public static void setCoverUrl(ImageView view, String url) {
        final Context context = view.getContext();
        Glide.with(context)
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/workout/" + url)
                .centerCrop()
                .into(view);
    }
}
