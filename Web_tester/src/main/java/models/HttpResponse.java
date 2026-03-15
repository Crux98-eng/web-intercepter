package models;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private String statusLine;
    private final Map<String, String> headers = new LinkedHashMap<>();
    private String body = "";

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}