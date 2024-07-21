package algorithms.maze3D;

import algorithms.search.AState;

;

/**
 * Maze3DState represents a state in a 3D maze search problem.
 * It extends AState and includes specific functionality for 3D maze states.
 */
public class Maze3DState extends AState {

    public Maze3DState(AState PS, Position3D position, double cost) {
        super(PS, position, cost);
    }
}
