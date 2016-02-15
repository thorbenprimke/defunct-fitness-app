package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.manager.UserManager;
import com.femlite.app.misc.Constants;
import com.femlite.app.model.realm.RealmWorkout;
import com.femlite.app.views.WorkoutItemView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tapglue.Tapglue;
import com.tapglue.model.TGEvent;
import com.tapglue.model.TGEventObject;
import com.tapglue.model.TGFeed;
import com.tapglue.model.TGUser;
import com.tapglue.networking.requests.TGRequestCallback;
import com.tapglue.networking.requests.TGRequestErrorType;

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
    UserManager userManager;

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

        Tapglue.user().createAndLoginUserWithUsernameAndMail(
                userManager.getUser().getUsername(),
                userManager.getUser().getUsername(),
                userManager.getUser().getUsername() + "@testing.com",
                new TGRequestCallback<Boolean>() {
                    @Override
                    public boolean callbackIsEnabled() {
                        return true;
                    }

                    @Override
                    public void onRequestError(TGRequestErrorType cause) {
                        Log.d("tapglue", cause.getMessage());
                    }

                    @Override
                    public void onRequestFinished(Boolean output, boolean changeDoneOnline) {
                        Log.d("tapglue", output + "");

                        JsonElement jsonObject = new JsonPrimitive("some string");
//                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.addProperty("testing", "good stuff");

                        TGEventObject tgEventObject = new TGEventObject();
                        tgEventObject.setUrl("http://www.femlite.com");
                        tgEventObject.setID("1243");

                        TGEvent event = new TGEvent()
                                .setType("like")
                                .setLanguage("en")
                                .setLocation("berlin")
                                .setPriority("1")
                                .setVisibility(TGEvent.TGEventVisibility.Global)
                                .setObject(tgEventObject)
                                .setMetadata(jsonObject);

                        Tapglue.event().createEvent(event, new TGRequestCallback<TGEvent>() {
                            @Override
                            public boolean callbackIsEnabled() {
                                return true;
                            }

                            @Override
                            public void onRequestError(TGRequestErrorType cause) {
                                Log.d("tapglue", cause.getMessage());

                            }

                            @Override
                            public void onRequestFinished(TGEvent output, boolean changeDoneOnline) {
                                Log.d("tapglue", output + "");
                            }
                        });

                        Tapglue.feed().retrieveEventsForCurrentUser(
                                new TGRequestCallback<TGFeed>() {
                                    @Override
                                    public boolean callbackIsEnabled() {
                                        return true;
                                    }

                                    @Override
                                    public void onRequestError(TGRequestErrorType cause) {

                                    }

                                    @Override
                                    public void onRequestFinished(TGFeed output, boolean changeDoneOnline) {
                                        Log.d("tapglue", output + "");
                                    }
                                }
                        );
//
                        Tapglue.feed().retrieveFeedForCurrentUser(
                                new TGRequestCallback<TGFeed>() {
                                    @Override
                                    public boolean callbackIsEnabled() {
                                        return true;
                                    }

                                    @Override
                                    public void onRequestError(TGRequestErrorType cause) {

                                    }

                                    @Override
                                    public void onRequestFinished(TGFeed output, boolean changeDoneOnline) {
                                        Log.d("tapglue", output + "");
                                    }
                                }
                        );
                    }
                }
        );



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    @Override
    public int getContentLayoutResId() {
        return R.layout.workout_main_layout;
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
