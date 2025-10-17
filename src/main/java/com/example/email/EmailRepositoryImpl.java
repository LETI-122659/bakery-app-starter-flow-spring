package com.example.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import jakarta.mail.internet.MimeMessage;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final JavaMailSender mailSender;

    public EmailRepositoryImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }

    @Override
    public void send(MimeMessage message) {
        mailSender.send(message);
    }
}
