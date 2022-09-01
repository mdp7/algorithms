package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Obstacle {

    private final double ROBOT_OFFSET = 8;  // distance the robot needs to be away from the obstacle

    private boolean detected;
    private int cellX, cellY;  // cell location of the obstacle
    private Facing facing;
    private ObstacleImage obstacleImage;

    public Obstacle(int cellX, int cellY, Facing facing, ObstacleImage obstacleImage) {
        this(cellX, cellY, facing, obstacleImage, false);
    }

    public Obstacle(int cellX, int cellY, Facing facing, ObstacleImage obstacleImage, boolean detected) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.facing = facing;
        this.obstacleImage = obstacleImage;
        this.detected = detected;
    }

    /**
     * Computes the x position of the obstacle center in cm
     */
    public double getX() {
        return (cellX + 0.5) * Arena.CELL_WIDTH;
    }

    /**
     * Computes the y position of the obstacle center in cm
     */
    public double getY() {
        return (cellY + 0.5) * Arena.CELL_HEIGHT;
    }

    public Position computeRobotPosition() {
        return switch (this.facing) {
            case UP -> new Position(getX(), getY() + ROBOT_OFFSET, -Math.PI / 2);
            case DOWN -> new Position(getX(), getY() - ROBOT_OFFSET, Math.PI / 2);
            case LEFT -> new Position(getX() - ROBOT_OFFSET, getY(), 0);
            case RIGHT -> new Position(getX() + ROBOT_OFFSET, getY(), Math.PI);
        };
    }

    @Override
    public String toString() {
        return ("x: " + getCellX() + " y: " + getCellY() + " dir: " + this.facing);
    }

    public enum Facing {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public String toSymbol() {
            return switch (this) {
                case UP -> "^";
                case DOWN -> "v";
                case LEFT -> "<";
                case RIGHT -> ">";
            };
        }
    }

    public enum ObstacleImage {
        A,
        B,
        C,
        CIRCLE,
        D,
        DOWN,
        E,
        EIGHT,
        F,
        FIVE,
        FOUR,
        G,
        H,
        LEFT,
        NINE,
        ONE,
        RIGHT,
        S,
        SEVEN,
        SIX,
        T,
        THREE,
        TWO,
        U,
        UP,
        V,
        W,
        X,
        Y,
        Z;
    }
}
