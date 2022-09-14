from mdpg7.config.constants import Moves

# Take picture symbol
TAKE_PICTURE = '*'

def compress_instructions(instruction_list : list):
    to_return = ""
    counter = 1
    pre = instruction_list[0]
    for i in range(1, len(instruction_list)):
        if pre == instruction_list[i]:
            counter += 1

        else:
            to_return = (to_return + f"{pre}/") if pre == "*" else (to_return + f"{pre}{counter}/")
            counter = 1
            pre = instruction_list[i]

    # Append no more instructions
    to_return += "!"

    return to_return

if __name__ == "__main__":
    iList = [Moves.BB] * 15 +  ["*", Moves.FF, Moves.LF, Moves.LF, Moves.LF, Moves.RB, Moves.RF]
    print(compress_instructions(iList))

