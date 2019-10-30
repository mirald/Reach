package com.example.erikh.reach;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserInfo {

    static UserInfo the_list = new UserInfo();

    public static UserInfo getInstance(){
        return the_list;
    }

    public static ArrayList<PassedRuns> listPassedRuns;

    PassedRuns Run1 = new PassedRuns("Slottsskogen", "22 m 0 s", new Date(2019, 10, 20));


//    //Used to set the Header on the profile view
//    public int getRunKm (){
//        //return runKm;
//        int tempKm = 0;
//        for (PassedRuns pr : listPassedRuns){
//            //Gets all the ints from the length string
//            tempKm += Integer.parseInt(pr.length.replaceAll("\\D+",""));
//        }
//        return tempKm;
//    }


    private UserInfo(){

        listPassedRuns = new ArrayList<PassedRuns>();

        addListPassedRuns(Run1);
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

    public void addListPassedRuns(PassedRuns p) {
        listPassedRuns.add(0, p);
    }
}
