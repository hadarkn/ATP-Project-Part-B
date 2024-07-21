package algorithms.mazeGenerators;

import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates an empty maze with given rows and columns.
     * The maze is initialized with empty cells, and ensures that start and goal positions are solvable and not the same.
     *
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     * @return The generated Maze object.
     */
    @Override
    public Maze generate(int rows, int cols) {
        if (rows < 1 || cols < 1) return new Maze(1, 1);
        Maze maze = new Maze(rows, cols);
        // Fill the maze with empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze.setCell(i, j, 0);
            }
        }

        // Ensure start and goal positions are solvable and not the same
        Position start, goal;
        do {
            start = randomEdgePosition(rows, cols);
            goal = randomEdgePosition(rows, cols);
            maze.setStartPosition(start);
            maze.setGoalPosition(goal);
        } while (start.equals(goal) || !maze.isSolvable());

        return maze;
    }

    /**
     * Generates a random position on the edge of the maze.
     * The edge can be top, bottom, left, or right.
     *
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     * @return A random Position object located on the edge of the maze.
     */
    private Position randomEdgePosition(int rows, int cols) {
        Random rand = new Random();
        int side = rand.nextInt(4);
        switch (side) {
            case 0: return new Position(0, rand.nextInt(cols)); // Top edge
            case 1: return new Position(rows - 1, rand.nextInt(cols)); // Bottom edge
            case 2: return new Position(rand.nextInt(rows), 0); // Left edge
            case 3: return new Position(rand.nextInt(rows), cols - 1); // Right edge
            default: return new Position(0, 0); // Default case (should not occur)
        }
    }
}
