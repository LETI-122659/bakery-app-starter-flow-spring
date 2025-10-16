package com.example.forex;

//import com.github.sarxos.exchangerate.ExchangeRatesApi;
import java.math.BigDecimal;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class ForexService {
    private final double spread;

    public ForexService(double spread) {
        this.spread = spread;
    }

    public double convert(String from, String to, double amount) throws Exception {
        double rate = getRate(from, to);
        double rateWithSpread = rate * (1 - spread);
        return amount * rateWithSpread;
    }

    private double getRate(String from, String to) throws Exception {
        String urlStr = "https://api.exchangerate.host/convert?from=" + from + "&to=" + to;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        JSONObject obj = new JSONObject(content.toString());
        return obj.getDouble("result");
    }
}
