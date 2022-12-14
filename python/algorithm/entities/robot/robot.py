import datetime

import pygame

from algorithm import const
from algorithm.entities.assets import colors
from algorithm.entities.assets.direction import Direction
from algorithm.entities.commands.command import Command
from algorithm.entities.commands.left_command import LeftTurn
from algorithm.entities.commands.right_command import RightTurn
from algorithm.entities.commands.straight_command import StraightCommand
from algorithm.entities.commands.tpt_command import TPTCommand
from algorithm.entities.commands.turn_command import TurnCommand
from algorithm.entities.grid.position import RobotPosition
from algorithm.entities.robot.brain.brain import Brain


class Robot:
    def __init__(self, robot_x, robot_y, robot_dir: Direction, grid):
        # Note that we assume the robot starts always facing the top.
        self.pos = RobotPosition(
                                 # const.ROBOT_SAFETY_DISTANCE,
                                 # const.ROBOT_SAFETY_DISTANCE,
                                 # const.ROBOT_CUSTOM_START_X,
                                 # const.ROBOT_CUSTOM_START_Y,
                                 # Direction.TOP,
                                 robot_x * const.SCALING_FACTOR,
                                 robot_y * const.SCALING_FACTOR,
                                 robot_dir,
                                 robot_dir)
        # This value will never change, but it will not affect us as the robot uses a more fine-tuned internal
        # angle tracker.
        self._start_copy = self.pos.copy()
        
        self.brain = Brain(self, grid)
        
        # self.__image = pygame.transform.scale(pygame.image.load("entities/assets/robot.png"),
        #                                       (const.ROBOT_LENGTH*1.5, const.ROBOT_LENGTH // 1.2))
        
        self.path_hist = []  # Stores the history of the path taken by the robot.
        
        self.__current_command = 0  # Index of the current command being executed.
        self.printed = False  # Never printed total time before.
    
    def get_current_pos(self):
        return self.pos
    
    def convert_all_commands(self):
        """
        Convert the list of command objects to corresponding list of messages.
        """
        print("Converting commands to string...", end="")
        # string_commands = [command.convert_to_message() for command in self.brain.commands]

        result_str = ""
        for c in self.brain.commands:
            result_str += f"{c}/"
        print("Done!")
        return result_str
    
    def turn(self, d_angle, rev):
        """
        Turns the robot by the specified angle, and whether to do it in reverse or not.
        Take note that the angle is in radians.

        A negative angle will always cause the robot to be rotated in a clockwise manner, regardless
        of the value of rev.

        x_new = x + R(sin(????? + ??) ??? sin ??)
        y_new = y ??? R(cos(????? + ??) ??? cos ??)
        ??_new = ?? + ?????
        R is the turning radius.

        Take note that:
            - +ve ????? -> rotate counter-clockwise
            - -ve ????? -> rotate clockwise

        Note that ????? is in radians.
        """
        TurnCommand(d_angle, rev).apply_on_pos(self.pos)

    def left_turn(self, d_angle, rev):
        LeftTurn(d_angle, rev).apply_on_pos(self.pos)

    def right_turn(self, d_angle, rev):
        RightTurn(d_angle, rev).apply_on_pos(self.pos)
    def tptTurn(self, d_angle, rev):
        TPTCommand(d_angle, rev).apply_on_pos(self.pos)

    def straight(self, dist):
        """
        Make a robot go straight.

        A negative number indicates that the robot will move in reverse, and vice versa.
        """
        StraightCommand(dist).apply_on_pos(self.pos)
    
    def draw_simple_hamiltonian_path(self, screen):
        prev = self._start_copy.xy_pygame()
        for obs in self.brain.simple_hamiltonian:
            target = obs.get_robot_target_pos().xy_pygame()
            pygame.draw.line(screen, colors.DARK_GREEN, prev, target)
            prev = target
    
    def draw_self(self, screen):
        # The arrow to represent the direction of the robot.
        rot_image = pygame.transform.rotate(self.__image, -(90 - self.pos.angle))
        rect = rot_image.get_rect()
        rect.center = self.pos.xy_pygame()
        screen.blit(rot_image, rect)
    
    def draw_historic_path(self, screen):
        for dot in self.path_hist:
            pygame.draw.circle(screen, colors.BLACK, dot, 3)
    
    def draw(self, screen):
        # Draw the robot itself.
        self.draw_self(screen)
        # Draw the simple hamiltonian path found by the robot.
        self.draw_simple_hamiltonian_path(screen)
        # Draw the path sketched by the robot
        self.draw_historic_path(screen)
    
    def update(self):
        # Store historic path
        if len(self.path_hist) == 0 or self.pos.xy_pygame() != self.path_hist[-1]:
            # Only add a new point history if there is none, and it is different from previous history.
            self.path_hist.append(self.pos.xy_pygame())
        
        # If no more commands to execute, then return.
        if self.__current_command >= len(self.brain.commands):
            return
        
        # Check current command has non-null ticks.
        # Needed to check commands that have 0 tick execution time.
        if self.brain.commands[self.__current_command].total_ticks == 0:
            self.__current_command += 1
            if self.__current_command >= len(self.brain.commands):
                return
        
        # If not, the first command in the list is always the command to execute.
        command: Command = self.brain.commands[self.__current_command]
        command.process_one_tick(self)
        # If there are no more ticks to do, then we can assume that we have
        # successfully completed this command, and so we can remove it.
        # The next time this method is run, then we will process the next command in the list.
        if command.ticks <= 0:
            # print(f"Finished processing {command}, {self.pos}")
            self.__current_command += 1
            if self.__current_command == len(self.brain.commands) and not self.printed:
                total_time = 0
                for command in self.brain.commands:
                    total_time += command.time
                    total_time = round(total_time)
                # Calculate time for all commands
                # Then print it out.
                print(f"All commands took {datetime.timedelta(seconds=total_time)}")
                self.printed = True
