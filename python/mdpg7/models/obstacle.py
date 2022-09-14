from mdpg7.config.constants import ObstacleImage, Facing, ArenaConst
from mdpg7.models.cell_position import CellPosition


class Obstacle:
    
    def __init__(self, cell_pos: CellPosition, image: ObstacleImage) -> None:
        self.cell_pos = cell_pos
        self.image = image

    def get_target_position(self) -> CellPosition:
        target_cell_x, target_cell_y, target_facing = self.cell_pos.cell_x, self.cell_pos.cell_y, self.cell_pos.facing
        if self.cell_pos.facing == Facing.R:
            target_cell_x += ArenaConst.DETECT_CELL_DISTANCE
            target_facing = Facing.L
        elif self.cell_pos.facing == Facing.U:
            target_cell_y -= ArenaConst.DETECT_CELL_DISTANCE
            target_facing = Facing.D
        elif self.cell_pos.facing == Facing.L:
            target_cell_x -= ArenaConst.DETECT_CELL_DISTANCE
            target_facing = Facing.R
        elif self.cell_pos.facing == Facing.D:
            target_cell_y += ArenaConst.DETECT_CELL_DISTANCE
            target_facing = Facing.U
        return CellPosition(target_cell_x, target_cell_y, target_facing)

    def __str__(self):
        return f'<Obstacle x: {self.cell_pos.cell_x}, y: {self.cell_pos.cell_x}, image: {self.image.name}>'

    def __repr__(self):
        return self.__str__()
