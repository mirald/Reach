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
        map.put("mirandas kort",  new Checkpoint("Mirandas Kort", 1.0f, 1.0f, "52 d0 25" +
                " 63"));
        map.put("blue tag", new Checkpoint("Blue Tag", 2.0f, 2.0f, "3e a8 82 b9"));
        map.put("154", new Checkpoint("154", 3.0f, 3.0f, "fa b9 be 44"));
        map.put("lindholmen", new Checkpoint("Lindholmen", 4.0f, 4.0f, "6e 39 2c e1"));
        map.put("nordstan", new Checkpoint("Nordstan", 4.0f, 5.0f, "6e 8d a3 0f"));
        map.put("eriks kort", new Checkpoint("Eriks Kort", 6.0f, 6.0f, "32 f9 12 63"));
        map.put("jens kort", new Checkpoint("Jens Kort", 7.0f, 7.0f, "62 94 dc 27"));
        map.put("johans kort", new Checkpoint("Johans Kort", 8.0f, 8.0f, "f2 a7 21 63"));
        map.put("adams kort", new Checkpoint("Adams Kort", 9.0f, 9.0f, "f2 0e 23 63"));

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
