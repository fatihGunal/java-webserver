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

    public ClientHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public String handleClient() {
        logger.info("New connection established");

        String request = "";

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            request = in.readLine();
        } catch (IOException e) {
            logger.error(e);
        }
        return request;
    }
}
