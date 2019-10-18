package com.example.erikh.reach;

import java.util.ArrayList;

public class UserInfo {

    static UserInfo the_list = new UserInfo();

    public static UserInfo getInstance(){
        return the_list;
    }

    public ArrayList<PassedRuns> listPassedRuns = new ArrayList<PassedRuns>();

    PassedRuns Run1 = new PassedRuns("Slottsskogen", "23 km", "22 min");


    //Used to set the Header on the profile view
    public int getRunKm (){
        //return runKm;
        int tempKm = 0;
        for (PassedRuns pr : listPassedRuns){
            //Gets all the ints from the length string
            tempKm += Integer.parseInt(pr.length.replaceAll("\\D+",""));
        }
        return tempKm;
    }


    private UserInfo(){

        listPassedRuns.add(Run1);
        //Adds a shit ton of runs
        /*
        for(int i = 0; i < 9; i++){
            listPassedRuns.add(new PassedRuns("Run " + i, i + 12 + "km", i + 10 + " min"));
        }
        */


    }

    public ArrayList<PassedRuns> getListPassedRuns() {
        return listPassedRuns;
    }
}
