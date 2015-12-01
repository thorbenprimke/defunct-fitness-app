package com.femlite.app.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for the application/singleton scope of the graph as in the application lifecycle.
 * Any dependencies that are required by the entire application should be included here.
 */
@Module
public class ApplicationModule {

    private final Application _application;

    public ApplicationModule(Application application) {
        _application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return _application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return _application;
    }

    @Provides
    @Singleton
    Resources providesResources() { return _application.getResources(); }
}
