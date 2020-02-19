package com.example.hijazitransport.model;

public class Places {
    private String irbid_to_amman,amman_to_irbid,yarmouk_to_amman,amman_to_yarmouk;

    public Places(String irbid_to_amman, String amman_to_irbid, String yarmouk_to_amman, String amman_to_yarmouk) {
        this.irbid_to_amman = irbid_to_amman;
        this.amman_to_irbid = amman_to_irbid;
        this.yarmouk_to_amman = yarmouk_to_amman;
        this.amman_to_yarmouk = amman_to_yarmouk;
    }

    public String getIrbid_to_amman() {
        return irbid_to_amman;
    }

    public void setIrbid_to_amman(String irbid_to_amman) {
        this.irbid_to_amman = irbid_to_amman;
    }

    public String getAmman_to_irbid() {
        return amman_to_irbid;
    }

    public void setAmman_to_irbid(String amman_to_irbid) {
        this.amman_to_irbid = amman_to_irbid;
    }

    public String getYarmouk_to_amman() {
        return yarmouk_to_amman;
    }

    public void setYarmouk_to_amman(String yarmouk_to_amman) {
        this.yarmouk_to_amman = yarmouk_to_amman;
    }

    public String getAmman_to_yarmouk() {
        return amman_to_yarmouk;
    }

    public void setAmman_to_yarmouk(String amman_to_yarmouk) {
        this.amman_to_yarmouk = amman_to_yarmouk;
    }
}
