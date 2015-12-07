package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.femlite.app.network.FbbdService;
import com.parse.ParseCloud;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodTrackerMainActivity extends FemliteDrawerActivity {

    @Inject
    FbbdService fbbdService;

    @Bind(R.id.food_tracker_main_consumed_calories)
    TextView consumedCalories;

    @Bind(R.id.food_tracker_main_title_date)
    TextView titleDate;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getComponent().inject(this);


        date = new Date();
        updateData(0);
    }

    private void updateData(long offset) {
        date = new Date(date.getTime() + offset);


        Map<String, String> params = new HashMap<>();
        params.put("date", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date));
        params.put("offset", String.valueOf(-1 * date.getTimezoneOffset()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        titleDate.setText(simpleDateFormat.format(date));

        ParseCloud.callFunctionInBackground(
                "consumedFoodByDate",
                params,
                (object, e) -> {
                    consumedCalories
                            .setText("Consumed calories: " + ((HashMap) object).get("total"));
                }
        );
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.content_calorie_tracker_overview;
    }

    @OnClick(R.id.food_tracker_main_title_before)
    public void handleBeforeClicked() {
        updateData(-(24 * 60 * 60 * 1000));
    }

    @OnClick(R.id.food_tracker_main_title_after)
    public void handleAfterClicked() {
        updateData(24 * 60 * 60 * 1000);
    }

    @OnClick(R.id.food_tracker_button)
    public void handleFootTrackerButtonClicked() {
        startActivity(new Intent(this, FoodTrackerAddFoodActivity.class));
    }
}
