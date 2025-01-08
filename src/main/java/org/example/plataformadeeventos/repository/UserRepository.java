package org.example.plataformadeeventos.repository;

import org.example.plataformadeeventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}