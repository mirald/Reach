package com.example.reach;

public class checkpoint {
    private float x;
    private float y;
    private String n;

    public checkpoint (String name, float xCordinate, float yCordinate) {
        n = name;
        x = xCordinate;
        y = yCordinate;
    }

    public String getName() {
        return n;
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
