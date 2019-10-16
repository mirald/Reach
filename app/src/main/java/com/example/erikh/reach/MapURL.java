package com.example.erikh.reach;

import java.util.ArrayList;

public class MapURL {
    private String mapURL;
    private CurrentRun currentRun;
    private int width, height;
    private String API_key = BuildConfig.MapquestAPIKey;
    private String checkedTag = "|marker-FF8A65-lg";
    private String defaultColorOfMarker = "gray";



    public MapURL(CurrentRun cRun, int width, int height){
        this.currentRun = cRun;
        this.width = width;
        this.height = height;
        String locations = getLocations(cRun);
        this.mapURL = makeURL(locations, width, height);
    }

    private String getLocations(CurrentRun cRun){
        StringBuilder tempLocations = new StringBuilder();
        for (Checkpoint key : cRun.getCurrentRun().keySet()){
            String location = Float.toString(key.getX())+","+Float.toString(key.getY());
            tempLocations.append(location);
            if(cRun.getStatus(key)){
                tempLocations.append(checkedTag);
            }
            if(cRun.getCurrentRun().keySet().size()>1){
                tempLocations.append("||");
            }
        }

        return tempLocations.toString();
    }

    private String makeURL(String loc, int w, int h){
        return "https://www.mapquestapi.com/staticmap/v5/map?key="+API_key+"&locations" +
                "="+ loc +"&size="+ w + "," + h +
                "@2x&defaultMarker=marker-"+defaultColorOfMarker+"-lg&margin=200";
    }

    public String getMapURL() {
        return mapURL;
    }

    public void updateURL(CurrentRun currentRun){
        String locations = getLocations(currentRun);
        mapURL = makeURL(locations, this.width, this.height);
    }
}
