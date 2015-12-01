package com.femlite.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareOpenGraphAction;
import com.femlite.app.model.parse.ParseWorkout;
import com.femlite.app.views.WorkoutItemView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FemliteActivity {

    @Bind(R.id.workout_recycler_view)
    RecyclerView recyclerView;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();


        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery = parseQuery.orderByAscending("name");
        parseQuery.findInBackground(
                (workouts, error) -> {
                    if (workouts == null) {
                        Toast.makeText(MainActivity.this, "failed to load workouts", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.addItems(workouts);
                });

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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {

            private WorkoutItemView workoutItemView;

            public ViewHolder(WorkoutItemView workoutItemView) {
                super(workoutItemView);
                this.workoutItemView = workoutItemView;
            }
        }

        private List<ParseWorkout> items;

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
                            intent.putExtra("workoutId", items.get(position).getObjectId());
                            startActivity(intent);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItems(List<ParseWorkout> objects) {
            this.items.addAll(objects);
            notifyDataSetChanged();
        }
    }
}
