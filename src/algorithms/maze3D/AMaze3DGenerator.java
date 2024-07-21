package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.maze3D.Maze3D;

/**
 * Abstract class implementing common functionality for 3D maze generators.
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    /**
     * Generates a maze of specified dimensions.
     *
     * @param depth the num of the depth of the maze.
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     * @return The generated Maze object.
     */
    @Override
    public abstract Maze3D generate(int depth, int rows, int columns);

    /**
     * Measures the time it takes to generate a 3D maze with the specified dimensions.
     *
     * @param depth  The depth dimension of the maze.
     * @param rows   The row dimension of the maze.
     * @param columns The column dimension of the maze.
     * @return The time it took to generate the maze in milliseconds.
     */
    @Override

    public long measureAlgorithmTimeMillis(int depth, int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(depth,rows, columns);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
