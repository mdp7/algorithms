import sys
import time
from typing import List

from algorithm import const
from algorithm.app import AlgoSimulator, AlgoMinimal
from algorithm.const import OBSTACLE_LAYOUT
from algorithm.entities.assets.direction import Direction
from algorithm.entities.connection.rpi_client import RPiClient
from algorithm.entities.connection.rpi_server import RPiServer
from algorithm.entities.grid.grid import Grid
from algorithm.entities.grid.obstacle import Obstacle
from algorithm.entities.robot.robot import Robot


def parse_obstacle_data(data) -> List[Obstacle]:
    obs = []
    for obstacle_params in data:
        obs.append(Obstacle(obstacle_params[0],
                            obstacle_params[1],
                            Direction(obstacle_params[2]),
                            obstacle_params[3]))
    # [[x, y, orient, index], [x, y, orient, index]]
    return obs


def run_penalty_tester():
    """
    Use different penalty values
    Pass to function to iteration through x times
    return the one with min time/distance
    """
    backwards_penalty = [1+i*0.2 for i in range(5, 10)]
    BT_penalty = [i for i in range(4, 6)]
    FT_penalty = [1+i*0.2 for i in range(0, 5)]
    penalties = []
    # Get all permutation of penalties
    for i in range(len(backwards_penalty)):
        for j in range(len(BT_penalty)):
            for k in range(len(FT_penalty)):
                penalties.append([backwards_penalty[i], BT_penalty[j], FT_penalty[k]])
    print(f"Length Penalties: {len(penalties)}")

    # Get obstacle list
    obstacles = OBSTACLE_LAYOUT[2]
    for i, o in enumerate(obstacles):
        o.append(i)

    # Create grid
    grid = Grid(parse_obstacle_data(obstacles))
    robot = Robot(15, 15, Direction(90), grid)
    h_path = robot.brain.compute_simple_hamiltonian_path()
    results = []
    for index, p in enumerate(penalties):
        robot = Robot(15, 15, Direction(90), grid)
        robot.brain.simple_hamiltonian = h_path
        print(f"Testing penalty {index}: {p}")
        results.append([p])
        # Create robot

        results[index].append(robot.brain.plan_path_test(p))

    for i, r in enumerate(results):
        print(f"Result {i}: {r[0]} {r[1][:2]}")
        # print(f"Commands:\n{r[1][2]}")
        print()
    def get_best_pen(r):
        return r[1][0]

    best = min(results, key=get_best_pen)
    print(best)
    print(f"Best Penalty: {best[0]}")

    # print(f"All results: {results}")
    # Plan path and return calculated time/distance
    # Return best penalty values
    print()


def run_simulator():
    # Fill in obstacle positions with respect to lower bottom left corner.
    # Obstacle positions are center of cell ie ends with 5!
    # (x-coordinate, y-coordinate, Direction)
    # obstacles = [[25, 185, -90], [125, 185, -90], [135, 25, 90], [25, 95, 0], [125, 85, 180]]
    obstacles = [[25, 185, -90], [125, 185, -90], [135, 25, 90], [25, 95, 0], [125, 85, 0]]
    for index, o in enumerate(obstacles):
        o.append(index)

    obs = parse_obstacle_data(obstacles)

    app = AlgoSimulator([])
    app.init()
    app.execute()


def run_minimal(also_run_simulator):
    # Create a client to connect to the RPi.
    print(f"Attempting to connect to {const.RPI_HOST}:{const.RPI_PORT}")
    client = RPiClient(const.RPI_HOST, const.RPI_PORT)
    # Wait to connect to RPi.
    while True:
        try:
            client.connect()
            break
        except OSError:
            pass
        except KeyboardInterrupt:
            client.close()
            sys.exit(1)
    print("Connected to RPi!\n")

    print("Waiting to receive obstacle data from RPi...")
    # Create a server to receive information from the RPi.
    server = RPiServer(const.PC_HOST, const.PC_PORT)
    # Wait for the RPi to connect to the PC.
    try:
        server.start()
    except OSError or KeyboardInterrupt as e:
        print(e)
        server.close()
        client.close()
        sys.exit(1)

    # At this point, both the RPi and the PC are connected to each other.
    # Create a synchronous call to wait for RPi data.
    obstacle_data: list = server.receive_data()
    server.close()
    print("Got data from RPi:")
    print(obstacle_data)

    obstacles = parse_obstacle_data(obstacle_data)
    if also_run_simulator:
        app = AlgoSimulator(obstacles)
        app.init()
        app.execute()
    app = AlgoMinimal(obstacles)
    app.init()
    app.execute()

    # Send the list of commands over.
    print("Sending list of commands to RPi...")
    commands = app.robot.convert_all_commands()
    client.send_message(commands)
    client.close()


def run_rpi():
    while True:
        run_minimal(False)
        time.sleep(5)


if __name__ == '__main__':
    run_simulator()
    # run_penalty_tester()