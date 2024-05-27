package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GitHubContentReader {

    @Autowired
    private static final String GITHUB_API_URL = "https://api.github.com/repos/parkswon1/Coding_Test/contents/%EB%B0%B1%EC%A4%80";

    @Scheduled(fixedDelay = 600000)
    public static void main(String[] args) {
        try {
            String jsonData = getContentFromRepo();
            JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                String fileName = element.getAsJsonObject().get("name").getAsString();

                if (fileName.endsWith(".py")) {
                    String[] parts = fileName.split("_");
                    Integer problemNumber = Integer.parseInt(parts[0]);
                    System.out.println(getProblemInfo.getProblemInfo(problemNumber).body());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getContentFromRepo() throws Exception {
        URL url = new URL(GITHUB_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }
}