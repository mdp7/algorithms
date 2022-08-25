package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
