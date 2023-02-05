package com.simplestreamingsystem.http_request;


import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class HttpRequest {
    public static int requestOpenSocket(int port, String type, String password) throws IOException {
        URL url = new URL("http://localhost:8080/api/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = String.format("{\"port\": \"%d\", \"type\": \"%s\",\"password\": \"%s\"}", port, type, password);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return con.getResponseCode();
    }
}
