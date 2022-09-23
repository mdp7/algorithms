"""
Position of any 10x10 square on board
"""
from mdpg7.utils.position_utils import *


class CellPosition:
    
    def __init__(self, cell_x: int, cell_y: int, facing: Facing) -> None:
        self.cell_x = cell_x
        self.cell_y = cell_y
        self.facing = facing
    
    def cell_pos2d(self):
        return self.cell_x, self.cell_y
    
    def pos(self):
        return cell_pos_to_pos(self.cell_pos2d())
    
    def win_pos(self):
        return cell_pos_to_win_pos(self.cell_pos2d())
    
    def __str__(self):
        return f'x: {self.cell_x}, y: {self.cell_y}, facing: {self.facing.name}'
    
    def __repr__(self):
        return self.__str__()
