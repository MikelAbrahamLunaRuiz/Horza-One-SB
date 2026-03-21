package com.example.demo.respository;

import com.example.demo.model.PermisoDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PermisoDiaRepository extends JpaRepository<PermisoDia, Integer> {

    // Obtener todos los permisos activos
    List<PermisoDia> findByActivoOrderByFechaDesc(String activo);

    // Obtener permisos generales
    List<PermisoDia> findByEsGeneralAndActivoOrderByFecha(Boolean esGeneral, String activo);

    // Obtener permisos de un usuario específico
    List<PermisoDia> findByMatriculaAndActivoOrderByFecha(Integer matricula, String activo);

    // Obtener permisos por fecha
    List<PermisoDia> findByFechaAndActivo(LocalDate fecha, String activo);

    // Obtener permisos por rango de fechas
    @Query("SELECT p FROM PermisoDia p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin AND p.activo = :activo ORDER BY p.fecha")
    List<PermisoDia> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                         @Param("fechaFin") LocalDate fechaFin, 
                                         @Param("activo") String activo);

    // Obtener permisos de un usuario en un rango de fechas
    @Query("SELECT p FROM PermisoDia p WHERE p.matricula = :matricula AND p.fecha BETWEEN :fechaInicio AND :fechaFin AND p.activo = :activo ORDER BY p.fecha")
    List<PermisoDia> findByMatriculaAndFechaBetween(@Param("matricula") Integer matricula,
                                                      @Param("fechaInicio") LocalDate fechaInicio,
                                                      @Param("fechaFin") LocalDate fechaFin,
                                                      @Param("activo") String activo);

    // Obtener permisos por tipo
    List<PermisoDia> findByTipoPermisoAndActivoOrderByFecha(String tipoPermiso, String activo);

    // Verificar si existe un permiso general en una fecha
    boolean existsByFechaAndEsGeneralAndActivo(LocalDate fecha, Boolean esGeneral, String activo);

    // Verificar si un usuario tiene permiso en una fecha
    boolean existsByFechaAndMatriculaAndActivo(LocalDate fecha, Integer matricula, String activo);
}
