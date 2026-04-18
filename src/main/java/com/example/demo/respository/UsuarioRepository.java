package com.example.demo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);

    // Query explícita que usa el campo Java 'contrasena' que está mapeado a 'contrasenia' en la BD
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasena = :contrasena")
    Optional<Usuario> findByCorreoAndContrasena(
            @Param("correo") String correo,
            @Param("contrasena") String contrasena
    );
    
    // Para control de emergencias
    @Query("SELECT u FROM Usuario u WHERE u.estadoPresencia = :estadoPresencia")
    java.util.List<Usuario> findByEstadoPresencia(@Param("estadoPresencia") String estadoPresencia);
}