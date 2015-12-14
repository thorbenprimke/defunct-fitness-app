package com.femlite.app.manager;

import com.femlite.app.model.Exercise;
import com.femlite.app.model.Workout;
import com.femlite.app.model.parse.ParseExercise;
import com.femlite.app.model.parse.ParseWorkout;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Manager to handle any REST/Parse requests.
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
    }

    public Workout fetchWorkout(String workoutKey) throws ParseException {
        ParseQuery<ParseWorkout> parseQuery = ParseQuery.getQuery(ParseWorkout.class);
        parseQuery.whereEqualTo("Key", workoutKey);
        return parseQuery.getFirst();
    }

    public List<Exercise> fetchExercises(String workoutKey) throws ParseException {
        ParseQuery<ParseExercise> query = ParseQuery.getQuery(ParseExercise.class);
        query = query.whereEqualTo("WorkoutKey", workoutKey);
        query = query.addAscendingOrder("Order");
        List<ParseExercise> parseExercises = query.find();

        List<Exercise> exercises = new ArrayList<>(parseExercises.size());
        for (ParseExercise parseExercise : parseExercises) {
            exercises.add(parseExercise);
        }
        return exercises;
    }
}
