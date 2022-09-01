package edu.ntu.mdpg7algo.models;

import lombok.Data;

/**
 * Robot position
 */
@Data
public class Position {
    private double x, y, theta;

    public Position(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        computeTheta();
    }

    public void move(double delX, double delY) {
        x += delX;
        y += delY;
    }

    public void move(double delX, double delY, double delTheta) {
        move(delX, delY);
        theta += delTheta;
        computeTheta();
    }

    public void place(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        computeTheta();
    }

    /**
     * Normalize theta to a fixed range of (-PI, PI]
     */
    private void computeTheta() {
        while (theta > Math.PI) theta -= 2 * Math.PI;
        while (theta <= -Math.PI) theta += 2 * Math.PI;
    }
}
