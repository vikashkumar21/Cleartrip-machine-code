package com.example.demo.model;


import java.util.List;

public class Centre {
    private String name;
    private List<Timing> timings;
    private List<String> activities;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTimings(final List<Timing> timings) {
        this.timings = timings;
    }

    public List<Timing> getTimings() {
        return timings;
    }

    public void setActivities(final List<String> activities) {
        this.activities = activities;
    }

    public List<String> getActivities() {
        return activities;
    }
}