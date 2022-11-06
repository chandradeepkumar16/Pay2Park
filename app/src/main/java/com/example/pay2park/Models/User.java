package com.example.pay2park.Models;

public class User {
    public String  fullname , email , password;

    public User(){

    }

    public  User(String fullname , String email , String password){
        this.fullname=fullname;
        this.email=email;
        this.password=password;
    }
}
