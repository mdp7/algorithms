import pygame

from mdpg7.algorithm.pathfinder import plan_paths
from mdpg7.config.constants import SimulatorConst


def handle_events_display_default_map(simulator, events):
    for event in events:
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE or event.key == pygame.K_RETURN:
                print('Start path planning')
                simulator.mode = SimulatorConst.MODE_FIND_PATH
                simulator.commands = plan_paths(simulator.arena, simulator.robot)
                # TODO: run path planning in a separate thread


def draw_display_default_map(simulator):
    pass
