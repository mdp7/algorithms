import argparse

from mdpg7.simulator.app import Simulator


def run_simulator():
    simulator = Simulator()
    simulator.init_display()
    simulator.init_map(args.map)
    
    while True:
        simulator.handle_events()
        if not simulator.running:
            print('Exiting simulator')
            print('Bye!')
            break
        simulator.draw()
        simulator.tick()


def parse_args():
    parser = argparse.ArgumentParser(description='Simulator for robot path planning')
    parser.add_argument(
        '--map',
        default=0,
        type=int,
        help='index of map to load, default to -1 for user input'
    )
    return parser.parse_args()


if __name__ == '__main__':
    args = parse_args()
    run_simulator()
