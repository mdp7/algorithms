import math
from queue import PriorityQueue
from typing import List, Tuple

from algorithm import const
from algorithm.entities.commands.command import Command
from algorithm.entities.commands.straight_command import StraightCommand
from algorithm.entities.commands.turn_command import TurnCommand
from algorithm.entities.commands.tpt_command import TPTCommand
from algorithm.entities.commands.left_command import LeftTurn
from algorithm.entities.commands.right_command import RightTurn
from algorithm.entities.grid.grid import Grid
from algorithm.entities.grid.node import Node
from algorithm.entities.grid.position import RobotPosition

from math import pi


class ModifiedAStar:
    def __init__(self, grid, brain, start: RobotPosition, end: RobotPosition):
        # We use a copy of the grid rather than use a reference
        # to the exact grid.
        self.grid: Grid = grid.copy()
        self.brain = brain
        
        self.start = start
        self.end = end
    
    def get_neighbours(self, pos: RobotPosition) -> List[Tuple[Node, RobotPosition, int, Command]]:
        """
        Get movement neighbours from this position.

        Note that all values in the Position object (x, y, direction) are all with respect to the grid!

        We also expect the return Positions to be with respect to the grid.
        """
        # We assume the robot will always make a full 90-degree turn to the next neighbour, and that it will travel
        # a fix distance of 10 when travelling straight.
        neighbours = []
        
        # Check travel straights.
        straight_dist = 10 * const.SCALING_FACTOR
        penalty = const.PATH_TURN_COST // 2
        straight_commands = [
            StraightCommand(straight_dist),
            StraightCommand(-straight_dist)
        ]

        backwards_move_mul = 10

        for c in straight_commands:
            # Check if doing this command does not bring us to any invalid position.
            after, p = self.check_valid_command(c, pos)
            if after:
                penalty_adjusted = penalty * backwards_move_mul if c.dist < 0 else 0
                neighbours.append((after, p, penalty_adjusted, c))
        
        # Check turns
        turn_penalty = const.PATH_TURN_COST

        turn_commands = [
            # LeftTurn(90, False),  # Forward right turn
            # RightTurn(-90, False),  # Forward left turn
            # RightTurn(90, True),  # Reverse with wheels to right.
            # LeftTurn(-90, True),  # Reverse with wheels to left.
            TPTCommand(90, False), #3Pt turn Left
            TPTCommand(-90, False) #3Pt turn right
        ]
        for c in turn_commands:
            # Check if doing this command does not bring us to any invalid position.
            after, p = self.check_valid_command(c, pos)
            if after:
                # Check if TPT Turn
                if isinstance(c, TPTCommand):
                    turn_penalty_adjusted = turn_penalty * const.TPT_MUL
                else:
                    turn_penalty_adjusted = turn_penalty * 1.5 if c.rev else turn_penalty
                neighbours.append((after, p, turn_penalty_adjusted, c))
        return neighbours
    
    def check_valid_command(self, command: Command, p: RobotPosition):
        """
        Checks if a command will bring a point into any invalid position.

        If invalid, we return None for both the resulting grid location and the resulting position.
        """
        # Check specifically for validity of turn command.
        p = p.copy()
        if isinstance(command, TurnCommand):
            if not isinstance(command, TPTCommand):
                p_c = p.copy()
                if isinstance(command, LeftTurn):
                    for tick in range(command.ticks // const.PATH_TURN_CHECK_GRANULARITY):
                        tick_command = LeftTurn(command.angle / (command.ticks // const.PATH_TURN_CHECK_GRANULARITY),
                                                   command.rev)
                        tick_command.apply_on_pos(p_c)
                        if not (self.grid.check_valid_position(p_c) and self.grid.get_coordinate_node(*p_c.xy())):
                            return None, None
                else:
                    for tick in range(command.ticks // const.PATH_TURN_CHECK_GRANULARITY):
                        tick_command = RightTurn(command.angle / (command.ticks // const.PATH_TURN_CHECK_GRANULARITY),
                                                   command.rev)
                        tick_command.apply_on_pos(p_c)
                        if not (self.grid.check_valid_position(p_c) and self.grid.get_coordinate_node(*p_c.xy())):
                            return None, None

        command.apply_on_pos(p)
        if self.grid.check_valid_position(p) and (after := self.grid.get_coordinate_node(*p.xy())):
            after.pos.direction = p.direction
            return after.copy(), p
        return None, None
    
    def heuristic(self, curr_pos: RobotPosition):
        """
        Measure the difference in distance between the provided position and the
        end position.
        """
        dx = abs(curr_pos.x - self.end.x)
        dy = abs(curr_pos.y - self.end.y)
        return math.sqrt(dx ** 2 + dy ** 2)
    
    def start_astar(self):
        frontier = PriorityQueue()  # Store frontier nodes to travel to.
        backtrack = dict()  # Store the sequence of nodes being travelled.
        cost = dict()  # Store the cost to travel from start to a node.
        
        # We can check what the goal node is
        goal_node = self.grid.get_coordinate_node(*self.end.xy()).copy()  # Take note of copy!
        goal_node.pos.direction = self.end.direction  # Set the required direction at this node.
        
        # Add starting node set into the frontier.
        start_node: Node = self.grid.get_coordinate_node(*self.start.xy()).copy()  # Take note of copy!
        start_node.direction = self.start.direction  # Make the node know which direction the robot is facing.
        
        offset = 0  # Used to tie-break.
        frontier.put((0, offset, (start_node, self.start)))  # Extra time parameter to tie-break same priority.
        cost[start_node] = 0
        # Having None as the parent means this key is the starting node.
        backtrack[start_node] = (None, None)  # Parent, Command
        
        while not frontier.empty():  # While there are still nodes to process.
            # Get the highest priority node.
            priority, _, (current_node, current_position) = frontier.get()
            # If the current node is our goal.
            if current_node == goal_node:
                # Get the commands needed to get to destination.
                self.extract_commands(backtrack, goal_node)
                return current_position
            
            # Otherwise, we check through all possible locations that we can
            # travel to from this node.
            for new_node, new_pos, weight, c in self.get_neighbours(current_position):
                new_cost = cost.get(current_node) + weight
                
                if new_node not in backtrack or new_cost < cost[new_node]:
                    offset += 1
                    priority = new_cost + self.heuristic(new_pos)
                    
                    frontier.put((priority, offset, (new_node, new_pos)))
                    backtrack[new_node] = (current_node, c)
                    cost[new_node] = new_cost
        # If we are here, means that there was no path that we could find.
        # We return None to show that we cannot find a path.
        return None
    
    def extract_commands(self, backtrack, goal_node):
        """
        Extract required commands to get to destination.
        """
        commands = []
        curr = goal_node
        while curr:
            curr, c = backtrack.get(curr, (None, None))
            if c:
                commands.append(c)
        commands.reverse()
        self.brain.commands.extend(commands)

    """
    Penalty Testing
    """
    def start_astar_test(self, penalties: list):
        frontier = PriorityQueue()  # Store frontier nodes to travel to.
        backtrack = dict()  # Store the sequence of nodes being travelled.
        cost = dict()  # Store the cost to travel from start to a node.

        # We can check what the goal node is
        goal_node = self.grid.get_coordinate_node(*self.end.xy()).copy()  # Take note of copy!
        goal_node.pos.direction = self.end.direction  # Set the required direction at this node.

        # Add starting node set into the frontier.
        start_node: Node = self.grid.get_coordinate_node(*self.start.xy()).copy()  # Take note of copy!
        start_node.direction = self.start.direction  # Make the node know which direction the robot is facing.

        offset = 0  # Used to tie-break.
        frontier.put((0, offset, (start_node, self.start)))  # Extra time parameter to tie-break same priority.
        cost[start_node] = 0
        # Having None as the parent means this key is the starting node.
        backtrack[start_node] = (None, None)  # Parent, Command

        while not frontier.empty():  # While there are still nodes to process.
            # Get the highest priority node.
            priority, _, (current_node, current_position) = frontier.get()
            # If the current node is our goal.
            if current_node == goal_node:
                # Get the commands needed to get to destination.
                self.extract_commands(backtrack, goal_node)
                return current_position

            # Otherwise, we check through all possible locations that we can
            # travel to from this node.
            for new_node, new_pos, weight, c in self.get_neighbours_test(current_position, penalties):
                new_cost = cost.get(current_node) + weight

                if new_node not in backtrack or new_cost < cost[new_node]:
                    offset += 1
                    priority = new_cost + self.heuristic(new_pos)

                    frontier.put((priority, offset, (new_node, new_pos)))
                    backtrack[new_node] = (current_node, c)
                    cost[new_node] = new_cost
        # If we are here, means that there was no path that we could find.
        # We return None to show that we cannot find a path.
        return None

    def get_neighbours_test(self, pos: RobotPosition, penalties: list) -> List[Tuple[Node, RobotPosition, int, Command]]:
        """
        Get movement neighbours from this position.

        Note that all values in the Position object (x, y, direction) are all with respect to the grid!

        We also expect the return Positions to be with respect to the grid.
        """
        # We assume the robot will always make a full 90-degree turn to the next neighbour, and that it will travel
        # a fix distance of 10 when travelling straight.
        neighbours = []

        # Check travel straights.
        straight_dist = 10 * const.SCALING_FACTOR
        penalty = straight_dist
        straight_commands = [
            StraightCommand(straight_dist),
            StraightCommand(-straight_dist)
        ]

        backwards_move_mul = penalties[0]
        forward_move_mul = 1

        backwards_turn_mul = penalties[1]
        forward_turn_mul = penalties[2]
        # print(backwards_turn_mul, forward_turn_mul)
        for c in straight_commands:
            # Check if doing this command does not bring us to any invalid position.
            after, p = self.check_valid_command(c, pos)
            if after:
                adjusted_penalty = penalty * backwards_move_mul if c.dist < 0 else (penalty * forward_move_mul)
                neighbours.append((after, p, adjusted_penalty, c))

        # Check turns
        # turn_penalty = const.PATH_TURN_COST
        turn_penalty = const.ROBOT_TURN_RADIUS * (pi / 2)
        turn_commands = [
            TurnCommand(90, False),  # Forward right turn
            TurnCommand(-90, False),  # Forward left turn
            TurnCommand(90, True),  # Reverse with wheels to right.
            TurnCommand(-90, True),  # Reverse with wheels to left.
        ]

        for c in turn_commands:
            # Check if doing this command does not bring us to any invalid position.
            after, p = self.check_valid_command(c, pos)
            if after:
                adjusted_turn_penalty = turn_penalty * backwards_turn_mul if c.rev else turn_penalty * forward_turn_mul
                neighbours.append((after, p, adjusted_turn_penalty, c))
        return neighbours