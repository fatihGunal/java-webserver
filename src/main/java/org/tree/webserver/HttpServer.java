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
        logger.info("Starting HTTP Server");
        while (true) {
            logger.info("Accepting client connections...");
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            RequestHandler requestHandler = new RequestHandler(socket);
            String request = clientHandler.handleClient();
            requestHandler.handleRequest(request);
            stop();
        }
    }

    public void stop() throws IOException {
        logger.info("Shutting down socket...");
        serverSocket.close();
    }
}
