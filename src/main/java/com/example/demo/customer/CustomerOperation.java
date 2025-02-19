package com.example.demo.customer;

import com.example.demo.model.Booking;
import com.example.demo.model.Centre;
import com.example.demo.model.Timing;
import com.example.demo.model.Workout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomerOperation {

    private final Map<String, List<Workout>> centreWorkouts;
    private final Map<String, List<Booking>> bookedDetails;

    public CustomerOperation(final Map<String, List<Workout>> centreWorkouts) {
        this.bookedDetails = new HashMap<>();
        this.centreWorkouts = centreWorkouts;
    }

    public Map<String, List<Booking>> getBookedDetails() {
        return bookedDetails;
    }

    public List<Workout> viewWorkoutAvailability(final String activity) {
        final List<Workout> result = new ArrayList<>();
        for (final List<Workout> workouts : centreWorkouts.values()) {
            for (final Workout workout : workouts) {
                if (workout.getActivity().equals(activity)) {
                    result.add(workout);
                }
            }
        }
        result.sort(Comparator.comparingInt(o -> o.getTiming().getStartTime()));
        return result;
    }

    public void bookSession(final String customerName, final String centreName, final String activity, final int startTime, final int endTime) {
        final Workout workout = centreWorkouts.get(centreName).stream()
                .filter(workout1 -> workout1.getActivity().equals(activity))
                .filter(workout1 -> workout1.getAvailableSlots() > 0)
                .filter(workout1 -> workout1.getTiming().getStartTime() <= startTime && workout1.getTiming().getEndTime() >= endTime)
                .findFirst()
                .orElseThrow();
        workout.setAvailableSlots(workout.getAvailableSlots()-1);
        final Booking booking = new Booking(customerName, centreName, activity, new Timing(startTime, endTime));
        final List<Booking> bookings = Optional.ofNullable(bookedDetails.get(customerName))
                        .orElse(new ArrayList<>());
        bookings.add(booking);
        bookedDetails.put(customerName, bookings);
    }

    public void cancelSession(final String customerName, final String centreName, final String activity, final int startTime, final int endTime) {
        final Workout workout = centreWorkouts.get(centreName).stream()
                .filter(workout1 -> workout1.getActivity().equals(activity))
                .findFirst()
                .orElseThrow();
        workout.setAvailableSlots(workout.getAvailableSlots()+1);
        final Booking booking = new Booking(customerName, centreName, activity, new Timing(startTime, endTime));
        Optional.ofNullable(bookedDetails.get(customerName))
                .ifPresent(bookings -> bookings.remove(booking));
    }
}
