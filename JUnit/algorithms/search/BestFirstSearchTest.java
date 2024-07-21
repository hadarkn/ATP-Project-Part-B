package algorithms.search;

import algorithms.mazeGenerators.Maze;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void testSolveNullMaze() {
        ISearchingAlgorithm searcher = new BestFirstSearch();
        assertNull(searcher.solve(null), "Expected null for a null maze");
    }

    @Test
    void testSolveZeroOnOneMaze() {
        SearchableMaze maze = new SearchableMaze(new Maze(0, 1));
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(maze);
        assertNull(solution, "Solution should be null for an unsolvable maze of size 0x1");
    }

    @Test
    void testSolveOneOnZeroMaze() {
        SearchableMaze maze = new SearchableMaze(new Maze(1, 0));
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(maze);
        assertNull(solution, "Solution should be null for an unsolvable maze of size 1x0");
    }


    @Test
    void testGetName() {
        ISearchingAlgorithm searcher = new BestFirstSearch();
        assertEquals("Best First Search", searcher.getName(), "Expected the name 'Best First Search'");
    }


}
