from mdpg7.algorithm.instructions_manager import commands_to_message
from mdpg7.algorithm.pathfinder import plan_paths
from mdpg7.models.arena import Arena
from mdpg7.models.robot import Robot
from mdpg7.models.obstacle import Obstacle, ObstacleImage
from mdpg7.config.default_arena import arena0
from mdpg7.models.cell_position import CellPosition
from mdpg7.config.constants import Facing

'''
Receives list of obstacles and robot location
Create arena
Plan path
'''


def get_instruction_path(input_str: str):
    arena, robot = string_converter(input_str)
    commands = plan_paths(arena, robot)
    return commands_to_message(commands)


def string_converter(input_str: str):
    # split by '|'
    # First split will be robot position
    # convert each to a cellPos for obstacle and robot
    locations = input_str.split('|')
    arena = Arena()

    for index, l in enumerate(locations):
        # x,y,direction_number
        vars = l.split(',')
        print(vars)
        x = int(vars[0])
        y = int(vars[1])
        dir = int(vars[2])

        tmp_cell_pos = CellPosition(x, y, dir)
        if index == 0:  # Robot
            robot = Robot(tmp_cell_pos)

        else:
            arena.add_obstacle(Obstacle(tmp_cell_pos, ObstacleImage.U))

    return arena, robot


if __name__ == '__main__':
    print("Testing:")
    # robot_cell_pos = CellPosition(1, 1, Facing.U)

    # robot = Robot(robot_cell_pos)
    input_string = "1,1,1|1,10,1|4,13,3|7,10,0|13,12,2"
    print(get_instruction_path(input_string))
