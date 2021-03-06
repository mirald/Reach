package com.example.erikh.reach;

import com.example.erikh.reach.Checkpoint;
import com.example.erikh.reach.Run;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentRun {
    private static HashMap<Checkpoint, Boolean> map;
    private boolean firstTag = false;

    public CurrentRun(ArrayList<Checkpoint> checkpoints) {
        HashMap<Checkpoint, Boolean> initMap = new HashMap<>();
        for (Checkpoint cp : checkpoints) {
            initMap.put(cp, false);
        }
        this.map = initMap;
    }


    public HashMap<Checkpoint, Boolean> getCurrentRun() {
        return map;
    }

    public Boolean getStatus(Checkpoint checkpoints){
        if(!firstTag){
            firstTag = true;
        }
        return map.get(checkpoints);
    }

    public void updateCheckpointScannedStatus(Checkpoint checkpoint, Boolean bool) {
        this.map.put(checkpoint, bool);
    }

    public static String toString(CurrentRun cRun){
        return map.toString();
    }

    public boolean isFinished(){
        HashMap<Checkpoint, Boolean> cr = this.getCurrentRun();
        return !cr.containsValue(false);
    }

    public boolean isFirstTag() {
        return firstTag;
    }
}
