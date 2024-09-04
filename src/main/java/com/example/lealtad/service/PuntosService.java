package com.example.lealtad.service;


import com.example.lealtad.model.PuntosTransaccion;
import com.example.lealtad.repository.PuntosTransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuntosService {
    @Autowired
    private PuntosTransaccionRepository puntosRepository;

    public PuntosTransaccion agregarPuntos(PuntosTransaccion puntos) {
        return puntosRepository.save(puntos);
    }
}