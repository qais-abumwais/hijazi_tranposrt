package com.example.hijazitransport.model;

public class HijaziBookingInformation {
    private String from,to,date,time,payment,numberOfPassenger;
    private UserRegisterData userRegisterData;

    public HijaziBookingInformation() {
    }

    public HijaziBookingInformation(String from, String to, String date, String time, String payment, String numberOfPassenger,  UserRegisterData userRegisterData) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.payment = payment;
        this.numberOfPassenger = numberOfPassenger;
        this.userRegisterData = userRegisterData;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNumberOfPassenger() {
        return numberOfPassenger;
    }

    public void setNumberOfPassenger(String numberOfPassenger) {
        this.numberOfPassenger = numberOfPassenger;
    }


    public UserRegisterData getUserRegisterData() {
        return userRegisterData;
    }

    public void setUserRegisterData(UserRegisterData userRegisterData) {
        this.userRegisterData = userRegisterData;
    }
}
