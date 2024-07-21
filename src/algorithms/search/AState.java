package algorithms.search;

import java.io.Serializable;
import java.util.Objects;

/**
 * AState is an abstract class representing a state in a search algorithm.
 * It implements Serializable and Comparable interfaces.
 */
public abstract class AState implements Serializable, Comparable<AState> {
    private double cost;
    private AState prevState;
    private Object currState;

    /**
     * Default constructor initializes the state with default values.
     */
    public AState() {
        this.cost = 0;
        this.prevState = null;
        this.currState = null;
    }

    /**
     * Constructor to initialize the state with a previous state, current state, and cost.
     *
     * @param prevState the previous state leading to this state.
     * @param currState the current state.
     * @param cost      the cost to reach this state.
     */
    public AState(AState prevState, Object currState, double cost) {
        this.cost = cost;
        this.prevState = prevState;
        this.currState = currState;
    }

    /**
     * Gets the cost to reach this state.
     *
     * @return the cost.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost to reach this state.
     *
     * @param cost the cost.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets the previous state leading to this state.
     *
     * @return the previous state.
     */
    public AState getPrevState() {
        return prevState;
    }

    /**
     * Sets the previous state leading to this state.
     *
     * @param prevState the previous state.
     */
    public void setPrevState(AState prevState) {
        this.prevState = prevState;
    }

    /**
     * Gets the current state.
     *
     * @return the current state.
     */
    public Object getPosition() {
        return currState;
    }

    /**
     * Checks equality between this state and another object.
     *
     * @param obj the object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AState state = (AState) obj;
        return Objects.equals(currState, state.currState);
    }

    /**
     * Returns a hash code value for the state.
     *
     * @return a hash code value for the state.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currState);
    }

    /**
     * Returns a string representation of the current state.
     *
     * @return string representation of the current state.
     */
    @Override
    public String toString() {
        return currState != null ? currState.toString() : "null";
    }

    /**
     * Compares this state to another state based on cost.
     *
     * @param other the state to compare to.
     * @return 1 if this state has a lower cost, -1 if higher, and 0 if equal.
     */
    @Override
    public int compareTo(AState other) {
        return Double.compare(this.cost, other.cost);
    }
}
