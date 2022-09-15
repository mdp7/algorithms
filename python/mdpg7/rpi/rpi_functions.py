from mdpg7.algorithm.pathfinder import plan_paths
from mdpg7.models.arena import Arena
from mdpg7.models.robot import Robot
from mdpg7.algorithm.instructions_manager import commands_to_message

def get_instruction_path(arena: Arena, robot : Robot):
    commands = plan_paths(arena, robot)
    return commands_to_message(commands)


