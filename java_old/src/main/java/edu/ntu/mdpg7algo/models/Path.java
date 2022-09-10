package edu.ntu.mdpg7algo.models;

import lombok.Data;

import java.util.ArrayList;

/**
 * Collection of moves
 */
@Data
public class Path {
    private ArrayList<Move> moves;
    private double totalDistance;

    public Path() {
        this.moves = new ArrayList<>();
        this.totalDistance = 0;
    }

    public Path(ArrayList<Move> moves) {
        this.totalDistance = 0;
        this.moves = moves;
        for (Move move : moves) {
            totalDistance += move.getDistance();
        }
    }

    // TODO: why this is needed
    public Path(Path[] paths) {
        this.totalDistance = 0;
        for (Path path : paths) {
            moves.addAll(path.getMoves());
            totalDistance += path.getTotalDistance();
        }
    }

    public void addMove(Move move) {
        moves.add(move);
        totalDistance += move.getDistance();
    }
}
