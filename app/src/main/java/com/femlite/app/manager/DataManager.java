package com.femlite.app.manager;

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

    public DataManager() {

    }

    public Subscription getWorkouts(
            String url,
            Action1<ProgressUpdate> onNext,
            Action1<Throwable> onError) {
        return Observable
                .create((Observable.OnSubscribe<ProgressUpdate>) subscriber -> {



                    subscriber.onNext(new ProgressUpdate(false, 50));
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }
}
