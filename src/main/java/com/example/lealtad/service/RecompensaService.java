package com.example.lealtad.service;

import com.example.lealtad.model.Recompensa;
import com.example.lealtad.repository.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecompensaService {
    @Autowired
    private RecompensaRepository recompensaRepository;

    public Recompensa crearRecompensa(Recompensa recompensa) {
        return recompensaRepository.save(recompensa);
    }
}