package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GrupoDTO;
import com.example.demo.dto.GrupoDetalleDTO;
import com.example.demo.dto.GrupoIntegranteDTO;
import com.example.demo.model.Grupo;
import com.example.demo.model.GrupoIntegrante;
import com.example.demo.model.Usuario;
import com.example.demo.respository.GrupoIntegranteRepository;
import com.example.demo.respository.GrupoRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoIntegranteRepository grupoIntegranteRepository;

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

    @Override
    public List<GrupoDetalleDTO> obtenerDetallePorUsuario(Integer matriculaUsuario) {
        if (matriculaUsuario == null) {
            throw new RuntimeException("La matrícula del usuario es obligatoria");
        }

        usuarioRepository.findById(matriculaUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matriculaUsuario));

        Set<Integer> idsGrupo = new LinkedHashSet<>();
        grupoIntegranteRepository.findByMatricula(matriculaUsuario)
                .forEach(integrante -> idsGrupo.add(integrante.getIdGrupo()));
        grupoRepository.findByMatriculaLider(matriculaUsuario)
                .forEach(grupo -> idsGrupo.add(grupo.getIdGrupo()));

        if (idsGrupo.isEmpty()) {
            return Collections.emptyList();
        }

        List<Grupo> grupos = grupoRepository.findAllById(idsGrupo);
        grupos.sort(Comparator.comparing(Grupo::getNombreGrupo, String.CASE_INSENSITIVE_ORDER));

        return grupos.stream()
                .map(this::convertirADetalleDTO)
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
        String nombreLider = usuarioRepository.findById(grupo.getMatriculaLider())
                .map(this::construirNombreCompleto)
                .orElse(null);

        return new GrupoDTO(grupo.getIdGrupo(), grupo.getNombreGrupo(), grupo.getMatriculaLider(), nombreLider);
    }

    private GrupoDetalleDTO convertirADetalleDTO(Grupo grupo) {
        LinkedHashMap<Integer, GrupoIntegranteDTO> integrantesPorMatricula = new LinkedHashMap<>();

        List<GrupoIntegrante> integrantes = grupoIntegranteRepository.findByIdGrupo(grupo.getIdGrupo());
        for (GrupoIntegrante integrante : integrantes) {
            Usuario usuario = integrante.getUsuario();
            if (usuario == null) {
                usuario = usuarioRepository.findById(integrante.getMatricula()).orElse(null);
            }

            String nombreCompleto = usuario != null ? construirNombreCompleto(usuario) : "MAT-" + integrante.getMatricula();
            String nombreRolSistema = (usuario != null && usuario.getRol() != null) ? usuario.getRol().getNombreRol() : null;
            String fotoPerfil = usuario != null ? usuario.getFotoPerfil() : null;
            String rolGrupo = normalizarRolGrupo(integrante.getRolGrupo());

            integrantesPorMatricula.put(
                    integrante.getMatricula(),
                    new GrupoIntegranteDTO(integrante.getMatricula(), nombreCompleto, nombreRolSistema, rolGrupo, fotoPerfil)
            );
        }

        GrupoIntegranteDTO liderIntegrante = integrantesPorMatricula.get(grupo.getMatriculaLider());
        if (liderIntegrante != null) {
            liderIntegrante.setRolGrupo("LIDER_SUPREMO");
        } else {
            Usuario lider = usuarioRepository.findById(grupo.getMatriculaLider()).orElse(null);
            if (lider != null) {
                integrantesPorMatricula.put(
                        lider.getMatricula(),
                        new GrupoIntegranteDTO(
                                lider.getMatricula(),
                                construirNombreCompleto(lider),
                                lider.getRol() != null ? lider.getRol().getNombreRol() : null,
                                "LIDER_SUPREMO",
                                lider.getFotoPerfil()
                        )
                );
            }
        }

        List<GrupoIntegranteDTO> integrantesOrdenados = new ArrayList<>(integrantesPorMatricula.values());
        integrantesOrdenados.sort(
                Comparator.comparingInt((GrupoIntegranteDTO integrante) -> prioridadRolGrupo(integrante.getRolGrupo()))
                        .thenComparing(
                                integrante -> tieneTexto(integrante.getNombreCompleto()) ? integrante.getNombreCompleto() : "",
                                String.CASE_INSENSITIVE_ORDER
                        )
        );

        String nombreLider = usuarioRepository.findById(grupo.getMatriculaLider())
                .map(this::construirNombreCompleto)
                .orElse(null);

        String nombreCapitan = obtenerNombresPorRol(integrantesOrdenados, "CAPITAN");
        String nombreAdministrador = obtenerNombresPorRol(integrantesOrdenados, "ADMINISTRADOR");

        return new GrupoDetalleDTO(
                grupo.getIdGrupo(),
                grupo.getNombreGrupo(),
                grupo.getMatriculaLider(),
                nombreLider,
                nombreCapitan,
                nombreAdministrador,
                integrantesOrdenados
        );
    }

    private String obtenerNombresPorRol(List<GrupoIntegranteDTO> integrantes, String rolGrupoObjetivo) {
        String nombres = integrantes.stream()
                .filter(integrante -> rolGrupoObjetivo.equals(normalizarRolGrupo(integrante.getRolGrupo())))
                .map(GrupoIntegranteDTO::getNombreCompleto)
                .filter(this::tieneTexto)
                .distinct()
                .collect(Collectors.joining(", "));
        return tieneTexto(nombres) ? nombres : null;
    }

    private String construirNombreCompleto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        StringBuilder nombreCompleto = new StringBuilder();
        if (tieneTexto(usuario.getNombreUsuario())) {
            nombreCompleto.append(usuario.getNombreUsuario().trim());
        }
        if (tieneTexto(usuario.getApellidoPaternoUsuario())) {
            if (nombreCompleto.length() > 0) {
                nombreCompleto.append(" ");
            }
            nombreCompleto.append(usuario.getApellidoPaternoUsuario().trim());
        }
        if (tieneTexto(usuario.getApellidoMaternoUsuario())) {
            if (nombreCompleto.length() > 0) {
                nombreCompleto.append(" ");
            }
            nombreCompleto.append(usuario.getApellidoMaternoUsuario().trim());
        }
        return nombreCompleto.toString();
    }

    private String normalizarRolGrupo(String rolGrupo) {
        if (!tieneTexto(rolGrupo)) {
            return "MIEMBRO";
        }

        String rolNormalizado = rolGrupo.trim().toUpperCase();
        switch (rolNormalizado) {
            case "LIDER_SUPREMO":
            case "CAPITAN":
            case "ADMINISTRADOR":
                return rolNormalizado;
            default:
                return "MIEMBRO";
        }
    }

    private int prioridadRolGrupo(String rolGrupo) {
        String rolNormalizado = normalizarRolGrupo(rolGrupo);
        if ("LIDER_SUPREMO".equals(rolNormalizado)) {
            return 0;
        }
        if ("CAPITAN".equals(rolNormalizado)) {
            return 1;
        }
        if ("ADMINISTRADOR".equals(rolNormalizado)) {
            return 2;
        }
        return 3;
    }

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
