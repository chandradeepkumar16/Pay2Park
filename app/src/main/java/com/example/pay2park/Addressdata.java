package com.example.pay2park;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Addressdata implements Serializable {
    private String locality;
    private String address;
    private String parking;
    private String price;
    private String id;


    public Addressdata() {
    }

    public Addressdata(String locality, String address, String parking, String price, String id) {
        this.locality = locality;
        this.address = address;
        this.parking=parking;
        this.price=price;
        this.id=id;
    }

    public String getLocality() {
        return locality;
    }

    public String getAddress() {
        return address;
    }

    public String getParking(){ return parking; }

    public String getPrice(){ return price; }

    public String getId(){ return id; }
}

