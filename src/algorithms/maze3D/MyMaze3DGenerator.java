package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;



/**
 * Generates a 3D maze using a randomized algorithm.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator {
    /**
     * Generates a 3D maze of specified dimensions using a randomized algorithm.
     *
     * @param depth   The depth dimension of the maze.
     * @param rows    The row dimension of the maze.
     * @param columns The column dimension of the maze.
     * @return A Maze3D object representing the generated maze.
     */
    @Override
    public Maze3D generate(int depth, int rows, int columns) {
        if (depth < 1 || rows < 1 || columns < 1) return new Maze3D(1, 1, 1);
        int[][][] mazeData = new int[depth][rows][columns];
        Position3D start, end;
        Random random = new Random();

        // Initialize maze with walls (1's)
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    mazeData[i][j][k] = 1;
                }
            }
        }

        // Choose starting position
        start = initializeStartPosition(mazeData, depth, rows, columns, random);

        // Generate maze using DFS-like algorithm
        return dfsGenerateMaze(mazeData, depth, rows, columns, start, random);
    }

    /**
     * Generates a solvable 3D maze using a randomized algorithm.
     *
     * @param mazeData      The 3D maze grid represented as a 3D array of integers.
     * @param depth         The depth dimension of the maze.
     * @param rows          The row dimension of the maze.
     * @param columns       The column dimension of the maze.
     * @param startPosition The starting position of the maze.
     * @param random        Random number generator.
     * @return A Maze3D object representing the generated maze.
     */
    private Maze3D dfsGenerateMaze(int[][][] mazeData, int depth, int rows, int columns, Position3D startPosition, Random random) {
        Stack<Position3D> positionStack = new Stack<>();
        ArrayList<Position3D> path = new ArrayList<>();
        Position3D currentPosition, nextPosition;
        ArrayList<Position3D> allNeighbors;

        nextPosition = startPosition;
        positionStack.push(nextPosition);
        while (!positionStack.isEmpty()) {
            currentPosition = positionStack.pop();
            boolean hasUnvisitedNeighbor = hasUnvisitedNeighbors(currentPosition, depth, rows, columns, mazeData);
            if (hasUnvisitedNeighbor) {
                positionStack.push(currentPosition);
                allNeighbors = getNeighboringPositions(currentPosition, mazeData, 2, 1);
                nextPosition = allNeighbors.get(random.nextInt(allNeighbors.size()));

                // Break the walls and add them to the path
                path.add(breakWalls(mazeData, currentPosition.getDepthIndex(), nextPosition.getDepthIndex(),
                        currentPosition.getRowIndex(), nextPosition.getRowIndex(),
                        currentPosition.getColumnIndex(), nextPosition.getColumnIndex()));

                // Mark the neighbor as visited
                mazeData[nextPosition.getDepthIndex()][nextPosition.getRowIndex()][nextPosition.getColumnIndex()] = 0;
                positionStack.push(nextPosition);
                path.add(nextPosition);
            }
        }

        // Select random end position and ensure it's on the border
        Position3D endPosition = path.get(random.nextInt(path.size()));
        while (!isOnBorder(endPosition, depth, rows, columns)) {
            if (path.size() > 1)
                path.remove(endPosition);
            endPosition = path.get(random.nextInt(path.size()));
        }

        // Create a new Maze3D object with the generated maze and positions
        Maze3D generatedMaze = new Maze3D(depth, rows, columns);
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    generatedMaze.setCell(i, j, k, mazeData[i][j][k]);
                }
            }
        }
        generatedMaze.setStartPosition(startPosition);
        generatedMaze.setGoalPosition(endPosition);

        return generatedMaze;
    }

    /**
     * Checks if a position in the 3D maze has any unvisited neighbors (cells with value 1).
     *
     * @param currentPosition The current position to check.
     * @param depth           Number of depth levels in the maze.
     * @param rows            Number of rows in the maze.
     * @param columns         Number of columns in the maze.
     * @param mazeData        The 3D maze grid represented as a 3D array of integers.
     * @return True if the current position has unvisited neighbors; false otherwise.
     */
    public boolean hasUnvisitedNeighbors(Position3D currentPosition, int depth, int rows, int columns, int[][][] mazeData) {
        int distance = 2;

        // Check neighbors in depth dimension
        if (currentPosition.getDepthIndex() + distance < depth) {
            if (mazeData[currentPosition.getDepthIndex() + distance][currentPosition.getRowIndex()][currentPosition.getColumnIndex()] == 1)
                return true;
        }
        if (currentPosition.getDepthIndex() - distance >= 0) {
            if (mazeData[currentPosition.getDepthIndex() - distance][currentPosition.getRowIndex()][currentPosition.getColumnIndex()] == 1)
                return true;
        }

        // Check neighbors in row dimension
        if (currentPosition.getRowIndex() + distance < rows) {
            if (mazeData[currentPosition.getDepthIndex()][currentPosition.getRowIndex() + distance][currentPosition.getColumnIndex()] == 1)
                return true;
        }
        if (currentPosition.getRowIndex() - distance >= 0) {
            if (mazeData[currentPosition.getDepthIndex()][currentPosition.getRowIndex() - distance][currentPosition.getColumnIndex()] == 1)
                return true;
        }

        // Check neighbors in column dimension
        if (currentPosition.getColumnIndex() + distance < columns) {
            if (mazeData[currentPosition.getDepthIndex()][currentPosition.getRowIndex()][currentPosition.getColumnIndex() + distance] == 1)
                return true;
        }
        if (currentPosition.getColumnIndex() - distance >= 0) {
            if (mazeData[currentPosition.getDepthIndex()][currentPosition.getRowIndex()][currentPosition.getColumnIndex() - distance] == 1)
                return true;
        }

        return false;
    }

    /**
     * Breaks the "wall" between two cells in the 3D maze and returns the position of the broken wall.
     *
     * @param mazeData         The 3D maze grid represented as a 3D array of integers.
     * @param currentDepth     Depth index of current position.
     * @param nextDepth        Depth index of next position.
     * @param currentRow       Row index of current position.
     * @param nextRow          Row index of next position.
     * @param currentColumn    Column index of current position.
     * @param nextColumn       Column index of next position.
     * @return Position3D of the broken wall.
     */
    private Position3D breakWalls(int[][][] mazeData, int currentDepth, int nextDepth, int currentRow, int nextRow, int currentColumn, int nextColumn) {
        if (currentDepth - nextDepth == 2) {
            mazeData[currentDepth - 1][currentRow][currentColumn] = 0;
            return new Position3D(currentDepth - 1, currentRow, currentColumn);
        }
        if (currentDepth - nextDepth == -2) {
            mazeData[currentDepth + 1][currentRow][currentColumn] = 0;
            return new Position3D(currentDepth + 1, currentRow, currentColumn);
        }
        if (currentRow - nextRow == 2) {
            mazeData[currentDepth][currentRow - 1][currentColumn] = 0;
            return new Position3D(currentDepth, currentRow - 1, currentColumn);
        }
        if (currentRow - nextRow == -2) {
            mazeData[currentDepth][currentRow + 1][currentColumn] = 0;
            return new Position3D(currentDepth, currentRow + 1, currentColumn);
        }
        if (currentColumn - nextColumn == 2) {
            mazeData[currentDepth][currentRow][currentColumn - 1] = 0;
            return new Position3D(currentDepth, currentRow, currentColumn - 1);
        }
        if (currentColumn - nextColumn == -2) {
            mazeData[currentDepth][currentRow][currentColumn + 1] = 0;
            return new Position3D(currentDepth, currentRow, currentColumn + 1);
        }

        // Default return if no walls were broken (should not happen)
        return new Position3D(-1, -1, -1);
    }

    /**
     * Checks if a position is on the border of the 3D maze.
     *
     * @param pos       The position to check.
     * @param depth     Number of depth levels in the maze.
     * @param rows      Number of rows in the maze.
     * @param columns   Number of columns in the maze.
     * @return True if the position is on the border; false otherwise.
     */
    private boolean isOnBorder(Position3D pos, int depth, int rows, int columns) {
        int depthIndex = pos.getDepthIndex();
        int rowIndex = pos.getRowIndex();
        int columnIndex = pos.getColumnIndex();
        return depthIndex <= 0 || depthIndex >= depth - 1 || rowIndex <= 0 || rowIndex >= rows - 1 || columnIndex <= 0 || columnIndex >= columns - 1;
    }

    /**
     * Initializes the start position of the 3D maze.
     *
     * @param mazeData The 3D maze grid represented as a 3D array of integers.
     * @param depth    Number of depth levels in the maze.
     * @param rows     Number of rows in the maze.
     * @param columns  Number of columns in the maze.
     * @param random   Random number generator.
     * @return The initialized start position.
     */
    private Position3D initializeStartPosition(int[][][] mazeData, int depth, int rows, int columns, Random random) {
        Position3D startPosition;
        int startDepth = random.nextInt(depth);
        int startRow = random.nextInt(rows);
        int startColumn = random.nextInt(columns);

        // Ensure start position is on the border of the 3D maze
        while (startDepth != 0 && startDepth != depth - 1 && startRow != 0 && startRow != rows - 1 && startColumn != 0 && startColumn != columns - 1) {
            startDepth = random.nextInt(depth);
            startRow = random.nextInt(rows);
            startColumn = random.nextInt(columns);
        }

        startPosition = new Position3D(startDepth, startRow, startColumn);
        mazeData[startDepth][startRow][startColumn] = 0; // Mark start position as visited

        return startPosition;
    }

    /**
     * Gets a list of valid neighboring positions for a given position in the 3D maze.
     *
     * @param pos              The position to get neighbors for.
     * @param mazeData         The 3D maze grid represented as a 3D array of integers.
     * @param neighborDistance The distance between neighboring cells.
     * @param wallValue        The value representing walls in the maze.
     * @return An ArrayList of valid neighboring positions.
     */
    private ArrayList<Position3D> getNeighboringPositions(Position3D pos, int[][][] mazeData, int neighborDistance, int wallValue) {
        ArrayList<Position3D> neighbors = new ArrayList<>();
        int depth = pos.getDepthIndex();
        int row = pos.getRowIndex();
        int column = pos.getColumnIndex();

        // Check neighbors in depth dimension
        if (depth + neighborDistance < mazeData.length && mazeData[depth + neighborDistance][row][column] == wallValue) {
            neighbors.add(new Position3D(depth + neighborDistance, row, column));
        }
        if (depth - neighborDistance >= 0 && mazeData[depth - neighborDistance][row][column] == wallValue) {
            neighbors.add(new Position3D(depth - neighborDistance, row, column));
        }

        // Check neighbors in row dimension
        if (row + neighborDistance < mazeData[0].length && mazeData[depth][row + neighborDistance][column] == wallValue) {
            neighbors.add(new Position3D(depth, row + neighborDistance, column));
        }
        if (row - neighborDistance >= 0 && mazeData[depth][row - neighborDistance][column] == wallValue) {
            neighbors.add(new Position3D(depth, row - neighborDistance, column));
        }

        // Check neighbors in column dimension
        if (column + neighborDistance < mazeData[0][0].length && mazeData[depth][row][column + neighborDistance] == wallValue) {
            neighbors.add(new Position3D(depth, row, column + neighborDistance));
        }
        if (column - neighborDistance >= 0 && mazeData[depth][row][column - neighborDistance] == wallValue) {
            neighbors.add(new Position3D(depth, row, column - neighborDistance));
        }

        return neighbors;
    }

}
