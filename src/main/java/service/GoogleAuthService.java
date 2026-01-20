package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletContext;

import model.GoogleUser;

public class GoogleAuthService {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public GoogleAuthService(ServletContext context) {
        clientId = context.getInitParameter("googleClientId");
        clientSecret = context.getInitParameter("googleClientSecret");
        redirectUri = context.getContextPath().equals("")
                ? "http://localhost:8088/google/callback"
                : "http://localhost:8088" + context.getContextPath() + "/google/callback";
    }

    public GoogleUser getGoogleUser(String code) {

        String accessToken = requestAccessToken(code);
        return requestUserInfo(accessToken);
    }

    private String requestAccessToken(String code) {

        String accessToken = null;

        try {
        	URI uri = URI.create("https://oauth2.googleapis.com/token");
        	URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String params =
                    "code=" + code +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&redirect_uri=" + redirectUri +
                    "&grant_type=authorization_code";

            OutputStreamWriter writer =
                    new OutputStreamWriter(conn.getOutputStream());
            writer.write(params);
            writer.flush();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String response = br.readLine();

            // ⭐ JSON 파싱 (임시 방식)
            accessToken =
                    response.split("\"access_token\":\"")[1].split("\"")[0];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken; // ✅ 여기서 반환
    }


    private GoogleUser requestUserInfo(String accessToken) {

        GoogleUser user = new GoogleUser();

        try {
        	URI uri = new URI(
        		    "https",
        		    "www.googleapis.com",
        		    "/oauth2/v2/userinfo",
        		    "access_token=" + accessToken,
        		    null
        		);
        	URL url = uri.toURL();



            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String response = br.readLine();

            // 임시 파싱
            String email = response.split("\"email\": \"")[1].split("\"")[0];
            String name  = response.split("\"name\": \"")[1].split("\"")[0];

            user.setEmail(email);
            user.setName(name);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}

