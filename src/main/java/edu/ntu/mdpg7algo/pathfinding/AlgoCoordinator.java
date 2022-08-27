package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Move;
import lombok.Data;

import java.util.ArrayList;
@Data
public class AlgoCoordinator {

    private Greedy greedySearch;
    private Arena arena;

    public AlgoCoordinator(Arena arena) {
        this.greedySearch = new Greedy(arena);
        this.arena = arena;
    }

    public ArrayList<Move> getNextMoves(){
        return greedySearch.getOptimalMoves();
    }
}
