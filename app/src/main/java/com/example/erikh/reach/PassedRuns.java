package com.example.erikh.reach;

public class PassedRuns {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String name;
    String time;





    public PassedRuns(String name, String time) {
        this.name = name;
        this.time = time;

    }

    public PassedRuns() {

        this.name = null;
        this.time = null;

    }



}
