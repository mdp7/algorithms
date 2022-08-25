package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Move {

    private Dir dir;
    private double distance;

    public Move(Dir dir, double distance) {
        this.dir = dir;
        this.distance = distance;
    }

    public enum Dir {
        R,
        S,
        L;
    }
}
