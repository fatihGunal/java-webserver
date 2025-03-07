package org.tree.webserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tree.webserver.handler.ClientHandler;
import org.tree.webserver.handler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {
    private static final Logger logger = LogManager.getLogger(HttpServer.class);

    private ServerSocket serverSocket;

    public HttpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            logger.info("Accepting client connections...");
            ClientHandler clientHandler = new ClientHandler(socket);
            RequestHandler requestHandler = new RequestHandler(socket);
            String request = clientHandler.handleClient();
            requestHandler.handleRequest(request);
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
