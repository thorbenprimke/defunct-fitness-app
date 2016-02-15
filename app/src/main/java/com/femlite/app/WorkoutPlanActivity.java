package com.femlite.app;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.femlite.app.views.WorkoutPlanView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WorkoutPlanActivity extends FemliteDrawerActivity {

    @Bind(R.id.workout_plan_day_container)
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        // Setup all the views here
        WorkoutPlanView workoutPlanView1 = new WorkoutPlanView(this);
        workoutPlanView1.bind("Monday", "Femlite", "Missed");
        container.addView(workoutPlanView1);

        WorkoutPlanView workoutPlanView2 = new WorkoutPlanView(this);
        workoutPlanView2.bind("Tuesday", "Berlin", "Do it now!", true);
        container.addView(workoutPlanView2);

        WorkoutPlanView workoutPlanView3 = new WorkoutPlanView(this);
        workoutPlanView3.bind("Wednesday", "----", "");
        container.addView(workoutPlanView3);

        WorkoutPlanView workoutPlanView4 = new WorkoutPlanView(this);
        workoutPlanView4.bind("Thursday", "Munich", "Upcoming");
        container.addView(workoutPlanView4);

        WorkoutPlanView workoutPlanView5 = new WorkoutPlanView(this);
        workoutPlanView5.bind("Friday", "----", "");
        container.addView(workoutPlanView5);

        WorkoutPlanView workoutPlanView6 = new WorkoutPlanView(this);
        workoutPlanView6.bind("Saturday", "Berlin", "Upcoming");
        container.addView(workoutPlanView6);

        WorkoutPlanView workoutPlanView7 = new WorkoutPlanView(this);
        workoutPlanView7.bind("Sunday", "----", "");
        container.addView(workoutPlanView7);
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.workout_plan_main_layout;
    }
}
