package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Server strategy for generating a maze based on client input.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream clientInputStream, OutputStream clientOutputStream) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientInputStream);
            ObjectOutputStream outputStream = new ObjectOutputStream(clientOutputStream);

            int[] mazeDimensions = (int[]) inputStream.readObject();
            if (mazeDimensions.length != 2) {
                throw new IOException("Received an invalid dimensions array from the client.");
            }

            Maze maze = createMaze(mazeDimensions[0], mazeDimensions[1]);

            ByteArrayOutputStream compressedOutputStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressorStream = new MyCompressorOutputStream(compressedOutputStream);
            compressorStream.write(maze.toByteArray());

            outputStream.writeObject(compressedOutputStream.toByteArray());
            outputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Maze createMaze(int rows, int columns) {
        Lock lock = new ReentrantLock(true);
        Maze maze = null;

        try {
            Configurations config = Configurations.getInstance();
            AMazeGenerator mazeGen = config.getMazeGeneratingAlgorithm();

            String tempDir = System.getProperty("java.io.tmpdir");
            String mazeFilePath = tempDir + File.separator + "maze_" + rows + "x" + columns + ".maze";

            lock.lock();
            File mazeFile = new File(mazeFilePath);

            if (mazeFile.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(mazeFilePath);
                     ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    maze = (Maze) objectInputStream.readObject();
                }
            } else {
                lock.unlock();
                maze = mazeGen.generate(rows, columns);

                lock.lock();
                try (FileOutputStream fileOutputStream = new FileOutputStream(mazeFilePath);
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                    objectOutputStream.writeObject(maze);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return maze;
    }
}
