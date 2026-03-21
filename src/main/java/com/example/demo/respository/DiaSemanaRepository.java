package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DiaSemana;

/**
 * Repositorio para DIAS_SEMANA (catálogo fijo de 7 días)
 */
@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Integer> {
}
