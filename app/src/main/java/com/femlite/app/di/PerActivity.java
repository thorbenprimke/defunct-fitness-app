package com.femlite.app.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Scoping annotation for a component whose lifetime is that of the activity.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
