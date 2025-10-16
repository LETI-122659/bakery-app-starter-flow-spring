package com.example.QRcode;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import java.time.Instant;

@Entity
@Table(name = "QRcode")
public class QRcode {

    public static final int DESCRIPTION_MAX_LENGTH = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "QRcode_id")
    private Long id;

    @Column(name = "link", nullable = false, length = DESCRIPTION_MAX_LENGTH)
    private String link = "";

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;

    protected QRcode() {
    }

    public QRcode(String link, Instant creationDate, byte[] imageData) {
        setLink(link);
        this.creationDate = creationDate;
        this.imageData = imageData;
    }

    public @Nullable Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if (link.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Description length exceeds " + DESCRIPTION_MAX_LENGTH);
        }
        this.link = link;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        QRcode other = (QRcode) obj;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
