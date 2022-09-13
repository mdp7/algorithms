from mdpg7.config.constants import ArenaConst


class Arena:

	def __init__(self):
		self.arena = [[None for _ in range(ArenaConst.NUM_COLS)] for _ in range(ArenaConst.NUM_ROWS)]
		self.obstacles = list()

	def add_obstacle(self, obstacle):
		self.arena[obstacle.cell_x][obstacle.cell_y] = obstacle
		self.obstacles.append(obstacle)
