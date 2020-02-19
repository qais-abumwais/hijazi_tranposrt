package com.example.hijazitransport.model;

public class UserRegisterData {
    private String name,email,address,phoneNumber,birthdate,gender;
    private CardNumber hijaziCard;

    public UserRegisterData() {
    }

    public UserRegisterData(String name, String email, String address, String phoneNumber, String birthdate, String gender, CardNumber hijaziCard) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.gender = gender;
        this.hijaziCard = hijaziCard;
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

    public CardNumber getHijaziCard() {
        return hijaziCard;
    }

    public void setHijaziCard(CardNumber hijaziCard) {
        this.hijaziCard = hijaziCard;
    }
}
