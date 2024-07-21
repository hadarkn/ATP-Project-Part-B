package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;


public class RunMaze3DGenerator {

    public static void main(String[] args) {
        // Define maze dimensions
        int depth = 500;
        int rows = 500;
        int columns = 500;

        // Create a maze generator
        IMaze3DGenerator generator = new MyMaze3DGenerator();

        // Measure maze generation time
        long startTime = System.currentTimeMillis();
        Maze3D maze = generator.generate(depth, rows, columns);
        long endTime = System.currentTimeMillis();

        // Output generation time
        long duration = (endTime - startTime);
        System.out.println("Generated maze in " + duration + " milliseconds.");

        // Validate if the maze is solvable (placeholder logic)
        boolean solvable = maze.isSolvable(); // Implement this method
        if (duration > 60000) {
            System.out.println("Warning: Generation time exceeded 1 minute.");
        }

        if (solvable) {
            System.out.println("Maze is solvable.");
        } else {
            System.out.println("Maze is not solvable.");
        }
    }


}
