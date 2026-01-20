package service;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.json.JSONObject;

public class GeminiService {

    private String apiKey;

    public GeminiService() {
        try {
            Properties prop = new Properties();
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            prop.load(is);
            apiKey = prop.getProperty("gemini.api.key").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escapeJson(String s) {
        return s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }

    // 🇰🇷 → 🇯🇵 번역
    public String translateToJapanese(String text) throws Exception {

        String safeText = escapeJson(text);

        String jsonBody = """
        {
          "contents": [
            {
              "role": "user",
              "parts": [
                {
                  "text": "다음 한국어 문장을 자연스러운 일본어로 번역해줘:\\n%s"
                }
              ]
            }
          ]
        }
        """.formatted(safeText);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
        	    .uri(URI.create(
        	        "https://generativeai.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent"
        	        + "?key=" + apiKey
        	    ))
        	    .header("Content-Type", "application/json")
        	    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        	    .build();




        HttpResponse<String> response =
        	    client.send(request, HttpResponse.BodyHandlers.ofString());

        	System.out.println("STATUS = " + response.statusCode());
        	System.out.println("RAW = " + response.body());

        	String body = response.body();

        	if (body == null || body.isBlank()) {
        	    return "번역 실패 (빈 응답)";
        	}

        	body = body.trim();

        	if (!body.startsWith("{")) {
        	    return "번역 실패 (JSON 아님): " + body;
        	}

        	JSONObject json = new JSONObject(body);

        	if (json.has("error")) {
        	    return "Gemini ERROR: " +
        	        json.getJSONObject("error").getString("message");
        	}

        	return json
        	    .getJSONArray("candidates")
        	    .getJSONObject(0)
        	    .getJSONObject("content")
        	    .getJSONArray("parts")
        	    .getJSONObject(0)
        	    .getString("text");
    }

}

