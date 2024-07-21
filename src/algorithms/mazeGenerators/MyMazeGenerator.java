package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Generates a maze using the Depth-First Search (DFS) algorithm.
 */
public class MyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze of specified dimensions using the Depth-First Search (DFS) algorithm.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return A Maze object representing the generated maze.
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows < 1 || columns < 1) return new Maze(1, 1);
        int[][] mazeData = new int[rows][columns];
        Position start, end;
        Random random = new Random();

        // Initialize maze with walls (1's)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mazeData[i][j] = 1;
            }
        }

        // Choose starting position
        start = initializeStartPosition(mazeData, rows, columns, random);

        // Generate maze using DFS algorithm
        return dfsGenerateMaze(mazeData, rows, columns, start, random);
    }

    /**
     * Generates a solvable maze using the Depth-First Search (DFS) algorithm.
     *
     * @param mazeData       The maze grid represented as a 2D array of integers.
     * @param rows           The number of rows in the maze.
     * @param columns        The number of columns in the maze.
     * @param startPosition  The starting position of the maze.
     * @param random         Random number generator.
     * @return               A Maze object representing the generated maze.
     */
    private Maze dfsGenerateMaze(int[][] mazeData, int rows, int columns, Position startPosition, Random random) {
        Stack<Position> positionStack = new Stack<>();
        ArrayList<Position> path = new ArrayList<>();  // Save the path of the maze
        Position currentPosition, nextPosition;
        ArrayList<Position> allNeighbors;

        nextPosition = startPosition;
        positionStack.push(nextPosition);
        while (!positionStack.isEmpty()) {
            currentPosition = positionStack.pop();
            boolean hasUnvisitedNeighbor = hasUnvisitedNeighbors(currentPosition, rows, columns, mazeData);
            if (hasUnvisitedNeighbor) {
                positionStack.push(currentPosition);
                allNeighbors = getNeighboringPositions(currentPosition, mazeData, 2, 1);
                nextPosition = allNeighbors.get(random.nextInt(allNeighbors.size()));

                // Break the walls and add them to the path
                path.add(breakWalls(mazeData, currentPosition.getRowIndex(), nextPosition.getRowIndex(),
                        currentPosition.getColumnIndex(), nextPosition.getColumnIndex()));

                // Mark the neighbor as visited
                mazeData[nextPosition.getRowIndex()][nextPosition.getColumnIndex()] = 0;
                positionStack.push(nextPosition);
                path.add(nextPosition);
            }
        }

        // Select random end position and ensure it's on the border
        Position endPosition = path.get(random.nextInt(path.size()));
        while (!isOnBorder(endPosition, rows, columns)) {
            if (path.size() > 1)
                path.remove(endPosition);
            endPosition = path.get(random.nextInt(path.size()));
        }

        // Create a new Maze object with the generated maze and positions
        Maze generatedMaze = new Maze(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                generatedMaze.setCell(i, j, mazeData[i][j]);
            }
        }
        generatedMaze.setStartPosition(startPosition);
        generatedMaze.setGoalPosition(endPosition);

        return generatedMaze;
    }

    /**
     * Checks if a position has any unvisited neighbors (cells with value 1).
     *
     * @param currentPosition  The current position to check.
     * @param rows             Number of rows in the maze.
     * @param columns          Number of columns in the maze.
     * @param mazeData         The maze grid represented as a 2D array of integers.
     * @return                 True if the current position has unvisited neighbors; false otherwise.
     */
    public boolean hasUnvisitedNeighbors(Position currentPosition, int rows, int columns, int[][] mazeData) {
        int distance = 2;

        // Check downward neighbor
        if (currentPosition.getRowIndex() + distance < rows) {
            if (mazeData[currentPosition.getRowIndex() + distance][currentPosition.getColumnIndex()] == 1)
                return true;
        }

        // Check upward neighbor
        if (currentPosition.getRowIndex() - distance >= 0) {
            if (mazeData[currentPosition.getRowIndex() - distance][currentPosition.getColumnIndex()] == 1)
                return true;
        }

        // Check right neighbor
        if (currentPosition.getColumnIndex() + distance < columns) {
            if (mazeData[currentPosition.getRowIndex()][currentPosition.getColumnIndex() + distance] == 1)
                return true;
        }

        // Check left neighbor
        if (currentPosition.getColumnIndex() - distance >= 0) {
            if (mazeData[currentPosition.getRowIndex()][currentPosition.getColumnIndex() - distance] == 1)
                return true;
        }

        return false;
    }

    /**
     * Breaks the "wall" between two cells and returns the position of the broken wall.
     *
     * @param mazeData  The maze grid represented as a 2D array of integers.
     * @param currentRow  Row index of current position.
     * @param nextRow  Row index of next position.
     * @param currentColumn  Column index of current position.
     * @param nextColumn  Column index of next position.
     * @return  Position of the broken wall.
     */
    private Position breakWalls(int[][] mazeData, int currentRow, int nextRow, int currentColumn, int nextColumn) {
        if (currentRow - nextRow == 2) {
            mazeData[currentRow - 1][currentColumn] = 0;
            return new Position(currentRow - 1, currentColumn);
        }
        if (currentRow - nextRow == -2) {
            mazeData[currentRow + 1][currentColumn] = 0;
            return new Position(currentRow + 1, currentColumn);
        }
        if (currentColumn - nextColumn == 2) {
            mazeData[currentRow][currentColumn - 1] = 0;
            return new Position(currentRow, currentColumn - 1);
        }
        if (currentColumn - nextColumn == -2) {
            mazeData[currentRow][currentColumn + 1] = 0;
            return new Position(currentRow, currentColumn + 1);
        }

        // Default return if no walls were broken (should not happen)
        return new Position(-1, -1);
    }

    /**
     * Checks if a position is on the border of the maze.
     *
     * @param pos      The position to check.
     * @param rows     Number of rows in the maze.
     * @param columns  Number of columns in the maze.
     * @return         True if the position is on the border; false otherwise.
     */
    private boolean isOnBorder(Position pos, int rows, int columns) {
        int rowIndex = pos.getRowIndex();
        int columnIndex = pos.getColumnIndex();
        return rowIndex <= 0 || rowIndex >= rows - 1 || columnIndex <= 0 || columnIndex >= columns - 1;
    }

    /**
     * Initializes the start position of the maze.
     *
     * @param mazeData  The maze grid represented as a 2D array of integers.
     * @param rows      Number of rows in the maze.
     * @param columns   Number of columns in the maze.
     * @param random    Random number generator.
     * @return          The initialized start position.
     */
    private Position initializeStartPosition(int[][] mazeData, int rows, int columns, Random random) {
        Position startPosition;
        int startRow = random.nextInt(rows);
        int startColumn = random.nextInt(columns);

        // Ensure start position is on the border of the maze
        while (startRow != 0 && startRow != rows - 1 && startColumn != 0 && startColumn != columns - 1) {
            startRow = random.nextInt(rows);
            startColumn = random.nextInt(columns);
        }

        startPosition = new Position(startRow, startColumn);
        mazeData[startRow][startColumn] = 0; // Mark start position as visited

        return startPosition;
    }

    /**
     * Gets a list of valid neighboring positions for a given position in the maze.
     *
     * @param pos                The position to get neighbors for.
     * @param mazeData           The maze grid represented as a 2D array of integers.
     * @param neighborDistance   The distance between neighboring cells.
     * @param wallValue          The value representing walls in the maze.
     * @return                   An ArrayList of valid neighboring positions.
     */
    private ArrayList<Position> getNeighboringPositions(Position pos, int[][] mazeData, int neighborDistance, int wallValue) {
        ArrayList<Position> neighbors = new ArrayList<>();
        int row = pos.getRowIndex();
        int column = pos.getColumnIndex();

        // Check downward neighbor
        if (row + neighborDistance < mazeData.length && mazeData[row + neighborDistance][column] == wallValue) {
            neighbors.add(new Position(row + neighborDistance, column));
        }

        // Check upward neighbor
        if (row - neighborDistance >= 0 && mazeData[row - neighborDistance][column] == wallValue) {
            neighbors.add(new Position(row - neighborDistance, column));
        }

        // Check right neighbor
        if (column + neighborDistance < mazeData[0].length && mazeData[row][column + neighborDistance] == wallValue) {
            neighbors.add(new Position(row, column + neighborDistance));
        }

        // Check left neighbor
        if (column - neighborDistance >= 0 && mazeData[row][column - neighborDistance] == wallValue) {
            neighbors.add(new Position(row, column - neighborDistance));
        }

        return neighbors;
    }
}

