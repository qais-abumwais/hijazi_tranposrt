package com.example.hijazitransport.model;

import java.util.List;

public class TripScheduleModel {
    String title,times;

    public TripScheduleModel(String title, String times) {
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
