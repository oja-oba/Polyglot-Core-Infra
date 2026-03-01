package com.polyglot; // This must match the folder path

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/health", exchange -> {
            String response = String.format(
                "{\"status\":\"healthy\", \"runtime\":\"Java %s\"}",
                System.getProperty("java.version")
            );
            sendResponse(exchange, response);
        });

        server.createContext("/security", exchange -> {
            String response = String.format(
                "{\"user_id\": \"%s\", \"is_root\": %b}",
                System.getProperty("user.name"),
                System.getProperty("user.name").equals("root")
            );
            sendResponse(exchange, response);
        });

        System.out.println("Java app listening at http://0.0.0.0:" + port);
        server.start();
    }

    private static void sendResponse(com.sun.net.httpserver.HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}