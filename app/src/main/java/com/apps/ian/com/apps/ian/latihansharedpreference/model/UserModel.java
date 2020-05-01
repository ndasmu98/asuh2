package com.apps.ian.com.apps.ian.latihansharedpreference.model;

/*
    Developed by Ian Herdiansyah - 10117194 - IF5
    on 01-05-2020
 */

public class UserModel {
    private String username;
    private String password;
    private String phone;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
}
