package com.example.demo.model;

public class Workout {

    private String centreName;
    private String activity;
    private Timing timing;
    private int availableSlots;

    public String getCentreName() {
        return centreName;
    }

    public Workout setCentreName(final String centreName) {
        this.centreName = centreName;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public Workout setActivity(final String activity) {
        this.activity = activity;
        return this;
    }

    public Timing getTiming() {
        return timing;
    }

    public Workout setTiming(final Timing timing) {
        this.timing = timing;
        return this;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public Workout setAvailableSlots(final int availableSlots) {
        this.availableSlots = availableSlots;
        return this;
    }
}
