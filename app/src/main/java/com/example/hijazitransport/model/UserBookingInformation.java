package com.example.hijazitransport.model;

public class UserBookingInformation {
    private String from,to,date,time,payment,numberOfPassenger;

    public UserBookingInformation() {
    }

    public UserBookingInformation(String from, String to, String date, String time, String payment, String numberOfPassenger) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.payment = payment;
        this.numberOfPassenger = numberOfPassenger;
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
}
