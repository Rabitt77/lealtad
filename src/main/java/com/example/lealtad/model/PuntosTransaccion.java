package com.example.lealtad.model;
import jakarta.persistence.*;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class PuntosTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int puntos;
    private String descripcion;


}