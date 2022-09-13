from mdpg7.config.constants import ObstacleConst, ObstacleImage
from mdpg7.models.arena import Arena
from mdpg7.models.obstacle import Obstacle

ARENAS = list()

arena0 = Arena()
ARENAS.append(arena0)
arena0.add_obstacle(Obstacle.new(1, 18, ObstacleConst.FACING_D, ObstacleImage.S))
arena0.add_obstacle(Obstacle.new(6, 12, ObstacleConst.FACING_U, ObstacleImage.EIGHT))
arena0.add_obstacle(Obstacle.new(10, 7, ObstacleConst.FACING_R, ObstacleImage.SIX))
arena0.add_obstacle(Obstacle.new(13, 2, ObstacleConst.FACING_R, ObstacleImage.B))
arena0.add_obstacle(Obstacle.new(15, 16, ObstacleConst.FACING_L, ObstacleImage.C))
arena0.add_obstacle(Obstacle.new(19, 9, ObstacleConst.FACING_L, ObstacleImage.D))
