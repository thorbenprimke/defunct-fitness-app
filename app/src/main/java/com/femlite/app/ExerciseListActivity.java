package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.femlite.app.manager.CacheManager;
import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.manager.VideoManager;
import com.femlite.app.misc.ActionHelper;
import com.femlite.app.misc.Constants;
import com.femlite.app.model.realm.RealmExercise;
import com.femlite.app.views.ExerciseItemView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import rx.Subscription;
import rx.functions.Action1;

public class ExerciseListActivity extends FemliteBaseActivity {

    @Inject
    DataManager dataManager;

    @Inject
    CacheManager cacheManager;

    @Inject
    UiStorageHelper uiStorageHelper;

    private String workoutKey;

    @Bind(R.id.exercise_recycler_view)
    RealmRecyclerView recyclerView;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list_layout);
        ButterKnife.bind(this);
        getComponent().inject(this);
        uiStorageHelper.onCreate();

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        workoutKey = getIntent().getStringExtra(Constants.EXTRA_WORKOUT_KEY);
        dataManager.getExercises(
                workoutKey,
                fetched -> {
                    if (fetched) {
                        RealmResults<RealmExercise> workouts =
                                uiStorageHelper.getExercises(workoutKey);
                        adapter.updateRealmResults(workouts);
                    } else {
                        Toast.makeText(
                                ExerciseListActivity.this,
                                "failed to load exercises",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                ActionHelper.getDefaultErrorAction(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    public class Adapter extends RealmBasedRecyclerViewAdapter<RealmExercise, Adapter.ViewHolder> {

        public Adapter() {
            super(ExerciseListActivity.this, null, false, false);
        }

        public class ViewHolder extends RealmViewHolder {

            private ExerciseItemView exerciseItemView;
            private Subscription subscription;

            public ViewHolder(ExerciseItemView exerciseItemView) {
                super(exerciseItemView);
                this.exerciseItemView = exerciseItemView;
            }
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(new ExerciseItemView(viewGroup.getContext()));
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            if (viewHolder.subscription != null && !viewHolder.subscription.isUnsubscribed()) {
                viewHolder.subscription.unsubscribe();
                viewHolder.subscription = null;
            }

            RealmExercise realmExercise = realmResults.get(position);
            viewHolder.exerciseItemView.bind(realmExercise);
            viewHolder.exerciseItemView.setOnClickListener(
                    view -> {
                        if (viewHolder.subscription != null) {
                            return;
                        }

                        if (viewHolder.subscription == null &&
                                cacheManager.hasVideo(realmExercise.getVideoUrl())) {
                            Intent intent = new Intent(
                                    ExerciseListActivity.this,
                                    ExerciseViewerActivity.class);
                            intent.putExtra(Constants.EXTRA_VIDEO_URL, realmExercise.getVideoUrl());
                            startActivity(intent);
                        } else {
                            viewHolder.subscription = cacheManager.loadVideo(
                                    realmExercise.getVideoUrl(),
                                    new Action1<VideoManager.ProgressUpdate>() {
                                        @Override
                                        public void call(VideoManager.ProgressUpdate progressUpdate) {
                                            if (progressUpdate.finished && progressUpdate.path != null) {
                                                viewHolder.subscription = null;
                                                viewHolder.exerciseItemView.updateForPlay();
                                            } else if (!progressUpdate.finished) {
                                                viewHolder.exerciseItemView
                                                        .updateForDownloadProgress(progressUpdate);
                                            } else {
                                                Toast.makeText(ExerciseListActivity.this, "error with video", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    },
                                    new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            viewHolder.subscription = null;
                                            Toast.makeText(ExerciseListActivity.this, "fatal error with video", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
            );
        }
    }
}
