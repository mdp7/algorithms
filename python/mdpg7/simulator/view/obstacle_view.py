"""
Obstacle UI
"""
import os
import sys

import pygame

from mdpg7.config.constants import SimulatorConst, Facing
from mdpg7.models.obstacle import Obstacle
from mdpg7.utils.log_utils import print_warning


def draw_obstacle(simulator, obstacle: Obstacle):
    # TODO: add stop position
    win_x, win_y = obstacle.cell_pos.win_pos()
    tl_x = win_x - SimulatorConst.WINDOW_CELL_WIDTH_RADIUS + SimulatorConst.CELL_BORDER_LINE_WIDTH
    tl_y = win_y - SimulatorConst.WINDOW_CELL_HEIGHT_RADIUS + SimulatorConst.CELL_BORDER_LINE_WIDTH
    cell_w = SimulatorConst.WINDOW_CELL_WIDTH - SimulatorConst.CELL_BORDER_LINE_WIDTH
    cell_h = SimulatorConst.WINDOW_CELL_HEIGHT - SimulatorConst.CELL_BORDER_LINE_WIDTH
    obstacle_rect = pygame.Rect((tl_x, tl_y), (cell_w, cell_h))
    pygame.draw.rect(simulator.surface, SimulatorConst.OBSTACLE_COLOR, obstacle_rect)
    
    # tl_border_x = win_x - SimulatorConst.WINDOW_ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE
    # tl_border_y = win_y - SimulatorConst.WINDOW_ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE
    # border_rect = pygame.Rect(
    #     tl_border_x,
    #     tl_border_y,
    #     SimulatorConst.WINDOW_ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE * 2 + SimulatorConst.CELL_BORDER_LINE_WIDTH,
    #     SimulatorConst.WINDOW_ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE * 2 + SimulatorConst.CELL_BORDER_LINE_WIDTH
    # )
    # pygame.draw.rect(
    #     simulator.surface,
    #     SimulatorConst.OBSTACLE_BORDER_LINE_COLOR,
    #     border_rect,
    #     SimulatorConst.OBSTACLE_BORDER_LINE_WIDTH
    # )
    
    image_x, image_y = win_x, win_y
    if obstacle.cell_pos.facing == Facing.R:
        image_x += SimulatorConst.WINDOW_IMAGE_WIDTH / 2
    elif obstacle.cell_pos.facing == Facing.U:
        image_y -= SimulatorConst.WINDOW_IMAGE_HEIGHT / 2 - SimulatorConst.CELL_BORDER_LINE_WIDTH
    elif obstacle.cell_pos.facing == Facing.L:
        image_x -= SimulatorConst.WINDOW_IMAGE_WIDTH / 2 - SimulatorConst.CELL_BORDER_LINE_WIDTH
    elif obstacle.cell_pos.facing == Facing.D:
        image_y += SimulatorConst.WINDOW_IMAGE_HEIGHT / 2
    else:
        # this line shouldn't be reached
        print_warning('ERROR: attempting to draw obstacle with no facing')
        sys.exit(1)
    
    image = pygame.image.load(os.path.join('assets', f'{obstacle.image.name}.png'))
    image = pygame.transform.scale(image, (SimulatorConst.WINDOW_IMAGE_WIDTH, SimulatorConst.WINDOW_IMAGE_HEIGHT))
    image_rect = image.get_rect()
    image_rect.center = image_x, image_y
    simulator.surface.blit(image, image_rect)


def draw_obstacles(simulator):
    for obstacle in simulator.arena.obstacles:
        draw_obstacle(simulator, obstacle)
