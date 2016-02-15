package com.femlite.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.femlite.app.adapter.DayPagerAdapter;
import com.femlite.app.fragments.FoodDayFragment;
import com.femlite.app.network.FbbdService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hirondelle.date4j.DateTime;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.parse.ParseCloud;

public class FoodTrackerMainActivity extends FemliteDrawerActivity {

    @Inject
    FbbdService fbbdService;

    @Bind(R.id.food_tracker_main_title_date)
    TextView titleDate;

    @Bind(R.id.day_infinite_pager)
    InfiniteViewPager dayViewPager;

    private DateTime date;
    private DayPagerAdapter pagerAdapter;
    private InfinitePagerAdapter infinitePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getComponent().inject(this);

        pagerAdapter = new DayPagerAdapter(getSupportFragmentManager());

        // Setup InfinitePagerAdapter to wrap around MonthPagerAdapter
        infinitePagerAdapter = new InfinitePagerAdapter(pagerAdapter);

        // Use the infinitePagerAdapter to provide data for dateViewPager
        dayViewPager.setAdapter(infinitePagerAdapter);

        // Setup pageChangeListener
        dayViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int offset = position - (infinitePagerAdapter.getRealCount() * 100);
                DateTime pageDate;
                if (offset > 0) {
                    pageDate = date.plus(0, 0, offset, 0, 0, 0, 0, DateTime.DayOverflow.Spillover);
                } else if (offset < 0) {
                    pageDate = date.minus(0, 0, -1*offset, 0, 0, 0, 0, DateTime.DayOverflow.Spillover);
                } else {
                    pageDate = date;
                }

                updateData(pageDate, ((FoodDayFragment) (pagerAdapter.getItem(position % 4))));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        date = DateTime.now(TimeZone.getDefault());
    }


    @Override
    protected void onResume() {
        super.onResume();
        FoodDayFragment item = (FoodDayFragment) (pagerAdapter.getItem(dayViewPager.getCurrentItem()));
        updateData(date, item);
    }

    private void updateData(DateTime dateTime, FoodDayFragment foodDayFragment) {
        Date simpleDate = new Date(dateTime.getMilliseconds(TimeZone.getDefault()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        foodDayFragment.updateText(simpleDateFormat.format(simpleDate));

        Map<String, Object> params = new HashMap<>();
        params.put("date", simpleDate);
        params.put("offset", String.valueOf(-1 * simpleDate.getTimezoneOffset()));
        ParseCloud.callFunctionInBackground(
                "consumedFoodByDate",
                params,
                (object, e) -> {
                    if (e != null) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        foodDayFragment.update(
                                (Integer) ((HashMap) object).get("total"),
                                (Integer) ((HashMap) object).get("dailyAvailable"));
                    }
                }
        );

        titleDate.setText(simpleDateFormat.format(simpleDate));
        foodDayFragment.update(0, 0);
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.content_calorie_tracker_overview;
    }

    @OnClick(R.id.food_tracker_main_title_before)
    public void handleBeforeClicked() {
        dayViewPager.setCurrentItem(-10, true);
//        updateData(-(24 * 60 * 60 * 1000));
    }

    @OnClick(R.id.food_tracker_main_title_after)
    public void handleAfterClicked() {
        dayViewPager.setCurrentItem(10, true);
//        updateData(24 * 60 * 60 * 1000);
    }
}
