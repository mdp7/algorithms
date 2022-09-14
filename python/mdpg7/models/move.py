from mdpg7.config.constants import Moves


class Move:
    
    def __init__(self, movement: Moves):
        self.movement = movement
        self.repeat = 1
        
    def repeat(self):
        self.repeat += 1
        
    def __str__(self):
        return f'{self.movement.name}{self.repeat}'
    
    def __repr__(self):
        return self.__str__()
