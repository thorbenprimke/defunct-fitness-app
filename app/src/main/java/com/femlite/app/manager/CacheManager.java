package com.femlite.app.manager;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by thorben on 11/27/15.
 */
public class CacheManager {

    private final VideoManager videoManager;

    @Inject
    public CacheManager(VideoManager videoManager) {
        this.videoManager = videoManager;
    }

    public Subscription loadVideo(
            String videoUrl,
            Action1<VideoManager.ProgressUpdate> onNext,
            Action1<Throwable> onError) {
        return videoManager.loadVideo(videoUrl, onNext, onError);
    }

    public boolean hasVideo(String videoUrl) {
        return videoManager.isVideoCached(videoUrl);
    }
}
