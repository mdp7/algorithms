package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Vector {
    private double x;
    private double y;

    public Vector(Vector from, Vector to) {
        this.x = to.getX() + from.getX();
        this.y = to.getY() + from.getY();
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // TODO: haven't check below functions
    public static Vector vectorRotateCounterClockwise(Vector v1, double angle) {
        double newX = v1.getX() * Math.cos(angle) - v1.getY() * Math.sin(angle);
        double newY = v1.getX() * Math.sin(angle) + v1.getY() * Math.cos(angle);

        return new Vector(newX, newY);
    }

    public static Vector getMiddlePointVector(Vector v1, Vector v2) {
        double newX = (v1.getX() + v2.getX()) / 2;
        double newY = (v1.getY() + v2.getY()) / 2;

        return new Vector(newX, newY);
    }

    public static double calculateAngle(Vector V1, Vector V2, boolean isRightTurn) {
        double angle = Math.atan2(V2.getY(), V2.getX()) - Math.atan2(V1.getY(), V1.getX());
        if (isRightTurn && angle > 0) {
            angle -= 2 * Math.PI;
        }
        else if (!isRightTurn && angle < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public double getLength() {
        return Math.hypot(x, y);
    }
}
