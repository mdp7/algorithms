from mdpg7.config.constants import Moves


"""
LF = 0
FF = 1
RF = 2
LB = 3
BB = 4
RB = 5
"""
# Take picture symbol
TAKE_PICTURE = '*'

def compress_instructions(instruction_list : list):
    print("Compressing Instructions!")
    to_return = ""
    counter = 1
    pre = instruction_list[0]
    for i in range(1, len(instruction_list)):
        if pre == instruction_list[i]:
            counter += 1

        else:
            to_return = (to_return + f"{pre}/") if pre == TAKE_PICTURE else (to_return + f"{pre},{counter}/")
            counter = 1
            pre = instruction_list[i]

    # Append no more instructions
    to_return += "!"

    return to_return

def commands_to_message(commands : list):
    index = 0
    to_return = ""
    while(index < len(commands)):
        to_return = to_return + f"{commands[index].move},{commands[index].repeat}/" if commands[index] != TAKE_PICTURE else to_return + f"{TAKE_PICTURE}/"
        index += 1

    to_return += '!'
    print("Message: ", to_return)

    return to_return


def find_object_face():
    # Scan, Move back 3x, Turn right, Forward, TL * 2
    iList = ["*"] + [Moves.BB] * 3 + [Moves.RF] + [Moves.FF] + [Moves.LF] * 2
    return compress_instructions(iList * 4)


if __name__ == "__main__":
    iList = [Moves.BB] * 15 +  ["*", Moves.FF, Moves.LF, Moves.LF, Moves.LF, Moves.RB, Moves.RF]
    print(compress_instructions(iList))

    # print(find_object_face())

