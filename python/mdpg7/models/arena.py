"""
Collection of obstacles
"""
from typing import List, Union, Any

from mdpg7.config.constants import ArenaConst
from mdpg7.models.obstacle import Obstacle


class Arena:
    
    def __init__(self):
        self.arena: List[List[Union[Obstacle, Any]]] = \
            [[None for _ in range(ArenaConst.NUM_COLS)] for _ in range(ArenaConst.NUM_ROWS)]
        self.obstacles: List[Obstacle] = list()
    
    def add_obstacle(self, obstacle: Obstacle):
        if self.arena[obstacle.cell_pos.cell_y][obstacle.cell_pos.cell_x] is None:
            self.arena[obstacle.cell_pos.cell_y][obstacle.cell_pos.cell_x] = obstacle
            self.obstacles.append(obstacle)
    
    def remove_obstacle(self, cell_x, cell_y):
        obstacle = self.arena[cell_y][cell_x]
        self.obstacles.remove(obstacle)
        self.arena[cell_y][cell_x] = None
