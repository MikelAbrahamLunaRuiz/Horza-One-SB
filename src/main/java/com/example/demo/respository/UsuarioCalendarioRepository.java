package com.example.demo.respository;

import com.example.demo.model.UsuarioCalendario;
import com.example.demo.model.UsuarioCalendarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioCalendarioRepository extends JpaRepository<UsuarioCalendario, UsuarioCalendarioId> {
    List<UsuarioCalendario> findById_Matricula(Integer matricula);
}
