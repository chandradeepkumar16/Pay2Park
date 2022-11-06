package com.example.pay2park.Models;

public class parkingiduser {

    private  String id;
    private String dp_hashval;

    public parkingiduser(String id, String dp_hashval) {
        this.id = id;
        this.dp_hashval = dp_hashval;
    }

    public parkingiduser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDp_hashval() {
        return dp_hashval;
    }

    public void setDp_hashval(String dp_hashval) {
        this.dp_hashval = dp_hashval;
    }

    //    public parkingiduser(){}
//
//    public parkingiduser(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

}
