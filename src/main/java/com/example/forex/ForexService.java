package com.example.forex;

import com.github.sarxos.exchangerate.ExchangeRatesApi;
import java.math.BigDecimal;
import java.io.IOException;



public class ForexService {
    private final double spread;

    public ForexService(double spread) {
        this.spread = spread;
    }

    public double convert(String from, String to, double amount) throws IOException {
        BigDecimal rate = ExchangeRatesApi.getRate(from, to);
        double rateWithSpread = rate.doubleValue() * (1 - spread);
        return amount * rateWithSpread;
    }
}
