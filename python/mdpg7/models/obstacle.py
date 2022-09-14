from mdpg7.models.position import Position
from mdpg7.utils.position_utils import *


class Obstacle:
    
    def __init__(self):
        self.cell_x = -1
        self.cell_y = -1
        self.facing = None
        self.detected = False
        self.image = None
    
    def initialize(self, cell_x, cell_y, facing, image):
        self.cell_x = cell_x
        self.cell_y = cell_y
        self.facing = facing
        self.image = image
    
    def cell_pos(self):
        return self.cell_x, self.cell_y
    
    def x(self):
        return cell_x_to_x(self.cell_x)
    
    def y(self):
        return cell_y_to_y(self.cell_y)
    
    def pos(self):
        return cell_pos_to_pos(self.cell_pos())
    
    def win_x(self):
        return cell_x_to_win_x(self.cell_x)
    
    def win_y(self):
        return cell_y_to_win_y(self.cell_y)
    
    def win_pos(self):
        return cell_pos_to_win_pos(self.cell_pos())
    
    def get_target_node(self):
        win_x, win_y = self.win_pos()
        theta = 0
        if self.facing == Facing.R:
            win_x += ArenaConst.ROBOT_OBSTACLE_CENTER_DETECT_DISTANCE
            theta = math.pi
        elif self.facing == Facing.U:
            win_y -= ArenaConst.ROBOT_OBSTACLE_CENTER_DETECT_DISTANCE
            theta = -math.pi / 2
        elif self.facing == Facing.L:
            win_x -= ArenaConst.ROBOT_OBSTACLE_CENTER_DETECT_DISTANCE
            theta = 0
        elif self.facing == Facing.D:
            win_x += ArenaConst.ROBOT_OBSTACLE_CENTER_DETECT_DISTANCE
            theta = math.pi / 2
        return Position(win_x, win_y, theta).pos_to_node()
    
    def __str__(self):
        return f'<Obstacle x: {self.cell_x}, y: {self.cell_y}, image: {self.image.name}>'
    
    def __repr__(self):
        return self.__str__()
    
    @classmethod
    def new(cls, cell_x, cell_y, facing, image):
        obstacle = cls()
        obstacle.initialize(cell_x, cell_y, facing, image)
        return obstacle
