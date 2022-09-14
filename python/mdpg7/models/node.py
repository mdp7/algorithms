from typing import Union

from mdpg7.models.cell_position import CellPosition


class Node:
    
    def __init__(self, cell_pos: CellPosition):
        self.cell_pos = cell_pos
        self.g = 0.
        self.h = 0.
        self.move = None
        self.open = False
        self.close = False
        self.predecessor: Union[Node, None] = None
    
    def score(self) -> float:
        return self.g + self.h
    
    def __cmp__(self, other):
        return self.score().__cmp__(other.score())
    
    def __gt__(self, other):
        return self.score().__gt__(other.score())
    
    def __str__(self):
        return f'{self.cell_pos}, g: {self.g}, h: {self.h}, open: {self.open}'
