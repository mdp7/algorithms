from mdpg7.algorithm.instructions_manager import commands_to_message
from mdpg7.algorithm.pathfinder import plan_paths
from mdpg7.models.arena import Arena
from mdpg7.models.robot import Robot
from mdpg7.config.default_arena import arena0
from mdpg7.models.cell_position import CellPosition
from mdpg7.config.constants import Facing

'''
Receives list of obstacles and robot location
Create arena
Plan path
'''


def get_instruction_path(arena: Arena, robot: Robot):
    commands = plan_paths(arena, robot)
    return commands_to_message(commands)


if __name__ == '__main__':
    print("Testing:")
    robot_cell_pos = CellPosition(1, 1, Facing.U)

    robot = Robot(robot_cell_pos)
    print(get_instruction_path(arena0, robot))
