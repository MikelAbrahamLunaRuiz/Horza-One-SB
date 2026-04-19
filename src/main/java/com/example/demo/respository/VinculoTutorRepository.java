package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.VinculoTutor;
import com.example.demo.model.VinculoTutorId;

@Repository
public interface VinculoTutorRepository extends JpaRepository<VinculoTutor, VinculoTutorId> {

    List<VinculoTutor> findByIdTutor(Integer idTutor);

    List<VinculoTutor> findByMatriculaEstudiante(Integer matriculaEstudiante);

    boolean existsByIdTutorAndMatriculaEstudiante(Integer idTutor, Integer matriculaEstudiante);

    @Modifying
    @Transactional
    void deleteByIdTutorAndMatriculaEstudiante(Integer idTutor, Integer matriculaEstudiante);
}
