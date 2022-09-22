import math

from algorithm import const
from algorithm.entities.assets.direction import Direction
from algorithm.entities.commands.command import Command
from algorithm.entities.grid.position import Position, RobotPosition


class TurnCommand(Command):
    def __init__(self, angle, rev):
        """
        Angle to turn and whether the turn is done in reverse or not. Note that this is in degrees.

        Note that negative angles will always result in the robot being rotated clockwise.
        """
        time = abs((math.radians(angle) * const.ROBOT_LENGTH) /
                   (const.ROBOT_SPEED_PER_SECOND * const.ROBOT_S_FACTOR))
        super().__init__(time)
        
        self.angle = angle
        self.rev = rev
    
    def __str__(self):
        # return f"TurnCommand({self.angle:.2f}degrees, {self.total_ticks} ticks, rev={self.rev})"
        move_string = "FORWARD TURN" if self.rev == False else "BACKWARD TURN"

        if move_string == "FORWARD TURN":
            dir_string = "RIGHT" if int(self.angle) == -90 else "LEFT"
        else:
            dir_string = "LEFT" if int(self.angle) == -90 else "RIGHT"
        return f"{move_string} {dir_string}"
    __repr__ = __str__
    
    def process_one_tick(self, robot):
        if self.total_ticks == 0:
            return
        
        self.tick()
        angle = self.angle / self.total_ticks
        robot.turn(angle, self.rev)
    
    def apply_on_pos(self, curr_pos: Position):
        """
        x_new = x + R(sin(∆θ + θ) − sin θ)
        y_new = y − R(cos(∆θ + θ) − cos θ)
        θ_new = θ + ∆θ
        R is the turning radius.

        Take note that:
            - +ve ∆θ -> rotate counter-clockwise
            - -ve ∆θ -> rotate clockwise

        Note that ∆θ is in radians.
        """
        assert isinstance(curr_pos, RobotPosition), print("Cannot apply turn command on non-robot positions!")
        """
        Change in x,y coordinate depends on robot facing direction. Current implementation we assume a
        fixed change of 25/35
        """
        # print(curr_pos.angle, self.angle)
        # Get change in (x, y) coordinate.
        x_change = const.ROBOT_TURN_RADIUS * (math.sin(math.radians(curr_pos.angle + self.angle)) -
                                              math.sin(math.radians(curr_pos.angle)))
        y_change = const.ROBOT_TURN_RADIUS * (math.cos(math.radians(curr_pos.angle + self.angle)) -
                                              math.cos(math.radians(curr_pos.angle)))

        # x_change = const.ROBOT_TURN_DELTA_35 if (curr_pos.direction == Direction.TOP or curr_pos.direction == Direction.BOTTOM ) else const.ROBOT_TURN_DELTA_25
        # y_change = const.ROBOT_TURN_DELTA_35 if (curr_pos.direction == Direction.LEFT or curr_pos.direction == Direction.RIGHT) else const.ROBOT_TURN_DELTA_25
        
        if self.angle < 0 and not self.rev:  # Wheels to right moving forward.
            curr_pos.x -= x_change
            curr_pos.y += y_change
        elif (self.angle < 0 and self.rev) or (self.angle >= 0 and not self.rev):
            # (Wheels to left moving backwards) or (Wheels to left moving forwards).
            curr_pos.x += x_change
            curr_pos.y -= y_change
        else:  # Wheels to right moving backwards.
            curr_pos.x -= x_change
            curr_pos.y += y_change
        curr_pos.angle += self.angle
        
        if curr_pos.angle < -180:
            curr_pos.angle += 2 * 180
        elif curr_pos.angle >= 180:
            curr_pos.angle -= 2 * 180
        
        # Update the Position's direction.
        if 45 <= curr_pos.angle <= 3 * 45:
            curr_pos.direction = Direction.TOP
        elif -45 < curr_pos.angle < 45:
            curr_pos.direction = Direction.RIGHT
        elif -45 * 3 <= curr_pos.angle <= -45:
            curr_pos.direction = Direction.BOTTOM
        else:
            curr_pos.direction = Direction.LEFT
        return self
    
    def convert_to_message(self):
        if self.angle > 0 and not self.rev:
            # This is going forward left.
            return "l0090"  # Note the smaller case L.
        elif self.angle > 0 and self.rev:
            # This is going backward and with the wheels to the right.
            return "R0090"
        elif self.angle < 0 and not self.rev:
            # This is going forward right.
            return "r0090"
        else:
            # This is going backward and with the wheels to the left.
            return "L0090"
