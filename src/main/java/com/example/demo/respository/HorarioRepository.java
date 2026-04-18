package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    // Horario ya no tiene FK a Calendario (relación invertida)
    // Para buscar horarios de un calendario, usar CalendarioRepository.findByIdHorario
}
