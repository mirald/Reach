package com.example.reach;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class run {
    private String n;
    private ArrayList<String> c = new ArrayList<String>();
    private Date e = new Date();
    private String l;
    private  ArrayList<Date> f = new ArrayList<Date>();

    private DateFormat formatter = new SimpleDateFormat("hh:mm:ss:SS");

    public run (String name, String[] checkpoints, String location, String estimate) {
        n = name;
        l = location;

        for (String s : checkpoints) {
            c.add(s);
        }

        try {
            e = formatter.parse(estimate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public String getName () {
        return n;
    }

    public ArrayList<String> getCheckpoints() {
        return c;
    }

    public Date getEstimateAsDate() {
        return e;
    }

    public String getEstimateAsString() {
        return e.toString();
    }

    public String getLocation() {
        return l;
    }

    public Boolean addFinishTime(String FinishTime) {
        Date timeToAdd;
        try {
            timeToAdd =formatter.parse(FinishTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        f.add(timeToAdd);
        return true;
    }

    public ArrayList<Date> getFinishTimesAsDates() {
        return f;
    }

    public ArrayList<String> getFinishTimesAsStrings() {
        ArrayList<String> returnList = new ArrayList<String>();
        for (Date d: f) {
            returnList.add(d.toString());
        }
        return returnList;
    }
}
