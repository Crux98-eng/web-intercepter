package services;



import models.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ResponseParser {

    public HttpResponse parse(BufferedReader reader) throws IOException {

        HttpResponse response = new HttpResponse();

        String statusLine = reader.readLine();
        response.setStatusLine(statusLine);

        String line;

        while (!(line = reader.readLine()).isEmpty()) {

            int index = line.indexOf(":");

            if (index > 0) {

                String key = line.substring(0, index);
                String value = line.substring(index + 1).trim();

                response.addHeader(key, value);
            }
        }

        return response;
    }
}