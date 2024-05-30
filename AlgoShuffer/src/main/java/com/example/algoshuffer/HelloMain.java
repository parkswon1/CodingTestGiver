package com.example.algoshuffer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HelloMain {
    public static void main(String[] args) throws Exception {
        String username = "parkswon1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://solved.ac/api/v3/search/problem?query=s%40parkswon1"))
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // JSON 데이터를 객체로 변환
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object jsonObject = gson.fromJson(response.body(), Object.class);

        // 객체를 다시 JSON 문자열로 변환하여 출력
        String prettyJson = gson.toJson(jsonObject);
        System.out.println(prettyJson);
    }
}