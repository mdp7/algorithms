package edu.ntu.mdpg7algo.arena;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author Bian Hengwei
 */
@Data
public class Arena {
    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;
    private Cell[][] arena = new Cell[NUM_ROWS][NUM_COLS];
    private ArrayList<Obstacle> obstacles;

    public Arena(ArrayList<Obstacle> _obstacles) {
        _createEmptyArena();
        obstacles = _obstacles;
        for (Obstacle o : obstacles
        ) {
/*            For y value, last index - 1 - y cord
              This is to ensure that the object location corresponds to the grid shown in the notes
              because the first cell in the GUI is bottom left (0,0) while it's the top left for us
 */
//            int row = NUM_ROWS - 2 - o.get_yCord();
            int row = o.get_yCord();
            int col = o.get_xCord();
            this.arena[row][col].setType(CellType.OBSTACLE);
            this.arena[row][col].setObstacle(o);
        }
    }

//    Prints arena in format of the grid shown in notes
    public void printArena() {
        for (int i = NUM_ROWS-1; i >= 0; i--) {
            for (int j = 0; j < NUM_COLS; j++) {
                switch (this.arena[i][j].getType()) {
                    case WALL:
                        System.out.print("#|");
                        break;
                    case EMPTY:
                        System.out.print(" |");
                        break;
                    case OBSTACLE:
                        switch (this.arena[i][j].getObstacle().getDirection()) {
                            case UP:
                                System.out.print("^|");
                                break;
                            case DOWN:
                                System.out.print("v|");
                                break;
                            case LEFT:
                                System.out.print("<|");
                                break;
                            case RIGHT:
                                System.out.print(">|");
                                break;
                        }
                }

            }
            System.out.println();
            for(int k = 0; k <NUM_ROWS*2; k++){
                if (k % 2 == 0) System.out.print("-");
                else System.out.print("+");
            }
            System.out.println();
        }
    }

    private void _createEmptyArena(){
//        this.arena = new Cell[NUM_ROWS][NUM_COLS];
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){
//                Check for edge of arena
//                if(i == 0 || j == 0 || i == NUM_ROWS - 1 || j == NUM_COLS - 1){
//                    this.arena[i][j] = new Cell(CellType.WALL, null);
//                } else{
//                    this.arena[i][j] = new Cell(CellType.EMPTY, null);
//                }
                this.arena[i][j] = new Cell(CellType.EMPTY, null);

            }
        }

    }
}
