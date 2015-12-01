package com.femlite.app.manager;

import com.femlite.app.model.Workout;
import com.parse.ParseException;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thorben on 11/27/15.
 */
public class DataManager {

    public static class ProgressUpdate {
        public final boolean finished;
        public final int progress;

        public ProgressUpdate(boolean finished, int progress) {
            this.finished = finished;
            this.progress = progress;
        }
    }

    private final NetworkRequestManager networkRequestManager;
    private final StorageManager storageManager;

    @Inject
    public DataManager(
            NetworkRequestManager networkRequestManager,
            StorageManager storageManager) {
        this.networkRequestManager = networkRequestManager;
        this.storageManager = storageManager;
    }

    public Subscription getWorkouts(
            Action1<Boolean> onNext,
            Action1<Throwable> onError) {
        return Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {

                    if (storageManager.hasWorkouts()) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        return;
                    }

                    List<Workout> workouts = null;
                    try {
                        workouts = networkRequestManager.fetchWorkouts();
                    } catch (ParseException e) {
                        subscriber.onError(e);
                    }

                    if (workouts == null) {
                        subscriber.onNext(false);
                        subscriber.onCompleted();
                        return;
                    }

                    storageManager.storeWorkouts(workouts);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }
}
