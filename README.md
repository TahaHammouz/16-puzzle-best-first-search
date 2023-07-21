# 16-puzzle-best-first-search

Consider the 16-puzzle in which there is a 3x4 board that contains tiles numbered 1-11 and a blank. Similar to the 8-puzzle, the blank can move up, down, left, and right in every step. You need to come up with a heuristic and solve the problem using  best first search, no repeated states. 
The program must be written in either C or Java. When executed, the program must read the name of the text file containing the the initial and goal states. Assume the file is always formatted as shown in the example below. The first 3 lines in the file represent the initial state and the last 3 lines in the file represent the goal state. The blank is indicated by 0.

Example: Input text file 
2	3	5	6
10	0	9	4
7	1	8	11

1	2	3	4
5	6	7	8
9	10	11	0
The program should print
1. The solution found, expressed as a sequence of operators ∈{UP, DOWN, LEFT, RIGHT}.
2. Number of states expanded.
You will be graded based on correctness and also based on the quality of heuristic. The less number of states expanded using best-first search using the heuristic, the better the heuristic.
