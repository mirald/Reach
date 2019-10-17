package com.example.erikh.reach;

public class PassedRuns {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String name;
    String length;
    String time;





    public PassedRuns(String name, String length, String time) {
        this.name = name;
        this.length = length;
        this.time = time;

    }

    public PassedRuns() {

        this.name = null;
        this.length = null;
        this.time = null;

    }



}
