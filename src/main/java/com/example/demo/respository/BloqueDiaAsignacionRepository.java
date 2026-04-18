package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BloqueDiaAsignacion;
import com.example.demo.model.DiaHorario;

/**
 * Repositorio para gestionar las asignaciones de bloques a días
 */
@Repository
public interface BloqueDiaAsignacionRepository extends JpaRepository<BloqueDiaAsignacion, Integer> {
    
    /**
     * Obtener todas las asignaciones de un día específico
     */
    List<BloqueDiaAsignacion> findByDiaHorario(DiaHorario diaHorario);
    
    /**
     * Obtener todas las asignaciones de un día por su ID
     */
    List<BloqueDiaAsignacion> findByDiaHorario_IdDiaHorario(Integer idDiaHorario);
    
    /**
     * Eliminar todas las asignaciones de un día específico
     */
    void deleteByDiaHorario(DiaHorario diaHorario);
    
    /**
     * Eliminar todas las asignaciones de un día por su ID
     */
    void deleteByDiaHorario_IdDiaHorario(Integer idDiaHorario);
}
