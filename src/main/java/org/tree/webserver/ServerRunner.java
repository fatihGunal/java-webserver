package org.tree.webserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerRunner {
    private static final int PORT = 8080;

    private static final Logger logger = LogManager.getLogger(ServerRunner.class);

    public static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            HttpServer server = new HttpServer(serverSocket);
            server.start();
            server.stop();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
