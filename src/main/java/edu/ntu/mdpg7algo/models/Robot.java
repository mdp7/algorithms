package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Robot {
    private Position position;
    
    public Robot(Position position) {
        this.position = position;
    }
    
    public void move(double delX, double delY) {
        position.move(delX, delY);
    }
    
    public void move(double delX, double delY, double delTheta) {
        position.move(delX, delY, delTheta);
    }
}
