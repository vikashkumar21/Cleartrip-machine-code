package com.example.demo.model;

public class Timing {

    private int startTime;
    private int endTime;

    public Timing(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}