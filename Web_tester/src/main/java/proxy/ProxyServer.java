package proxy;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer implements Runnable {

    private final int port;
    private ServerSocket serverSocket;

    private final ExecutorService pool = Executors.newCachedThreadPool();

    public ProxyServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {

            serverSocket = new ServerSocket(port);

            System.out.println("Proxy started on port " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                pool.submit(new ClientHandler(clientSocket));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}