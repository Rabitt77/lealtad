package com.example.lealtad.controller;

import com.example.lealtad.exception.UsuarioNoEncontradoException;
import com.example.lealtad.model.PuntosTransaccion;
import com.example.lealtad.model.Recompensa;
import com.example.lealtad.model.Usuario;
import com.example.lealtad.model.UsuarioDTO;
import com.example.lealtad.repository.PuntosTransaccionRepository;
import com.example.lealtad.repository.RecompensaRepository;
import com.example.lealtad.repository.UsuarioRepository;
import com.example.lealtad.request.PuntosRequest;
import com.example.lealtad.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    private final UsuarioRepository usuarioRepository;
    private final PuntosTransaccionRepository puntosTransaccionRepository;
    private final RecompensaRepository recompensaRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, PuntosTransaccionRepository puntosTransaccionRepository, RecompensaRepository recompensaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.puntosTransaccionRepository = puntosTransaccionRepository;
        this.recompensaRepository = recompensaRepository;
    }

    @Operation(
            summary = "Programa de lealtad",
            description = "En este sistema podremos registrar usuarios, consultar puntos, agregar recompensas y consultarlas.",
            tags = {"Usuarios"}
    )
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario user) {
        return ResponseEntity.ok(userService.registrarUser(user.getNombreUsuario(), user.getContrasena()));
    }

    @Operation(
            summary = "Registrar puntos para un usuario",
            description = "Permite registrar puntos para un usuario especificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Puntos registrados correctamente", content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Puntos registrados correctamente")
                    )),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "Usuario no encontrado")
                    ))
            }
    )
    @PostMapping("/registrarPuntos/{userId}")
    public ResponseEntity<String> registrarPuntos(
            @PathVariable Long userId,
            @RequestBody PuntosRequest puntosRequest) {

        // Verificar si el usuario existe
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        // Registrar puntos en la base de datos
        PuntosTransaccion registroPuntos = new PuntosTransaccion();
        registroPuntos.setUserId(userId);
        registroPuntos.setPuntos(puntosRequest.getPuntos());
        registroPuntos.setDescripcion(puntosRequest.getDescripcion());
        puntosTransaccionRepository.save(registroPuntos);

        // Acumular puntos al usuario
        usuario.setPuntos(usuario.getPuntos() + puntosRequest.getPuntos());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Puntos registrados correctamente");
    }

    @Operation(
            summary = "Obtener información de usuario",
            description = "Obtiene la información de un usuario, incluyendo sus puntos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Información del usuario obtenida con éxito"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/obtenerPuntos/{userId}")
    public ResponseEntity<UsuarioDTO> obtenerInfo(@PathVariable Long userId) {
        UsuarioDTO usuarioDTO = userService.getUsuarioInfo(userId);
        return ResponseEntity.ok(usuarioDTO);
    }

    @Operation(
            summary = "Crear una nueva recompensa",
            description = "Permite crear una nueva recompensa en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recompensa creada correctamente")
            }
    )
    @PostMapping("/recompensa/agregar")
    public ResponseEntity<Recompensa> crearRecompensa(@RequestBody Recompensa recompensa) {
        Recompensa nuevaRecompensa = recompensaRepository.save(recompensa);
        return ResponseEntity.ok(nuevaRecompensa);
    }

    @Operation(
            summary = "Obtener todas las recompensas",
            description = "Recupera una lista de todas las recompensas disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de recompensas obtenida con éxito"),
                    @ApiResponse(responseCode = "204", description = "No hay recompensas disponibles en este momento", content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "No hay recompensas disponibles en este momento.")
                    ))
            }
    )
    @GetMapping("/recompensas")
    public ResponseEntity<Object> obtenerRecompensas() {
        List<Recompensa> recompensas = recompensaRepository.findAll();

        if (recompensas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay recompensas disponibles en este momento.");
        }

        return ResponseEntity.ok(recompensas);
    }
}
