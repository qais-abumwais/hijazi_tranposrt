package com.example.hijazitransport.model;

public class UserRegisterData {
    String name,email,address,phoneNumber,birthdate,gender,hijaziCardd;

    public UserRegisterData() {
    }

    public UserRegisterData(String name, String email, String address, String phoneNumber, String birthdate, String gender,  String hijaziCardd) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.gender = gender;
        this.hijaziCardd=hijaziCardd;
    }

    public String getHijaziCardd() {
        return hijaziCardd;
    }

    public void setHijaziCardd(String hijaziCardd) {
        this.hijaziCardd = hijaziCardd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



}
