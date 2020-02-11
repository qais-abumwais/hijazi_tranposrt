package com.example.hijazitransport.model;

public class TripSchedule {
    private String title,times;

    public TripSchedule(String title, String times) {
        this.title = title;
        this.times = times;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
