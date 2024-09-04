package com.example.lealtad.repository;

import com.example.lealtad.model.PuntosTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntosTransaccionRepository extends JpaRepository<PuntosTransaccion, Long> {
}