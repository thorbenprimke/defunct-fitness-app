package com.femlite.app.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.femlite.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WorkoutPlanView extends RelativeLayout {

    @Bind(R.id.workout_plan_item_day_title)
    TextView title;

    @Bind(R.id.workout_plan_item_day_exercise)
    TextView exercise;

    @Bind(R.id.workout_plan_item_day_status)
    TextView status;

    public WorkoutPlanView(Context context) {
        super(context);
        init(context);
    }

    public WorkoutPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkoutPlanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.workout_plan_item_view, this);
        ButterKnife.bind(this);
    }

    public void bind(String day, String exercise, String action) {
        bind(day, exercise, action, false);
    }

    public void bind(String day, String exercise, String action, boolean highlight) {
        this.title.setText(day);
        this.exercise.setText(exercise);
        this.status.setText(action);
        if (highlight) {
            setBackgroundColor(Color.parseColor("#CBBABA"));
        }
    }
}
