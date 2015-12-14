package com.femlite.app.manager;

import com.femlite.app.model.realm.RealmExercise;
import com.femlite.app.model.realm.RealmWorkout;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * UI / Realm helper to query UI data elements from the UI and ensures that the realm is present
 * while the owning activity/fragment/view is active.
 */
public class UiStorageHelper {

    private Realm realm;

    @Inject
    public UiStorageHelper() {
    }

    public void onCreate() {
        realm = Realm.getDefaultInstance();
    }

    public void onDestroy() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    public RealmResults<RealmWorkout> getWorkouts() {
        // query realm for all workouts
        return realm.where(RealmWorkout.class).findAll();
    }

    public RealmWorkout getWorkout(String workoutKey) {
        return realm.where(RealmWorkout.class).equalTo("key", workoutKey).findFirst();
    }

    public RealmResults<RealmExercise> getExercises(String workoutKey) {
        return realm.where(RealmExercise.class).equalTo("workoutKey", workoutKey).findAll();
    }
}
