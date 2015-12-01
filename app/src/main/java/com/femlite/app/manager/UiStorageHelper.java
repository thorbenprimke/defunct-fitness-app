package com.femlite.app.manager;

import com.femlite.app.model.realm.RealmWorkout;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by thorben2 on 12/1/15.
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
}
