package algorithms.mazeGenerators;

/**
 * An abstract class that implements common functionality for maze generators.
 * It provides an implementation for measuring the execution time of the generate method.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Generates a maze of specified dimensions.
     *
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     * @return The generated Maze object.
     */
    @Override
    public abstract Maze generate(int rows, int cols);

    /**
     * Measures the execution time of the generate method in milliseconds.
     *
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     * @return The execution time of the generate method in milliseconds.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long startTime = System.currentTimeMillis();
        generate(rows, cols);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
