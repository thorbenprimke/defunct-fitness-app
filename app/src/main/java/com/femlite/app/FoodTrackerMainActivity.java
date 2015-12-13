package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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

    private static final int NUMBER_OF_PAGES = 4;

    @Inject
    FbbdService fbbdService;

//    @Bind(R.id.food_tracker_main_consumed_calories)
//    TextView consumedCalories;
//
//    @Bind(R.id.food_tracker_main_title_date)
//    TextView titleDate;

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

//        date = new Date();
//        updateData(0);

        pagerAdapter = new DayPagerAdapter(getSupportFragmentManager());

        // Setup InfinitePagerAdapter to wrap around MonthPagerAdapter
        infinitePagerAdapter = new InfinitePagerAdapter(
                pagerAdapter);

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
//                int dayOffset = position - dayViewPager.getOffsetAmount();
//                ((FoodDayFragment) (pagerAdapter.getItem(position % 4))).updateText(pageDate.toString());

                updateData(pageDate, ((FoodDayFragment) (pagerAdapter.getItem(position % 4))));
//                ((FoodDayFragment) (pagerAdapter.getItem(dayViewPager.getCurrentItem()))).updateText(date.toString());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        date = DateTime.now(TimeZone.getDefault());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ((FoodDayFragment) (pagerAdapter.getItem(dayViewPager.getCurrentItem()))).updateText(date.toString());
    }

    private void updateData(DateTime dateTime, FoodDayFragment foodDayFragment) {
        Date simpleDate = new Date(dateTime.getMilliseconds(TimeZone.getDefault()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        foodDayFragment.updateText(simpleDateFormat.format(simpleDate));

        Map<String, Object> params = new HashMap<>();
        params.put("date", simpleDate);
        params.put("offset", String.valueOf(-1 * simpleDate.getTimezoneOffset()));
        ParseCloud.callFunctionInBackground(
                "consumedFoodByDate",
                params,
                (object, e) -> {
                    foodDayFragment.updateCalories("Consumed calories: " + ((HashMap) object).get("total"));
                }
        );
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.content_calorie_tracker_overview;
    }

//    @OnClick(R.id.food_tracker_main_title_before)
//    public void handleBeforeClicked() {
//        updateData(-(24 * 60 * 60 * 1000));
//    }
//
//    @OnClick(R.id.food_tracker_main_title_after)
//    public void handleAfterClicked() {
//        updateData(24 * 60 * 60 * 1000);
//    }

    @OnClick(R.id.food_tracker_button)
    public void handleFootTrackerButtonClicked() {
        startActivity(new Intent(this, FoodTrackerAddFoodActivity.class));
    }

//    public class DatePageChangeListener implements ViewPager.OnPageChangeListener {
//        private int currentPage = InfiniteViewPager.OFFSET;
//        private DateTime currentDateTime;
//        private ArrayList<CaldroidGridAdapter> caldroidGridAdapters;
//
//        /**
//         * Return currentPage of the dateViewPager
//         *
//         * @return
//         */
//        public int getCurrentPage() {
//            return currentPage;
//        }
//
//        public void setCurrentPage(int currentPage) {
//            this.currentPage = currentPage;
//        }
//
//        /**
//         * Return currentDateTime of the selected page
//         *
//         * @return
//         */
//        public DateTime getCurrentDateTime() {
//            return currentDateTime;
//        }
//
//        public void setCurrentDateTime(DateTime dateTime) {
//            this.currentDateTime = dateTime;
//            setCalendarDateTime(currentDateTime);
//        }
//
//        /**
//         * Return 4 adapters
//         *
//         * @return
//         */
//        public ArrayList<CaldroidGridAdapter> getCaldroidGridAdapters() {
//            return caldroidGridAdapters;
//        }
//
//        public void setCaldroidGridAdapters(
//                ArrayList<CaldroidGridAdapter> caldroidGridAdapters) {
//            this.caldroidGridAdapters = caldroidGridAdapters;
//        }
//
//        /**
//         * Return virtual next position
//         *
//         * @param position
//         * @return
//         */
//        private int getNext(int position) {
//            return (position + 1) % NUMBER_OF_PAGES;
//        }
//
//        /**
//         * Return virtual previous position
//         *
//         * @param position
//         * @return
//         */
//        private int getPrevious(int position) {
//            return (position + 3) % NUMBER_OF_PAGES;
//        }
//
//        /**
//         * Return virtual current position
//         *
//         * @param position
//         * @return
//         */
//        public int getCurrent(int position) {
//            return position % NUMBER_OF_PAGES;
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int position) {
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//
//        public void refreshAdapters(int position) {
//            // Get adapters to refresh
//            CaldroidGridAdapter currentAdapter = caldroidGridAdapters
//                    .get(getCurrent(position));
//            CaldroidGridAdapter prevAdapter = caldroidGridAdapters
//                    .get(getPrevious(position));
//            CaldroidGridAdapter nextAdapter = caldroidGridAdapters
//                    .get(getNext(position));
//
//            if (position == currentPage) {
//                // Refresh current adapter
//
//                currentAdapter.setAdapterDateTime(currentDateTime);
//                currentAdapter.notifyDataSetChanged();
//
//                // Refresh previous adapter
//                prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0,
//                        0, 0, 0, 0, DateTime.DayOverflow.LastDay));
//                prevAdapter.notifyDataSetChanged();
//
//                // Refresh next adapter
//                nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0,
//                        0, 0, 0, DateTime.DayOverflow.LastDay));
//                nextAdapter.notifyDataSetChanged();
//            }
//            // Detect if swipe right or swipe left
//            // Swipe right
//            else if (position > currentPage) {
//                // Update current date time to next month
//                currentDateTime = currentDateTime.plus(0, 1, 0, 0, 0, 0, 0,
//                        DateTime.DayOverflow.LastDay);
//
//                // Refresh the adapter of next gridview
//                nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0,
//                        0, 0, 0, DateTime.DayOverflow.LastDay));
//                nextAdapter.notifyDataSetChanged();
//
//            }
//            // Swipe left
//            else {
//                // Update current date time to previous month
//                currentDateTime = currentDateTime.minus(0, 1, 0, 0, 0, 0, 0,
//                        DateTime.DayOverflow.LastDay);
//
//                // Refresh the adapter of previous gridview
//                prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0,
//                        0, 0, 0, 0, DateTime.DayOverflow.LastDay));
//                prevAdapter.notifyDataSetChanged();
//            }
//
//            // Update current page
//            currentPage = position;
//        }
//
//        /**
//         * Refresh the fragments
//         */
//        @Override
//        public void onPageSelected(int position) {
//            refreshAdapters(position);
//
//            // Update current date time of the selected page
//            setCalendarDateTime(currentDateTime);
//
//            // Update all the dates inside current month
//            CaldroidGridAdapter currentAdapter = caldroidGridAdapters
//                    .get(position % NUMBER_OF_PAGES);
//
//            // Refresh dateInMonthsList
//            dateInMonthsList.clear();
//            dateInMonthsList.addAll(currentAdapter.getDatetimeList());
//        }
//
//    }
}
