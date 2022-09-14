from queue import PriorityQueue


def run_simple_hybrid_astar(arena, start, goal):
    p_index = None
    s_index = None
    new_g = None
    
    open_queue = PriorityQueue()
    start.update_h()
    start.open()
    open_queue.put(start)
