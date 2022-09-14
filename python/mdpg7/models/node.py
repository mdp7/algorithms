from typing import Union

from mdpg7.models.cell_position import CellPosition


class Node:
    
    def __init__(self, cell_pos: CellPosition):
        self.cell_pos = cell_pos
        self.g = 0.
        self.h = 0.
        self.open = False
        self.close = False
        self.predecessor: Union[Node, None] = None
    
    def score(self) -> float:
        return self.g + self.h
    
    def __cmp__(self, other):
        return self.score().__cmp__(other.score())
