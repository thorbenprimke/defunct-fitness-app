package com.femlite.app.di;

import android.app.Activity;

import com.femlite.app.ExerciseListActivity;
import com.femlite.app.FemliteBaseActivity;
import com.femlite.app.FemliteDrawerActivity;
import com.femlite.app.FoodTrackerAddFoodActivity;
import com.femlite.app.FoodTrackerMainActivity;
import com.femlite.app.WorkoutDetailActivity;
import com.femlite.app.WorkoutMainActivity;
import com.femlite.app.views.ExerciseItemView;

import dagger.Component;

/**
 * A base component for activities. It uses the {@link PerActivity} scope. Any components that
 * extend this component must also annotate it with {@link PerActivity}.
 *
 * Note: Any instances provided via {@link Module}s are only available to be injected
 * into any objects within the same {@link Component} level / {@link Scope}. If any instances should
 * be available to sub-graphs / other scopes, they need to be exposed within the component.
 * In order to expose at least the same instances as the parent/dependent component, this interface
 * extends {@link ApplicationComponent} in order to inherit all of its exposed instances.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends ApplicationComponent {

    Activity activity();

    void inject(WorkoutMainActivity workoutMainActivity);

    void inject(FoodTrackerMainActivity foodTrackerMainActivity);

    void inject(FoodTrackerAddFoodActivity foodTrackerAddFoodActivity);

    void inject(FemliteDrawerActivity femliteDrawerActivity);

    void inject(WorkoutDetailActivity workoutDetailActivity);

    void inject(ExerciseListActivity exerciseListActivity);

    void inject(ExerciseItemView exerciseItemView);
}
