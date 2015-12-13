package com.femlite.app.manager;

import android.widget.Toast;

import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseWorkout;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by thorben on 11/27/15.
 */
public class NetworkRequestManager {

    @Inject
    public NetworkRequestManager() {
    }


    public List<Workout> fetchWorkouts() throws ParseException {
        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery = parseQuery.orderByAscending("name");
        List<ParseWorkout> parseWorkouts = parseQuery.find();

        List<Workout> workouts = new ArrayList<>(parseWorkouts.size());
        for (ParseWorkout parseWorkout : parseWorkouts) {
            workouts.add(parseWorkout);
        }

        return workouts;

//                (workouts, error) -> {
//                    if (workouts == null) {
//                        Toast.makeText(WorkoutMainActivity.this, "failed to load workouts", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    adapter.addItems(workouts);
//                });
    }

    public Workout fetchWorkout(String workoutKey) throws ParseException {
        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("Key", workoutKey);
        return parseQuery.getFirst();
    }
}
