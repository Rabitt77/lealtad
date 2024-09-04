package com.example.lealtad.model;

import jakarta.persistence.*;


import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String contrasena;
    private int puntos;

}
