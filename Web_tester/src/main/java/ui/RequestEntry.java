package ui;



public class RequestEntry {

    private final String method;
    private final String host;
    private final String path;
    private final String status;

    private final String rawRequest;
    private final String rawResponse;

//    the contructor which initialises the table events
    public RequestEntry(String method, String host, String path, String status, String rawRequest, String rawResponse) {
        this.method = method;
        this.host = host;
        this.path = path;
        this.status = status;
        this.rawRequest = rawRequest;
        this.rawResponse = rawResponse;
    }
// the getter for the method of the request such as GET, POST and so on
    public String getMethod() {
        return method;
    }
//the getter for the host
    public String getHost() {
        return host;
    }
//the getter for the path, think of it like an API path you know guys what am talking about
    public String getPath() {
        return path;
    }
//API response status like 200, 301
    public String getStatus() {
        return status;
    }

    public String getRawRequest() {
        return rawRequest;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}