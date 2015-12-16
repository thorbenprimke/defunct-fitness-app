package com.femlite.app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

import com.femlite.app.misc.Constants;

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


        String extraVideoUrl = getIntent().getStringExtra(Constants.EXTRA_VIDEO_URL);
        final File videoUrl = new File(getFilesDir() + "/" + extraVideoUrl);

        videoView.setOnErrorListener((mp, what, extra) -> false);
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                }
        );
        videoView.setVideoPath(videoUrl.getPath());
    }
}
