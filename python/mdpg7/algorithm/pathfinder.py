"""
pathfinder should be isolated from simulator
"""

import itertools
import math

from mdpg7.algorithm.simple_hybrid_astar import run_simple_hybrid_astar
from mdpg7.models.position import Position
from mdpg7.models.robot import Robot
from mdpg7.utils.math_utils import dist_pos


def compute_simple_hamiltonian_path(arena, robot):
	permutations = list(itertools.permutations(arena.obstacles))

	def path_distance(path):
		nodes = [robot.pos.win_pos()] + [obstacle.win_pos() for obstacle in path]
		dist = 0
		for i in range(len(nodes) - 1):
			dist += dist_pos(nodes[i], nodes[i + 1])
		return dist

	return min(permutations, key=path_distance)


def plan_paths(arena, robot):
	print('planning path')
	simple_hamiltonian_path = compute_simple_hamiltonian_path(arena, robot)

	virtual_robot = Robot.new(robot)
	for obstacle in simple_hamiltonian_path:
		target = obstacle.win_pos()
		run_simple_hybrid_astar(arena, robot, target)
