package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Calendario;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {
    // Buscar calendarios que usan un horario específico (relación N:1)
    List<Calendario> findByHorario_IdHorario(Integer idHorario);
}
