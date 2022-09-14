import pygame

from algorithm import const
from algorithm.entities.assets import colors
from algorithm.entities.grid.position import Position


class Node:
    def __init__(self, x, y, occupied, direction=None):
        """
        x and y coordinates are in terms of the grid.
        """
        self.pos = Position(x, y, direction)
        self.occupied = occupied
        self.start = True if 0 <= x < 5 * const.GRID_CELL_LENGTH and 0 <= y <= const.GRID_CELL_LENGTH * 5 else False
    
    def __str__(self):
        return f"Node({self.pos})"
    
    __repr__ = __str__
    
    def __eq__(self, other):
        return self.pos.xy_dir() == other.pos.xy_dir()
    
    def __hash__(self):
        return hash(self.pos.xy_dir())
    
    def copy(self):
        """
        Return a copy of this node.
        """
        return Node(self.pos.x, self.pos.y, self.occupied, self.pos.direction)
    
    def draw_self(self, screen):
        # if self.occupied:  # If current node is not permissible to the robot
        #     rect = pygame.Rect(0, 0, const.GRID_CELL_LENGTH, const.GRID_CELL_LENGTH)
        #     rect.center = self.pos.xy_pygame()
        #     pygame.draw.rect(screen, colors.ORANGE, rect)
        if self.start:
            rect = pygame.Rect(0, 0, const.GRID_CELL_LENGTH, const.GRID_CELL_LENGTH)
            rect.center = self.pos.xy_pygame()
            pygame.draw.rect(screen, colors.LIGHT_BLUE, rect)
    
    def draw_boundary(self, screen):
        x_pygame, y_pygame = self.pos.xy_pygame()
        
        left = x_pygame - const.GRID_CELL_LENGTH // 2
        right = x_pygame + const.GRID_CELL_LENGTH // 2
        top = y_pygame - const.GRID_CELL_LENGTH // 2
        bottom = y_pygame + const.GRID_CELL_LENGTH // 2
        
        # Draw
        pygame.draw.line(screen, colors.BLACK, (left, top), (left, bottom))  # Left border
        pygame.draw.line(screen, colors.BLACK, (left, top), (right, top))  # Top border
        pygame.draw.line(screen, colors.BLACK, (right, top), (right, bottom))  # Right border
        pygame.draw.line(screen, colors.BLACK, (left, bottom), (right, bottom))  # Bottom border
    
    def draw(self, screen):
        # Draw self
        self.draw_self(screen)
        # Draw node border
        self.draw_boundary(screen)
