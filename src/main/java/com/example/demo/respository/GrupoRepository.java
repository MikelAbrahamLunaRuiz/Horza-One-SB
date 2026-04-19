package com.example.demo.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    List<Grupo> findByMatriculaLider(Integer matriculaLider);

    Optional<Grupo> findByNombreGrupo(String nombreGrupo);
}
