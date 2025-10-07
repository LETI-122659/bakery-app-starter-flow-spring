package com.example.email.ui;

import com.example.email.EmailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.mail.MessagingException;


@Route("Email")
@PageTitle("Email")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Email")
public class EmailView extends Main {


    public EmailView(EmailService emailService) {
        TextField toField = new TextField("Para");
        TextField subjectField = new TextField("Assunto");
        TextArea bodyField = new TextArea("Mensagem");

        Button sendBtn = new Button("Enviar", event -> {
            try {
                emailService.sendEmail(toField.getValue(), subjectField.getValue(), bodyField.getValue());
                Notification.show("E-mail enviado!");
            } catch (MessagingException e) {
                Notification.show("Erro ao enviar: " + e.getMessage());
            }
        });

        add(toField, subjectField, bodyField, sendBtn);
    }
}
