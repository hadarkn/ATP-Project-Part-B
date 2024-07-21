package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private boolean stop;
    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started on port " + port);
                threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getThreadPoolSize());

                while (!stop) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Client connected: " + clientSocket.getInetAddress());
                        threadPool.execute(() -> handleClient(clientSocket));
                    } catch (IOException e) {
                        if (stop) {
                            System.out.println("Server stopped.");
                            break;
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
                threadPool.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println("Handling client on port: " + clientSocket.getPort());
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
            System.out.println("Client handled and socket closed on port: " + clientSocket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
