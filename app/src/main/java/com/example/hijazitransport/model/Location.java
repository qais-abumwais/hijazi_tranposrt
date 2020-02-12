package com.example.hijazitransport.model;

public class Location {
    private String map,title,phoneOne,phoneTow,workong;

    public Location(String map, String title, String phoneOne, String phoneTow, String workong) {
        this.map = map;
        this.title = title;
        this.phoneOne = phoneOne;
        this.phoneTow = phoneTow;
        this.workong = workong;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTow() {
        return phoneTow;
    }

    public void setPhoneTow(String phoneTow) {
        this.phoneTow = phoneTow;
    }

    public String getWorkong() {
        return workong;
    }

    public void setWorkong(String workong) {
        this.workong = workong;
    }
}
