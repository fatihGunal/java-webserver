package org.tree.webserver.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    private Socket socket;
    PrintWriter out;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void handleRequest(String request) {
        logger.info("New connection established");
        printRequest(out);
    }

    private void printRequest(PrintWriter out) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println(); // End of headers
        out.println("Fatih"); // Body
        out.flush(); // Ensure data is sent
    }

    public void close() {
        out.close();
    }
}
