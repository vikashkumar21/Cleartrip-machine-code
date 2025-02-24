package com.example.demo.service;

import com.example.demo.dao.CentreDao;
import com.example.demo.dao.WorkoutDao;
import com.example.demo.exception.ActivityNotSupportedException;
import com.example.demo.exception.CentreDoesNotExistException;
import com.example.demo.exception.TimingMismatchException;
import com.example.demo.model.Centre;
import com.example.demo.model.Timing;
import com.example.demo.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class AdminWorkoutService {

    private final CentreDao centreDao;
    private final WorkoutDao workoutDao;

    public AdminWorkoutService(final CentreDao centreDao,
                               final WorkoutDao workoutDao) {
        this.centreDao = centreDao;
        this.workoutDao = workoutDao;
    }

    public void addWorkout(final String centreName,
                           final String activity,
                           final int startTime,
                           final int endTime,
                           final int availableSlots) {

        final Centre centre = Optional.ofNullable(centreDao.getCentre(centreName))
                .orElseThrow(() ->
                        new CentreDoesNotExistException(format("Centre %s not found", centreName)));

        centre.getActivities().stream()
                .filter(activity::equals)
                .findFirst()
                .orElseThrow(() ->
                        new ActivityNotSupportedException(format("Activity %s not supported at centre %s", activity, centreName)));

        centre.getTimings().stream()
                .filter(timing -> timing.getStartTime() <= startTime && timing.getEndTime() >= endTime)
                .findFirst()
                .orElseThrow(() ->
                        new TimingMismatchException(format("Timing %s-%s not supported for activity %s at centre %s", startTime, endTime, activity, centreName)));

        final Workout workout = Workout.newBuilder()
                .setCentreName(centreName)
                .setActivity(activity)
                .setTiming(new Timing(startTime, endTime))
                .setAvailableSlots(availableSlots)
                .build();

        final List<Workout> workouts = Optional.ofNullable(workoutDao.centreWorkouts(centreName))
                .orElse(new ArrayList<>());
        workouts.add(workout);
        workoutDao.addWorkouts(centreName, workouts);
    }
}
