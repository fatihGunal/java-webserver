package org.tree.webserver.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    private Socket socket;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String handleClient() {
        logger.info("New connection established");
        String request = "";
        try {
            request = in.readLine();
        } catch (IOException e) {
            logger.error(e);
        }
        return request;
    }

    public void close() throws IOException {
        in.close();
    }
}
