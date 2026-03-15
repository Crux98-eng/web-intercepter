package services;


import models.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {

    public HttpRequest parse(BufferedReader reader) throws IOException {

        HttpRequest request = new HttpRequest();

        String requestLine = reader.readLine();

        if (requestLine == null || requestLine.isEmpty()) {
            System.out.println("the request line is empty");
            return null;
        }

        String[] parts = requestLine.split(" ");
        request.setMethod(parts[0]);
        request.setPath(parts[1]);
        request.setHttpVersion(parts[2]);

        String line;

        while (!(line = reader.readLine()).isEmpty()) {

            int index = line.indexOf(":");

            if (index > 0) {
                String key = line.substring(0, index);
                String value = line.substring(index + 1).trim();

                request.addHeader(key, value);
            }
        }

        return request;
    }
}