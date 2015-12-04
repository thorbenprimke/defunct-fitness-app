package com.femlite.app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExerciseViewerActivity extends FemliteBaseActivity {

    @Bind(R.id.video)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_viewer_activity);
        ButterKnife.bind(this);


        final File filesDir = this.getFilesDir();
        final File fileToStore = new File(filesDir + "/clip2.mp4");

        videoView.setOnErrorListener((mp, what, extra) -> false);
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                }
        );
        videoView.setVideoPath(fileToStore.getPath());
    }
}
