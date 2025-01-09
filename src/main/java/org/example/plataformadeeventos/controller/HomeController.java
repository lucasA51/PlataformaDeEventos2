package org.example.plataformadeeventos.controller;

import org.example.plataformadeeventos.model.Evento;
import org.example.plataformadeeventos.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/")
    public String home(Evento evento) {
        return "home";
    }

    @GetMapping("/cadastroevento")
    public String cadastroEvento(Evento evento) {
        return "cadastroevento";
    }

    @GetMapping("/meuseventos")
    public String meuseventos(Evento evento) {
        return "meuseventos";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "register";
    }

    @GetMapping("/evento/{id}")
    public String eventoPage() {
        return "evento";
    }
}
