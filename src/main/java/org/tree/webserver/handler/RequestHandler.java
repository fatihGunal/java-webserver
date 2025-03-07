package org.tree.webserver.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void handleRequest(String request) {
        logger.info("New connection established");
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println(request);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private String printRequest() {
        return "HTTP/1.1 404 OK\nContent-Type: text/plain\n\nHello, world!";
    }
}
