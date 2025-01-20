package com.ruoyi.web;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class DeepseekTest {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String API_KEY = "sk-eb93f9f22ca440139ff2289c786ebdc3"; // Replace with actual API key

    public static void main(String[] args) {
        /*if (args.length == 0) {
            System.out.println("Please provide a message as an argument");
            return;
        }
*/
        String message = "9.8 and 9.11 which one is the bigger number?";
        OkHttpClient client = new OkHttpClient();

        try {
            // Create request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek-chat");

            JSONObject messageObj = new JSONObject();
            messageObj.put("role", "user");
            messageObj.put("content", message);

            requestBody.put("messages", new JSONObject[]{messageObj});

            // Create HTTP request
            RequestBody body = RequestBody.create(requestBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();

            // Execute request and print response
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Error: " + response.code() + " " + response.message());
                    return;
                }
                System.out.println("Response:");
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            System.err.println("Error making API request: " + e.getMessage());
        }
    }
}
