from mdpg7.models.position import Position
from mdpg7.utils.position_utils import pos_to_win_pos


class Robot:

	def __init__(self):
		self.pos = None

	def win_pos(self):
		return pos_to_win_pos(self.pos.pos2d())

	@classmethod
	def new(cls, robot):
		new_robot = cls()
		new_robot.pos = Position.new(robot.pos)
		return new_robot
