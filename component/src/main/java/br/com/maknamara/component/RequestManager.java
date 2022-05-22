package br.com.maknamara.component;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RequestManager {


    public RequestManager() {
    }

    public void get(URL url, OnResponseCallback callback) throws Exception {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        StringBuilder response = new StringBuilder(1);
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        }
        callback.onResponse(response.toString());
    }

    public void get(String url, OnResponseCallback callback) throws Exception {
        URL _url = new URL(url);
        get(_url, callback);
    }

    @FunctionalInterface
    public interface OnResponseCallback {
        void onResponse(String response);
    }
}
