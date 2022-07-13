package com.example.pay2park;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Addressdata {
    private String locality;
    private String address;

    public Addressdata() {
    }

    public Addressdata(String locality, String address) {
        this.locality = locality;
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }
}
