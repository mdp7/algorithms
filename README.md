# algorithms

cellX, cellY: x, y in the virtual arena grid whereas the bl is 0, 0 and tr is 19, 19  
gridX, gridY: x, y in the UI where tl is 0, 0 and br is 19, 19

obstaclesMatrix: [i][j] means cellX = i and cellY = j

## Commands 
Command comprises a move and times the move is carried out (move, times).

Each move corresponds to a value:  

Left turn (LF) - 0  
Forward (FF) - 1  
Right turn (RF) - 2  
Backward Left (LB) - 3  
Backward (BB) - 4  
Backward right (RB) - 5  

### Special 'move'
Take picture - '*'  
End of commands - '!'

### Message from Algo to RPI
A list of commands will be converted to a string. Each command is separated by '/'.
### Example
If moves to travel a path to take a picture is [FF, RF, FF, FF, LB], it will be converted to "1,1/2,1/1,2/3,1/*/!" 

## Running simulator
If you want to run the simulator, go to `python/algorithm/main.py` and run `main.py`.
- Use arrow keys to set obstacle direction, then click on grid
- Backspace to clear all obstacle on field
- Space to start running
- Running the simulator with space will print the commands that would be fed to stm
