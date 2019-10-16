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
        map.put("mirandas kort",  new Checkpoint("Kuggen", 57.706301f, 11.939255f, "52 d0 25" +
                " 63"));
        map.put("blue tag", new Checkpoint("Piren", 57.704536f, 11.941009f, "3e a8 82 b9"));
        map.put("154", new Checkpoint("Ericsson", 57.705975f, 11.941867f, "fa b9 be 44"));
        map.put("lindholmen", new Checkpoint("Linnéplatsen", 57.690371f, 11.951407f, "6e 39 2c e1"));
        map.put("nordstan", new Checkpoint("Slottsskogen1", 57.684397f, 11.947325f, "6e 8d a3 0f"));
        map.put("eriks kort", new Checkpoint("Slottsskogen2", 57.684528f, 11.937109f, "32 f9 12 63"));
        map.put("jens kort", new Checkpoint("Slottsskogen3", 57.690230f, 11.939141f, "62 94 dc 27"));
        map.put("johans kort", new Checkpoint("Färjeterminal", 57.701405f, 8.0f, "f2 a7 21 63"));
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

    public Checkpoint getClosestCheckpoint(float[] coords){
        double yCoord, xCoord;
        double closestDist = -1;
        double distance;
        String closestKey = "";
        Checkpoint temp;

        for(String key: checkpointMap.keySet()){
            temp = checkpointMap.get(key);
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

        return checkpointMap.get(closestKey); // returns null if there are no checkpoints
    }




}
