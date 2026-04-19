package com.example.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TutorDTO;
import com.example.demo.dto.VinculoTutorDTO;
import com.example.demo.model.Tutor;
import com.example.demo.model.Usuario;
import com.example.demo.model.VinculoTutor;
import com.example.demo.respository.TutorRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.respository.VinculoTutorRepository;
import com.example.demo.service.TutorService;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VinculoTutorRepository vinculoTutorRepository;

    @Override
    public List<TutorDTO> obtenerTodos() {
        return tutorRepository.findAll().stream()
                .map(this::convertirTutorADTOOcultandoContrasenia)
                .collect(Collectors.toList());
    }

    @Override
    public TutorDTO obtenerPorId(Integer idTutor) {
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado con ID: " + idTutor));
        return convertirTutorADTOOcultandoContrasenia(tutor);
    }

    @Override
    public TutorDTO crear(TutorDTO tutorDTO) {
        if (tutorDTO.getNombre() == null || tutorDTO.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del tutor es obligatorio");
        }
        if (tutorDTO.getCorreo() == null || tutorDTO.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo del tutor es obligatorio");
        }
        if (tutorDTO.getContrasenia() == null || tutorDTO.getContrasenia().trim().isEmpty()) {
            throw new RuntimeException("La contraseña del tutor es obligatoria");
        }

        tutorRepository.findByCorreo(tutorDTO.getCorreo().trim()).ifPresent(t -> {
            throw new RuntimeException("Ya existe un tutor con ese correo");
        });

        Tutor tutor = new Tutor();
        tutor.setNombre(tutorDTO.getNombre().trim());
        tutor.setCorreo(tutorDTO.getCorreo().trim());
        tutor.setContrasenia(tutorDTO.getContrasenia());

        Tutor guardado = tutorRepository.save(tutor);
        return convertirTutorADTOOcultandoContrasenia(guardado);
    }

    @Override
    public TutorDTO actualizar(Integer idTutor, TutorDTO tutorDTO) {
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado con ID: " + idTutor));

        if (tutorDTO.getNombre() != null && !tutorDTO.getNombre().trim().isEmpty()) {
            tutor.setNombre(tutorDTO.getNombre().trim());
        }
        if (tutorDTO.getCorreo() != null && !tutorDTO.getCorreo().trim().isEmpty()) {
            tutor.setCorreo(tutorDTO.getCorreo().trim());
        }
        if (tutorDTO.getContrasenia() != null && !tutorDTO.getContrasenia().trim().isEmpty()) {
            tutor.setContrasenia(tutorDTO.getContrasenia());
        }

        Tutor actualizado = tutorRepository.save(tutor);
        return convertirTutorADTOOcultandoContrasenia(actualizado);
    }

    @Override
    public void eliminar(Integer idTutor) {
        tutorRepository.deleteById(idTutor);
    }

    @Override
    public VinculoTutorDTO vincularTutorAEstudiante(Integer idTutor, Integer matriculaEstudiante) {
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado con ID: " + idTutor));
        Usuario estudiante = usuarioRepository.findById(matriculaEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con matrícula: " + matriculaEstudiante));

        if (vinculoTutorRepository.existsByIdTutorAndMatriculaEstudiante(idTutor, matriculaEstudiante)) {
            throw new RuntimeException("El vínculo tutor-estudiante ya existe");
        }

        VinculoTutor vinculo = new VinculoTutor(idTutor, matriculaEstudiante);
        VinculoTutor guardado = vinculoTutorRepository.save(vinculo);

        return new VinculoTutorDTO(
                guardado.getIdTutor(),
                guardado.getMatriculaEstudiante(),
                tutor.getNombre(),
                obtenerNombreCompleto(estudiante)
        );
    }

    @Override
    public void desvincularTutorDeEstudiante(Integer idTutor, Integer matriculaEstudiante) {
        if (!vinculoTutorRepository.existsByIdTutorAndMatriculaEstudiante(idTutor, matriculaEstudiante)) {
            throw new RuntimeException("El vínculo tutor-estudiante no existe");
        }
        vinculoTutorRepository.deleteByIdTutorAndMatriculaEstudiante(idTutor, matriculaEstudiante);
    }

    @Override
    public List<TutorDTO> obtenerTutoresPorEstudiante(Integer matriculaEstudiante) {
        usuarioRepository.findById(matriculaEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con matrícula: " + matriculaEstudiante));

        return vinculoTutorRepository.findByMatriculaEstudiante(matriculaEstudiante).stream()
                .map(v -> {
                    Tutor tutor = v.getTutor();
                    if (tutor == null) {
                        tutor = tutorRepository.findById(v.getIdTutor()).orElse(null);
                    }
                    return tutor;
                })
                .filter(Objects::nonNull)
                .map(this::convertirTutorADTOOcultandoContrasenia)
                .collect(Collectors.toList());
    }

    @Override
    public List<VinculoTutorDTO> obtenerVinculosPorTutor(Integer idTutor) {
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado con ID: " + idTutor));

        return vinculoTutorRepository.findByIdTutor(idTutor).stream()
                .map(v -> {
                    String nombreEstudiante = null;
                    if (v.getEstudiante() != null) {
                        nombreEstudiante = obtenerNombreCompleto(v.getEstudiante());
                    } else {
                        nombreEstudiante = usuarioRepository.findById(v.getMatriculaEstudiante())
                                .map(this::obtenerNombreCompleto)
                                .orElse(null);
                    }
                    return new VinculoTutorDTO(v.getIdTutor(), v.getMatriculaEstudiante(), tutor.getNombre(), nombreEstudiante);
                })
                .collect(Collectors.toList());
    }

    private TutorDTO convertirTutorADTOOcultandoContrasenia(Tutor tutor) {
        return new TutorDTO(
                tutor.getIdTutor(),
                tutor.getNombre(),
                tutor.getCorreo(),
                null
        );
    }

    private String obtenerNombreCompleto(Usuario usuario) {
        return usuario.getNombreUsuario() + " " +
                usuario.getApellidoPaternoUsuario() + " " +
                usuario.getApellidoMaternoUsuario();
    }
}
