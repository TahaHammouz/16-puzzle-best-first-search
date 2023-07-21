import java.util.*;
import java.io.*;

class Puzzle {
    int[] board;
    String history;
    int zeroIndex;
    int manhattan = 0;
    int[] goalState;

    Puzzle(int[] arr, String history, int zeroIndex, int[] goalState) {
        this.board = arr.clone();
        this.history = history;
        this.zeroIndex = zeroIndex;
        for (int i = 0; i < 12; i++) {
            if (board[i] == 0) continue;
            manhattan += Math.abs(i / 4 - (board[i] - 1) / 4) + Math.abs(i % 4 - (board[i] - 1) % 4);
        }
        this.goalState = goalState;
    }

    List<Puzzle> getNeighbors() {
        List<Puzzle> neighbors = new ArrayList<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] dirNames = {"UP", "DOWN", "LEFT", "RIGHT"};
        for (int i = 0; i < 4; i++) {
            int newZeroIndex = zeroIndex + dirs[i][0] * 4 + dirs[i][1];
            if (newZeroIndex >= 0 && newZeroIndex < 12 && Math.abs(zeroIndex / 4 - newZeroIndex / 4) + Math.abs(zeroIndex % 4 - newZeroIndex % 4) == 1) {
                Puzzle neighbor = new Puzzle(board, history + " " + dirNames[i], newZeroIndex, goalState);
                neighbor.board[zeroIndex] = neighbor.board[newZeroIndex];
                neighbor.board[newZeroIndex] = 0;
                neighbor.zeroIndex = newZeroIndex;
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    static Puzzle fromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int[] initialState = new int[12];
        int[] goalState = new int[12];
        int zeroIndex = -1;

        // Read initial state
        for (int i = 0; i < 3; i++) {
            String[] line = reader.readLine().split("\\s+");
            for (int j = 0; j < 4; j++) {
                initialState[i * 4 + j] = Integer.parseInt(line[j]);
                if (initialState[i * 4 + j] == 0) {
                    zeroIndex = i * 4 + j;
                }
            }
        }

        reader.readLine();


        for (int i = 0; i < 3; i++) {
            String[] line = reader.readLine().split("\\s+");
            for (int j = 0; j < 4; j++) {
                goalState[i * 4 + j] = Integer.parseInt(line[j]);
            }
        }

        reader.close();

        return new Puzzle(initialState, "", zeroIndex, goalState);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            Puzzle start = Puzzle.fromFile("/Users/tahahammouz/IdeaProjects/_16-puzzle/src/input.txt");
            PriorityQueue<Puzzle> priorityQueue = new PriorityQueue<>((a, b) -> a.manhattan - b.manhattan);
            priorityQueue.add(start);
            Set<String> visited = new HashSet<>()
                    ;
            int statesExpanded = 0;
            while (!priorityQueue.isEmpty()) {
                Puzzle current = priorityQueue.poll();
                if (Arrays.equals(current.board, current.goalState)) { // Check against the goal state from the file
                    System.out.println("Solution: " + current.history);
                    System.out.println("States expanded: " + statesExpanded);
                    return;
                }
                String serialized = Arrays.toString(current.board);
                if (visited.contains(serialized)) continue;
                visited.add(serialized);
                priorityQueue.addAll(current.getNeighbors());
                statesExpanded++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

