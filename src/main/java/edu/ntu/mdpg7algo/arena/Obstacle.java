package edu.ntu.mdpg7algo.arena;

import lombok.Data;

@Data
public class Obstacle {
    private int _xCord;
    private int _yCord;

    private Directions direction;

    public Obstacle(int _xCord, int _yCord, Directions direction) {
        this._xCord = _xCord;
        this._yCord = _yCord;
        this.direction = direction;
    }

    @Override
    public String toString(){
        return ("xCord: "+this._xCord + " yCord: " + this._yCord + " direction: "+this.direction);
    }
}
