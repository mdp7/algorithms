from algorithm.entities.assets.direction import Direction
from algorithm.entities.grid.grid import Grid
from algorithm.entities.grid.obstacle import Obstacle
from algorithm.entities.robot.robot import Robot
from algorithm.const import SCALING_FACTOR

'''
Receives list of obstacles and robot location
Create arena
Plan path
'''


def get_instruction_path(input_str: str):
    robot = string_converter(input_str)
    robot.brain.plan_path()
    obs_order_string = convert_obstacle_order(robot.brain.simple_hamiltonian)
    return [obs_order_string, robot.convert_all_commands()]

def convert_obstacle_order(obs_order : list):
    to_return = ''
    for o in obs_order:
        x = o.pos.x//SCALING_FACTOR
        y = o.pos.y//SCALING_FACTOR
        to_return = to_return + str(x) +',' + str(y) +'/'

    return to_return


def string_converter(input_str: str):
    # split by '|'
    # First split will be robot position
    # convert each to a cellPos for obstacle and robot
    locations = input_str.split('|')

    obstacles = []

    for index, l in enumerate(locations):
        # x,y,direction_number
        variables = l.split(',')
        print(variables)
        x = int(variables[0])
        y = int(variables[1])
        dir = Direction(int(variables[2]))

        if index == 0:  # Robot
            robot_x = x
            robot_y = y
            robot_dir = dir

        else:
            obstacles.append(Obstacle(x, y, dir, index-1))

    return Robot(robot_x, robot_y, robot_dir, Grid(obstacles))


if __name__ == '__main__':
    print("Testing:")
    # robot_cell_pos = CellPosition(1, 1, Facing.U)

    # robot = Robot(robot_cell_pos)
    input_string = "15,15,90|15,105,90|45,135,-90|75,105,0|135,125,180"
    print(get_instruction_path(input_string))
