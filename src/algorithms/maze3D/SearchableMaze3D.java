package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;


/**Class that implement the interface ISearchable and return data about the states in the maze, there are 3 main functions in this class:
 * GetStartState, GetGoalState and getAllPossibleStates(important function that return all the possible states to move to from
 * the current state in the maze, the function also take care about the cost of every step to another state in the maze)*/
import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private Maze3D maze;
    private Maze3DState startState;
    private Maze3DState endState;

    public SearchableMaze3D(Maze3D maze) {
        this.maze=maze;
        this.startState=new Maze3DState(null, maze.getStartPosition(),0);
        this.endState=new Maze3DState(null, maze.getGoalPosition(),0);
    }

    public Maze3D getMaze() {
        return maze;
    }

    @Override
    public AState getGoalState() {
        return this.endState;
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    /**Function that find all the possible states of the solution of the maze.
     * the difference here in comparison to the regular maze is the option to move up and down, in
     * the depth index of the maze. another difference is that in the 3DMaze the path of the solution
     * can not be with via diagonally*/
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> allPossibleStates=new ArrayList<>();
        if (s==null)
            return allPossibleStates;
        Position3D currentPosition=(Position3D) s.getPosition();
        //all the 6 options to move in maze
        Position3D top=new Position3D(currentPosition.getDepthIndex(),currentPosition.getRowIndex()-1, currentPosition.getColumnIndex());
        Position3D bottom=new Position3D(currentPosition.getDepthIndex(),currentPosition.getRowIndex()+1,currentPosition.getColumnIndex());
        Position3D right=new Position3D(currentPosition.getDepthIndex(),currentPosition.getRowIndex(),currentPosition.getColumnIndex()+1);
        Position3D left=new Position3D(currentPosition.getDepthIndex(),currentPosition.getRowIndex(),currentPosition.getColumnIndex()-1);
        Position3D up=new Position3D(currentPosition.getDepthIndex()-1,currentPosition.getRowIndex(),currentPosition.getColumnIndex());
        Position3D down=new Position3D(currentPosition.getDepthIndex()+1,currentPosition.getRowIndex(),currentPosition.getColumnIndex());


        //if up is path add to the list and update is cost
        //top
        if (maze.inMaze(top) && maze.getCell(top)==0)
            allPossibleStates.add(new Maze3DState(s,top, s.getCost()+10));
        //bottom
        if (maze.inMaze(bottom) && maze.getCell(bottom)==0)
            allPossibleStates.add(new Maze3DState(s,bottom, s.getCost()+10));
        //right
        if (maze.inMaze(right) && maze.getCell(right)==0)
            allPossibleStates.add(new Maze3DState(s,right, s.getCost()+10));
        //left
        if (maze.inMaze(left) && maze.getCell(left)==0)
            allPossibleStates.add(new Maze3DState(s,left, s.getCost()+10));
        //up
        if (maze.inMaze(up) && maze.getCell(up)==0)
            allPossibleStates.add(new Maze3DState(s,up, s.getCost()+10));
        //down
        if (maze.inMaze(down) && maze.getCell(down)==0)
            allPossibleStates.add(new Maze3DState(s,down, s.getCost()+10));

        return allPossibleStates;
    }
}