package com.example.pay2park;

import java.io.Serializable;

public class Sellertimedata implements Serializable {

    private String sellerstarttime;
    private String sellerstoptime;

    public Sellertimedata() {
    }

    public Sellertimedata(String sellerstarttime, String sellerstoptime) {
        this.sellerstarttime = sellerstarttime;
        this.sellerstoptime = sellerstoptime;
    }

    public String getSellerstarttime() {
        return sellerstarttime;
    }

    public String getSellerstoptime() {
        return sellerstoptime;
    }
}
