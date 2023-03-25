package com.example.pay2park.Models;

import java.io.Serializable;

public class Sellertimedata{

    private String sellerstarttime;
    private String sellerstoptime;

    public Sellertimedata() {
    }



    public String getSellerstarttime() {
        return sellerstarttime;
    }

    public void setSellerstarttime(String sellerstarttime) {
        this.sellerstarttime = sellerstarttime;
    }

    public String getSellerstoptime() {
        return sellerstoptime;
    }

    public void setSellerstoptime(String sellerstoptime) {
        this.sellerstoptime = sellerstoptime;
    }
}
