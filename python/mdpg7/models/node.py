class Node:
    
    def __init__(self, cell_x, cell_y, theta, g, h, predecessor):
        self.cell_x = cell_x
        self.cell_y = cell_y
        self.theta = theta
        self.g = g
        self.h = h
        self.predecessor = predecessor
