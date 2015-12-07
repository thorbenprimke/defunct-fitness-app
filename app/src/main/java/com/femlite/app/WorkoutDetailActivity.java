package com.femlite.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.femlite.app.databinding.WorkoutDetailLayoutBinding;
import com.femlite.app.model.parse.ParseWorkout;
import com.femlite.app.viewmodel.WorkoutViewModel;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WorkoutDetailActivity extends FemliteBaseActivity {

    @Bind(R.id.workout_detail_title)
    TextView title;

    @Bind(R.id.video_overlay)
    View overlay;

    @Bind(R.id.video)
    VideoView videoView;

    @Bind(R.id.workout_detail_influencer_image)
    ImageView influencerImage;

    @Bind(R.id.workout_detail_influencer_name)
    TextView influencerName;

    @Bind(R.id.workout_detail_description)
    TextView description;

    @Bind(R.id.workout_detail_exercise_time)
    TextView exerciseTime;

    @Bind(R.id.workout_detail_exercise_count)
    TextView exerciseCount;

    @Bind(R.id.workout_detail_exercise_calorie)
    TextView exerciseCalerie;

    @Bind(R.id.video_placeholder_image)
    ImageView videoPlaceholderImage;

    @Bind(R.id.download_progress)
    ProgressBar downloadProgress;

    @Bind(R.id.playback_control)
    ImageView playbackControl;

    private WorkoutDetailLayoutBinding binding;

    private String workoutKey;
    private String workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.workout_detail_layout);
        ButterKnife.bind(this);

        workoutId = getIntent().getStringExtra("workoutId");

        playbackControl.setVisibility(View.GONE);

        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("objectId", workoutId);
        parseQuery.getFirstInBackground(
                (workout, e) -> {
                    workoutKey = workout.getKey();
                    title.setText(
                            "\"" + workout.getTitle() + "\" " + workout.getType());
                    exerciseTime.setText(workout.getDurationMin() + " minutes");
                    exerciseCalerie.setText(workout.getCalories() + " calories");
                    exerciseCount.setText(workout.getNumExercises() + " exercises");
                    description.setText(workout.getDescription());
                    binding.setWorkout(new WorkoutViewModel(workout));
                }
        );

        final File filesDir = this.getFilesDir();
        final File fileToStore = new File(filesDir + "/clip2.mp4");

        Subscription subscription = Observable
                .create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        if (fileToStore != null && fileToStore.exists()) {
                            downloadProgress.setProgress(100);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                            return;
                        }

                        InputStream input = null;
                        OutputStream output = null;
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL("https://dl.dropboxusercontent.com/u/2651558/femlite/video/femlite_video_1.mp4");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.connect();

                            // expect HTTP 200 OK, so we don't mistakenly save error report
                            // instead of the file
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                subscriber.onNext(false);
                                subscriber.onCompleted();
                                return;
//                                return "Server returned HTTP " + connection.getResponseCode()
//                                        + " " + connection.getResponseMessage();
                            }

                            // this will be useful to display download percentage
                            // might be -1: server did not report the length
                            int fileLength = connection.getContentLength();

                            // download the file
                            input = connection.getInputStream();
                            output = new FileOutputStream(fileToStore);

                            byte data[] = new byte[4096];
                            long total = 0;
                            int count;
                            while ((count = input.read(data)) != -1) {
                                // allow canceling with back button
                                if (subscriber.isUnsubscribed()) {
                                    input.close();
                                    subscriber.onCompleted();
                                    return;
                                }
                                total += count;
                                // publishing the progress....
                                if (fileLength > 0) { // only if total length is known{
                                    downloadProgress.setProgress((int) (total * 100 / fileLength));
//                                    publishProgress((int) (total * 100 / fileLength));
                                }
                                output.write(data, 0, count);
                            }
                        } catch (Exception e) {
                            subscriber.onNext(false);
                            subscriber.onCompleted();
                            return;
//                            return e.toString();
                        } finally {
                            try {
                                if (output != null)
                                    output.close();
                                if (input != null)
                                    input.close();
                            } catch (IOException ignored) {
                            }

                            if (connection != null)
                                connection.disconnect();
                        }
//                        return null;
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        aBoolean -> {
                            if (aBoolean) {
                                videoView.setVideoPath(fileToStore.getPath());
                                playbackControl.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(WorkoutDetailActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

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

    @OnClick(R.id.workout_detail_start_workout_button)
    public void handleStartWorkoutButtonClick() {
        Intent intent = new Intent(this, WorkoutProgressActivity.class);
        intent.putExtra("WorkoutId", workoutId);
        intent.putExtra("WorkoutKey", workoutKey);
        startActivity(intent);
    }

    @OnClick(R.id.workout_detail_exercises_button)
    public void handleShowExerciseListClick() {
        Intent intent = new Intent(this, ExerciseViewerActivity.class);
        startActivity(intent);
    }
}
