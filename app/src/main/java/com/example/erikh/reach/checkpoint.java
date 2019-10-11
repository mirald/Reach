package com.example.erikh.reach;

public class checkpoint {
    private float x;
    private float y;
    private String n;
    private String s;

    public checkpoint (String name, float xCordinate, float yCordinate, String serialNumber) {
        n = name;
        x = xCordinate;
        y = yCordinate;
        s = serialNumber;
    }

    public String getName() {
        return n;
    }

    public String getSerialNumber() {
        return s;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float[] getCordinates() {
        return new float[] {x, y};
    }
}
