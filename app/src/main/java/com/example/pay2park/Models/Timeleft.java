package com.example.pay2park.Models;

import android.widget.TextView;

public class Timeleft {

    String timeleft;

    public Timeleft(String time) {
        this.timeleft = time;
    }

    public Timeleft() {
    }

    public String getTime() {
        return timeleft;
    }

    public void setTime(String time) {
        this.timeleft = time;
    }
}
