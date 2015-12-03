package com.femlite.app;

import android.os.Bundle;

public class CalorieTrackerOverviewActivity extends FemliteDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.content_calorie_tracker_overview;
    }

}
