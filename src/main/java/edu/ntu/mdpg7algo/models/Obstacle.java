package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Obstacle {
    
    private Position position;
    private Dir dir;
    private boolean detected;

    public Obstacle(double x, double y, Dir dir) {
        this.position = new Position(x, y, dir.theta);
        this.dir = dir;

        this.detected = false;
    }

    public Obstacle(int x, int y, Dir dir, boolean detected) {
        this.position = new Position(x, y, dir.theta);
        this.dir = dir;
        this.detected = detected;
    }
    
    @Override
    public String toString() {
        return ("x: " + this.position.getX() + " y: " + this.position.getY() + " dir: " + this.dir);
    }



    public enum Dir {
        UP(Math.PI/2), DOWN(-Math.PI/2), LEFT(Math.PI), RIGHT(0);

        public final double theta;

        private Dir(double theta){
            this.theta = theta;
        }
        
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
