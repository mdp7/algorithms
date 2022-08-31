package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Car {

    private Position position;

    public Car(double x, double y, double theta) {
        this.position = new Position(x, y, theta);
    }

    public Car(Position position) {
        this.position = position;
    }

    public void move(double delX, double delY) {
        position.move(delX, delY);
    }

    public void move(double delX, double delY, double delTheta) {
        position.move(delX, delY, delTheta);
    }
}
