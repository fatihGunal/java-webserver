package org.tree.webserver.dto;

public record SimpleRequest(String httpMethod, String uri, String httpProtocolVersion) {
}
