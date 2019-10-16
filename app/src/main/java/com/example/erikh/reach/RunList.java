package com.example.erikh.reach;

import java.util.ArrayList;

public class RunList {
    private ArrayList<Run> rl = new ArrayList<Run>();

    private static RunList instance;

    public static RunList getRunList() {
        if (instance == null) {
            instance = new RunList();
        }
        return instance;
    }

    public void addRun(Run run) {
        rl.add(run);
    }

    public Run getRun(int index) {
        return rl.get(index);
    }

    public int getLength() {
        return rl.size();
    }

    public ArrayList<Run> getRunArrayList() {
        return rl;
    }

    private RunList() {


    }
}
