package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Position {
    private double x, y, theta;

    public Position(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public void move(double delX, double delY) {
        x += delX;
        y += delY;
    }

    public void move(double delX, double delY, double delTheta) {
        move(delX, delY);
        theta += delTheta;
        if(theta > Math.PI) theta -= 2*Math.PI;
        else if (theta < -Math.PI) theta += 2*Math.PI;
    }

    public void place(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }
}
