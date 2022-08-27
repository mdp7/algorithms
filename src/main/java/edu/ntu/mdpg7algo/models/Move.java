package edu.ntu.mdpg7algo.models;

import lombok.Data;

/**
 * Defines robot movement, with wheel direction and distance to travel
 */
@Data
public class Move {

    private MoveDir moveDir;
    private double distance;

    public Move(MoveDir moveDir, double distance) {
        this.moveDir = moveDir;
        this.distance = distance;
    }

    public enum MoveDir {
        R,
        S,
        L
    }
}
