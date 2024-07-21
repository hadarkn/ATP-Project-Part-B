package algorithms.maze3D;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a 3D Maze with a grid of cells, start position, and goal position.
 */
public class Maze3D {
    private int[][][] maze3D;
    private int depth;
    private int rows;
    private int cols;
    private Position3D startPosition;
    private Position3D goalPosition;

    /**
     * Constructs a 3D Maze with the specified dimensions.
     *
     * @param depth The depth of the maze.
     * @param rows  The number of rows in the maze.
     * @param cols  The number of columns in the maze.
     */
    public Maze3D(int depth, int rows, int cols) {
        this.depth = depth;
        this.rows = rows;
        this.cols = cols;
        startPosition = new Position3D(0, 0, 0);
        goalPosition = new Position3D(depth- 1, rows - 1, cols - 1);
        this.maze3D = new int[depth][rows][cols];
    }



    /**
     * Returns the 3D array representing the maze.
     *
     * @return The maze grid.
     */
    public int[][][] getMaze() {
        return maze3D;
    }

    /**
     * Returns the starting position of the maze.
     *
     * @return The starting position.
     */
    public Position3D getStartPosition() {
        return startPosition;
    }

    /**
     * Returns the goal position of the maze.
     *
     * @return The goal position.
     */
    public Position3D getGoalPosition() {
        return goalPosition;
    }

    // Setters

    /**
     * Sets the value of a specific cell in the maze.
     *
     * @param depth The depth index of the cell.
     * @param row   The row index of the cell.
     * @param col   The column index of the cell.
     * @param value The value to set in the cell.
     */
    public void setCell(int depth, int row, int col, int value) {
        this.maze3D[depth][row][col] = value;
    }

    /**
     * Returns the value of a specific cell in the maze.
     *
     * @param pos The position of the cell.
     * @return The value of the cell.
     */
    public int getCell(Position3D pos) {
        return maze3D[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()];
    }

    /**
     * Sets the starting position of the maze.
     *
     * @param startPosition The starting position to set.
     */
    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Sets the goal position of the maze.
     *
     * @param goalPosition The goal position to set.
     */
    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
    }

    public void wall(Position3D p) {
        if (!p.equals(startPosition) || !p.equals(goalPosition))
            maze3D[p.getDepthIndex()][p.getRowIndex()][p.getColumnIndex()] = 1;
    }

    /**
     * Function that create path in a specific position in the maze
     */
    public void path(Position3D p) {
        maze3D[p.getDepthIndex()][p.getRowIndex()][p.getColumnIndex()] = 0;
    }
    /**
     * Prints the maze to the console, marking the start position with 'S' and the goal position with 'E'.
     */
    public void print() {
        System.out.println("{");
        for (int k = 0; k <depth; k++) {
            System.out.println("  {");
            for (int i = 0; i < rows; i++) {
                System.out.print("    {");
                for (int j = 0; j < cols; j++) {
                    if (0 == i && 0 == j && 0 == k)
                        System.out.print("S,");
                    else if (getGoalPosition().getDepthIndex() == k && getGoalPosition().getRowIndex() == i && getGoalPosition().getColumnIndex() == j)
                        System.out.print("E");
                    else if (j < cols - 1) {
                        System.out.print(maze3D[k][i][j] + ",");
                    } else
                        System.out.print(maze3D[k][i][j]);
                }
                if (i == rows-1)
                    System.out.println("}");
                else
                    System.out.println("},");
            }
            if (k == depth-1)
                System.out.println("  }");
            else
                System.out.println("  },");
        }
        System.out.println("}");
    }

    /**
     * Checks if there is a path from the start position to the goal position using BFS.
     *
     * @return true if there is a path, false otherwise.
     */
    public boolean isSolvable() {
        boolean[][][] visited = new boolean[depth][rows][cols];
        Queue<Position3D> queue = new LinkedList<>();
        queue.add(startPosition);
        visited[startPosition.getDepthIndex()][startPosition.getRowIndex()][startPosition.getColumnIndex()] = true;

        while (!queue.isEmpty()) {
            Position3D current = queue.poll();
            int depth = current.getDepthIndex();
            int row = current.getRowIndex();
            int col = current.getColumnIndex();

            // Check if we reached the goal
            if (current.equals(goalPosition)) {
                return true;
            }

            // Explore neighbors
            Position3D[] neighbors = {
                    new Position3D(depth - 1, row, col),
                    new Position3D(depth + 1, row, col),
                    new Position3D(depth, row - 1, col),
                    new Position3D(depth, row + 1, col),
                    new Position3D(depth, row, col - 1),
                    new Position3D(depth, row, col + 1)
            };

            for (Position3D neighbor : neighbors) {
                int neighborDepth = neighbor.getDepthIndex();
                int neighborRow = neighbor.getRowIndex();
                int neighborCol = neighbor.getColumnIndex();
                if (inMaze(neighbor) && !visited[neighborDepth][neighborRow][neighborCol] && maze3D[neighborDepth][neighborRow][neighborCol] == 0) {
                    queue.add(neighbor);
                    visited[neighborDepth][neighborRow][neighborCol] = true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if a given position is within the boundaries of the maze.
     *
     * @param position The position to check.
     * @return true if the position is within the maze boundaries, false otherwise.
     */
    public boolean inMaze(Position3D position) {
        if (position == null) {
            return false;
        }
        int depth = position.getDepthIndex();
        int row = position.getRowIndex();
        int col = position.getColumnIndex();
        return depth >= 0 && depth < this.depth && row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    public void fillMazeWithWalls() {
        for (int k = 0; k < depth; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    wall(new Position3D(k, i, j));
                }
            }
        }
    }
}
