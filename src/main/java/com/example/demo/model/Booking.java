package com.example.demo.model;

import java.util.Objects;

public class Booking {

    private final String userName;
    private final String centreName;
    private final String activity;
    private final Timing timing;

    public Booking(final String userName,
                   final String centreName,
                   final String activity,
                   final Timing timing) {
        this.userName = userName;
        this.centreName = centreName;
        this.activity = activity;
        this.timing = timing;
    }

    public String getUserName() {
        return userName;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Booking booking = (Booking) o;
        return Objects.equals(userName, booking.userName) && Objects.equals(centreName, booking.centreName) && Objects.equals(activity, booking.activity) && Objects.equals(timing, booking.timing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, centreName, activity, timing);
    }
}
