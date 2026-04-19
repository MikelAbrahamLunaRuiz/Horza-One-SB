package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.GrupoIntegrante;
import com.example.demo.model.GrupoIntegranteId;

@Repository
public interface GrupoIntegranteRepository extends JpaRepository<GrupoIntegrante, GrupoIntegranteId> {
    List<GrupoIntegrante> findByMatricula(Integer matricula);

    List<GrupoIntegrante> findByIdGrupo(Integer idGrupo);

    List<GrupoIntegrante> findByIdGrupoOrderByMatriculaAsc(Integer idGrupo);
}
