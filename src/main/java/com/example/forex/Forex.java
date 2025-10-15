package com.example.forex;

import jakarta.persistence.*;

@Entity
@Table(name = "Forex")

public class Forex {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "task_id")
    private Long id;
}
