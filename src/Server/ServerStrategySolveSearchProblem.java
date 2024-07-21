package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Server strategy for solving a maze using a client-generated maze instance.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inputStreamFromClient, OutputStream outputStreamToClient) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStreamFromClient);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStreamToClient)) {

            Maze maze = (Maze) objectInputStream.readObject();
            Solution solution = fetchOrSolveMaze(maze);

            objectOutputStream.writeObject(solution);
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution fetchOrSolveMaze(Maze maze) {
        Lock lock = new ReentrantLock(true);
        Solution solution = null;

        try {
            String tempDirPath = System.getProperty("java.io.tmpdir");
            String solutionFilePath = tempDirPath + File.separator + "solution_" + maze.hashCode() + ".solution";

            lock.lock();
            File solutionFile = new File(solutionFilePath);

            if (solutionFile.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(solutionFilePath);
                     ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    solution = (Solution) objectInputStream.readObject();
                }
            } else {
                lock.unlock();
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                Configurations config = Configurations.getInstance();
                solution = config.getMazeSolvingAlgorithm().solve(searchableMaze);

                lock.lock();
                try (FileOutputStream fileOutputStream = new FileOutputStream(solutionFilePath);
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                    objectOutputStream.writeObject(solution);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return solution;
    }
}
