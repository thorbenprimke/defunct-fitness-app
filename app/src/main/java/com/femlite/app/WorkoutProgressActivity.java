package com.femlite.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.femlite.app.databinding.WorkoutProgressLayoutBinding;
import com.femlite.app.model.Exercise;
import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseExercise;
import com.femlite.app.model.parse.ParseWorkout;
import com.femlite.app.viewmodel.WorkoutProgressViewModel;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutProgressActivity extends FemliteActivity {

    private static final float DURATION_SEC = 5.f;

    private ProgressBar circleProgressView;
    private TextView circleText;

    @Bind(R.id.workout_progress_indicator)
    LinearLayout progressIndicatorContainer;

    @Bind(R.id.workout_progress_title)
    TextView title;

    @Bind(R.id.workout_progress_timer_container)
    FrameLayout timerContainer;

    private List<Exercise> exercises;
    private Workout workout;

    private WorkoutProgressViewModel workoutProgressViewModel;

    private WorkoutProgressLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.workout_progress_layout);
        ButterKnife.bind(this);

        circleText = (TextView) findViewById(R.id.workout_progress_circle_title);
        circleProgressView = (ProgressBar) findViewById(R.id.circle_progress_bar);

        final Button button = (Button) findViewById(R.id.start_counter);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startOrStopCounter();
                    }
                }
        );

        String workoutId = getIntent().getStringExtra("WorkoutId");
        String workoutKey = getIntent().getStringExtra("WorkoutKey");

        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("objectId", workoutId);
        parseQuery.getFirstInBackground(
                (workoutResult, e) -> {
                    workout = workoutResult;

                    ParseQuery<ParseExercise> query = ParseQuery.getQuery(ParseExercise.class);
                    query = query.whereEqualTo("WorkoutKey", workoutKey);
                    query = query.addAscendingOrder("Order");
                    query.findInBackground((exercisesResult, error) -> {
                        // Update for exercise 1
                        exercises = new ArrayList<>(exercisesResult.size());
                        exercises.addAll(exercisesResult);
                        workoutProgressViewModel = new WorkoutProgressViewModel(WorkoutProgressActivity.this, workout, exercises);

                        updateForExercise(0);

                        binding.setModel(workoutProgressViewModel);
                    });

                }
        );

        handler = new Handler();
        for (int i = 0; i < 15; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(10, 0, 10, 0);
            imageView.setImageResource(i == 0 ? R.drawable.progress_indicator_active :
                    R.drawable.progress_indicator_inactive);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 20, 1);
            imageView.setLayoutParams(layoutParams);
            progressIndicatorContainer.addView(imageView);
        }

        timerContainer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startOrStopCounter();
                    }
                }
        );
    }

    private void updateProgressIndicator() {
        final int childCount = progressIndicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childAt = progressIndicatorContainer.getChildAt(i);
            ((ImageView) childAt).setImageResource(
                    i == index ? R.drawable.progress_indicator_active :
                            R.drawable.progress_indicator_inactive);

        }
    }

    private int index = 0;

    @OnClick(R.id.workout_next_button)
    public void handleNextButtonClick() {
        if (++index < workout.getNumExercises()) {
            updateForExercise(index);
        } else {
            Intent intent = new Intent(this, WorkoutFinishActivity.class);
            intent.putExtra("WorkoutId", workout.getId());
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.workout_previous_button)
    public void handlePreviousButtonClick() {
        if (index <= 0) {
            return;
        }
        updateForExercise(--index);
    }

    private void updateForExercise(int index) {
        final Exercise parseExercise = exercises.get(index);

        final ImageView startImage = (ImageView) findViewById(R.id.workout_image_start);
        Glide.with(this)
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/workout/"
                        + parseExercise.getPhotoStartPositionUrl())
                .centerCrop()
                .into(startImage);

        final ImageView postionImage = (ImageView) findViewById(R.id.workout_image_position);
        Glide.with(this)
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/workout/"
                        + parseExercise.getPhotoMidPositionUrl())
                .centerCrop()
                .into(postionImage);

        title.setText(parseExercise.getName());

        stopped = true;
        isRunning = false;
        startTime = 0L;
        circleProgressView.setProgress(0);
        circleText.setText("0");

        workoutProgressViewModel.setIndex(index);
        updateProgressIndicator();
    }

    private Handler handler;

    private boolean isRunning;
    private boolean stopped;
    private long startTime;

    private void startOrStopCounter() {
        if (isRunning) {
            stopped = true;
            isRunning = false;
            startTime = 0L;
        } else {
            stopped = false;
            isRunning = true;
            startTime = System.currentTimeMillis();
            circleText.setText("0");
            updateTimer();
        }
    }

    private void updateTimer() {
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (stopped) {
                            circleProgressView.setProgress(0);
                            circleText.setText("0");
                            return;
                        }

                        long diff = System.currentTimeMillis() - startTime;
                        diff = diff / 1000;
                        if (diff > DURATION_SEC) {
                            return;
                        }
                        circleProgressView.setProgress((int)(100.f/DURATION_SEC*((float) diff)));
                        circleText.setText(String.valueOf(diff));
                        updateTimer();
                    }
                }
                ,1000);
    }
}
