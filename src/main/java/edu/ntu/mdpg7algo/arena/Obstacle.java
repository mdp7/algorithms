package edu.ntu.mdpg7algo.arena;

public class Obstacle {
    private int _xCord;
    private int _yCord;

    private Directions direction;

    public Obstacle(int _xCord, int _yCord, Directions direction) {
        this._xCord = _xCord;
        this._yCord = _yCord;
        this.direction = direction;
    }


    public int get_xCord() {
        return _xCord;
    }

    public void set_xCord(int _xCord) {
        this._xCord = _xCord;
    }

    public int get_yCord() {
        return _yCord;
    }

    public void set_yCord(int _yCord) {
        this._yCord = _yCord;
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}
