package com.femlite.app.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * A module for the activity scope of the graph.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return activity;
    }
}
