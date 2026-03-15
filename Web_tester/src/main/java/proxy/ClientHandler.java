package proxy;


import models.HttpRequest;
import services.RequestParser;
import java.io.*;
import java.net.Socket;
import ui.*;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {

            BufferedReader clientReader =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            OutputStream clientOutput = clientSocket.getOutputStream();

            RequestParser parser = new RequestParser();

            HttpRequest request = parser.parse(clientReader);

            if (request == null) {
                System.out.println("from the client has null request");
                return;
            }

            String host = request.getHeaders().get("Host");

            String[] hostParts = host.split(":");

            String hostname = hostParts[0];
            int port = hostParts.length > 1 ? Integer.parseInt(hostParts[1]) : 80;

            Socket serverSocket = new Socket(hostname, port);

            OutputStream serverOut = serverSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(serverOut);

            writer.println(request.getMethod() + " " + request.getPath() + " " + request.getHttpVersion());

            request.getHeaders().forEach((k, v) -> writer.println(k + ": " + v));

            writer.println();
            writer.flush();

            InputStream serverInput = serverSocket.getInputStream();

            byte[] buffer = new byte[8192];
            int bytesRead;

            StringBuilder responseCapture = new StringBuilder();

            while ((bytesRead = serverInput.read(buffer)) != -1) {

                clientOutput.write(buffer, 0, bytesRead);
                clientOutput.flush();

                responseCapture.append(new String(buffer, 0, bytesRead));
            }

            // ---- ADD UI LOGGING HERE ----

            String rawRequest =
                    request.getMethod() + " " + request.getPath();

            String rawResponse = responseCapture.toString();

            RequestEntry entry = new RequestEntry(
                    request.getMethod(),
                    hostname,
                    request.getPath(),
                    "200",
                    rawRequest,
                    rawResponse
            );

            ProxyEventBus.publish(entry);

            // ----------------------------

            serverSocket.close();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}