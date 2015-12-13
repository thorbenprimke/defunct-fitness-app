package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.misc.Constants;
import com.femlite.app.model.realm.RealmWorkout;
import com.femlite.app.views.WorkoutItemView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class WorkoutMainActivity extends FemliteDrawerActivity {

    @Bind(R.id.workout_recycler_view)
    RealmRecyclerView recyclerView;

    @Inject
    DataManager dataManager;

    @Inject
    UiStorageHelper uiStorageHelper;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getComponent().inject(this);
        uiStorageHelper.onCreate();

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        dataManager.getWorkouts(
                fetched -> {
                    if (fetched) {
                        RealmResults<RealmWorkout> workouts = uiStorageHelper.getWorkouts();
                        adapter.updateRealmResults(workouts);
                    } else {
                        Toast.makeText(
                                WorkoutMainActivity.this,
                                "failed to load workouts",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                throwable -> Toast.makeText(
                        WorkoutMainActivity.this,
                        "failed to load workouts",
                        Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.workout_list_layout;
    }

    public class Adapter extends RealmBasedRecyclerViewAdapter<RealmWorkout, Adapter.ViewHolder> {

        public Adapter() {
            super(WorkoutMainActivity.this, null, false, false);
        }

        public class ViewHolder extends RealmViewHolder {

            private WorkoutItemView workoutItemView;

            public ViewHolder(WorkoutItemView workoutItemView) {
                super(workoutItemView);
                this.workoutItemView = workoutItemView;
            }
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(new WorkoutItemView(viewGroup.getContext()));
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.workoutItemView.bind(realmResults.get(position));
            viewHolder.workoutItemView.setOnClickListener(
                    view -> {
                        Intent intent =
                                new Intent(WorkoutMainActivity.this, WorkoutDetailActivity.class);
                        intent.putExtra(
                                Constants.EXTRA_WORKOUT_KEY,
                                realmResults.get(position).getKey());
                        startActivity(intent);
                    }
            );
        }
    }
}
