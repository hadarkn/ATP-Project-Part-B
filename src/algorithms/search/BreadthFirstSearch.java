package algorithms.search;

import java.util.*;

/**
 * BFS algorithm works with LinkedHashSet and Queue. Algorithm for searching a tree data structure for a node that
 * satisfies a given property. It starts at the tree root and explores all nodes at the present depth prior to moving
 * on to the nodes at the next depth level.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch() {
        super();
    }

    @Override
    public Solution solve(ISearchable s) {
        if (s == null || ((SearchableMaze) s).getMaze() == null ||
                ((SearchableMaze) s).getMaze().getRows() < 1 || ((SearchableMaze) s).getMaze().getCols() < 1) {
            return null;
        }

        // Initialize solution and queue
        Solution solution = new Solution();
        Set<String> visited = new HashSet<>();
        Queue<AState> states = getDataStructure();

        // Start from the initial state
        AState startState = s.getStartState();
        states.add(startState);
        visited.add(startState.toString());

        while (!states.isEmpty()) {
            AState current = states.poll();
            nodesEvaluatedCount++;

            // Check if we reached the goal state
            if (current.equals(s.getGoalState())) {
                return constructSolutionPath(current);
            }

            // Get all possible states from the current state
            List<AState> neighbors = s.getAllPossibleStates(current);

            // Add unvisited neighbors to the queue
            for (AState neighbor : neighbors) {
                if (!visited.contains(neighbor.toString())) {
                    neighbor.setPrevState(current);
                    states.add(neighbor);
                    visited.add(neighbor.toString());
                }
            }
        }

        return null;
    }

    /**
     * Constructs the solution path from the goal state to the start state.
     *
     * @param goalState The goal state from which to construct the path.
     * @return The constructed solution.
     */
    private Solution constructSolutionPath(AState goalState) {
        Solution solution = new Solution();
        AState current = goalState;

        // Backtrack from the goal state to the start state to construct the path
        while (current != null) {
            solution.addState(current);
            current = current.getPrevState();
        }

        // Reverse the path to start from the start state
        Collections.reverse(solution.getSolutionPath());
        return solution;
    }

    public Queue<AState> getDataStructure() {
        return new LinkedList<>();
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
