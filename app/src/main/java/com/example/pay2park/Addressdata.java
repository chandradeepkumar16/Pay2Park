package com.example.pay2park;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Addressdata implements Serializable {
    private String locality;
    private String address;
    private String parking;
    private String price;


    public Addressdata() {
    }

    public Addressdata(String locality, String address, String parking, String price) {
        this.locality = locality;
        this.address = address;
        this.parking=parking;
        this.price=price;
    }

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }

    public String getParking(){ return parking; }

    public String getPrice(){ return price; }
}

