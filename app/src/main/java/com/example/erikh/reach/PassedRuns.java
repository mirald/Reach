package com.example.erikh.reach;

import java.util.Date;

public class PassedRuns {

    String name;
    String time;
    Date date;





    public PassedRuns(String name, String time, Date date) {
        this.name = name;
        this.time = time;
        this.date = date;

    }

    public PassedRuns() {

        this.name = null;
        this.time = null;
        this.date = null;
    }

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

    public String getDate(){
        String day = Integer.toString(date.getDate());
        String month = Integer.toString(date.getMonth());
        String year = Integer.toString(date.getYear());
        return day + "/" + month + "/" + year;
    }

    public void setDate(Date date) {
        this.date = date;
    }



}
