package com.femlite.app.manager;

import com.femlite.app.model.Exercise;
import com.femlite.app.model.Workout;
import com.parse.ParseException;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thorben on 11/27/15.
 */
public class DataManager {

    private final NetworkRequestManager networkRequestManager;
    private final StorageManager storageManager;
    private final VideoManager videoManager;

    @Inject
    public DataManager(
            NetworkRequestManager networkRequestManager,
            StorageManager storageManager,
            VideoManager videoManager) {
        this.networkRequestManager = networkRequestManager;
        this.storageManager = storageManager;
        this.videoManager = videoManager;
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

    public Subscription getWorkout(
            String workoutKey,
            Action1<Boolean> onNext,
            Action1<Throwable> onError) {
        return Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    if (storageManager.hasWorkout(workoutKey)) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        return;
                    }

                    Workout workout = null;
                    try {
                        workout = networkRequestManager.fetchWorkout(workoutKey);
                    } catch (ParseException e) {
                        subscriber.onError(e);
                    }
                    storageManager.storeWorkout(workout);

                    subscriber.onNext(true);
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    public void loadVideo(
            String videoUrl,
            Action1<VideoManager.ProgressUpdate> onNext,
            Action1<Throwable> onError) {
        videoManager.loadVideo(videoUrl, onNext, onError);
    }

    public Subscription getExercises(
            String workoutKey,
            Action1<Boolean> onNext,
            Action1<Throwable> onError) {
        return Observable
                .create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    if (storageManager.hasExercises(workoutKey)) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        return;
                    }

                    List<Exercise> exercises = null;
                    try {
                        exercises = networkRequestManager.fetchExercises(workoutKey);
                    } catch (ParseException e) {
                        subscriber.onError(e);
                    }

                    if (exercises == null) {
                        subscriber.onNext(false);
                        subscriber.onCompleted();
                        return;
                    }

                    storageManager.storeExercises(exercises);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }
}
