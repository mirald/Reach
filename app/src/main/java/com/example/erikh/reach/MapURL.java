package com.example.erikh.reach;

import java.util.ArrayList;

public class MapURL {
    StringBuilder URL;
    String mapURL;
    String API_key = BuildConfig.MapquestAPIKey;

    public MapURL() {
        mapURL = "https://www.mapquestapi.com/staticmap/v5/map?key="+API_key+"&locations" +
                "=Gothenburg&size=@2x";
    }
    public MapURL(CurrentRun cRun){

    }
}
