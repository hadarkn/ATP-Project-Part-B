package algorithms.maze3D;

import algorithms.maze3D.Maze3D;

/**
 * Interface for generating 3D mazes.
 */
public interface IMaze3DGenerator {

    /**
     * Generates a 3D maze with the specified dimensions.
     *
     * @param depth  The depth dimension of the maze.
     * @param rows   The row dimension of the maze.
     * @param columns The column dimension of the maze.
     * @return The generated Maze3D object.
     */
    Maze3D generate(int depth, int rows, int columns);

    /**
     * Measures the time it takes to generate a 3D maze with the specified dimensions.
     *
     * @param depth  The depth dimension of the maze.
     * @param rows   The row dimension of the maze.
     * @param columns The column dimension of the maze.
     * @return The time it took to generate the maze in milliseconds.
     */
    long measureAlgorithmTimeMillis(int depth, int rows, int columns);
}
