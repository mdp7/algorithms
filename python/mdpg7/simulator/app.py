import sys

import pygame

from mdpg7.config.constants import Color, RobotConst
from mdpg7.config.constants import SimulatorConst
from mdpg7.config.default_arena import ARENAS
from mdpg7.models.arena import Arena
from mdpg7.models.cell_position import CellPosition
from mdpg7.models.robot import Robot
from mdpg7.simulator.manager.customize_map import draw_customize_map, handle_events_customize_map
from mdpg7.simulator.manager.display_animation import handle_events_display_animation
from mdpg7.simulator.manager.display_default_map import draw_display_default_map, handle_events_display_default_map
from mdpg7.simulator.manager.display_result import handle_events_display_result
from mdpg7.simulator.manager.find_path import handle_events_find_path
from mdpg7.simulator.view.arena_view import draw_arena
from mdpg7.simulator.view.robot_view import draw_robot, RobotView, RobotViewCommand
from mdpg7.utils.log_utils import print_warning, print_error
from mdpg7.utils.position_utils import cell_x_to_win_x, cell_y_to_win_y


class Simulator:
    
    def __init__(self):
        print('Initializing simulator')
        
        # UI attributes
        self.running = False
        self.surface = None
        self.clock = None
        self.mode = SimulatorConst.MODE_NULL
        
        # algorithm attributes
        self.arena = None
        self.robot = None
        self.command = None
        self.commands = None
        self.robot_view = None
    
    def init_display(self):
        print('Initializing pygame')
        
        # UI
        pygame.init()
        self.running = True
        self.surface = pygame.display.set_mode(SimulatorConst.WINDOW_SIZE)
        self.clock = pygame.time.Clock()
        
        # algorithm
        self.arena = Arena()
        self.robot = Robot(CellPosition(RobotConst.START_X, RobotConst.START_Y, RobotConst.START_FACING))
        self.command = None
        self.commands = list()
        self.robot_view = \
            RobotView(cell_x_to_win_x(RobotConst.START_X), cell_y_to_win_y(RobotConst.START_Y), RobotConst.START_THETA)
    
    def init_map(self, map_index):
        print('Loading map to pygame')
        print(f'User choice is {map_index}')
        if map_index == -1:
            print('Default map is not loaded, waiting for user input')
            self.mode = SimulatorConst.MODE_CUSTOMIZE_MAP
        elif map_index < 0 or len(ARENAS) <= map_index:
            print_warning('Invalid default map, switching to customize mode')
            self.mode = SimulatorConst.MODE_CUSTOMIZE_MAP
        else:
            self.arena = ARENAS[map_index]
            self.mode = SimulatorConst.MODE_DISPLAY_DEFAULT_MAP
    
    def handle_events(self):
        if not self.running or self.mode == SimulatorConst.MODE_NULL:
            # this line shouldn't be reached
            print_error('ERROR: handling events while pygame is not running')
            sys.exit(1)
        
        events = pygame.event.get()
        for event in events:
            if event.type == pygame.QUIT:
                self.running = False
                self.mode = SimulatorConst.MODE_NULL
                pygame.quit()
                print('QUIT button is clicked')
                print('Quitting pygame')
                return
        
        if self.mode == SimulatorConst.MODE_DISPLAY_DEFAULT_MAP:
            handle_events_display_default_map(self, events)
        elif self.mode == SimulatorConst.MODE_CUSTOMIZE_MAP:
            handle_events_customize_map(self, events)
        elif self.mode == SimulatorConst.MODE_FIND_PATH:
            handle_events_find_path(self, events)
        elif self.mode == SimulatorConst.MODE_DISPLAY_ANIMATION:
            handle_events_display_animation(self, events)
        elif self.mode == SimulatorConst.MODE_DISPLAY_RESULT:
            handle_events_display_result(self, events)
        else:
            # this line shouldn't be reached
            print_error('ERROR: invalid simulator mode')
            sys.exit(1)
    
    def draw(self):
        if not self.running or self.mode == SimulatorConst.MODE_NULL:
            # this line shouldn't be reached
            print_error('ERROR: drawing while pygame is not running')
            sys.exit(1)
        
        self.surface.fill(Color.WHITE)
        draw_arena(self)
        
        if self.mode == SimulatorConst.MODE_DISPLAY_DEFAULT_MAP:
            draw_display_default_map(self)
        elif self.mode == SimulatorConst.MODE_CUSTOMIZE_MAP:
            draw_customize_map(self)
        elif self.mode == SimulatorConst.MODE_FIND_PATH:
            pass
        elif self.mode == SimulatorConst.MODE_DISPLAY_ANIMATION:
            pass
        elif self.mode == SimulatorConst.MODE_DISPLAY_RESULT:
            pass
        else:
            # this line shouldn't be reached
            print_error('ERROR: invalid simulator mode')
            sys.exit(1)
        
        draw_robot(self)
        
        pygame.display.flip()
    
    def tick(self):
        if not self.running or self.mode == SimulatorConst.MODE_NULL or self.clock is None:
            # this line shouldn't be reached
            print_error('ERROR: clock ticking while pygame is not running')
            sys.exit(1)
        
        self.clock.tick(SimulatorConst.FRAMES_PER_SECOND)
        
        assert SimulatorConst.FRAMES_PER_SECOND / SimulatorConst.ROBOT_SPEED
        
        if self.command is None and self.commands is not None and 0 < len(self.commands):
            self.command = RobotViewCommand(self.commands.pop(0), self.robot_view)
        
        if self.command is not None:
            self.command = self.command.forward()
