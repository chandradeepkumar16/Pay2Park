package com.example.pay2park;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Addressdata {
    private String locality;
    private String address;
    private String parking;

    public Addressdata() {
    }

    public Addressdata(String locality, String address, String parking) {
        this.locality = locality;
        this.address = address;
        this.parking=parking;
    }

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }

    public String getParking(){ return parking; }
}
