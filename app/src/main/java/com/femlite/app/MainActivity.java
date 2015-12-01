package com.femlite.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.femlite.app.manager.DataManager;
import com.femlite.app.manager.UiStorageHelper;
import com.femlite.app.model.parse.ParseWorkout;
import com.femlite.app.model.realm.RealmWorkout;
import com.femlite.app.views.WorkoutItemView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FemliteActivity {

    @Bind(R.id.workout_recycler_view)
    RecyclerView recyclerView;

    private Adapter adapter;

    @Inject
    DataManager dataManager;

    @Inject
    UiStorageHelper uiStorageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getComponent().inject(this);
        uiStorageHelper.onCreate();

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        dataManager.getWorkouts(
                fetched -> {
                    if (fetched) {
                        RealmResults<RealmWorkout> workouts = uiStorageHelper.getWorkouts();
                        adapter.addItems(workouts);
                    } else {
                        Toast.makeText(MainActivity.this, "failed to load workouts", Toast.LENGTH_SHORT).show();
                    }
                },
                throwable -> Toast.makeText(MainActivity.this, "failed to load workouts", Toast.LENGTH_SHORT).show());


//        final ParseQueryAdapter<Pa> adapterParse =
//                new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
//                    public ParseQuery<ParseObject> create() {
//                        // Here we can configure a ParseQuery to our heart's desire.
//                        ParseQuery query = new ParseQuery("Workout");
////                        query.whereContainedIn("genre", Arrays.asList({"Punk", "Metal"}));
////                        query.whereGreaterThanOrEqualTo("memberCount", 4);
////                        query.orderByDescending("albumsSoldCount");
//                        query.orderByAscending("name");
////                        query.include("occupants");
////                        query.include("livein");
//                        return query;
//                    }
//                });
//        adapterParse.addOnQueryLoadListener(
//                new ParseQueryAdapter.OnQueryLoadListener<ParseObject>() {
//                    @Override
//                    public void onLoading() {
//
//                    }
//
//                    @Override
//                    public void onLoaded(List<ParseObject> objects, Exception e) {
////                        final TextView viewById = (TextView) MainActivity.this.findViewById(R.id.text_out);
////                        viewById.setText("Num: " + objects.size());
//                        adapter.addItems(objects);
//                    }
//                }
//        );
//        adapterParse.loadObjects();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiStorageHelper.onDestroy();
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {

            private WorkoutItemView workoutItemView;

            public ViewHolder(WorkoutItemView workoutItemView) {
                super(workoutItemView);
                this.workoutItemView = workoutItemView;
            }
        }

        private List<RealmWorkout> items;

        public Adapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new WorkoutItemView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.workoutItemView.bind(items.get(position));
            holder.workoutItemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent =
                                    new Intent(MainActivity.this, WorkoutDetailActivity.class);
                            intent.putExtra("workoutId", items.get(position).getId());
                            startActivity(intent);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItems(RealmResults<RealmWorkout> workouts) {
            this.items = workouts;
            notifyDataSetChanged();
        }
    }
}
