"""
pathfinder should be isolated from simulator
"""

import itertools

from mdpg7.algorithm.simple_hybrid_astar import run_simple_hybrid_astar
from mdpg7.utils.math_utils import dist_pos


def compute_simple_hamiltonian_path(arena, robot):
    permutations = list(itertools.permutations(arena.obstacles))
    
    def path_distance(path):
        nodes = [robot.pos.pos2d()] + [obstacle.pos() for obstacle in path]
        dist = 0
        for i in range(len(nodes) - 1):
            dist += dist_pos(nodes[i], nodes[i + 1])
        return dist
    
    return min(permutations, key=path_distance)


def plan_paths(arena, robot):
    simple_hamiltonian_path = compute_simple_hamiltonian_path(arena, robot)
    print('Found hamiltonian path')
    print(simple_hamiltonian_path)
    
    for obstacle in simple_hamiltonian_path:
        robot_node = robot.pos.pos_to_node
        target_node = obstacle.get_target_node()
        run_simple_hybrid_astar(arena, robot_node, target_node)
