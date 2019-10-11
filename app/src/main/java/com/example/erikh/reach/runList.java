package com.example.erikh.reach;

import java.util.ArrayList;

public class runList {
    private ArrayList<run> rl = new ArrayList<run>();

    private static runList instance;

    public static runList getRunList() {
        if (instance == null) {
            instance = new runList();
        }
        return instance;
    }

    public void addRun(run run) {
        rl.add(run);
    }

    public run getRun(int index) {
        return rl.get(index);
    }

    public int getLength() {
        return rl.size();
    }

    private runList() {

    }
}
