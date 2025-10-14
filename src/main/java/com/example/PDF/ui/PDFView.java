// Java
package com.example.PDF.ui;

import com.example.PDF.PDFService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;



@PageTitle("com/example/PDF")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "com/example/PDF")
@Route("pdf")
public class PDFView extends Main {

    @Autowired
    private PDFService pdfService;

    public PDFView() {
        TextField nameField = new TextField("Nome do PDF");
        TextField textField = new TextField("Texto para o PDF");
        Button generateButton = new Button("Gerar e Salvar PDF", event -> {
            try {
                pdfService.createAndSavePDF(nameField.getValue(), textField.getValue());
                Notification.show("PDF gerado e salvo com sucesso!");
            } catch (Exception e) {
                Notification.show("Erro ao gerar PDF: " + e.getMessage());
            }
        });

        add(nameField, textField, generateButton);
    }
}