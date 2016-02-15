package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseWorkout;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutFinishActivity extends FemliteBaseActivity {

    private String workoutId;
    private Workout workout;

    @Bind(R.id.workout_finish_title)
    TextView title;

    @Bind(R.id.workout_finish_calorie_count)
    TextView calorieCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_finish_activity);
        ButterKnife.bind(this);

        workoutId = getIntent().getStringExtra("WorkoutId");

        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("objectId", workoutId);
        parseQuery.getFirstInBackground(
                (workoutResult, e) -> {
                    workout = workoutResult;

                    title.setText("Congratulations! You have done " + workout.getTitle() + " workout!");
                    calorieCount.setText(String.valueOf(workout.getCalories()));
                }
        );

    }

    @OnClick(R.id.workout_finish_finalize_button)
    public void handleFinalizeButton() {
        final ParseUser currentUser = ParseUser.getCurrentUser();

        ParseObject userWorkoutRelation = new ParseObject("UserWorkout");
        userWorkoutRelation.put("user", currentUser);
        userWorkoutRelation.put("workout", workout);
        userWorkoutRelation.put("workoutTime", new Date(System.currentTimeMillis()));

        userWorkoutRelation.saveInBackground(
                new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent = new Intent(WorkoutFinishActivity.this, WorkoutMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }
}
