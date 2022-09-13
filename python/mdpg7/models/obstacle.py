from mdpg7.config.constants import ObstacleConst
from mdpg7.utils.position_utils import *


class Obstacle:

	def __init__(self):
		self.cell_x = -1
		self.cell_y = -1
		self.facing = ObstacleConst.FACING_N
		self.detected = False
		self.image = None

	def initialize(self, cell_x, cell_y, facing, image):
		self.cell_x = cell_x
		self.cell_y = cell_y
		self.facing = facing
		self.image = image

	def cell_pos(self):
		return self.cell_x, self.cell_y

	def x(self):
		return cell_x_to_x(self.cell_x)

	def y(self):
		return cell_y_to_y(self.cell_y)

	def pos(self):
		return cell_pos_to_pos(self.cell_pos())

	def win_x(self):
		return cell_x_to_win_x(self.cell_x)

	def win_y(self):
		return cell_y_to_win_y(self.cell_y)

	def win_pos(self):
		return cell_pos_to_win_pos(self.cell_pos())

	@classmethod
	def new(cls, cell_x, cell_y, facing, image):
		obstacle = cls()
		obstacle.initialize(cell_x, cell_y, facing, image)
		return obstacle
