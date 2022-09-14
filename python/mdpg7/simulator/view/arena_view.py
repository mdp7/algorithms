import pygame

from mdpg7.config.constants import ArenaConst, SimulatorConst
from mdpg7.simulator.view.obstacle_view import draw_obstacles


def draw_arena(simulator):
    for i in range(ArenaConst.NUM_ROWS):
        pygame.draw.line(
            simulator.surface,
            SimulatorConst.CELL_BORDER_LINE_COLOR,
            (0, i * SimulatorConst.WINDOW_CELL_HEIGHT),
            (SimulatorConst.WINDOW_WIDTH, i * SimulatorConst.WINDOW_CELL_HEIGHT),
            SimulatorConst.CELL_BORDER_LINE_WIDTH
        )
    for i in range(ArenaConst.NUM_COLS):
        pygame.draw.line(
            simulator.surface,
            SimulatorConst.CELL_BORDER_LINE_COLOR,
            (i * SimulatorConst.WINDOW_CELL_WIDTH, 0),
            (i * SimulatorConst.WINDOW_CELL_WIDTH, SimulatorConst.WINDOW_HEIGHT),
            SimulatorConst.CELL_BORDER_LINE_WIDTH
        )
    # green border for the arena
    # pygame.draw.rect(
    # 	simulator.surface,
    # 	SimulatorConst.ARENA_BORDER_LINE_COLOR,
    # 	pygame.Rect(0, 0, SimulatorConst.WINDOW_WIDTH, SimulatorConst.WINDOW_HEIGHT),
    # 	SimulatorConst.ARENA_BORDER_LINE_WIDTH
    # )
    draw_obstacles(simulator)
