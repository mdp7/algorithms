import os

import pygame

from mdpg7.config.constants import SimulatorConst

image = pygame.image.load(os.path.join('assets', 'Car.png'))


def draw_robot(simulator):
    win_x, win_y = simulator.robot.cell_pos.win_pos()
    current_image = pygame.transform.rotate(image, 0)
    current_image = pygame.transform.scale(current_image, (SimulatorConst.ROBOT_WIDTH, SimulatorConst.ROBOT_HEIGHT))
    image_rect = current_image.get_rect()
    image_rect.center = win_x, win_y
    simulator.surface.blit(current_image, image_rect)
