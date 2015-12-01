package com.femlite.app.viewmodel;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.femlite.app.R;
import com.femlite.app.model.Exercise;
import com.femlite.app.model.Workout;

import java.util.List;

public class WorkoutProgressViewModel extends BaseObservable {

    private Activity activity;
    private Workout workout;

    private List<Exercise> exercises;

    private int currentExerciseIndex;

    public WorkoutProgressViewModel(Activity activity, Workout workout, List<Exercise> exercises) {
        this.activity = activity;
        this.workout = workout;
        this.exercises = exercises;

        currentExerciseIndex = 0;
    }

    @Bindable
    public String getExerciseTitle() {
        return exercises.get(currentExerciseIndex).getName();
    }

    @Bindable
    public Drawable getBackArrowDrawable() {
        final Resources res = activity.getResources();
        return currentExerciseIndex == 0 ? res.getDrawable(R.drawable.ic_previous_inactive) :
                res.getDrawable(R.drawable.ic_previous_active);
    }

    @Bindable
    public boolean getBackArrowEnabled() {
        return currentExerciseIndex != 0;
    }

    @Bindable
    public String getNextTitle() {
        if (currentExerciseIndex + 1 < exercises.size()) {
            return "Next: " + exercises.get(currentExerciseIndex + 1).getName();
        } else {
            return "Last exercise! Finish Strong!";
        }
    }

    public void setIndex(int index) {
        currentExerciseIndex = index;
        notifyChange();
    }
}
