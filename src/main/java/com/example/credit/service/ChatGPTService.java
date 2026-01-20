package com.example.credit.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;

@Service
public class ChatGPTService {

    private final String LOCAL_OLLAMA_URL = "http://localhost:11434/api/chat";
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .build();

    public String askLocalModel(String userMessage) throws IOException {
        JSONObject root = new JSONObject();
        root.put("model", "mistral");
        root.put("stream", false);

        JSONArray messages = new JSONArray();
        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.put(userMsg);

        root.put("messages", messages);


        RequestBody body = RequestBody.create(root.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(LOCAL_OLLAMA_URL)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.code() == 429) {
                    return "{\"error\":\"Ollama rate limit exceeded. Please try again later.\"}";
                }
                return "{\"error\":\"Ollama API error code: " + response.code() + "\"}";
            }

            String responseBody = response.body().string();
            System.out.println("Response: " + responseBody); // for debugging

            JSONObject json = new JSONObject(responseBody);
            return json.getJSONObject("message").getString("content");
        }
    }
}
