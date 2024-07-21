package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * SearchableMaze implements the ISearchable interface and provides functionality to retrieve
 * information about states within a maze. It includes methods to get the start state, goal state,
 * and all possible states from a given state in the maze.
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState startState;
    private MazeState goalState;

    /**
     * Constructs a SearchableMaze object with the specified maze.
     *
     * @param maze The maze to be searched.
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;

        // Check if the maze is valid
        if (maze != null && maze.getRows() > 0 && maze.getCols() > 0) {
            this.startState = new MazeState(null, maze.getStartPosition(), 0);
            this.goalState = new MazeState(null, maze.getGoalPosition(), 0);
        } else {
            this.startState = null;
            this.goalState = null;
        }
    }

    /**
     * Retrieves the maze associated with this searchable maze.
     *
     * @return The maze.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Retrieves the goal state of the maze.
     *
     * @return The goal state.
     */
    @Override
    public AState getGoalState() {
        return this.goalState;
    }

    /**
     * Retrieves the start state of the maze.
     *
     * @return The start state.
     */
    @Override
    public AState getStartState() {
        return this.startState;
    }

    /**
     * Retrieves all possible states from a given state in the maze.
     *
     * @param state The current state.
     * @return An ArrayList of all possible states from the current state.
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null) {
            return possibleStates;
        }
        Position currentPosition = (Position) state.getPosition();
        addStraightStates(state, possibleStates, currentPosition);
        addDiagonalStates(state, possibleStates, currentPosition);
        return possibleStates;
    }

    private void addStraightStates(AState state, ArrayList<AState> possibleStates, Position currentPosition) {
        addStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() - 1, currentPosition.getColumnIndex()), 10);
        addStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() + 1, currentPosition.getColumnIndex()), 10);
        addStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() - 1), 10);
        addStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() + 1), 10);
    }

    private void addDiagonalStates(AState state, ArrayList<AState> possibleStates, Position currentPosition) {
        addDiagonalStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() - 1, currentPosition.getColumnIndex() + 1), new Position(currentPosition.getRowIndex() - 1, currentPosition.getColumnIndex()), new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() + 1), 15);
        addDiagonalStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() - 1, currentPosition.getColumnIndex() - 1), new Position(currentPosition.getRowIndex() - 1, currentPosition.getColumnIndex()), new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() - 1), 15);
        addDiagonalStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() + 1, currentPosition.getColumnIndex() + 1), new Position(currentPosition.getRowIndex() + 1, currentPosition.getColumnIndex()), new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() + 1), 15);
        addDiagonalStateIfValid(state, possibleStates, new Position(currentPosition.getRowIndex() + 1, currentPosition.getColumnIndex() - 1), new Position(currentPosition.getRowIndex() + 1, currentPosition.getColumnIndex()), new Position(currentPosition.getRowIndex(), currentPosition.getColumnIndex() - 1), 15);
    }

    private void addStateIfValid(AState state, ArrayList<AState> possibleStates, Position newPosition, double cost) {
        if (maze.inMaze(newPosition) && maze.getCell(newPosition) == 0) {
            possibleStates.add(new MazeState(state, newPosition, state.getCost() + cost));
        }
    }

    private void addDiagonalStateIfValid(AState state, ArrayList<AState> possibleStates, Position newPosition, Position firstNeighbor, Position secondNeighbor, double cost) {
        if (maze.inMaze(newPosition) && maze.getCell(newPosition) == 0 && (maze.getCell(firstNeighbor) == 0 || maze.getCell(secondNeighbor) == 0)) {
            possibleStates.add(new MazeState(state, newPosition, state.getCost() + cost));
        }
    }
}
