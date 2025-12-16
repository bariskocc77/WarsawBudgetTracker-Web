package com.warsaw.budget_tracker;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyService {

    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/try/?format=json";

    public double getTryRate() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() != 200) return 0.0;

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // JSON parse (Manuel)
            String json = content.toString();
            String searchKey = "\"mid\":";
            int startIndex = json.indexOf(searchKey);

            if (startIndex != -1) {
                int start = startIndex + searchKey.length();
                int end = json.indexOf("}", start);
                if (json.indexOf(",", start) != -1 && json.indexOf(",", start) < end) {
                    end = json.indexOf(",", start);
                }
                return Double.parseDouble(json.substring(start, end));
            }
        } catch (Exception e) {
            System.out.println("⚠️ API Hatası: " + e.getMessage());
        }
        return 0.0;
    }
}