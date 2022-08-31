package edu.ntu.mdpg7algo.models;

import lombok.Data;

import java.util.ArrayList;

/**
 * A virtual arena consisting of a virtual gird, obstacles, and a robot
 */
@Data
public class Arena {

    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;
    public static final int CELL_WIDTH = 20;
    public static final int CELL_HEIGHT = 20;
    private ArrayList<Obstacle> obstacles;
    private Obstacle[][] obstaclesMatrix;  // stores the obstacle at corresponding grids
    private Car car;

    public Arena() {
        obstacles = new ArrayList<>();
        obstaclesMatrix = new Obstacle[NUM_ROWS][NUM_COLS];
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstaclesMatrix = new Obstacle[NUM_ROWS][NUM_COLS];
        for (Obstacle obstacle : obstacles) {
            this.obstaclesMatrix[obstacle.getColX()][obstacle.getColY()] = obstacle;
        }
        this.obstacles = obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        obstaclesMatrix[obstacle.getColX()][obstacle.getColY()] = obstacle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM_ROWS; i++) {
            sb.append("|");
            for (int j = 0; j < NUM_COLS; j++) {
                sb.append(obstaclesMatrix[i][j] == null ? " " : obstaclesMatrix[i][j].getFacing().toSymbol());
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
