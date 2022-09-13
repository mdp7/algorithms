import os

import pygame

from mdpg7.config.constants import SimulatorConst


def draw_robot(simulator):
	win_x, win_y = simulator.robot.win_pos()
	image = pygame.image.load(os.path.join('assets', 'Car.png'))
	image = pygame.transform.rotate(image, simulator.robot.pos.theta_degrees())
	image = pygame.transform.scale(image, (SimulatorConst.ROBOT_WIDTH, SimulatorConst.ROBOT_HEIGHT))
	image_rect = image.get_rect()
	image_rect.center = win_x, win_y
	simulator.surface.blit(image, image_rect)
