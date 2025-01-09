package org.example.plataformadeeventos.controller;

import org.example.plataformadeeventos.model.Evento;
import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.repository.UserRepository;
import org.example.plataformadeeventos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        // Criptografar a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioCriado = userService.inserirUsuario(usuario);
        return ResponseEntity.ok(usuarioCriado);
    }

    @GetMapping("/register")
    public String cadastroUsuario() {
        // Retorno ajustado para integração com mecanismos de template como Thymeleaf
        return "register"; // Página de cadastro de usuários (Thymeleaf ou similar)
    }

    @GetMapping("/api/meus-eventos")
    public List<Evento> listarEventosDoUsuario(Authentication authentication) {
        String email = authentication.getName(); // Obtém o email do usuário autenticado
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getEventos();
    }

}
