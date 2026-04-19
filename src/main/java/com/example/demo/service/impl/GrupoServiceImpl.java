package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GrupoDTO;
import com.example.demo.dto.GrupoDetalleDTO;
import com.example.demo.dto.GrupoIntegranteDTO;
import com.example.demo.model.Grupo;
import com.example.demo.model.GrupoIntegrante;
import com.example.demo.model.GrupoIntegranteId;
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
                .sorted(Comparator.comparing(Grupo::getNombreGrupo, String.CASE_INSENSITIVE_ORDER))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDTO obtenerPorId(Integer idGrupo) {
        Grupo grupo = obtenerGrupoPorId(idGrupo);
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

        Grupo grupoGuardado = grupoRepository.save(grupo);
        asegurarAdministradorComoIntegrante(grupoGuardado.getIdGrupo(), grupoGuardado.getMatriculaLider());
        return convertirADTO(grupoGuardado);
    }

    @Override
    public GrupoDTO actualizar(Integer idGrupo, GrupoDTO grupoDTO) {
        Grupo grupo = obtenerGrupoPorId(idGrupo);

        if (tieneTexto(grupoDTO.getNombreGrupo())) {
            String nombreNormalizado = grupoDTO.getNombreGrupo().trim();
            grupoRepository.findByNombreGrupo(nombreNormalizado)
                    .filter(g -> !g.getIdGrupo().equals(idGrupo))
                    .ifPresent(g -> {
                        throw new RuntimeException("Ya existe un grupo con ese nombre");
                    });
            grupo.setNombreGrupo(nombreNormalizado);
        }

        if (grupoDTO.getMatriculaLider() != null) {
            validarAdministrador(grupoDTO.getMatriculaLider());
            grupo.setMatriculaLider(grupoDTO.getMatriculaLider());
        }

        Grupo grupoGuardado = grupoRepository.save(grupo);
        asegurarAdministradorComoIntegrante(grupoGuardado.getIdGrupo(), grupoGuardado.getMatriculaLider());
        return convertirADTO(grupoGuardado);
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
        validarAdministrador(matriculaLider);

        return grupoRepository.findByMatriculaLider(matriculaLider).stream()
                .sorted(Comparator.comparing(Grupo::getNombreGrupo, String.CASE_INSENSITIVE_ORDER))
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

    @Override
    public List<GrupoDetalleDTO> obtenerDetalleCompleto() {
        return grupoRepository.findAll().stream()
                .sorted(Comparator.comparing(Grupo::getNombreGrupo, String.CASE_INSENSITIVE_ORDER))
                .map(this::convertirADetalleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDetalleDTO obtenerDetallePorGrupo(Integer idGrupo) {
        return convertirADetalleDTO(obtenerGrupoPorId(idGrupo));
    }

    @Override
    public GrupoDetalleDTO actualizarIntegrantes(Integer idGrupo, List<Integer> matriculas) {
        Grupo grupo = obtenerGrupoPorId(idGrupo);

        Set<Integer> matriculasObjetivo = new LinkedHashSet<>();
        if (matriculas != null) {
            matriculas.stream()
                    .filter(Objects::nonNull)
                    .forEach(matriculasObjetivo::add);
        }
        matriculasObjetivo.add(grupo.getMatriculaLider());

        validarUsuariosExistentes(matriculasObjetivo);

        List<GrupoIntegrante> integrantesActuales = grupoIntegranteRepository.findByIdGrupo(idGrupo);
        Set<Integer> matriculasActuales = integrantesActuales.stream()
                .map(GrupoIntegrante::getMatricula)
                .collect(Collectors.toSet());

        for (GrupoIntegrante integranteActual : integrantesActuales) {
            if (!matriculasObjetivo.contains(integranteActual.getMatricula())) {
                grupoIntegranteRepository.delete(integranteActual);
            }
        }

        for (Integer matricula : matriculasObjetivo) {
            if (!matriculasActuales.contains(matricula)) {
                grupoIntegranteRepository.save(new GrupoIntegrante(idGrupo, matricula));
            }
        }

        return convertirADetalleDTO(grupo);
    }

    private Grupo obtenerGrupoPorId(Integer idGrupo) {
        return grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + idGrupo));
    }

    private void validarGrupo(GrupoDTO grupoDTO) {
        if (!tieneTexto(grupoDTO.getNombreGrupo())) {
            throw new RuntimeException("El nombre del grupo es obligatorio");
        }
        if (grupoDTO.getMatriculaLider() == null) {
            throw new RuntimeException("La matrícula del administrador es obligatoria");
        }
        validarAdministrador(grupoDTO.getMatriculaLider());
    }

    private void validarAdministrador(Integer matriculaAdministrador) {
        usuarioRepository.findById(matriculaAdministrador)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con matrícula: " + matriculaAdministrador));
    }

    private void validarUsuariosExistentes(Set<Integer> matriculas) {
        if (matriculas.isEmpty()) {
            return;
        }

        Set<Integer> encontrados = new LinkedHashSet<>();
        usuarioRepository.findAllById(matriculas)
                .forEach(usuario -> encontrados.add(usuario.getMatricula()));

        if (encontrados.size() != matriculas.size()) {
            Integer faltante = matriculas.stream()
                    .filter(m -> !encontrados.contains(m))
                    .findFirst()
                    .orElse(null);
            throw new RuntimeException("Usuario no encontrado con matrícula: " + faltante);
        }
    }

    private void asegurarAdministradorComoIntegrante(Integer idGrupo, Integer matriculaAdministrador) {
        GrupoIntegranteId id = new GrupoIntegranteId(idGrupo, matriculaAdministrador);
        if (!grupoIntegranteRepository.existsById(id)) {
            grupoIntegranteRepository.save(new GrupoIntegrante(idGrupo, matriculaAdministrador));
        }
    }

    private GrupoDTO convertirADTO(Grupo grupo) {
        String nombreAdministrador = usuarioRepository.findById(grupo.getMatriculaLider())
                .map(this::construirNombreCompleto)
                .orElse(null);

        return new GrupoDTO(
                grupo.getIdGrupo(),
                grupo.getNombreGrupo(),
                grupo.getMatriculaLider(),
                nombreAdministrador
        );
    }

    private GrupoDetalleDTO convertirADetalleDTO(Grupo grupo) {
        Integer matriculaAdministrador = grupo.getMatriculaLider();
        LinkedHashMap<Integer, GrupoIntegranteDTO> integrantesPorMatricula = new LinkedHashMap<>();

        List<GrupoIntegrante> integrantes = grupoIntegranteRepository.findByIdGrupoOrderByMatriculaAsc(grupo.getIdGrupo());
        for (GrupoIntegrante integrante : integrantes) {
            integrantesPorMatricula.put(
                    integrante.getMatricula(),
                    construirIntegranteDTO(integrante.getMatricula(), matriculaAdministrador)
            );
        }

        if (!integrantesPorMatricula.containsKey(matriculaAdministrador)) {
            integrantesPorMatricula.put(
                    matriculaAdministrador,
                    construirIntegranteDTO(matriculaAdministrador, matriculaAdministrador)
            );
        }

        List<GrupoIntegranteDTO> integrantesOrdenados = new ArrayList<>(integrantesPorMatricula.values());
        integrantesOrdenados.sort(
                Comparator.comparing(
                                (GrupoIntegranteDTO integranteDTO) -> !Boolean.TRUE.equals(integranteDTO.getAdministrador())
                        )
                        .thenComparing(
                                integranteDTO -> tieneTexto(integranteDTO.getNombreCompleto()) ? integranteDTO.getNombreCompleto() : "",
                                String.CASE_INSENSITIVE_ORDER
                        )
        );

        String nombreAdministrador = integrantesOrdenados.stream()
                .filter(integranteDTO -> Boolean.TRUE.equals(integranteDTO.getAdministrador()))
                .map(GrupoIntegranteDTO::getNombreCompleto)
                .filter(this::tieneTexto)
                .findFirst()
                .orElse(null);

        return new GrupoDetalleDTO(
                grupo.getIdGrupo(),
                grupo.getNombreGrupo(),
                matriculaAdministrador,
                nombreAdministrador,
                integrantesOrdenados
        );
    }

    private GrupoIntegranteDTO construirIntegranteDTO(Integer matricula, Integer matriculaAdministrador) {
        Usuario usuario = usuarioRepository.findById(matricula).orElse(null);
        String nombreCompleto = usuario != null ? construirNombreCompleto(usuario) : "MAT-" + matricula;
        String nombreRolSistema = (usuario != null && usuario.getRol() != null) ? usuario.getRol().getNombreRol() : null;
        String fotoPerfil = usuario != null ? usuario.getFotoPerfil() : null;
        boolean esAdministrador = matriculaAdministrador != null && matriculaAdministrador.equals(matricula);

        return new GrupoIntegranteDTO(matricula, nombreCompleto, nombreRolSistema, fotoPerfil, esAdministrador);
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

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
