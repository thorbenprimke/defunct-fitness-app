package com.femlite.app.manager;

import android.app.Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thorben2 on 12/13/15.
 */
public class VideoManager {


    public static class ProgressUpdate {
        public final boolean finished;
        public final int progress;
        public final String path;

        public ProgressUpdate(boolean finished, int progress) {
            this.finished = finished;
            this.progress = progress;
            this.path = null;
        }

        public ProgressUpdate(boolean finished, int progress, String path) {
            this.finished = finished;
            this.progress = progress;
            this.path = path;
        }
    }

    private final Application application;

    private File basePath;

    @Inject
    public VideoManager(Application application) {
        this.application = application;
        basePath = application.getFilesDir();
    }

    public Subscription loadVideo(String videoUrl, Action1<ProgressUpdate> onNext, Action1<Throwable> onError) {
        final File fileToStore = new File(basePath + "/" + videoUrl);

        return Observable
                .create(new Observable.OnSubscribe<ProgressUpdate>() {
                    @Override
                    public void call(Subscriber<? super ProgressUpdate> subscriber) {
                        if (fileToStore != null && fileToStore.exists()) {
                            subscriber.onNext(new ProgressUpdate(true, 100, fileToStore.getPath()));
                            subscriber.onCompleted();
                            return;
                        }

                        InputStream input = null;
                        OutputStream output = null;
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL("https://dl.dropboxusercontent.com/u/2651558/femlite/video/" + videoUrl);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.connect();

                            // expect HTTP 200 OK, so we don't mistakenly save error report
                            // instead of the file
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                deleteVideo(videoUrl);
                                subscriber.onNext(new ProgressUpdate(true, 0, null));
                                subscriber.onCompleted();
                                return;
                            }

                            // this will be useful to display download percentage
                            // might be -1: server did not report the length
                            int fileLength = connection.getContentLength();

                            // download the file
                            input = connection.getInputStream();
                            output = new FileOutputStream(fileToStore);

                            byte data[] = new byte[4096];
                            long total = 0;
                            int count;
                            while ((count = input.read(data)) != -1) {
                                // allow canceling with back button
                                if (subscriber.isUnsubscribed()) {
                                    input.close();
                                    subscriber.onCompleted();
                                    deleteVideo(videoUrl);
                                    return;
                                }
                                total += count;

                                if (fileLength > 0) { // only if total length is known{
                                    subscriber.onNext(new ProgressUpdate(
                                            false,
                                            (int) (total * 100 / fileLength)));
                                }
                                output.write(data, 0, count);
                            }
                        } catch (Exception e) {
                            subscriber.onNext(new ProgressUpdate(true, 0, null));
                            subscriber.onCompleted();
                            deleteVideo(videoUrl);
                            return;
                        } finally {
                            try {
                                if (output != null)
                                    output.close();
                                if (input != null)
                                    input.close();
                            } catch (IOException ignored) {
                            }

                            if (connection != null)
                                connection.disconnect();
                        }
                        subscriber.onNext(new ProgressUpdate(true, 100, fileToStore.getPath()));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .onBackpressureLatest()
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }


    public boolean isVideoCached(String videoUrl) {
        return new File(basePath + "/" + videoUrl).exists();
    }

    public String getVideoPath(String videoUrl) {
        return new File(basePath + "/" + videoUrl).getPath();
    }

    public boolean deleteVideo(String videoUrl) {
        File file = new File(basePath + "/" + videoUrl);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }
}
