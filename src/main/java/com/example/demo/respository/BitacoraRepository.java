package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Bitacora;
import com.example.demo.model.Usuario;
import java.util.Optional;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Integer> {
    Optional<Bitacora> findByUsuario(Usuario usuario);
}
