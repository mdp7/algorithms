package edu.ntu.mdpg7algo.arena;

public class Cell {
    private CellType type;
    private Obstacle obstacle;

    public Cell(CellType type, Obstacle obstacle) {
        this.type = type;
        this.obstacle = obstacle;
    }

    public boolean hasObstacle(){
        return this.obstacle != null ? true : false;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}

