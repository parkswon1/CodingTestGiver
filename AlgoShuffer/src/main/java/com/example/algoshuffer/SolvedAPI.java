package com.example.algoshuffer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SolvedAPI {
    //uri요청이 있을시 요청을 받아주고 데이터 받아옴
    public static JsonObject solvedacAPIRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-solvedac-language", "ko")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // JSON 데이터
        JsonParser parser = new JsonParser();

        return parser.parse(response.body()).getAsJsonObject();
    }

    //사용자 이름으로 사용자 정보 가져오기
    public static String getUserByName(String User) {
        String uri = "https://solved.ac/api/v3/search/user?query=" + User;
        return uri;
    }


    //사용자 이름으로 사용자가 푼 문제 가져오기
    public static String getUserSolvedProblemByName(String User) {
        String uri = "https://solved.ac/api/v3/search/problem?query=s%40" + User;
        return uri;
    }


    //백준 문제 숫자열로 가져오기
    public static String getProblemByArray(List<Integer> problemIds) {
        String problemIdsParam = problemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String uri = "https://solved.ac/api/v3/problem/lookup?problemIds=" + problemIdsParam;
        return uri;
    }

    //문제 tag들 가져오기
    public static String getTag(int page){
        String uri = "https://solved.ac/api/v3/search/tag?query&page=" + page;
        return uri;
    }
}