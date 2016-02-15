package com.femlite.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.femlite.app.databinding.WorkoutProgressLayoutBinding;
import com.femlite.app.misc.Constants;
import com.femlite.app.model.Exercise;
import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseExercise;
import com.femlite.app.model.parse.ParseWorkout;
import com.femlite.app.viewmodel.WorkoutProgressViewModel;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutProgressActivity extends FemliteBaseActivity {

    private float DURATION_WORKOUT_SEC = 4.f;
    private float DURATION_BREAK_SEC = 2.f;

    private ProgressBar circleProgressView;
    private TextView circleText;

    @Bind(R.id.workout_progress_indicator)
    LinearLayout progressIndicatorContainer;

    @Bind(R.id.workout_progress_title)
    TextView title;

    @Bind(R.id.workout_progress_timer_container)
    FrameLayout timerContainer;

    @Bind(R.id.workout_progress_circle_play)
    ImageView playImageView;

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

        String workoutKey = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_KEY);

        ParseUser currentUser = ParseUser.getCurrentUser();
        int fitnessType = currentUser.getInt("FitnessType");
//        if (fitnessType == 1) {
//            DURATION_WORKOUT_SEC = 25.f;
//            DURATION_BREAK_SEC = 15.f;
//        } else if (fitnessType == 2) {
//            DURATION_WORKOUT_SEC = 30.f;
//            DURATION_BREAK_SEC = 10.f;
//        } else if (fitnessType == 3) {
//            DURATION_WORKOUT_SEC = 35.f;
//            DURATION_BREAK_SEC = 5.f;
//        }


        // TODO: Update to only load this once - doesn't need to be loaded each time.
        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("Key", workoutKey);
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

        circleText.setVisibility(View.GONE);
    }

    private void updateProgressIndicator() {
        final int childCount = progressIndicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childAt = progressIndicatorContainer.getChildAt(i);
            ((ImageView) childAt).setImageResource(
                    i <= index ? R.drawable.progress_indicator_active :
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

        if (!isAutoAdvance) {
            stopped = true;
            isRunning = false;
            startTime = 0L;
            circleProgressView.setProgress(100);
            circleText.setText(String.valueOf((int) getDuration()));
            playImageView.setVisibility(View.VISIBLE);
            circleText.setVisibility(View.GONE);
        }
        isAutoAdvance = false;

        workoutProgressViewModel.setIndex(index);
        updateProgressIndicator();
    }

    private Handler handler;

    private boolean isRunning;
    private boolean stopped;
    private long startTime;

    private boolean isBreak;
    private boolean isAutoAdvance;

    private void startOrStopCounter() {
        if (isRunning) {
            stopped = true;
            isRunning = false;
            startTime = 0L;
            isBreak = false;
            playImageView.setVisibility(View.VISIBLE);
            circleText.setVisibility(View.GONE);
        } else {
            isBreak = false;
            stopped = false;
            isRunning = true;
            startTime = System.currentTimeMillis();
            circleText.setText(String.valueOf((int) getDuration()));
            circleProgressView.setProgress(100);
            playImageView.setVisibility(View.GONE);
            circleText.setVisibility(View.VISIBLE);
            updateTimer();
        }
    }

    private void updateTimer() {
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (stopped) {
                            circleProgressView.setProgress(100);
                            circleText.setText(String.valueOf((int) getDuration()));
                            playImageView.setVisibility(View.VISIBLE);
                            circleText.setVisibility(View.GONE);
                            return;
                        }

                        long diff = System.currentTimeMillis() - startTime;
                        diff = diff / 1000;
                        float duration = getDuration();
                        if (diff > duration) {
                            if (index == workout.getNumExercises() - 1) {
                                Toast.makeText(getApplicationContext(), "All done!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            startTime = System.currentTimeMillis();
                            if (isBreak) {
                                isAutoAdvance = true;
                                handleNextButtonClick();
                                Toast.makeText(getApplicationContext(), "Back to it", Toast.LENGTH_SHORT).show();
                            } else  {
                                Toast.makeText(getApplicationContext(), "Take a break", Toast.LENGTH_SHORT).show();
                            }
                            isBreak = !isBreak;
                            duration = getDuration();
                            diff = 0;
                        }

                        circleProgressView.setProgress(100 - (int)(100.f / duration * ((float) diff)));
                        circleText.setText(String.valueOf((int) (duration - diff)));
                        updateTimer();
                    }
                }
                ,1000);
    }

    public float getDuration() {
        return isBreak ? DURATION_BREAK_SEC : DURATION_WORKOUT_SEC;
    }
}
