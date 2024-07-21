package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirstSearch extends BreadthFirstSearch {
    @Override
    public Queue<AState> getDataStructure() {
        return new PriorityQueue<>(new CostComparator());
    }

    @Override
    public String getName() {
        return "Best First Search";
    }
}

class CostComparator implements Comparator<AState> {
    @Override
    public int compare(AState o1, AState o2) {
        return Double.compare(o1.getCost(), o2.getCost());
    }
}
