package com.femlite.app.misc;

import android.support.v4.view.PagerAdapter;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

/**
 * Created by tester on 12/27/15.
 */
public class CustomInfiniatePagerAdapter extends InfinitePagerAdapter {

    public CustomInfiniatePagerAdapter(PagerAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getCount() {
        return this.getRealCount() == 0 ? 0 : 3000;
    }
}
