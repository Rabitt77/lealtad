package com.example.lealtad.service;


import com.example.lealtad.model.PuntosTransaccion;
import com.example.lealtad.model.Recompensa;
import com.example.lealtad.model.Usuario;
import com.example.lealtad.model.UsuarioDTO;
import com.example.lealtad.repository.PuntosTransaccionRepository;
import com.example.lealtad.repository.RecompensaRepository;
import com.example.lealtad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PuntosTransaccionRepository puntosTransaccionRepository;
    @Autowired
    private RecompensaRepository recompensaRepository;

    public Usuario registrarUser(String username, String password) {
        Usuario user = new Usuario();
        user.setNombreUsuario(username);
        user.setContrasena(password);
        user.setPuntos(0);
        return usuarioRepository.save(user);
    }

    public UsuarioDTO getUsuarioInfo(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UsuarioDTO(usuario.getNombreUsuario(), usuario.getPuntos());
    }

    public void AgregarPuntos(Long userId, int puntos, String descripccion) {
        Usuario user = usuarioRepository.findById(userId).orElseThrow();
        user.setPuntos(user.getPuntos() + puntos);
        usuarioRepository.save(user);

        PuntosTransaccion transaccion = new PuntosTransaccion();
        transaccion.setUserId(userId);
        transaccion.setPuntos(puntos);
        transaccion.setDescripcion(descripccion);
        puntosTransaccionRepository.save(transaccion);
    }

    public void redimirPuntos(Long userId, Long recompensaId) {
        Usuario user = usuarioRepository.findById(userId).orElseThrow();
        Recompensa recompensa = recompensaRepository.findById(recompensaId).orElseThrow();

        if (user.getPuntos() >= recompensa.getPuntosRequeridos()) {
            user.setPuntos(user.getPuntos() - recompensa.getPuntosRequeridos());
            usuarioRepository.save(user);
        } else {
            throw new IllegalArgumentException("No tienes suficientes puntos");
        }
    }
}