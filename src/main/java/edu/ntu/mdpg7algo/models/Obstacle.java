package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Obstacle {
    
    private int x;
    private int y;
    private Dir dir;
    private boolean detected;

    public Obstacle(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.detected = false;
    }

    public Obstacle(int x, int y, Dir dir, boolean detected) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.detected = detected;
    }
    
    @Override
    public String toString() {
        return ("x: " + this.x + " y: " + this.y + " dir: " + this.dir);
    }
    
    public enum Dir {
        UP, DOWN, LEFT, RIGHT;
        
        public String toSymbol() {
            return switch (this) {
                case UP -> "^";
                case DOWN -> "v";
                case LEFT -> "<";
                case RIGHT -> ">";
            };
        }
    }
}
