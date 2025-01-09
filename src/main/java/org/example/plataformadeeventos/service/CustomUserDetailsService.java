package org.example.plataformadeeventos.service;

import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository usuarioRepository;

    public CustomUserDetailsService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        String role = usuario.getAdministrador() ? "ADMIN" : "USER";

        return User.withUsername(usuario.getNome())
                .password(usuario.getSenha())
                .authorities(role)
                .build();
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        String role = usuario.getAdministrador() ? "ADMIN" : "USER";

        // Use o email como username para manter a consistência
        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(role)
                .build();
    }

}
