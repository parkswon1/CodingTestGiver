package com.example.algoshuffer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HelloMain {
    public static void main(String[] args) throws Exception {
        //String uri = getProblemByArray(List.of(1000));
        //String uri = getUserSolvedProblemByName("parkswon1");
        String uri = getUserByName("parkswon1");

        // HTTP 요청 생성
        solvedacAPIRequest(uri);
    }

    //uri요청이 있을시 요청을 받아주고 데이터 받아옴
    private static void solvedacAPIRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-solvedac-language", "ko")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // JSON 데이터를 객체로 변환
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object jsonObject = gson.fromJson(response.body(), Object.class);

        // 객체를 다시 JSON 문자열로 변환하여 출력
        String prettyJson = gson.toJson(jsonObject);
        System.out.println(prettyJson);
    }

    //사용자 이름으로 사용자 정보 가져오기
    private static String getUserByName(String User) {
        String uri = "https://solved.ac/api/v3/search/user?query=" + User;
        return uri;
    }


    //사용자 이름으로 사용자가 푼 문제 가져오기
    private static String getUserSolvedProblemByName(String User) {
        String uri = "https://solved.ac/api/v3/search/problem?query=s%40" + User;
        return uri;
    }


    //백준 문제 숫자열로 가져오기
    private static String getProblemByArray(List<Integer> problemIds) {
        String problemIdsParam = problemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String uri = "https://solved.ac/api/v3/problem/lookup?problemIds=" + problemIdsParam;
        return uri;
    }
}