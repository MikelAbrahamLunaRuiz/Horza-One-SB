package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ExpedienteDigital;

@Repository
public interface ExpedienteDigitalRepository extends JpaRepository<ExpedienteDigital, Integer> {
    List<ExpedienteDigital> findByMatriculaOrderByFechaCargaDesc(Integer matricula);
}
