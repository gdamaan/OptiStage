package fr.ensitech.optistage.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CaptchaValidator {

    private static final String SECRET_KEY = "0x4AAAAAADhc_dzUGFGFoPxXDYVb1fk3LKY";
    private static final String VERIFY_URL = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    public static boolean isValid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(VERIFY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // On prépare le colis pour Cloudflare
            String postData = "secret=" + URLEncoder.encode(SECRET_KEY, "UTF-8") +
                    "&response=" + URLEncoder.encode(token, "UTF-8");

            // On envoie la requête
            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            // On lit la réponse
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Cloudflare renvoie un JSON contenant "success": true ou "success": false
                // Une vérification sous forme de chaîne de caractères est rustique mais très efficace ici.
                return response.toString().contains("\"success\":true") || response.toString().contains("\"success\": true");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la validation du CAPTCHA : " + e.getMessage());
        }

        return false;
    }
}