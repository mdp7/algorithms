from mdpg7.config.constants import Moves, RobotConst

COMMANDS_MESSAGE = ['FORWARD LEFT', 'FORWARD MOVE', 'FORWARD RIGHT', 'BACKWARD LEFT', 'BACKWARD MOVE',
                    'BACKWARD RIGHT']


class Command:
    """Each command should be understood as perform MOVE for REPEAT times"""

    def __init__(self, move: Moves):
        self.move = move
        self.repeat = 1

    def inc_repeat(self):
        self.repeat += 1

    def __str__(self):
        if self.move == 1 or self.move == 4:
            dist = self.repeat * RobotConst.STRAIGHT_MOVE_DIST
            return f'{COMMANDS_MESSAGE[self.move]} {dist}'

        return f'{COMMANDS_MESSAGE[self.move]}'

    def __repr__(self):
        return self.__str__()
