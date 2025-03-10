package org.tree.webserver.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tree.webserver.dto.SimpleRequest;

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
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public SimpleRequest handleClient() {
        logger.info("New connection established");
        String request = "";
        try {
            request = in.readLine();
            return parseHttpRequest(request);
        } catch (IOException e) {
            logger.error(e);
        }
        return new SimpleRequest("", "", "");
    }

    public SimpleRequest parseHttpRequest(String request) {
        String[] parts = request.split(" ");
        if (parts.length == 3) {
            String httpMethod = parts[0];
            String uri = parts[1];
            String httpProtocolVersion = parts[2];

            logger.info("httpMethod: " + httpMethod);
            logger.info("uri: " + uri);
            logger.info("httpProtocolVersion: " + httpProtocolVersion);

            return new SimpleRequest(httpMethod, uri, httpProtocolVersion);
        } else {
            logger.error("Invalid request line");
        }
        return new SimpleRequest("", "", "");
    }

    public void close() throws IOException {
        in.close();
    }
}
