package com.femlite.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.femlite.app.databinding.WorkoutDetailLayoutBinding;
import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.manager.VideoManager;
import com.femlite.app.misc.ActionHelper;
import com.femlite.app.misc.Constants;
import com.femlite.app.model.Workout;
import com.femlite.app.viewmodel.WorkoutViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

public class WorkoutDetailActivity extends FemliteBaseActivity {

    @Inject
    DataManager dataManager;

    @Inject
    VideoManager videoManager;

    @Inject
    UiStorageHelper uiStorageHelper;

    @Bind(R.id.video_overlay)
    View overlay;

    @Bind(R.id.video)
    VideoView videoView;

    @Bind(R.id.video_placeholder_image)
    ImageView videoPlaceholderImage;

    @Bind(R.id.download_progress)
    ProgressBar downloadProgress;

    @Bind(R.id.playback_control)
    ImageView playbackControl;

    private WorkoutDetailLayoutBinding binding;

    private String workoutKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.workout_detail_layout);
        ButterKnife.bind(this);
        getComponent().inject(this);
        uiStorageHelper.onCreate();

        playbackControl.setVisibility(View.GONE);

        workoutKey = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_KEY);
        dataManager.getWorkout(
                workoutKey,
                new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Workout workout = uiStorageHelper.getWorkout(workoutKey);
                        binding.setWorkout(new WorkoutViewModel(workout));

                        if (videoManager.isVideoCached(workout.getVideoUrl())) {
                            videoView.setVideoPath(videoManager.getVideoPath(workout.getVideoUrl()));
                            playbackControl.setVisibility(View.VISIBLE);
                        } else {
                            videoManager.loadVideo(
                                    workout.getVideoUrl(),
                                    new Action1<VideoManager.ProgressUpdate>() {
                                        @Override
                                        public void call(VideoManager.ProgressUpdate progressUpdate) {
                                            if (progressUpdate.finished && progressUpdate.path != null) {
                                                videoView.setVideoPath(progressUpdate.path);
                                                playbackControl.setVisibility(View.VISIBLE);
                                            } else if (!progressUpdate.finished) {
                                                downloadProgress.setProgress(progressUpdate.progress);
                                            } else {
                                                Toast.makeText(
                                                        WorkoutDetailActivity.this,
                                                        "something went wrong",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    },
                                    ActionHelper.getDefaultErrorAction(getApplicationContext()));
                        }
                    }
                },
                ActionHelper.getDefaultErrorAction(this));

        videoView.setOnErrorListener((mp, what, extra) -> false);
        overlay.setOnClickListener(
                v -> {
                    if (videoView.isPlaying()) {
                        playbackControl.setImageResource(R.drawable.ic_play_active);
                        videoView.pause();
                    } else {
                        downloadProgress.setVisibility(View.GONE);
                        videoPlaceholderImage.setVisibility(View.GONE);
                        playbackControl.setImageResource(R.drawable.ic_pause);
                        videoView.start();
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    @OnClick(R.id.workout_detail_start_workout_button)
    public void handleStartWorkoutButtonClick() {
        Intent intent = new Intent(this, WorkoutProgressActivity.class);
        intent.putExtra(Constants.EXTRA_WORKOUT_KEY, workoutKey);
        startActivity(intent);
    }

    @OnClick(R.id.workout_detail_exercises_button)
    public void handleShowExerciseListClick() {
        Intent intent = new Intent(this, ExerciseListActivity.class);
        intent.putExtra(Constants.EXTRA_WORKOUT_KEY, workoutKey);
        startActivity(intent);
    }
}
