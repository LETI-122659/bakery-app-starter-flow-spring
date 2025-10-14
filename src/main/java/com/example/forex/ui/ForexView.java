package com.example.forex.ui;

import com.example.examplefeature.ForexService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



@Route("Forex")
@PageTitle("Forex")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Forex")
public class ForexView extends Main {

    private final ForexService converterService;

    public ForexView(ForexService converterService) {
        this.converterService = converterService;

        ComboBox<String> from = new ComboBox<>("De");
        ComboBox<String> to = new ComboBox<>("Para");
        from.setItems("USD", "EUR", "BRL", "GBP", "JPY", "CHF");
        to.setItems("USD", "EUR", "BRL", "GBP", "JPY", "CHF");

        NumberField amount = new NumberField("Valor");
        Button convert = new Button("Converter");
        convert.addClickListener(e -> {
            double result = converterService.convert(
                    from.getValue(), to.getValue(), amount.getValue()
            );
            Notification.show("Resultado: " + result);
        });

        add(from, to, amount, convert);
    }
}
