package edu.ntu.mdpg7algo.models;

import lombok.Data;

import java.util.ArrayList;
@Data
public class Path {
    private ArrayList<Move> moves;
    private double totalDistance;
    public Path(ArrayList<Move> moves) {
        this.totalDistance = 0;
        this.moves = moves;
        for(Move move : moves){
            totalDistance += move.getDistance();
        }

    }

    public Path(Path[] paths){
        this.totalDistance = 0;
        for(Path path : paths){
            moves.addAll(path.getMoves());
            totalDistance += path.getTotalDistance();
        }

    }

    public Path(){
        this.moves = new ArrayList<Move>();
        this.totalDistance = 0;
    }
}
