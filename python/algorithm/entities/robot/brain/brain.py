import itertools
import math
from collections import deque
from typing import Tuple

from algorithm import const
from algorithm.entities.commands.left_command import LeftTurn
from algorithm.entities.commands.right_command import RightTurn
from algorithm.entities.commands.scan_command import ScanCommand
from algorithm.entities.commands.straight_command import StraightCommand
from algorithm.entities.commands.turn_command import TurnCommand
from algorithm.entities.grid.obstacle import Obstacle
from algorithm.entities.robot.brain.mod_a_star import ModifiedAStar


class Brain:
    def __init__(self, robot, grid):
        self.robot = robot
        self.grid = grid

        # Compute the simple Hamiltonian path for all obstacles
        self.simple_hamiltonian = tuple()

        # Create all the commands required to finish the course.
        self.commands = deque()

    def compute_simple_hamiltonian_path(self) -> Tuple[Obstacle]:
        """
        Get the Hamiltonian Path to all points with the best possible effort.
        This is a simple calculation where we assume that we travel directly to the next obstacle.
        """
        # Generate all possible permutations for the image obstacles
        perms = list(itertools.permutations(self.grid.obstacles))

        # Get the path that has the least distance travelled.
        def calc_distance(path):
            # Create all target points, including the start.
            # targets = [self.robot.pos.xy_pygame()]
            targets = [self.robot.pos]
            for obstacle in path:
                # targets.append(obstacle.target_pos.xy_pygame())
                targets.append(obstacle.target_pos)
            dist = 0
            for i in range(len(targets) - 1):
                delta = abs(targets[i].angle - targets[i + 1].angle)
                if delta == 180:
                    mul = 2
                elif delta == 0:
                    mul = 1.2
                else:
                    mul = 1
                dist += mul * math.sqrt(((targets[i].xy_pygame()[0] - targets[i + 1].xy_pygame()[0]) ** 2) +
                                        ((targets[i].xy_pygame()[1] - targets[i + 1].xy_pygame()[1]) ** 2))
            return dist

        simple = min(perms, key=calc_distance)
        # print("Found a simple hamiltonian path:")
        # for ob in simple:
        #     print(f"\t{ob}")
        return simple

    def compress_paths(self):
        """
        Compress similar commands into one command.

        Helps to reduce the number of commands.
        """
        print("Compressing commands... ")
        total_distance = 0
        time = 0
        index = 0
        instruction_str = ""
        new_commands = deque()
        while index < len(self.commands):
            command = self.commands[index]
            if isinstance(command, StraightCommand):
                # print("Straight Command ticks: ", command.time)
                new_length = 0
                while index < len(self.commands) and isinstance(self.commands[index], StraightCommand):
                    new_length += self.commands[index].dist
                    index += 1
                    total_distance += 10
                    time += command.time * 5
                command = StraightCommand(new_length)
                new_commands.append(command)
            else:
                new_commands.append(command)
                index += 1
                if isinstance(command, TurnCommand):
                    if isinstance(command, LeftTurn):
                        turn_amt = (const.ROBOT_LEFT_TURN_RADIUS) // const.SCALING_FACTOR * math.pi / 2
                        time += command.time * 5
                    elif isinstance(command, RightTurn):
                        turn_amt = (const.ROBOT_RIGHT_TURN_RADIUS) // const.SCALING_FACTOR * math.pi / 2
                        time += command.time * 5
                    else:
                        turn_amt = 0
                        time += command.time * 20
                    total_distance += turn_amt


        # print("\n", new_commands)
        # for c in new_commands:
        #     print(c, end=",")

        print()
        # print(new_commands)
        self.commands = new_commands
        # print(f"Done! Distance Travelled: {total_distance}cm  Time taken: {time}s")

    def plan_path(self):
        print("-" * 40)
        print("STARTING PATH COMPUTATION...")
        self.simple_hamiltonian = self.compute_simple_hamiltonian_path()
        print()

        curr = self.robot.pos.copy()  # We use a copy rather than get a reference.
        for obstacle in self.simple_hamiltonian:
            target = obstacle.get_robot_target_pos()
            # print(f"Planning {curr} to {target}")
            res = ModifiedAStar(self.grid, self, curr, target).start_astar()
            if res is None:
                print(f"\tNo path found from {curr} to {target}")
            else:
                print(f"\tPath found from {curr} to {target}.")
                curr = res
                self.commands.append(ScanCommand(const.ROBOT_SCAN_TIME, obstacle.index))
        self.compress_paths()
        print("-" * 40)

    #     PENALTY TESTING

    def compress_paths_test(self):
        """
        Compress similar commands into one command.

        Helps to reduce the number of commands.
        """
        # print("Compressing commands... ")
        total_distance = 0
        time = 0
        index = 0
        instruction_str = ""
        new_commands = deque()
        while index < len(self.commands):
            command = self.commands[index]
            time += command.time * 5
            if isinstance(command, StraightCommand):
                # print("Straight Command ticks: ", command.time)
                new_length = 0
                while index < len(self.commands) and isinstance(self.commands[index], StraightCommand):
                    new_length += self.commands[index].dist
                    index += 1
                    total_distance += 10
                    time += command.time
                command = StraightCommand(new_length)
                new_commands.append(command)
            else:
                new_commands.append(command)
                index += 1
                if isinstance(command, TurnCommand):
                    # print("Turn Command ticks: ", command.total_ticks)
                    # print((const.ROBOT_TURN_RADIUS)//const.SCALING_FACTOR * math.pi/2)
                    total_distance += (const.ROBOT_TURN_RADIUS) // const.SCALING_FACTOR * math.pi / 2

        # print("\n", new_commands)
        # for c in new_commands:
        #     print(c, end="/")

        print()
        # print(new_commands)
        self.commands = new_commands
        return time, total_distance, new_commands

    def plan_path_test(self, penalties: list):
        # print("-" * 40)
        # print("STARTING PATH COMPUTATION...")
        # self.simple_hamiltonian = self.compute_simple_hamiltonian_path()
        # print()

        curr = self.robot.pos.copy()  # We use a copy rather than get a reference.
        for obstacle in self.simple_hamiltonian:
            target = obstacle.get_robot_target_pos()
            # print(f"Planning {curr} to {target}")
            res = ModifiedAStar(self.grid, self, curr, target).start_astar_test(penalties)
            if res is None:
                print(f"\tNo path found from {curr} to {obstacle}")
            else:
                # print("\tPath found.")
                curr = res
                self.commands.append(ScanCommand(const.ROBOT_SCAN_TIME, obstacle.index))

#       Return me path time, distance and penalties used
        return self.compress_paths_test()

