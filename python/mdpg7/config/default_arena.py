from mdpg7.config.constants import Facing, ObstacleImage
from mdpg7.models.arena import Arena
from mdpg7.models.cell_position import CellPosition
from mdpg7.models.obstacle import Obstacle

ARENAS = list()

obstacle_setup =[[Obstacle(CellPosition(1, 18, Facing.D), ObstacleImage.S),
                  Obstacle(CellPosition(6, 12, Facing.U), ObstacleImage.EIGHT),
                  Obstacle(CellPosition(10, 7, Facing.R), ObstacleImage.SIX),
                  Obstacle(CellPosition(13, 2, Facing.R), ObstacleImage.B),
                  Obstacle(CellPosition(15, 16, Facing.L), ObstacleImage.C),
                  Obstacle(CellPosition(19, 9, Facing.L), ObstacleImage.D)],

                 [Obstacle(CellPosition(1, 10, Facing.U), ObstacleImage.S),
                  Obstacle(CellPosition(4, 13, Facing.D), ObstacleImage.EIGHT),
                  Obstacle(CellPosition(7, 10, Facing.R), ObstacleImage.SIX),
                  Obstacle(CellPosition(13, 12, Facing.L), ObstacleImage.B),]
                 ]

arena0 = Arena()
ARENAS.append(arena0)
i = 1
for obs in obstacle_setup[i]:
    arena0.add_obstacle(obs)
# arena0.add_obstacle(Obstacle(CellPosition(1, 18, Facing.D), ObstacleImage.S))
# arena0.add_obstacle(Obstacle(CellPosition(6, 12, Facing.U), ObstacleImage.EIGHT))
# arena0.add_obstacle(Obstacle(CellPosition(10, 7, Facing.R), ObstacleImage.SIX))
# arena0.add_obstacle(Obstacle(CellPosition(13, 2, Facing.R), ObstacleImage.B))
# arena0.add_obstacle(Obstacle(CellPosition(15, 16, Facing.L), ObstacleImage.C))
# arena0.add_obstacle(Obstacle(CellPosition(19, 9, Facing.L), ObstacleImage.D))
