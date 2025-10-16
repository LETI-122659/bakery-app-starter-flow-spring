package com.example.forex.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.theme.lumo.LumoUtility;
import org.json.JSONObject;


@Route("Forex")
@PageTitle("Forex")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Forex")
public class ForexView extends Main {

    private final ComboBox<String> fromCurrency;
    private final ComboBox<String> toCurrency;
    private final NumberField amountField;
    private final Button convertBtn;
    private final TextField resultField;
    private final double spread = 0.02; // 2% spread

    public ForexView() {
        fromCurrency = new ComboBox<>("From");
        toCurrency = new ComboBox<>("To");
        amountField = new NumberField("Amount");
        resultField = new TextField("Converted Amount");
        convertBtn = new Button("Convert", e -> convertCurrency());

        // --- Setup ---
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "BRL");
        fromCurrency.setItems(currencies);
        toCurrency.setItems(currencies);

        fromCurrency.setPlaceholder("Select source currency");
        toCurrency.setPlaceholder("Select target currency");

        amountField.setPlaceholder("Enter amount");
        amountField.setMin(0);

        resultField.setReadOnly(true);

        convertBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // --- Layout ---
        VerticalLayout layout = new VerticalLayout(fromCurrency, toCurrency, amountField, convertBtn, resultField);
        layout.setSpacing(true);
        layout.setPadding(true);
        layout.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(layout);
        setSizeFull();
    }

    private void convertCurrency() {
        try {
            String from = fromCurrency.getValue();
            String to = toCurrency.getValue();
            Double amount = amountField.getValue();

            if (from == null || to == null || amount == null) {
                Notification.show("Please select both currencies and enter an amount.", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            double rate = getRate(from, to);
            double rateWithSpread = rate * (1 - spread);
            double converted = amount * rateWithSpread;

            resultField.setValue(String.format("%.4f %s", converted, to));

            Notification.show("Conversion successful!", 2000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            Notification.show("Error converting currency: " + e.getMessage(), 4000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private double getRate(String from, String to) throws Exception {
        String urlStr = "https://api.exchangerate.host/convert?from=" + from + "&to=" + to;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONObject obj = new JSONObject(content.toString());
            return obj.getDouble("result");
        } finally {
            conn.disconnect();
        }
    }
}
