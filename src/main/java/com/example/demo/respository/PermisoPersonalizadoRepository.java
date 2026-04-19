package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.PermisoPersonalizado;
import com.example.demo.model.PermisoPersonalizadoId;

@Repository
public interface PermisoPersonalizadoRepository extends JpaRepository<PermisoPersonalizado, PermisoPersonalizadoId> {

    List<PermisoPersonalizado> findByMatricula(Integer matricula);

    boolean existsByMatriculaAndIdAccion(Integer matricula, Integer idAccion);

    @Modifying
    @Transactional
    void deleteByMatriculaAndIdAccion(Integer matricula, Integer idAccion);

    @Query("SELECT p.accionAdmin.nombreAccion FROM PermisoPersonalizado p WHERE p.matricula = :matricula")
    List<String> findNombresAccionesByMatricula(@Param("matricula") Integer matricula);
}
