package Server;


import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configurations {
    private static Configurations instance=null;
    private static Properties properties;

    private Configurations() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("resources/config.properties");
            properties.load(fis);
            //fis.close();
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "4"));
    }




    public AMazeGenerator getMazeGeneratingAlgorithm() throws IOException {
        String propAlgorithm = properties.getProperty("mazeGeneratingAlgorithm");
        switch (propAlgorithm) {
            case "EmptyMazeGenerator":
                return new EmptyMazeGenerator();
            case "SimpleMazeGenerator":
                return new SimpleMazeGenerator();
            default:
                return new MyMazeGenerator();
        }
    }


    public ASearchingAlgorithm getMazeSolvingAlgorithm() {
        String propAlgorithm = properties.getProperty("mazeSearchingAlgorithm");
        switch (propAlgorithm) {
            case "BreadthFirstSearch":
                return new BreadthFirstSearch();
            case "DepthFirstSearch":
                return new DepthFirstSearch();
            default:
                return new BestFirstSearch();
        }
    }




}
