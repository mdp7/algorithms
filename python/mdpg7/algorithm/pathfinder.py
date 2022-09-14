"""
pathfinder should be isolated from simulator
"""

import itertools
import time
from typing import List, Tuple

from mdpg7.algorithm.simple_hybrid_astar import run_simple_hybrid_astar
from mdpg7.models.arena import Arena
from mdpg7.models.board import Board
from mdpg7.models.node import Node
from mdpg7.models.obstacle import Obstacle
from mdpg7.models.robot import Robot
from mdpg7.utils.math_utils import dist_pos


def compute_simple_hamiltonian_path(arena: Arena, robot: Robot):
    # TODO: switch heuristic to simple hybrid astar
    # TODO: another option is to switch to variable distance
    permutations = tuple(itertools.permutations(arena.obstacles))
    
    def path_distance(path):
        nodes = [robot.cell_pos.cell_pos2d()] + [obstacle.cell_pos.cell_pos2d() for obstacle in path]
        dist = 0
        for i in range(len(nodes) - 1):
            dist += dist_pos(nodes[i], nodes[i + 1])
        return dist
    
    return min(permutations, key=path_distance)


def plan_paths(arena: Arena, robot: Robot):
    start = time.time()
    simple_hamiltonian_path = compute_simple_hamiltonian_path(arena, robot)
    print('Found hamiltonian path')
    print(simple_hamiltonian_path)
    
    for obstacle in simple_hamiltonian_path:
        board = Board.from_arena(arena)
        board.start = Node(robot.cell_pos)
        board.goal = Node(obstacle.cell_pos)
        run_simple_hybrid_astar(board)
        
    end = time.time()
    print('Done path planning')
    print(f'Total time consumed: {(end - start) / 10e6:.2f} seconds')
