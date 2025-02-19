package com.example.demo.admin;

import com.example.demo.model.Centre;
import com.example.demo.model.Timing;
import com.example.demo.model.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CentreWorkout {

    private final Map<String, Centre> onboardedCentres;
    private final Map<String, List<Workout>> centreWorkouts;

    public CentreWorkout(final Map<String, Centre> onboardedCentres) {
        centreWorkouts = new HashMap<>();
        this.onboardedCentres = onboardedCentres;
    }

    public void addWorkout(final String centreName,
                           final String activity,
                           final int startTime,
                           final int endTime,
                           final int availableSlots) {

        final Centre centre = Optional.ofNullable(onboardedCentres.get(centreName)).orElseThrow();
        centre.getActivities().stream()
                .filter(activity::equals)
                .findFirst()
                .orElseThrow();
        centre.getTimings().stream()
                .filter(timing -> timing.getStartTime() <= startTime && timing.getEndTime() >= endTime)
                .findFirst()
                .orElseThrow();
        final Workout workout = new Workout()
                .setCentreName(centreName)
                .setActivity(activity)
                .setTiming(new Timing(startTime, endTime))
                .setAvailableSlots(availableSlots);
        final List<Workout> workouts = Optional.ofNullable(centreWorkouts.get(centreName))
                .orElse(new ArrayList<>());
        workouts.add(workout);
        centreWorkouts.put(centreName, workouts);
    }
}
