package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.TutorDTO;
import com.example.demo.dto.VinculoTutorDTO;

public interface TutorService {

    List<TutorDTO> obtenerTodos();

    TutorDTO obtenerPorId(Integer idTutor);

    TutorDTO crear(TutorDTO tutorDTO);

    TutorDTO actualizar(Integer idTutor, TutorDTO tutorDTO);

    void eliminar(Integer idTutor);

    VinculoTutorDTO vincularTutorAEstudiante(Integer idTutor, Integer matriculaEstudiante);

    void desvincularTutorDeEstudiante(Integer idTutor, Integer matriculaEstudiante);

    List<TutorDTO> obtenerTutoresPorEstudiante(Integer matriculaEstudiante);

    List<VinculoTutorDTO> obtenerVinculosPorTutor(Integer idTutor);
}
