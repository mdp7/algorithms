package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Vector {
    public static Vector vectorRotateCounterClockwise(Vector v1, double angle){
        double _x = v1.getX() * Math.cos(angle) - v1.getY() * Math.sin(angle);
        double _y = v1.getX() * Math.sin(angle) + v1.getY() * Math.cos(angle);

        return new Vector(_x, _y);
    }
    public static double calculateAngle(Vector V1, Vector V2, boolean isRightTurn){
        double angle = Math.atan2(V2.getY(), V2.getX()) - Math.atan2(V1.getY(), V1.getX());
        if(isRightTurn && angle > 0){
            angle -= 2 * Math.PI;
        } else if (!isRightTurn && angle < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    private double x;
    private double y;

    public Vector(Vector from, Vector to){
        this.x = to.getX() - from.getX();
        this.y = to.getY() - from.getY();
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLength(){
        double distance = Math.hypot(Math.abs(this.x), Math.abs(this.y));
        return distance;

    }
}
