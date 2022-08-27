package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Obstacle {
    private final double robotOffset = 8;
    
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


    public Position computeRobotPosition(){
        return switch (this.dir){
            case UP -> new Position(position.getX(), position.getY() + robotOffset, dir.DOWN.theta);
            case DOWN -> new Position(position.getX(), position.getY() - robotOffset, dir.UP.theta);
            case LEFT -> new Position(position.getX()  - robotOffset, position.getY(), dir.RIGHT.theta);
            case RIGHT -> new Position(position.getX()  + robotOffset, position.getY(), dir.LEFT.theta);
        };
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
