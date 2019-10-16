import com.example.erikh.reach.Checkpoint;
import com.example.erikh.reach.Run;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentRun {
    HashMap<Checkpoint, Boolean> map;

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

    public void updateCheckpointScannedStatus(Checkpoint checkpoint) {
        this.map.put(checkpoint, true);
    }
}
