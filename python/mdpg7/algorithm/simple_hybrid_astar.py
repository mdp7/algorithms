from queue import PriorityQueue
from typing import List

from mdpg7.config.constants import Moves
from mdpg7.models.node import Node


# TODO: may be improved
def get_h(board, node):
    return abs(board.goal.cell_pos.cell_x - node.cell_pos.cell_x) + \
           abs(board.goal.cell_pos.cell_y - node.cell_pos.cell_y)


def get_neighbors(board, node) -> List[Node]:
    neighbors: List[Node] = list()
    for move in Moves:
        pass
    return neighbors


def run_simple_hybrid_astar(board):
    p_index = None
    s_index = None
    new_g = None
    
    open_queue = PriorityQueue()
    open_queue.put(board.start)
    board.start.g = 0
    board.start.h = get_h(board, board.start)
    board.open = True
    
    while not open_queue.empty():
        node = open_queue.get()
        node.open = False
        node.close = True
        
        if node is board.goal:
            return
        
        neighbors = get_neighbors(board, node)
        for neighbor in neighbors:
            if neighbor.open:
                pass
