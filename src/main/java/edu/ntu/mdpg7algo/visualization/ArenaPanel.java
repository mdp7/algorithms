package edu.ntu.mdpg7algo.visualization;

import edu.ntu.mdpg7algo.Simulator;
import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Obstacle;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

@Data
public class ArenaPanel extends JPanel {

    private static final int NUM_ROW = 20, NUM_COL = 20;
    private int x0, y0, w, h, cellW, cellH;
    private Arena arena;

    public ArenaPanel(int x0, int y0, int cellW, int cellH, Arena arena) {
        this.x0 = x0;
        this.y0 = y0;
        this.cellW = cellW;
        this.cellH = cellH;
        this.arena = arena;

        w = cellW * NUM_COL;
        h = cellH * NUM_ROW;
        setSize(new Dimension(w, h));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // check bounds
                int x = e.getX(), y = e.getY();
                if (x < x0 || y < y0 || x0 + w < x || y0 + h < y) {
                    System.out.println("Mouse click performed outside arena, ignored");
                    return;
                }
                int cellX = getCellX(x), cellY = getCellY(y);
                System.out.println("cell x: " + cellX + ", cell y: " + cellY);

                if (e.isPopupTrigger()) {
                    new ObstacleMenu(cellX, cellY, arena, ArenaPanel.this).show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });
    }

    private int getCellX(double absX) {
        if (absX < x0 || x0 + w < absX) {
            return -1;
        }
        return (int) ((absX - x0) / cellW);
    }

    private int getCellY(double absY) {
        if (absY < y0 || y0 + h < absY) {
            return -1;
        }
        return 19 - (int) ((absY - y0) / cellH);
    }

    private int convertGridY(int cellY) {
        return 19 - cellY;
    }

    public void paintComponent(Graphics g) {
        System.out.println("called");
        super.paintComponent(g);
        // draw horizontal lines
        for (int i = 0; i <= NUM_ROW; i++) {
            g.drawLine(x0, y0 + i * cellH, x0 + w, y0 + i * cellH);
            if (i != 0) {
                g.drawString("" + (20 - i), x0, y0 + i * cellH);
            }
        }
        for (int i = 0; i <= NUM_COL; i++) {
            g.drawLine(x0 + i * cellW, y0, x0 + i * cellW, y0 + h);
            if (i != 20) {
                g.drawString("" + i, x0 + i * cellW, y0 + h);
            }
        }

        try {
            Image image = ImageIO.read(new File("assets/car.png"));
            image = image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            double carX = arena.getCar().getPosition().getX(), carY = arena.getCar().getPosition().getY();
            g.drawImage(image, (int) carX, (int) carY, this);
        }
        catch (IOException ioException) {
            System.exit(1);
        }

        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_ROW; j++) {
                if (arena.getObstaclesMatrix()[i][j] != null) {
                    Obstacle obstacle = arena.getObstaclesMatrix()[i][j];
                    int x = x0 + j * cellW;
                    int y = y0 + convertGridY(i) * cellH;
                    try {
                        Image image = ImageIO.read(
                                new File(String.format("assets/%s.png", obstacle.getObstacleImage().toString()))
                        );
                        image = image.getScaledInstance(cellW, cellH, Image.SCALE_DEFAULT);
                        g.drawImage(image, x, y, this);
                    }
                    catch (IOException ioException) {
                        System.exit(1);
                    }

                    int radius = 4;
                    switch (obstacle.getFacing()) {
                        case UP -> x = x + cellW / 2 - radius;
                        case LEFT -> y = y + cellH / 2 - radius;
                        case DOWN -> {
                            x = x + cellW / 2 - radius;
                            y = y + cellH - 2 * radius;
                        }
                        case RIGHT -> {
                            x = x + cellW - 2 * radius;
                            y = y + cellH / 2 - radius;
                        }
                    }
                    g.setColor(new Color(255, 0, 0));
                    g.fillOval(x, y, radius * 2, radius * 2);
                }
            }
        }
    }
}

class ObstacleMenu extends JPopupMenu {
    private final ArenaPanel arenaPanel;

    public ObstacleMenu(int cellX, int cellY, Arena arena, ArenaPanel arenaPanel) {
        this.arenaPanel = arenaPanel;
        initMenu(cellX, cellY, arena);
    }

    private void refreshPanel() {
        arenaPanel.revalidate();
        arenaPanel.repaint();
    }

    private void initMenu(int cellX, int cellY, Arena arena) {
        Obstacle obstacle = arena.getObstaclesMatrix()[cellY][cellX];
        if (obstacle == null) {
            JMenuItem addObstacleItem = new JMenuItem("Add default obstacle");
            addObstacleItem.addActionListener((e) -> {
                arena.addObstacle(new Obstacle(cellX, cellY, Obstacle.Facing.UP, Obstacle.ObstacleImage.A));
                refreshPanel();
            });
            add(addObstacleItem);
        }
        else {
            JMenuItem removeObstacleItem = new JMenuItem("Remove obstacle");
            removeObstacleItem.addActionListener((e) -> arena.removeObstacle(cellX, cellY));
            add(removeObstacleItem);

            JMenu changeFacingMenu = new JMenu("Change image facing");
            for (Obstacle.Facing facing: Obstacle.Facing.values()) {
                JMenuItem changeFacingItem = new JMenuItem(facing.toString());
                changeFacingItem.addActionListener((e) -> {
                    obstacle.setFacing(facing);
                    refreshPanel();
                });
                changeFacingMenu.add(changeFacingItem);
            }
            add(changeFacingMenu);

            JMenu changeImageMenu = new JMenu("Change image");
            for (Obstacle.ObstacleImage obstacleImage: Obstacle.ObstacleImage.values()) {
                JMenuItem changeImageItem = new JMenuItem(obstacleImage.toString());
                changeImageItem.addActionListener((e) -> {
                    obstacle.setObstacleImage(obstacleImage);
                    refreshPanel();
                });
                changeImageMenu.add(changeImageItem);
            }
            add(changeImageMenu);
        }
    }
}
