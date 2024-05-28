package com.example.algoshuffer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HelloMain {
    public static void main(String[] args) throws Exception {
        String username = "parkswon1"; // 대상 사용자 이름

        // API 요청을 보내는 HttpRequest 객체 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://solved.ac/api/v3/search/problem?query=s%40parkswon1"))
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // HttpClient를 사용하여 요청 보내고 응답 받기
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 출력
        System.out.println(response.body());
    }

}