package com.example.demo.dao;

import com.example.demo.model.Workout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutDao {

    private final Map<String, List<Workout>> centreWorkouts;

    public WorkoutDao() {
        centreWorkouts = new HashMap<>();
    }

    public List<Workout> centreWorkouts(final String centreName) {
        return centreWorkouts.get(centreName);
    }

    public void addWorkouts(final String centreName, final List<Workout> workouts) {
        centreWorkouts.put(centreName, workouts);
    }

    public void decrementAvailableSlots(final String centreName, final String activity) {
        centreWorkouts.get(centreName)
                .stream()
                .filter(workout -> workout.getActivity().equals(activity))
                .findFirst()
                .ifPresent(Workout::decrementAvailableSlots);
    }

    public void incrementAvailableSlots(final String centreName, final String activity) {
        centreWorkouts.get(centreName)
                .stream()
                .filter(workout -> workout.getActivity().equals(activity))
                .findFirst()
                .ifPresent(Workout::incrementAvailableSlots);
    }
}