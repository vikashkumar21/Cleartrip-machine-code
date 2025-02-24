package com.example.demo.model;

public class Workout {

    private final String centreName;
    private final String activity;
    private final Timing timing;
    private int availableSlots;

    private Workout(final String centreName,
                    final String activity,
                    final Timing timing,
                    final int availableSlots) {
        this.centreName = centreName;
        this.activity = activity;
        this.timing = timing;
        this.availableSlots = availableSlots;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String centreName;
        private String activity;
        private Timing timing;
        private int availableSlots;

        public Builder setCentreName(final String centreName) {
            this.centreName = centreName;
            return this;
        }

        public Builder setActivity(final String activity) {
            this.activity = activity;
            return this;
        }

        public Builder setTiming(final Timing timing) {
            this.timing = timing;
            return this;
        }

        public Builder setAvailableSlots(final int availableSlots) {
            this.availableSlots = availableSlots;
            return this;
        }

        public Workout build() {
            return new Workout(centreName, activity, timing, availableSlots);
        }
    }

    public void decrementAvailableSlots() {
        availableSlots--;
    }

    public void incrementAvailableSlots() {
        availableSlots++;
    }

    public String getCentreName() {
        return centreName;
    }

    public String getActivity() {
        return activity;
    }

    public Timing getTiming() {
        return timing;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }
}