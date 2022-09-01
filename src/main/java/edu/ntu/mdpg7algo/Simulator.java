package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Car;
import edu.ntu.mdpg7algo.models.Obstacle;
import edu.ntu.mdpg7algo.visualization.ArenaPanel;
import lombok.Data;

import javax.swing.*;

@Data
public class Simulator extends JFrame {

    private ArenaPanel arenaPanel;
    private Arena arena;
    private Car car;

    public Simulator(String title) {
        super(title);

        arena = new Arena();
        arena.addObstacle(new Obstacle(8, 11, Obstacle.Facing.DOWN, Obstacle.ObstacleImage.A));
        arena.setCar(new Car(30, 35, Math.PI * 2 / 3));
        arenaPanel = new ArenaPanel(40, 40, 30, 30, arena);

        add(arenaPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(720, 720);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Simulator("MDP Group7 Path Finding Visualization Tool");
    }
}
