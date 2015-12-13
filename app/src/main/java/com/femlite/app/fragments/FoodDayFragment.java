package com.femlite.app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.femlite.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thorben2 on 12/12/15.
 */
public class FoodDayFragment extends Fragment {

    @Bind(R.id.food_tracker_main_title_date)
    TextView textView;

    @Bind(R.id.food_tracker_main_consumed_calories)
    TextView caloriesTextView;

    private String title;
    private String calories;

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
        updateUI();
    }

    public void updateText(String title) {
        this.title = title;
        updateUI();
    }

    public void updateCalories(String calories) {
        this.calories = calories;
        updateUI();
    }

    private void updateUI() {
        if (textView == null || caloriesTextView == null) {
            return;
        }
        textView.setText(title);
        caloriesTextView.setText(calories);
    }
}
