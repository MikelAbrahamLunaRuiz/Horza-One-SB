package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ExpedienteDigitalDTO;
import com.example.demo.model.ExpedienteDigital;
import com.example.demo.respository.ExpedienteDigitalRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.ExpedienteDigitalService;

@Service
public class ExpedienteDigitalServiceImpl implements ExpedienteDigitalService {

    @Autowired
    private ExpedienteDigitalRepository expedienteDigitalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<ExpedienteDigitalDTO> obtenerTodos() {
        return expedienteDigitalRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpedienteDigitalDTO> obtenerPorMatricula(Integer matricula) {
        usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matricula));

        return expedienteDigitalRepository.findByMatriculaOrderByFechaCargaDesc(matricula).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExpedienteDigitalDTO crear(ExpedienteDigitalDTO expedienteDigitalDTO) {
        if (expedienteDigitalDTO.getMatricula() == null) {
            throw new RuntimeException("La matrícula es obligatoria");
        }
        if (expedienteDigitalDTO.getUrlPdf() == null || expedienteDigitalDTO.getUrlPdf().trim().isEmpty()) {
            throw new RuntimeException("La URL del documento es obligatoria");
        }
        if (expedienteDigitalDTO.getTipoDoc() == null || expedienteDigitalDTO.getTipoDoc().trim().isEmpty()) {
            throw new RuntimeException("El tipo de documento es obligatorio");
        }

        usuarioRepository.findById(expedienteDigitalDTO.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + expedienteDigitalDTO.getMatricula()));

        ExpedienteDigital expedienteDigital = new ExpedienteDigital();
        expedienteDigital.setMatricula(expedienteDigitalDTO.getMatricula());
        expedienteDigital.setUrlPdf(expedienteDigitalDTO.getUrlPdf().trim());
        expedienteDigital.setTipoDoc(expedienteDigitalDTO.getTipoDoc().trim());

        return convertirADTO(expedienteDigitalRepository.save(expedienteDigital));
    }

    @Override
    public void eliminar(Integer idArchivo) {
        if (!expedienteDigitalRepository.existsById(idArchivo)) {
            throw new RuntimeException("Documento no encontrado con ID: " + idArchivo);
        }
        expedienteDigitalRepository.deleteById(idArchivo);
    }

    private ExpedienteDigitalDTO convertirADTO(ExpedienteDigital expedienteDigital) {
        return new ExpedienteDigitalDTO(
                expedienteDigital.getIdArchivo(),
                expedienteDigital.getMatricula(),
                expedienteDigital.getUrlPdf(),
                expedienteDigital.getTipoDoc(),
                expedienteDigital.getFechaCarga()
        );
    }
}
