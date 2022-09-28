"""
Robot UI
"""
import math
import os
from typing import Union

import pygame

from mdpg7.config.constants import SimulatorConst, Moves, RobotConst
from mdpg7.utils.position_utils import theta_to_facing


class RobotView:
    """Stores robot information in UI"""
    
    def __init__(self, win_x, win_y, theta):
        self.win_x = win_x
        self.win_y = win_y
        self.theta = theta
        self.image = pygame.transform.scale(
            pygame.image.load(os.path.join('assets', 'Car.png')),
            (SimulatorConst.ROBOT_WIDTH, SimulatorConst.ROBOT_HEIGHT)
        )
    
    def facing(self):
        return theta_to_facing(self.theta)
    
    def set_theta(self, theta):
        self.theta = theta
        self.regularize_theta()
    
    def regularize_theta(self):
        while self.theta > 180:
            self.theta -= 360
        while self.theta <= -180:
            self.theta += 360


class RobotViewCommand:
    """This class is called each tick to handle command and update UI"""
    
    def __init__(self, command, robot_view):
        self.robot_view = robot_view
        self.tick = 0
        facing = robot_view.facing()
        
        fdx, fdy = RobotConst.MOVES_FUNC_DXY[command.move][facing]
        bx, by, bt = robot_view.win_x, robot_view.win_y, robot_view.theta
        if command.move in (Moves.FF, Moves.BB):
            dist = SimulatorConst.WINDOW_CELL_WIDTH * command.repeat
            self.ticks = int((SimulatorConst.FRAMES_PER_SECOND * dist) // SimulatorConst.ROBOT_SPEED)
            radius = 0
            step = dist / self.ticks
            step_t = 0
        else:
            dist = SimulatorConst.WINDOW_CELL_WIDTH * math.pi * command.repeat
            self.ticks = int((SimulatorConst.FRAMES_PER_SECOND * dist) // SimulatorConst.ROBOT_SPEED)
            radius = SimulatorConst.ROBOT_TURN_RADIUS
            step = ((math.pi / 2) * command.repeat) / self.ticks
            step_t = 90 * command.repeat / self.ticks
        
        self.fx = lambda t: fdx(radius, step, t) + bx
        self.fy = lambda t: fdy(radius, step, t) + by
        self.ft = lambda t: RobotConst.MOVES_SIGN_T[command.move] * step_t * t + bt
        
        # print(command)
    
    def forward(self) -> Union['RobotViewCommand', None]:
        self.tick += 1
        self.robot_view.win_x = self.fx(self.tick)
        self.robot_view.win_y = self.fy(self.tick)
        self.robot_view.set_theta(self.ft(self.tick))
        if self.tick != self.ticks:
            return self
        else:
            return None


def draw_robot(simulator):
    """Redraw robot on screen"""
    win_x, win_y = simulator.robot_view.win_x, simulator.robot_view.win_y
    image = pygame.transform.rotate(simulator.robot_view.image, simulator.robot_view.theta)
    image_rect = image.get_rect()
    image_rect.center = int(win_x), int(win_y)
    simulator.surface.blit(image, image_rect)
