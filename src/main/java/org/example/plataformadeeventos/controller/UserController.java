package org.example.plataformadeeventos.controller;

import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = userService.inserirUsuario(usuario);
        return ResponseEntity.ok(usuarioCriado);
    }

    @GetMapping("/register")
    public String cadastroUsuario() {
        return "register"; // página de cadastro de usuários
    }
}
