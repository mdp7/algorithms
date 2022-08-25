package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Move {

    private Dir dir;
    private double distance;

    public enum Dir {
        R,
        S,
        L;
    }
}
