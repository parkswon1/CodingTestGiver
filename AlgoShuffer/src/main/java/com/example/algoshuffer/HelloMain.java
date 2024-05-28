package com.example.algoshuffer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HelloMain {
    public static void main(String[] args) throws Exception {
        String username = "parkswon1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://solved.ac/api/v3/search/problem?query=s%40parkswon1"))
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

}