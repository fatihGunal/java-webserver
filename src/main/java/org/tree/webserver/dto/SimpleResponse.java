package org.tree.webserver.dto;

public record SimpleResponse(String statusLine, String contentType, String body) {
}
