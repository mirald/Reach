package com.example.erikh.reach;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Run {
    private String n;
    private ArrayList<Checkpoint> c = new ArrayList<Checkpoint>();
    private Date e = new Date();
    private String l;
    private  ArrayList<Date> f = new ArrayList<Date>();
    private  String p;

    private DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

    public Run(String name, String location, String estimate, String picPath, String[] checkpoints, CheckpointDatabase checkpointDatabase) {

        n = name;
        l = location;
        p = picPath;

        try {
            e = formatter.parse(estimate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        addAllCheckpointsFromDatabase(checkpoints, checkpointDatabase);
    }

    public String getName() {
        return n;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return c;
    }

    public Date getEstimateAsDate() {
        return e;
    }

    public String getEstimateAsString() {

        //Writing the time in a more comprehensive way
        String[] timeArray = formatter.format(e).split(":",0);
        //Remove the SSS part
        timeArray = Arrays.copyOf(timeArray, timeArray.length-1);

        String tempString = "";
        if(!timeArray[0].equals("00")){
            tempString += timeArray[0] + " hours ";
        }
        if(!timeArray[1].equals("00")){
            tempString += timeArray[1] + " min ";
        }
        if(!timeArray[2].equals("00")){
            tempString += timeArray[2] + " sec ";
        }
        return tempString;

        //Jens code
        //return formatter.format(e).toString();
    }

    public String getLocation() {
        return l;
    }

    public String GetPicPath() {
        return p;
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
            returnList.add(formatter.format(d.toString()));
        }
        return returnList;
    }

    public Boolean addNewCheckpoint(String name, float x, float y, String serialNumber) {
        try {
            c.add(new Checkpoint(name, x, y, serialNumber));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        c.add(checkpoint);
    }

    public int getNumberOfCheckpoints() {
        return c.size();
    }

    private void addAllCheckpointsFromDatabase (String[] checkpoints, CheckpointDatabase checkpointDatabase) {
        for (Checkpoint checkpoint :
                checkpointDatabase.getSpecificCheckpoints(checkpoints)) {
            addCheckpoint(checkpoint);
        }
    }

    public static String toString(Run run){
        return "Name: " + run.n + ", Location: " + run.l + ", Estimate: "+ run.e + ", picPath: " + run.p + ", " +
                "Checkpoints: " + run.c;
    }
}
