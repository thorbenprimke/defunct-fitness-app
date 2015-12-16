package com.femlite.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.femlite.app.R;
import com.femlite.app.di.ActivityComponent;
import com.femlite.app.di.HasComponent;
import com.femlite.app.manager.CacheManager;
import com.femlite.app.manager.VideoManager;
import com.femlite.app.model.Exercise;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ExerciseItemView extends RelativeLayout {

    @Inject
    CacheManager cacheManager;

    @Bind(R.id.exercise_item_image)
    ImageView image;

    @Bind(R.id.exercise_item_title)
    TextView title;

    @Bind(R.id.exercise_download_button)
    ImageView downloadButton;

    @Bind(R.id.exercise_download_progress)
    ProgressBar downloadProgress;

    @Bind(R.id.exercise_play_button)
    ImageView playButton;

    public ExerciseItemView(Context context) {
        super(context);
        init(context);
    }

    public ExerciseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExerciseItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.exercise_item_view, this);
        ButterKnife.bind(this);
        ((HasComponent<ActivityComponent>) getContext()).getComponent().inject(this);
    }

    public void bind(Exercise exercise) {
        title.setText(exercise.getName());
        Glide.with(getContext())
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/workout/"
                        + exercise.getPhotoMidPositionUrl())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(image);
        downloadProgress.setVisibility(View.GONE);
        if (cacheManager.hasVideo(exercise.getVideoUrl())) {
            downloadButton.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
        } else {
            downloadButton.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
        }
    }

    public void updateForDownloadProgress(VideoManager.ProgressUpdate progressUpdate) {
        downloadButton.setVisibility(View.GONE);
        downloadProgress.setVisibility(View.VISIBLE);
        downloadProgress.setProgress(progressUpdate.progress);
    }

    public void updateForPlay() {
        downloadProgress.setVisibility(View.GONE);
        playButton.setVisibility(View.VISIBLE);
    }
}
