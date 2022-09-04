package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Node2D;
import edu.ntu.mdpg7algo.models.Obstacle;
import lombok.Data;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Input:<br>
 *      1. A map of obstacles<br>
 *      2. Current car position<br>
 *      3. Destination<br><br>
 * Constants:<br>
 *      1. Target position threshold<br>
 *      2. Position resolution<br><br>
 * Output:<br>
 *      A path consisting of a set of moves<br>
 */
@Data
public class HybridAStarPlanner {

    private boolean[][] obstacles;

    public HybridAStarPlanner() {
    }

    public double aStar(Node2D start, Node2D goal, Node2D[] nodes2D, int width, int height) {
        int indexPredecessor, indexSuccessor;
        double newG;

        for (int i = 0; i < width * height; i++) {
            nodes2D[i].reset();
        }

        PriorityQueue<Node2D> open = new PriorityQueue<>((Comparator.comparingDouble(Node2D::getGH)));

        start.updateH(goal);
        start.open();
        open.add(start);

        indexPredecessor = start.setIndex(width);
        nodes2D[indexPredecessor] = start;

        Node2D predecessor, successor;
        while (!open.isEmpty()) {
            predecessor = open.poll();
            indexPredecessor = predecessor.setIndex(width);

            // Getting rid of illegal states
            if (nodes2D[indexPredecessor].isClose()) {
                System.out.println("ERROR: closed node is visited");
                System.exit(1);
            }
            else if (!nodes2D[indexPredecessor].isOpen()) {
                System.out.println("ERROR: unopened node is visited");
                System.exit(1);
            }

            nodes2D[indexPredecessor].close();
            nodes2D[indexPredecessor].visit();

            if (predecessor.equals(goal)) {
                return predecessor.getG();
            }

            for (int i = 0; i < Node2D.dir; i++) {
                successor = predecessor.createSuccessor(i);
                indexSuccessor = successor.setIndex(width);
                if (successor.isOnGrid(width, height) &&
                        isTraversable(successor) &&
                        !nodes2D[indexSuccessor].isClose()) {

                    successor.updateG();
                    newG = successor.getG();

                    if (!nodes2D[indexSuccessor].isOpen()) {
                        successor.updateH(goal);
                        successor.open();
                        nodes2D[indexSuccessor] = successor;
                        open.add(successor);
                    }
                    else if (nodes2D[indexSuccessor].isOpen() && newG < nodes2D[indexPredecessor].getG()) {
                        open.remove(nodes2D[indexPredecessor]);
                        successor.updateH(goal);
                        successor.open();
                        nodes2D[indexSuccessor] = successor;
                        open.add(successor);
                    }
                }
            }
        }
        System.out.println("NOT FOUND");
        return 1000.;
    }

    private boolean isTraversable(Node2D node) {
        return !obstacles[node.getX()][node.getY()];
    }

    public void setObstacles(Obstacle[][] obstaclesMatrix) {
        int height = obstaclesMatrix.length, width = obstaclesMatrix[0].length;
        obstacles = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (obstaclesMatrix[i][j] != null) {
                    obstacles[i][j] = true;
                    if (i != 0) {
                        obstacles[i - 1][j] = true;
                        if (j != 0) {
                            obstacles[i - 1][j - 1] = true;
                        }
                        if (j != width - 1) {
                            obstacles[i - 1][j + 1] = true;
                        }
                    }
                    if (i != height - 1) {
                        obstacles[i + 1][j] = true;
                        if (j != 0) {
                            obstacles[i + 1][j - 1] = true;
                        }
                        if (j != width - 1) {
                            obstacles[i + 1][j + 1] = true;
                        }
                    }
                    if (j != 0) {
                        obstacles[i][j - 1] = true;
                    }
                    if (j != width - 1) {
                        obstacles[i][j + 1] = true;
                    }
                }
            }
        }
    }
}
