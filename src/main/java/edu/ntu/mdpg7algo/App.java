package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.visualization.ArenaPanel;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MDP Group7 Path Finding Visualization Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(852, 480);
        
        JMenuBar menuBar = new JMenuBar();
        
        JPanel panel = new ArenaPanel(480, 480, 20, 20);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
