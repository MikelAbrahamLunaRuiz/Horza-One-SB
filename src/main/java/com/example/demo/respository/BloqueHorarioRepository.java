package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BloqueHorario;

/**
 * Repositorio para BloqueHorario
 * Los bloques son ahora INDEPENDIENTES (no tienen FK a DiaHorario ni Horario)
 * Para obtener bloques de un día, usar BloqueDiaAsignacionRepository
 */
@Repository
public interface BloqueHorarioRepository extends JpaRepository<BloqueHorario, Integer> {
    // Los bloques son independientes - no hay métodos de búsqueda por día/horario
    // Para obtener bloques de un día: usar BloqueDiaAsignacionRepository.findByDiaHorario_IdDiaHorario()
}
