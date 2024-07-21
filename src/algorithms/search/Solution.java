package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**Class that represents the solution of the maze. The class creates an ArrayList that contains all the steps in the maze*/
public class Solution implements Serializable {
    private ArrayList<AState> solPath;

    public Solution() {
        solPath = new ArrayList<>();
    }

    public Solution(ArrayList<AState> solPath) {
        this.solPath = solPath;
    }

    public ArrayList<AState> getSolutionPath() {
        return solPath;
    }

    public int getSolutionLength() {
        return solPath.size();
    }

    public void addState(AState state) {
        if (state != null) {
            solPath.add(solPath.size(), state);
        }
    }

    public void reversePath() {
        Collections.reverse(solPath);
    }
}
