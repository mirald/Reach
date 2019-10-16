package com.example.erikh.reach;

import java.util.ArrayList;

public class UserInfo {

    static UserInfo the_list = new UserInfo();

    public static UserInfo getInstance(){
        return the_list;
    }


    int runKm = 0;

    public ArrayList<PassedRuns> listPassedRuns = new ArrayList<PassedRuns>();

    PassedRuns Run1 = new PassedRuns("Inner city run", "23km", "22 min");



    public int getRunKm (){
        return runKm;
    }


    private UserInfo(){

        listPassedRuns.add(Run1);

    }

    public ArrayList<PassedRuns> getListPassedRuns() {
        return listPassedRuns;
    }
}
