package edu.ntu.mdpg7algo.arena;

import lombok.Data;

@Data
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
}

