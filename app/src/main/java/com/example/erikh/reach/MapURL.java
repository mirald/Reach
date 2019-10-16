package com.example.erikh.reach;

import java.util.ArrayList;

public class MapURL {
    private String mapURL;
    private String API_key = BuildConfig.MapquestAPIKey;
    private String checkedTag = "|marker-FF8A65-lg";
    private String defaultColorOfMarker = "gray";



    public MapURL(CurrentRun cRun, int width, int height){
        StringBuilder tempLocations = new StringBuilder();

        for (Checkpoint key : cRun.getCurrentRun().keySet()){
            String location = Float.toString(key.getX())+","+Float.toString(key.getY());
            if(cRun.getCurrentRun().keySet().size()>1){
                location = location + "||";
            }
            tempLocations.append(location);
            if(cRun.getStatus(key)){
                tempLocations.append(checkedTag);
            }
        }

        this.mapURL = "https://www.mapquestapi.com/staticmap/v5/map?key="+API_key+"&locations" +
                "="+ tempLocations.toString()+"&size="+ width + "," + height +
                "@2x&defaultMarker=marker-"+defaultColorOfMarker+"-lg&margin=200";
    }

    public String getMapURL() {
        return mapURL;
    }
}
