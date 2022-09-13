import math


class Position:

	def __init__(self, x, y, theta):
		self.x = x
		self.y = y
		self.theta = theta

	def pos2d(self):
		return self.x, self.y

	def theta_degrees(self):
		return math.degrees(self.theta)

	@classmethod
	def new(cls, position):
		return cls(position.x, position.y, position.theta)
