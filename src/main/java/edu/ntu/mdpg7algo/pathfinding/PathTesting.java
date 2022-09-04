package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Obstacle;

import java.util.ArrayList;

public class PathTesting {
    public static void main(String[] args) {

        ArrayList<Obstacle> obstacles = new ArrayList<>();
        //      fake start obstacle
        obstacles.add(new Obstacle(0,0,Obstacle.Facing.UP, Obstacle.ObstacleImage.E));

//        Actual Obstacles
        obstacles.add(new Obstacle(9, 13, Obstacle.Facing.UP, Obstacle.ObstacleImage.A));
        obstacles.add(new Obstacle(5, 5, Obstacle.Facing.LEFT, Obstacle.ObstacleImage.B));
        obstacles.add(new Obstacle(5, 10, Obstacle.Facing.RIGHT, Obstacle.ObstacleImage.C));
        obstacles.add(new Obstacle(1, 1, Obstacle.Facing.DOWN, Obstacle.ObstacleImage.F));


        Arena arena = new Arena();
        arena.setObstacles(obstacles);
        /*
        set start point
        Create distance 2D array
         */
        double [][] dist = new double[obstacles.size()][obstacles.size()];

        for(int i = 0; i < obstacles.size(); i++){
            for (int j = 0; j < obstacles.size(); j++){
                if(i == j) dist[i][j] = Double.MAX_VALUE;

                else {
                    dist[i][j] = computeDistance(obstacles.get(i), obstacles.get(j));
                }
            }
        }

        for(int i = 0; i < obstacles.size(); i++){
            for (int j = 0; j < obstacles.size(); j++){
                System.out.print(dist[i][j] + "\t");
                }
            System.out.println();
            }



        HamiltonianPathPlanner h = new HamiltonianPathPlanner(arena);
        int [] order = h.planPath(dist, obstacles.size());
        System.out.println("Number of iterations: "+h.getIterations() + "\nMin cost: " + h.getMinCost());
        for(int i = 0; i < order.length; i++){
            System.out.println(order[i] + "\t");

        }


    }

    public static double computeDistance(Obstacle o1, Obstacle o2){
        double delX = o1.getX() - o2.getX();
        double delY = o1.getY() - o2.getY();
        return Math.hypot(delX, delY);
    }
}
