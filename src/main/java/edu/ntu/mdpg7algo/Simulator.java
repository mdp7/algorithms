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
        arena.addObstacle(new Obstacle(10, 10, Obstacle.Facing.DOWN));
        arenaPanel = new ArenaPanel(30, 30, 30, 30, 20, 20, arena);
        car = new Car(0, 0, Math.PI / 2);

        add(arenaPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280, 720);
        setVisible(true);
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator("MDP Group7 Path Finding Visualization Tool");
    }
}
