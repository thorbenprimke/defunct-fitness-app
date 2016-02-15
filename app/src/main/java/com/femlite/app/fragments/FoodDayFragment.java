package com.femlite.app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.femlite.app.FoodTrackerAddFoodActivity;
import com.femlite.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thorben2 on 12/12/15.
 */
public class FoodDayFragment extends Fragment {

    @Bind(R.id.food_tracker_main_consumed_calories)
    TextView caloriesTextView;

    @Bind(R.id.food_tracker_day_consumed)
    TextView dayConsumed;

    @Bind(R.id.circle_progress_bar)
    ProgressBar circleProgressBar;

    private Integer consumedCalories;
    private Integer availableDaily;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.food_tracker_page, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        consumedCalories = 0;
        availableDaily = 0;
        updateUI();
    }

    @OnClick(R.id.food_tracker_day_add_button)
    @SuppressWarnings("unused")
    public void handleOnAddFoodClicked() {
        startActivity(new Intent(getActivity(), FoodTrackerAddFoodActivity.class));
    }

    public void update(Integer consumedCalories, Integer availableDaily) {
        this.consumedCalories = consumedCalories;
        this.availableDaily = availableDaily;
        updateUI();
    }

    private void updateUI() {
        if (caloriesTextView == null || circleProgressBar == null) {
            return;
        }
        if (availableDaily == null || consumedCalories == null) {
            return;
        }

        int calLeft = availableDaily - consumedCalories;
        caloriesTextView.setText(String.valueOf(calLeft));
        if (calLeft <= 0) {
            circleProgressBar.setProgress(100);
        } else {
            circleProgressBar.setProgress((int) (100.f / (double) availableDaily * (double) consumedCalories));
        }

        dayConsumed.setText(String.valueOf(consumedCalories) + "\nConsumed");
    }
}
