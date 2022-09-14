from queue import PriorityQueue
from typing import List, Tuple

from mdpg7.config.constants import Moves, RobotConst, ArenaConst
from mdpg7.models.cell_position import CellPosition


# TODO: may be improved
def get_h(board, node):
    return abs(board.goal.cell_pos.cell_x - node.cell_pos.cell_x) + \
           abs(board.goal.cell_pos.cell_y - node.cell_pos.cell_y)


def get_neighbors(board, node) -> List[Tuple[CellPosition, Moves]]:
    neighbors: List[Tuple[CellPosition, Moves]] = list()
    node_cell_x, node_cell_y, node_facing = node.cell_pos.cell_x, node.cell_pos.cell_y, node.cell_pos.facing
    for move in Moves:
        neighbor_dx, neighbor_dy = RobotConst.MOVES_DXY[move][node_facing]
        neighbor_cell_x = node_cell_x + neighbor_dx
        neighbor_cell_y = node_cell_y + neighbor_dy
        if (neighbor_cell_x < 0 or
                neighbor_cell_y < 0 or
                ArenaConst.NUM_COLS <= neighbor_cell_x or
                ArenaConst.NUM_ROWS <= neighbor_cell_y):
            continue
        valid = True
        for dx, dy in RobotConst.MOVES_VALID_CHECK_DXY[move][node_facing]:
            step_cell_x = node_cell_x + dx
            step_cell_y = node_cell_y + dy
            if not board.valid[step_cell_y][step_cell_x]:
                valid = False
        if not valid:
            continue
        neighbor_facing = RobotConst.MOVES_NEXT_FACING[move][node_facing]
        neighbors.append((CellPosition(neighbor_cell_x, neighbor_cell_y, neighbor_facing), move))
    return neighbors


def run_simple_hybrid_astar(board):
    open_queue = PriorityQueue()
    open_queue.put(board.start)
    board.start.g = 0
    board.start.h = get_h(board, board.start)
    board.open = True
    
    while not open_queue.empty():
        node = open_queue.get()
        
        if node.close:
            continue
        
        node.open = False
        node.close = True
        
        if node is board.goal:
            return
        
        neighbor_info = get_neighbors(board, node)
        for neighbor_cell_position, neighbor_move in neighbor_info:
            neighbor_cell_x = neighbor_cell_position.cell_x
            neighbor_cell_y = neighbor_cell_position.cell_y
            neighbor_facing = neighbor_cell_position.facing
            neighbor = board.nodes[neighbor_cell_y][neighbor_cell_x][neighbor_facing]
            
            if neighbor.close:
                continue
            
            penalty = 1
            penalty *= RobotConst.MOVES_PENALTY[neighbor_move]
            if node.move != neighbor_move:
                penalty *= RobotConst.CHANGE_MOVES_PENALTY
            
            neighbor_g = node.g + penalty
            
            if neighbor.open and neighbor_g < neighbor.g:
                neighbor.g = neighbor_g
                neighbor.move = neighbor_move
                neighbor.predecessor = node
                open_queue.put(neighbor)
            
            elif not neighbor.open:
                neighbor.g = neighbor_g
                neighbor.h = get_h(board, neighbor)
                neighbor.move = neighbor_move
                neighbor.open = True
                neighbor.predecessor = node
                open_queue.put(neighbor)
