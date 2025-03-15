package org.tree.webserver.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tree.webserver.dto.HttpMethod;
import org.tree.webserver.dto.SimpleRequest;
import org.tree.webserver.dto.SimpleResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    private Socket socket;
    PrintWriter out;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        try {
            // TODO use factory method to solve this ugly dependency injection violation
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void handleRequest(SimpleRequest simpleRequest) {
        logger.info("New connection established");
        if (HttpMethod.GET.name().equals(simpleRequest.httpMethod())) {
            htmlResponse();
        } else {
            notSupportedResponse();
        }
    }

    private void htmlResponse() {
//        SimpleResponse simpleResponse = createSimpleResponse();
        SimpleResponse simpleResponse = createSimpleHtmlFileResponse();
        out.println(simpleResponse.statusLine());
        out.println("Content-Type: " + simpleResponse.contentType());
        out.println();
        out.println(simpleResponse.body());
        out.flush();
    }

    private void notSupportedResponse() {
        out.println("HTTP/1.1 501 Not Implemented");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("This method is not implemented yet! Try GET :)"); // Body
        out.flush();
    }

    private SimpleResponse createSimpleHtmlFileResponse() {
        StringBuilder contentBuilder = new StringBuilder();
        try (
                BufferedReader in = new BufferedReader(new FileReader("./web/index.html"))
        ) {
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        String content = contentBuilder.toString();
        return new SimpleResponse("HTTP/1.1 200 OK", "text/html", content);
    }

    @Deprecated
    private SimpleResponse createSimpleResponse() {
        String htmlBody = """
                <h1>This is h1 tag</h1>
                <h2>This is h2 tag</h2>
                <h3>This is h3 tag</h3>
                """;
        return new SimpleResponse("HTTP/1.1 200 OK", "text/html", htmlBody);
    }

    @Deprecated
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
