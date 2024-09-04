package com.example.lealtad.model;


import jakarta.persistence.*;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int puntosRequeridos;
}
