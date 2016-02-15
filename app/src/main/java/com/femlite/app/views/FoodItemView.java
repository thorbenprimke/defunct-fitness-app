package com.femlite.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.femlite.app.R;
import com.femlite.app.model.Food;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by thorben2 on 12/5/15.
 */
public class FoodItemView extends RelativeLayout {

    @Bind(R.id.food_title)
    TextView title;

    @Bind(R.id.food_subtitle)
    TextView subTitle;

    @Bind(R.id.food_image)
    ImageView image;

    public FoodItemView(Context context) {
        super(context);
        init(context);
    }

    public FoodItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FoodItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.food_item_view, this);
        ButterKnife.bind(this);
    }

    public void bind(Food food) {
        title.setText(StringEscapeUtils.unescapeHtml4(food.getTitle()));
        subTitle.setText(food.getSubTitle());

        image.setImageResource(0);
        if (food.getImageUrl() != null) {
            Glide.with(getContext())
                    .load(food.getImageUrl())
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(image);
        }
    }
}
