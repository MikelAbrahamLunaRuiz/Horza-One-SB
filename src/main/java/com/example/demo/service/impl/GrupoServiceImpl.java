package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GrupoDTO;
import com.example.demo.model.Grupo;
import com.example.demo.model.Usuario;
import com.example.demo.respository.GrupoRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<GrupoDTO> obtenerTodos() {
        return grupoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDTO obtenerPorId(Integer idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + idGrupo));
        return convertirADTO(grupo);
    }

    @Override
    public GrupoDTO crear(GrupoDTO grupoDTO) {
        validarGrupo(grupoDTO);

        grupoRepository.findByNombreGrupo(grupoDTO.getNombreGrupo().trim())
                .ifPresent(g -> {
                    throw new RuntimeException("Ya existe un grupo con ese nombre");
                });

        Grupo grupo = new Grupo();
        grupo.setNombreGrupo(grupoDTO.getNombreGrupo().trim());
        grupo.setMatriculaLider(grupoDTO.getMatriculaLider());

        return convertirADTO(grupoRepository.save(grupo));
    }

    @Override
    public GrupoDTO actualizar(Integer idGrupo, GrupoDTO grupoDTO) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + idGrupo));

        if (grupoDTO.getNombreGrupo() != null && !grupoDTO.getNombreGrupo().trim().isEmpty()) {
            grupo.setNombreGrupo(grupoDTO.getNombreGrupo().trim());
        }
        if (grupoDTO.getMatriculaLider() != null) {
            usuarioRepository.findById(grupoDTO.getMatriculaLider())
                    .orElseThrow(() -> new RuntimeException("Líder no encontrado con matrícula: " + grupoDTO.getMatriculaLider()));
            grupo.setMatriculaLider(grupoDTO.getMatriculaLider());
        }

        return convertirADTO(grupoRepository.save(grupo));
    }

    @Override
    public void eliminar(Integer idGrupo) {
        if (!grupoRepository.existsById(idGrupo)) {
            throw new RuntimeException("Grupo no encontrado con ID: " + idGrupo);
        }
        grupoRepository.deleteById(idGrupo);
    }

    @Override
    public List<GrupoDTO> obtenerPorLider(Integer matriculaLider) {
        usuarioRepository.findById(matriculaLider)
                .orElseThrow(() -> new RuntimeException("Líder no encontrado con matrícula: " + matriculaLider));

        return grupoRepository.findByMatriculaLider(matriculaLider).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private void validarGrupo(GrupoDTO grupoDTO) {
        if (grupoDTO.getNombreGrupo() == null || grupoDTO.getNombreGrupo().trim().isEmpty()) {
            throw new RuntimeException("El nombre del grupo es obligatorio");
        }
        if (grupoDTO.getMatriculaLider() == null) {
            throw new RuntimeException("La matrícula del líder es obligatoria");
        }
        usuarioRepository.findById(grupoDTO.getMatriculaLider())
                .orElseThrow(() -> new RuntimeException("Líder no encontrado con matrícula: " + grupoDTO.getMatriculaLider()));
    }

    private GrupoDTO convertirADTO(Grupo grupo) {
        String nombreLider = null;
        Usuario lider = grupo.getLider();
        if (lider != null) {
            nombreLider = lider.getNombreUsuario() + " " + lider.getApellidoPaternoUsuario() + " " + lider.getApellidoMaternoUsuario();
        } else {
            nombreLider = usuarioRepository.findById(grupo.getMatriculaLider())
                    .map(u -> u.getNombreUsuario() + " " + u.getApellidoPaternoUsuario() + " " + u.getApellidoMaternoUsuario())
                    .orElse(null);
        }

        return new GrupoDTO(grupo.getIdGrupo(), grupo.getNombreGrupo(), grupo.getMatriculaLider(), nombreLider);
    }
}
