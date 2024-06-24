package com.example.algoshuffer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class SolvedAPI {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    //uri요청이 있을시 요청을 받아주고 데이터 받아옴
    public static HttpResponse<String> solvedacAPIRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-solvedac-language", "ko")
                .header("Accept", "application/json")
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //사용자 이름으로 사용자 정보 가져오기
    public static String getUserByName(String User) {
        return "https://solved.ac/api/v3/search/user?query=" + User;
    }


    //사용자 이름으로 사용자가 푼 문제 가져오기
    public static String getUserSolvedProblemByName(String User, int page) {
        return "https://solved.ac/api/v3/search/problem?query=s%40" + User + "&direction=asc&page=" + page +"&sort=id";
    }


    //백준 문제 숫자열로 가져오기
    public static String getProblemByArray(List<Integer> problemIds) {
        String problemIdsParam = problemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return "https://solved.ac/api/v3/problem/lookup?problemIds=" + problemIdsParam;
    }

    //문제 tag들 가져오기
    public static String getTag(int page){
        return "https://solved.ac/api/v3/search/tag?query&page=" + page;
    }
}