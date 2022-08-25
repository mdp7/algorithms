package edu.ntu.mdpg7algo.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Arena {
    
    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;
    private Obstacle[][] obstacles;
    private Robot robot;
    
    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = new Obstacle[NUM_ROWS][NUM_COLS];
        for (Obstacle obstacle : obstacles) {
            this.obstacles[obstacle.getX()][obstacle.getY()] = obstacle;
        }
    }
    
    public void setObstacles(Obstacle[][] obstacles) {
        this.obstacles = obstacles;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM_ROWS; i++) {
            sb.append("|");
            for (int j = 0; j < NUM_COLS; j++) {
                sb.append(obstacles[i][j] == null ? " " : obstacles[i][j].getDir().toSymbol()).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
