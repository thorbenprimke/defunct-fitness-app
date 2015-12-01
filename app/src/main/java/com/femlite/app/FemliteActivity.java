package com.femlite.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.femlite.app.di.ActivityComponent;
import com.femlite.app.di.ActivityModule;
import com.femlite.app.di.ApplicationComponent;
import com.femlite.app.di.DaggerActivityComponent;
import com.femlite.app.di.HasComponent;

/**
 * Base activity for any femlite activities
 */
public abstract class FemliteActivity extends AppCompatActivity
        implements HasComponent<ActivityComponent> {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponent();
    }

    // ================================================================================
    // Dependency Injection
    // ================================================================================
    private void initializeComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    private ApplicationComponent getApplicationComponent() {
        return ((FemliteApplication) getApplication()).getComponent();
    }

    private ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
