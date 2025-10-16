package com.example.QRcode;

import net.glxn.qrgen.QRCode;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class QRcodeService {

    private final QRcodeRepository QRcodeRepository;

    QRcodeService(QRcodeRepository QRcodeRepository) {
        this.QRcodeRepository = QRcodeRepository;
    }

    @Transactional
    public void createQRcode(String link) {
        if ("fail".equals(link)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        byte[] imageData = QRCode.from(link)
                .withSize(300, 300)
                .stream()
                .toByteArray();

        var QR = new QRcode(link, Instant.now(),imageData);
        QRcodeRepository.saveAndFlush(QR);
    }

    @Transactional(readOnly = true)
    public List<QRcode> list(Pageable pageable) {
        return QRcodeRepository.findAllBy(pageable).toList();
    }

}
