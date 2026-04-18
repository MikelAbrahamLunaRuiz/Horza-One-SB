package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DiaHorario;
import com.example.demo.model.Horario;

/**
 * Repositorio para DIA_HORARIO (relación entre HORARIO y DIAS_SEMANA)
 */
@Repository
public interface DiaHorarioRepository extends JpaRepository<DiaHorario, Integer> {
    
    /**
     * Busca todos los días asociados a un horario específico
     * Debe retornar exactamente 7 registros
     */
    List<DiaHorario> findByHorario(Horario horario);
    
    /**
     * Busca todos los días asociados a un horario por ID
     */
    List<DiaHorario> findByHorarioIdHorario(Integer idHorario);
    
    /**
     * Busca todos los días asociados a un horario ordenados por el orden del día
     * Útil para obtener los 7 días ordenados (Lunes, Martes, ..., Domingo)
     */
    List<DiaHorario> findByHorario_IdHorarioOrderByDiaSemana_OrdenDia(Integer idHorario);
}
