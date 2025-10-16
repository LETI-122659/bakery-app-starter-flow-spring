package com.example.email;

import jakarta.mail.internet.MimeMessage;

public interface EmailRepository {
    MimeMessage createMimeMessage();
    void send(MimeMessage message);
}
