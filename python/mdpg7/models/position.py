import math

from mdpg7.models.node import Node
from mdpg7.utils.position_utils import x_to_cell_x, y_to_cell_y, theta_to_facing


class Position:
    
    def __init__(self, x, y, theta):
        self.x = x
        self.y = y
        self.theta = theta
    
    def pos2d(self):
        return self.x, self.y
    
    def theta_degrees(self):
        return math.degrees(self.theta)
    
    def pos_to_node(self):
        return Node(x_to_cell_x(self.x), y_to_cell_y(self.y), theta_to_facing(self.theta), 0, 0, None)
    
    @classmethod
    def new(cls, position):
        return cls(position.x, position.y, position.theta)
