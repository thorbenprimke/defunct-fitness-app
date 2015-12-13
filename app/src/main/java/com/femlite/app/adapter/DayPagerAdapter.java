package com.femlite.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.femlite.app.fragments.FoodDayFragment;

import java.util.ArrayList;

/**
 * MonthPagerAdapter holds 4 fragments, which provides fragment for current
 * month, previous month and next month. The extra fragment helps for recycle
 * fragments.
 *
 * @author thomasdao
 */
public class DayPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<FoodDayFragment> fragments;

    // Lazily create the fragments
        public ArrayList<FoodDayFragment> getFragments() {
        if (fragments == null) {
            fragments = new ArrayList<FoodDayFragment>();
            for (int i = 0; i < getCount(); i++) {
                fragments.add(new FoodDayFragment());
            }
        }
        return fragments;
    }

    public void setFragments(ArrayList<FoodDayFragment> fragments) {
        this.fragments = fragments;
    }

    public DayPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FoodDayFragment fragment = getFragments().get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        // We need 4 gridviews for previous month, current month and next month,
        // and 1 extra fragment for fragment recycle
        return 4;//CaldroidFragment.NUMBER_OF_PAGES;
    }

}
