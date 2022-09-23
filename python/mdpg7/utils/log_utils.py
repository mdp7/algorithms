"""
Console logging tools
"""


def print_warning(msg):
    print(f'\033[93m\033[1m{msg}\033[0m')


def print_error(msg):
    print(f'\033[91m\033[1m{msg}\033[0m')
