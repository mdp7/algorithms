from mdpg7.config.constants import Moves


class Command:
    
    def __init__(self, move: Moves):
        self.move = move
        self.repeat = 1
    
    def inc_repeat(self):
        self.repeat += 1
    
    def __str__(self):
        return f'{self.move.name}{self.repeat}'
    
    def __repr__(self):
        return self.__str__()
