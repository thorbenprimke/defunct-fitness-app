package com.femlite.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.femlite.app.R;
import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseWorkout;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class WorkoutItemView extends RelativeLayout {

    @Bind(R.id.workout_item_image)
    ImageView image;

    @Bind(R.id.workout_item_title)
    TextView title;

    @Bind(R.id.workout_item_subtitle)
    TextView subtitle;

    @Bind(R.id.workout_item_featured_label)
    TextView featuredLabel;

    @Bind(R.id.workout_item_favorite)
    ImageView favorite;

//    private WorkoutDetailLayoutBinding binding;

    public WorkoutItemView(Context context) {
        super(context);
        init(context);
    }

    public WorkoutItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkoutItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.workout_item_view, this);
        ButterKnife.bind(this);
//        binding = WorkoutDetailLayoutBinding.bind(this);
    }

    public void bind(Workout workout) {

        final String titleStr = workout.getTitle();
        title.setText("\"" + titleStr + "\" " + workout.getCategory());
        subtitle.setText("By " + workout.getInfluencer());

        Glide.with(getContext())
                .load("https://dl.dropboxusercontent.com/u/2651558/femlite/influencer/" + workout.getPhotoUrl())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(image);

        final boolean featured = false; //workout.getBoolean("Featured");
        featuredLabel.setVisibility(featured ? View.VISIBLE : View.GONE);

        favorite.setImageResource(featured ?
                R.drawable.ic_favorite_active : R.drawable.ic_favorite_inactive);

//        binding.setWorkout(workout);
    }
}
