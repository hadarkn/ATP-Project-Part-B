package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * An abstract class representing a general searching algorithm.
 * It implements the ISearchingAlgorithm interface.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected Solution solution;
    protected int nodesEvaluatedCount;

    /**
     * Default constructor initializes the solution and node evaluation count.
     */
    public ASearchingAlgorithm() {
        this.solution = new Solution();
        this.nodesEvaluatedCount = 0;
    }

    /**
     * Solves the given searchable domain.
     * This method should be overridden by subclasses.
     *
     * @param searchable the domain to solve.
     * @return the solution of the search.
     */
    @Override
    public Solution solve(ISearchable searchable) {
        return null;
    }

    /**
     * Gets the number of nodes evaluated by the search algorithm.
     *
     * @return the number of nodes evaluated.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return nodesEvaluatedCount;
    }

    public void setNodesEvaluatedCount(int i){nodesEvaluatedCount+=i;}



    /**
     * Traces back from the goal state to the start state to construct the solution path.
     *
     * @param goalState the goal state from which to start backtracking.
     * @param solution the solution object to store the path.
     */
    public void constructSolutionPath(AState goalState, Solution solution) {
        Stack<AState> pathStack = new Stack<>();
        AState currentState = goalState;

        if (goalState != null && goalState.getPrevState() != null) {
            pathStack.push(currentState);
            while (currentState.getPrevState() != null) {
                currentState = currentState.getPrevState();
                pathStack.push(currentState);
            }

            while (!pathStack.isEmpty()) {
                solution.addState(pathStack.pop());
            }
        }
    }

}



