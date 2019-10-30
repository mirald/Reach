package com.example.erikh.reach;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.util.Map;

public class CheckpointDatabase {
    private static HashMap<String, Checkpoint> checkpointMap = new HashMap<>();
    private static CheckpointDatabase INSTANCE;

    private CheckpointDatabase(HashMap<String, Checkpoint> map){
        map.put("mirandas kort",  new Checkpoint("Kuggen", 57.706525f, 11.939051f, "52 d0 25" +
                " 63"));
        map.put("blue tag", new Checkpoint("Ericsson", 57.704732f, 11.941937f, "3e a8 82 b9"));
        map.put("154", new Checkpoint("Navet", 57.707033f, 11.941002f, "fa b9 be 44"));
        map.put("lindholmen", new Checkpoint("Linnéplatsen", 57.690371f, 11.951407f, "6e 39 2c e1"));
        map.put("nordstan", new Checkpoint("Dufvas Backe", 57.685522f, 11.943751f, "6e 8d a3 0f"));
        map.put("eriks kort", new Checkpoint("Dovhjortsstigen", 57.688090f, 11.940349f, "32 f9 12 63"));
        map.put("jens kort", new Checkpoint("Bangatan", 57.690230f, 11.939141f, "62 94 dc 27"));
        map.put("johans kort", new Checkpoint("Pustervik", 57.701625f, 11.954912f, "f2 a7 21 63"));
        map.put("adams kort", new Checkpoint("Lilla bommen", 57.711406f, 11.963881f, "f2 0e 23 63"));

        this.checkpointMap = map;

    }

    public static CheckpointDatabase getCheckpointDatabase(){
        if(INSTANCE == null){
            INSTANCE = new CheckpointDatabase(checkpointMap);
        }
        return INSTANCE;
    }

    public Checkpoint getCheckpointFromName(String name){
        // if null, then there is no checkpoint found with that name
        return checkpointMap.get(name);
    }

    public Checkpoint getCheckpointFromSerial(String serialNumber){
        String checkpointSerial;
        Checkpoint checkpoint = null;
        for(String key: checkpointMap.keySet()){
            Checkpoint current = checkpointMap.get(key);
            checkpointSerial = current.getSerialNumber();
            if(checkpointSerial.equals(serialNumber)){
                checkpoint = current;
                break;
            }
        }
        return checkpoint;
    }

    public ArrayList<Checkpoint> getAllCheckpoints(){
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        for(String key: checkpointMap.keySet()){
            checkpoints.add(checkpointMap.get(key));
        }
        return checkpoints;
    }

    public ArrayList<Checkpoint> getSpecificCheckpoints(String[] keyArray){
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        for(String key: keyArray){
            checkpoints.add(checkpointMap.get(key));
        }
        return checkpoints;
    }

    public Checkpoint getClosestCheckpoint(float[] coords, Run run){
        double yCoord, xCoord;
        double closestDist = -1;
        double distance;
        String closestKey = "";
        Checkpoint temp;

        ArrayList<Checkpoint> tempChList = run.getCheckpoints();
        HashMap<String, Checkpoint> tempchekpointMap =  new HashMap<>(checkpointMap);
        tempchekpointMap.clear();
        for (int i=0; i < tempChList.size(); i++){
            tempchekpointMap.put("i" + i, tempChList.get(i));
        }



        for(String key: tempchekpointMap.keySet()){
            temp = tempchekpointMap.get(key);
            yCoord = Math.abs(temp.getY()-coords[1]);
            xCoord = Math.abs(temp.getX()-coords[0]);
            distance = Math.hypot(xCoord, yCoord);

            if(closestDist<0){
                closestDist = distance;
                closestKey = key;
            }

            if(distance<closestDist){
                closestDist = distance;
                closestKey = key;
            }
        }

        return tempchekpointMap.get(closestKey); // returns null if there are no checkpoints
    }




}
