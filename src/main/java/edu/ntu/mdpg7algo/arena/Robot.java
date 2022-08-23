package edu.ntu.mdpg7algo.arena;

import lombok.Data;

@Data
public class Robot {
    private double x, y, theta;
    
    public Robot(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }
    
    public void move(double delX, double delY) {
        x += delX;
        y += delY;
    }
    
    public void turn(double delTheta) {
        theta += delTheta;
    }
    
    public void turnAndMove(double delX, double delY, double delTheta) {
        move(delX, delY);
        turn(delTheta);
    }
}
