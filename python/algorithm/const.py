import socket

# Obstacle Layout
OBSTACLE_LAYOUT = [
    [[55, 75, -90], [55, 135, 180], [125, 95, 0], [155, 45, 90], [155, 155, -90]],
    [[15, 145, -90], [145, 105, 180]],
    [[15, 95, 90], [45, 125, -90], [75, 95, 0], [135, 115, 180], [115, 35, 90]],
    [[25, 185, -90], [125, 185, -90], [105, 25, 0], [15, 115, -90], [125, 85, 180]],
    [[25, 185, -90], [125, 185, -90], [105, 25, 180], [15, 115, -90], [125, 85, 180]],
    [[15, 125, -90], [45, 185, -90], [115, 185, -90], [175, 115, 180], [115, 45, 90]],
    [[165, 145, 90], [165, 65, -90], [45, 75, 0], [35, 145, 0]],
    [[65, 65, -90], [155, 75, 180], [155, 135, 180], [45, 165, 0]]#, [65, 115, 180]]
]

# PyGame settings
SCALING_FACTOR = 4
FRAMES = 60
WINDOW_SIZE = 800, 800

# Connection to RPi
RPI_HOST: str = "192.168.8.8"
RPI_PORT: int = 4160

# Connection to PC
PC_HOST: str = socket.gethostbyname(socket.gethostname())
PC_PORT: int = 4161

# Robot Attributes
ROBOT_LENGTH = 35 * SCALING_FACTOR
ROBOT_TURN_RADIUS = 30 * SCALING_FACTOR
ROBOT_LEFT_TURN_RADIUS = 20 * SCALING_FACTOR
ROBOT_RIGHT_TURN_RADIUS = 30 * SCALING_FACTOR


ROBOT_SPEED_PER_SECOND = 100 * SCALING_FACTOR
ROBOT_S_FACTOR = ROBOT_LENGTH / ROBOT_TURN_RADIUS  # Please read briefing notes from Imperial
ROBOT_SAFETY_DISTANCE = 15 * SCALING_FACTOR
ROBOT_SCAN_TIME = 0.25  # Time provided for scanning an obstacle image in seconds.

ROBOT_CUSTOM_START_X = 105 * SCALING_FACTOR
ROBOT_CUSTOM_START_Y = 25 * SCALING_FACTOR


# Grid Attributes
GRID_LENGTH = 200 * SCALING_FACTOR
GRID_CELL_LENGTH = 10 * SCALING_FACTOR
GRID_START_BOX_LENGTH = 30 * SCALING_FACTOR
GRID_NUM_GRIDS = GRID_LENGTH // GRID_CELL_LENGTH

# Obstacle Attributes
OBSTACLE_LENGTH = 10 * SCALING_FACTOR
# OBSTACLE_SAFETY_WIDTH = ROBOT_SAFETY_DISTANCE // 3 * 4  # With respect to the center of the obstacle
OBSTACLE_SAFETY_WIDTH = 25 * SCALING_FACTOR
OBSTACLE_TARGET_OFFSET = 0 * SCALING_FACTOR

# Path Finding Attributes
PATH_TURN_COST = 999 * ROBOT_SPEED_PER_SECOND * ROBOT_TURN_RADIUS

TPT_MUL = 1.05

# NOTE: Higher number == Lower Granularity == Faster Checking.
# Must be an integer more than 0! Number higher than 3 not recommended.
PATH_TURN_CHECK_GRANULARITY = 1

