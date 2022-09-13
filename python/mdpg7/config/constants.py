import math
from enum import Enum


class ArenaConst:
	NUM_ROWS = 20
	NUM_COLS = 20
	CELL_WIDTH = 10  # cm
	CELL_HEIGHT = 10  # cm
	ARENA_WIDTH = 200  # cm
	ARENA_HEIGHT = 200  # cm
	ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE = 20


class RobotConst:
	START_X = 15
	START_Y = 15
	START_T = math.pi / 2
	WIDTH = 30
	HEIGHT = 30


class ObstacleConst:
	FACING_N = -1
	FACING_R = 0
	FACING_U = 1
	FACING_L = 2
	FACING_D = 3


class ObstacleImage(Enum):
	A = 1
	B = 2
	C = 3
	CIRCLE = 4
	D = 5
	DOWN = 6
	E = 7
	EIGHT = 8
	F = 9
	FIVE = 10
	FOUR = 11
	G = 12
	H = 13
	LEFT = 14
	NINE = 15
	ONE = 16
	RIGHT = 17
	S = 18
	SEVEN = 19
	SIX = 20
	T = 21
	THREE = 22
	TWO = 23
	U = 24
	UP = 25
	V = 26
	W = 27
	X = 28
	Y = 29
	Z = 30


class Color:
	WHITE = (255, 255, 255)
	BLACK = (0, 0, 0)
	RED = (127, 0, 0)
	GREEN = (0, 127, 0)
	BLUE = (0, 0, 127)
	GREY = (127, 127, 127)


class SimulatorConst:
	# UI constants
	WINDOW_WIDTH = 600
	WINDOW_HEIGHT = 600
	WINDOW_SIZE = (WINDOW_WIDTH, WINDOW_HEIGHT)
	WINDOW_CELL_WIDTH = WINDOW_WIDTH / ArenaConst.NUM_COLS
	WINDOW_CELL_HEIGHT = WINDOW_HEIGHT / ArenaConst.NUM_ROWS
	WINDOW_CELL_SIZE = (WINDOW_CELL_WIDTH, WINDOW_CELL_HEIGHT)
	WINDOW_CELL_WIDTH_RADIUS = WINDOW_CELL_WIDTH / 2
	WINDOW_CELL_HEIGHT_RADIUS = WINDOW_CELL_HEIGHT / 2
	WINDOW_IMAGE_WIDTH = WINDOW_CELL_WIDTH / 2
	WINDOW_IMAGE_HEIGHT = WINDOW_CELL_HEIGHT / 2
	WINDOW_IMAGE_SIZE = (WINDOW_IMAGE_WIDTH, WINDOW_IMAGE_HEIGHT)
	WINDOW_IMAGE_WIDTH_RADIUS = WINDOW_IMAGE_WIDTH / 2
	WINDOW_IMAGE_HEIGHT_RADIUS = WINDOW_IMAGE_HEIGHT / 2
	WINDOW_SCALE_X = WINDOW_WIDTH / ArenaConst.ARENA_WIDTH
	WINDOW_SCALE_Y = WINDOW_HEIGHT / ArenaConst.ARENA_HEIGHT
	WINDOW_ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE = ArenaConst.ROBOT_OBSTACLE_CENTER_SAFE_DISTANCE * WINDOW_SCALE_X

	ROBOT_WIDTH = RobotConst.WIDTH * WINDOW_SCALE_X
	ROBOT_HEIGHT = RobotConst.HEIGHT * WINDOW_SCALE_Y

	FRAMES_PER_SECOND = 15

	ARENA_BORDER_LINE_WIDTH = 4
	ARENA_BORDER_LINE_COLOR = Color.GREEN

	CELL_BORDER_LINE_WIDTH = 2
	CELL_BORDER_LINE_COLOR = Color.GREY

	OBSTACLE_COLOR = Color.BLUE

	OBSTACLE_BORDER_LINE_WIDTH = 4
	OBSTACLE_BORDER_LINE_COLOR = Color.RED

	# logic constants
	MODE_NULL = -1
	MODE_DISPLAY_DEFAULT_MAP = 0
	MODE_CUSTOMIZE_MAP = 1
	MODE_FIND_PATH = 2
	MODE_DISPLAY_ANIMATION = 3
	MODE_DISPLAY_RESULT = 4