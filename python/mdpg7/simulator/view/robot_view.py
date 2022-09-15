import os
from typing import Union

import pygame

from mdpg7.config.constants import SimulatorConst, Moves, Facing
from mdpg7.utils.position_utils import theta_to_facing


class RobotView:
    
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


class RobotViewCommand:
    
    def __init__(self, command, robot_view):
        self.robot_view = robot_view
        self.tick = 0
        self.dxs = list()
        self.dys = list()
        facing = robot_view.facing()
        try:
            if command.move in (Moves.FF, Moves.BB):
                self.ticks = int((SimulatorConst.FRAMES_PER_SECOND * SimulatorConst.WINDOW_CELL_WIDTH * command.repeat) // \
                    SimulatorConst.ROBOT_SPEED)
                dist = SimulatorConst.WINDOW_CELL_WIDTH * command.repeat
                step = dist / self.ticks
                self.dest_t = 0
                if facing == Facing.R and command.move == Moves.FF or facing == Facing.L and command.move == Moves.BB:
                    self.dxs = [step for _ in range(self.ticks)]
                    self.dys = [0 for _ in range(self.ticks)]
                    self.dest_x = robot_view.win_x + dist
                    self.dest_y = robot_view.win_y
                if facing == Facing.R and command.move == Moves.BB or facing == Facing.L and command.move == Moves.FF:
                    self.dxs = [-step for _ in range(self.ticks)]
                    self.dys = [0 for _ in range(self.ticks)]
                    self.dest_x = robot_view.win_x - dist
                    self.dest_y = robot_view.win_y
                if facing == Facing.U and command.move == Moves.FF or facing == Facing.D and command.move == Moves.BB:
                    self.dxs = [0 for _ in range(self.ticks)]
                    self.dys = [-step for _ in range(self.ticks)]
                    self.dest_x = robot_view.win_x
                    self.dest_y = robot_view.win_y - dist
                if facing == Facing.U and command.move == Moves.BB or facing == Facing.D and command.move == Moves.FF:
                    self.dxs = [0 for _ in range(self.ticks)]
                    self.dys = [step for _ in range(self.ticks)]
                    self.dest_x = robot_view.win_x
                    self.dest_y = robot_view.win_y + dist
            else:
                self.tick = 0
                self.ticks = 1
                if facing == Facing.R and command.move == Moves.LF or facing == Facing.L and command.move == Moves.RB:
                    self.dest_x = robot_view.win_x + 2 * 30
                    self.dest_y = robot_view.win_y - 2 * 30
                    self.dest_t = 90
                if facing == Facing.R and command.move == Moves.RF or facing == Facing.L and command.move == Moves.LB:
                    self.dest_x = robot_view.win_x + 2 * 30
                    self.dest_y = robot_view.win_y + 2 * 30
                    self.dest_t = -90
                if facing == Facing.R and command.move == Moves.LB or facing == Facing.L and command.move == Moves.RF:
                    self.dest_x = robot_view.win_x - 2 * 30
                    self.dest_y = robot_view.win_y - 2 * 30
                    self.dest_t = -90
                if facing == Facing.R and command.move == Moves.RB or facing == Facing.L and command.move == Moves.LF:
                    self.dest_x = robot_view.win_x - 2 * 30
                    self.dest_y = robot_view.win_y + 2 * 30
                    self.dest_t = 90

                if facing == Facing.U and command.move == Moves.LF or facing == Facing.D and command.move == Moves.RB:
                    self.dest_x = robot_view.win_x - 2 * 30
                    self.dest_y = robot_view.win_y - 2 * 30
                    self.dest_t = 90
                if facing == Facing.U and command.move == Moves.RF or facing == Facing.D and command.move == Moves.LB:
                    self.dest_x = robot_view.win_x + 2 * 30
                    self.dest_y = robot_view.win_y - 2 * 30
                    self.dest_t = -90
                if facing == Facing.U and command.move == Moves.LB or facing == Facing.D and command.move == Moves.RF:
                    self.dest_x = robot_view.win_x - 2 * 30
                    self.dest_y = robot_view.win_y + 2 * 30
                    self.dest_t = -90
                if facing == Facing.U and command.move == Moves.RB or facing == Facing.D and command.move == Moves.LF:
                    self.dest_x = robot_view.win_x + 2 * 30
                    self.dest_y = robot_view.win_y + 2 * 30
                    self.dest_t = 90
            print(command, robot_view.facing())

        except:
            print("Taking picture!")
    
    def forward(self) -> Union['RobotViewCommand', None]:
        self.tick += 1
        if self.tick != self.ticks:
            self.robot_view.win_x += self.dxs[self.tick - 1]
            self.robot_view.win_y += self.dys[self.tick - 1]
            return self
        else:
            self.robot_view.win_x = self.dest_x
            self.robot_view.win_y = self.dest_y
            self.robot_view.theta += self.dest_t
            return None


def draw_robot(simulator):
    win_x, win_y = simulator.robot_view.win_x, simulator.robot_view.win_y
    image = pygame.transform.rotate(simulator.robot_view.image, simulator.robot_view.theta)
    image_rect = image.get_rect()
    image_rect.center = int(win_x), int(win_y)
    simulator.surface.blit(image, image_rect)
