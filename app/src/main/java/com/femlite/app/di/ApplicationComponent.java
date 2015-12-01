package com.femlite.app.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The component that lives for the entire life of an application.
 *
 * Note: Any instances provided via {@link Module}s are only available to be injected
 * into any objects within the same {@link Component} level / {@link Scope}. If any instances should
 * be available to sub-graphs / other scopes, they need to be exposed within the component.
 */
@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {

    // Any provided classes that should be exposed to sub-graphs, need to be added here.
    Context context();

    Application application();

    Resources resources();
}