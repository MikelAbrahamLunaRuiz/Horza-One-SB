package com.example.demo.service.impl;

import com.example.demo.dto.EstadisticasDiariaDTO;
import com.example.demo.dto.EstadisticasMensualDTO;
import com.example.demo.dto.EstadisticasMensualDTO.EstadisticaDiaDTO;
import com.example.demo.model.Registro;
import com.example.demo.model.Usuario;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.respository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstadisticasServiceImpl {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EstadisticasDiariaDTO obtenerEstadisticasDelDia(LocalDate fecha) {
        // Total de trabajadores activos
        long totalTrabajadores = usuarioRepository.findAll().stream()
                .filter(u -> "Activo".equals(u.getActivo()))
                .count();

        // Obtener todos los registros del día
        List<Registro> registrosDelDia = registroRepository.findAll().stream()
                .filter(r -> r.getFecha().equals(fecha))
                .collect(Collectors.toList());

        // Contar entradas y salidas
        long totalEntradas = registrosDelDia.stream()
                .filter(r -> "Entrada".equals(r.getTipoRegistro()))
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();

        long totalSalidas = registrosDelDia.stream()
                .filter(r -> "Salida".equals(r.getTipoRegistro()))
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();

        // Contar estados de entrada
        long entradasPuntuales = registrosDelDia.stream()
                .filter(r -> "Entrada".equals(r.getTipoRegistro()) && "Puntual".equals(r.getEstadoRegistro()))
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();

        long retardos = registrosDelDia.stream()
                .filter(r -> "Entrada".equals(r.getTipoRegistro()) && "Retardo".equals(r.getEstadoRegistro()))
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();

        // Permisos: registros con observación que contenga "permiso"
        long permisos = registrosDelDia.stream()
                .filter(r -> r.getObservacion() != null &&
                        r.getObservacion().toLowerCase().contains("permiso"))
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();

        // Inasistencias: trabajadores activos que no registraron entrada
        long inasistencias = totalTrabajadores - totalEntradas;
        if (inasistencias < 0) inasistencias = 0;

        return new EstadisticasDiariaDTO(
                fecha,
                (int) totalTrabajadores,
                (int) totalEntradas,
                (int) totalSalidas,
                (int) entradasPuntuales,
                (int) retardos,
                (int) inasistencias,
                (int) permisos
        );
    }

    public EstadisticasMensualDTO obtenerEstadisticasMensuales(Integer mes, Integer anio, String tipoEstadistica) {
        YearMonth yearMonth = YearMonth.of(anio, mes);
        LocalDate primerDia = yearMonth.atDay(1);
        LocalDate ultimoDia = yearMonth.atEndOfMonth();

        // Obtener todos los registros del mes
        List<Registro> registrosDelMes = registroRepository.findAll().stream()
                .filter(r -> !r.getFecha().isBefore(primerDia) && !r.getFecha().isAfter(ultimoDia))
                .collect(Collectors.toList());

        // Agrupar por día
        Map<Integer, Integer> estadisticasPorDia = new HashMap<>();

        // Inicializar todos los días del mes en 0
        for (int dia = 1; dia <= yearMonth.lengthOfMonth(); dia++) {
            estadisticasPorDia.put(dia, 0);
        }

        // Contar según el tipo de estadística
        for (LocalDate fecha = primerDia; !fecha.isAfter(ultimoDia); fecha = fecha.plusDays(1)) {
            final LocalDate fechaFinal = fecha;
            int dia = fecha.getDayOfMonth();
            long count = 0;

            List<Registro> registrosDelDia = registrosDelMes.stream()
                    .filter(r -> r.getFecha().equals(fechaFinal))
                    .collect(Collectors.toList());

            switch (tipoEstadistica.toLowerCase()) {
                case "puntuales":
                    count = registrosDelDia.stream()
                            .filter(r -> "Entrada".equals(r.getTipoRegistro()) &&
                                    "Puntual".equals(r.getEstadoRegistro()))
                            .map(r -> r.getUsuario().getMatricula())
                            .distinct()
                            .count();
                    break;

                case "retardos":
                    count = registrosDelDia.stream()
                            .filter(r -> "Entrada".equals(r.getTipoRegistro()) &&
                                    "Retardo".equals(r.getEstadoRegistro()))
                            .map(r -> r.getUsuario().getMatricula())
                            .distinct()
                            .count();
                    break;

                case "permisos":
                    count = registrosDelDia.stream()
                            .filter(r -> r.getObservacion() != null &&
                                    r.getObservacion().toLowerCase().contains("permiso"))
                            .map(r -> r.getUsuario().getMatricula())
                            .distinct()
                            .count();
                    break;

                case "inasistencias":
                    long totalActivos = usuarioRepository.findAll().stream()
                            .filter(u -> "Activo".equals(u.getActivo()))
                            .count();
                    long entradasDelDia = registrosDelDia.stream()
                            .filter(r -> "Entrada".equals(r.getTipoRegistro()))
                            .map(r -> r.getUsuario().getMatricula())
                            .distinct()
                            .count();
                    count = totalActivos - entradasDelDia;
                    if (count < 0) count = 0;
                    break;
            }

            estadisticasPorDia.put(dia, (int) count);
        }

        // Convertir a lista de DTOs
        List<EstadisticaDiaDTO> listaEstadisticas = new ArrayList<>();
        for (int dia = 1; dia <= yearMonth.lengthOfMonth(); dia++) {
            LocalDate fecha = yearMonth.atDay(dia);
            listaEstadisticas.add(new EstadisticaDiaDTO(
                    dia,
                    fecha,
                    estadisticasPorDia.get(dia)
            ));
        }

        return new EstadisticasMensualDTO(mes, anio, tipoEstadistica, listaEstadisticas);
    }
}