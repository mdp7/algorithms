import pygame

from mdpg7.algorithm.pathfinder import plan_paths
from mdpg7.config.constants import SimulatorConst
from mdpg7.simulator.view.arena_view import draw_arena


def handle_events_display_default_map(simulator, events):
	for event in events:
		if event.type == pygame.MOUSEBUTTONDOWN:
			simulator.mode = SimulatorConst.MODE_FIND_PATH
			plan_paths(simulator.arena, simulator.robot)


def draw_display_default_map(simulator):
	pass
