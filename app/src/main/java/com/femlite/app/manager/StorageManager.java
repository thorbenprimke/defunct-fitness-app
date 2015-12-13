package com.femlite.app.manager;

import com.femlite.app.model.Workout;
import com.femlite.app.model.realm.RealmWorkout;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by thorben2 on 12/1/15.
 */
public class StorageManager {

    @Inject
    public StorageManager() {
    }

    public void storeWorkouts(List<Workout> workouts) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Workout workout : workouts) {
            RealmWorkout realmWorkout = new RealmWorkout(workout);
            realm.copyToRealmOrUpdate(realmWorkout);
        }
        realm.commitTransaction();
        realm.close();
    }

    public boolean hasWorkouts() {
        Realm realm = Realm.getDefaultInstance();
        boolean hasWorkouts = realm.where(RealmWorkout.class).count() > 0;
        realm.close();
        return hasWorkouts;
    }

    public boolean hasWorkout(String workoutKey) {
        Realm realm = Realm.getDefaultInstance();
        boolean hasWorkout = realm
                .where(RealmWorkout.class)
                .equalTo("key", workoutKey)
                .count() == 1;
        realm.close();
        return hasWorkout;
    }

    public void storeWorkout(Workout workout) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(new RealmWorkout(workout));
        realm.close();
    }


}
