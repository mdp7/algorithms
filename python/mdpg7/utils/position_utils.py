"""
Conversion among pos, win_pos, cell_pos
pos is a float representing the position of a point on the actual arena with origin at bottom-left
win_pos is a float representing the position of a point on the simulator with origin at top-left
cell_pos is an int representing the position of a cell on the arena board with origin at bottom-left
"""
import math
from typing import Tuple

from mdpg7.config.constants import ArenaConst, SimulatorConst, Facing


def x_to_win_x(x: float) -> float:
    return x * SimulatorConst.WINDOW_SCALE_X


def y_to_win_y(y: float) -> float:
    return SimulatorConst.WINDOW_HEIGHT - (y * SimulatorConst.WINDOW_SCALE_Y)


def pos_to_win_pos(pos: Tuple[float, float]) -> Tuple[float, float]:
    return x_to_win_x(pos[0]), y_to_win_y(pos[1])


def win_x_to_x(win_x: float) -> float:
    return win_x / SimulatorConst.WINDOW_SCALE_X


def win_y_to_y(win_y: float) -> float:
    return (SimulatorConst.WINDOW_HEIGHT - win_y) / SimulatorConst.WINDOW_SCALE_Y


def win_pos_to_pos(win_pos: Tuple[float, float]) -> Tuple[float, float]:
    return win_x_to_x(win_pos[0]), win_y_to_y(win_pos[1])


def x_to_cell_x(x: float) -> int:
    return x // ArenaConst.CELL_WIDTH


def y_to_cell_y(y: float) -> int:
    return y // ArenaConst.CELL_HEIGHT


def pos_to_cell_pos(pos: Tuple[float, float]) -> Tuple[int, int]:
    return x_to_cell_x(pos[0]), y_to_cell_y(pos[1])


def cell_x_to_x(cell_x: int) -> float:
    """returns the center of the cell"""
    return cell_x * ArenaConst.CELL_WIDTH + ArenaConst.CELL_WIDTH / 2


def cell_y_to_y(cell_y: int) -> float:
    """returns the center of the cell"""
    return cell_y * ArenaConst.CELL_HEIGHT + ArenaConst.CELL_HEIGHT / 2


def cell_pos_to_pos(cell_pos: Tuple[int, int]) -> Tuple[float, float]:
    """returns the center of the cell"""
    return cell_x_to_x(cell_pos[0]), cell_y_to_y(cell_pos[1])


def win_x_to_cell_x(win_x: float) -> int:
    return x_to_cell_x(win_x_to_x(win_x))


def win_y_to_cell_y(win_y: float) -> int:
    return y_to_cell_y(win_y_to_y(win_y))


def win_pos_to_cell_pos(win_pos: Tuple[float, float]) -> Tuple[int, int]:
    return pos_to_cell_pos(win_pos_to_pos(win_pos))


def cell_x_to_win_x(cell_x: int) -> float:
    return x_to_win_x(cell_x_to_x(cell_x))


def cell_y_to_win_y(cell_y: int) -> float:
    return y_to_win_y(cell_y_to_y(cell_y))


def cell_pos_to_win_pos(cell_pos: Tuple[int, int]) -> Tuple[float, float]:
    return cell_x_to_win_x(cell_pos[0]), cell_y_to_win_y(cell_pos[1])


def theta_to_facing(theta: float) -> Facing:
    if -135 < theta <= -45:
        return Facing.D
    if -45 < theta <= 45:
        return Facing.R
    if 45 < theta <= 135:
        return Facing.U
    return Facing.L
