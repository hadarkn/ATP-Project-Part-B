package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * MazeState extends AState and represents a state within a maze.
 * It encapsulates a position (coordinates) within the maze.
 */
public class MazeState extends AState {
    private Position position;

    /**
     * Constructs a MazeState object with the specified state, position, and cost.
     *
     * @param state    The predecessor state.
     * @param pos      The position (coordinates) within the maze.
     * @param cost     The cost to reach this state.
     */
    public MazeState(AState state, Position pos, double cost) {
        super(state, pos, cost);
        this.position = pos;
    }

    /**
     * Retrieves the position associated with this maze state.
     *
     * @return The position (coordinates) within the maze.
     */
    public Position getPosition() {
        return position;
    }
}
