package com.example.erikh.reach;

public class UserInfo {

    static UserInfo the_list = new UserInfo();

    public static UserInfo getInstance(){
        return the_list;
    }


    int runKm = 0;

    public int getRunKm (){
        return runKm;
    }





}
