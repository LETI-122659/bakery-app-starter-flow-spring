// Java
package com.example.PDF;

import jakarta.persistence.*;

@Entity
public class PDF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] content;

    // Getters e setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
}
