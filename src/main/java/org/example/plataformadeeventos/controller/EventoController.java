package org.example.plataformadeeventos.controller;

import org.example.plataformadeeventos.model.Evento;
import org.example.plataformadeeventos.repository.EventoRepository;
import org.example.plataformadeeventos.repository.UserRepository;
import org.example.plataformadeeventos.service.CustomUserDetailsService;
import org.example.plataformadeeventos.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody Evento evento) {
        Evento eventoCriado = eventoService.inserir(evento);
        return ResponseEntity.ok(eventoCriado); // Retorna o evento criado
    }

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> alterar(@PathVariable Long id, @RequestBody Evento evento) {
        Evento eventoAtualizado = eventoService.alterar(id, evento);
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.excluir(id);
        return ResponseEntity.noContent().build(); // Retorna status HTTP 204 (sem conteúdo)
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        Evento evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @PostMapping("/{eventoId}/inscrever")
    public ResponseEntity<String> inscreverUsuario(@PathVariable Long eventoId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        String email = userDetails.getUsername(); // UserDetails fornece o método getUsername()

        try {
            eventoService.inscreverUsuarioNoEvento(eventoId, email);
            return ResponseEntity.ok("Usuário inscrito com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
