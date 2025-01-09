package org.example.plataformadeeventos.controller;

import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
}
