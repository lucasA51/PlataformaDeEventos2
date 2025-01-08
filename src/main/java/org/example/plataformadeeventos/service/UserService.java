package org.example.plataformadeeventos.service;

import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Usuario inserirUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }
}
