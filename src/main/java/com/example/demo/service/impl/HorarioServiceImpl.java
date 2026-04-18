package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.dto.HorarioConBloques;
import com.example.demo.dto.HorarioCreateDTO;
import com.example.demo.dto.HorarioDTO;
import com.example.demo.dto.HorarioSemanalCreateDTO;
import com.example.demo.dto.HorarioSemanalResponseDTO;
import com.example.demo.model.BloqueDiaAsignacion;
import com.example.demo.model.BloqueHorario;
import com.example.demo.model.Calendario;
import com.example.demo.model.DiaHorario;
import com.example.demo.model.DiaSemana;
import com.example.demo.model.Horario;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.BloqueDiaAsignacionRepository;
import com.example.demo.respository.BloqueHorarioRepository;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.respository.DiaHorarioRepository;
import com.example.demo.respository.DiaSemanaRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.service.HorarioService;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private CalendarioRepository calendarioRepository;
    
    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;
    
    @Autowired
    private BloqueDiaAsignacionRepository bloqueDiaAsignacionRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private DiaSemanaRepository diaSemanaRepository;
    
    @Autowired
    private DiaHorarioRepository diaHorarioRepository;

    @Override
    public List<HorarioDTO> obtenerTodos() {
        System.out.println("🕐 Obteniendo todos los horarios");
        return horarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioDTO obtenerPorId(Integer id) {
        System.out.println("🕐 Obteniendo horario con ID: " + id);
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
        return convertirADTO(horario);
    }

    @Override
    public List<HorarioDTO> obtenerPorCalendario(Integer idCalendario) {
        System.out.println("🕐 Obteniendo horarios del calendario: " + idCalendario);
        
        // Relación invertida: Calendario tiene FK a Horario
        Calendario calendario = calendarioRepository.findById(idCalendario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + idCalendario));
        
        List<HorarioDTO> resultado = new ArrayList<>();
        if (calendario.getHorario() != null) {
            resultado.add(convertirADTO(calendario.getHorario()));
        }
        
        return resultado;
    }

    @Override
    public HorarioDTO crear(HorarioCreateDTO horarioDTO) {
        // TODO: Este método necesita ser actualizado para la nueva estructura
        // Ahora un horario contiene 7 días, no un solo día
        throw new UnsupportedOperationException("Usar crearHorarioSemanal() en su lugar");
    }

    @Override
    public HorarioDTO actualizar(Integer id, HorarioCreateDTO horarioDTO) {
        System.out.println("🕐 Actualizando horario con ID: " + id);
        
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
        
        // Horario ya no tiene FK a calendario - es independiente
        horario.setNombreHorario(horarioDTO.getNombreHorario());
        horario.setDescripcion(horarioDTO.getDescripcion());
        horario.setActivoHorario(horarioDTO.getActivoHorario());
        
        Horario horarioActualizado = horarioRepository.save(horario);
        System.out.println("✅ Horario actualizado: " + horarioActualizado.getNombreHorario());
        return convertirADTO(horarioActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        System.out.println("🕐 Eliminando horario con ID: " + id);
        
        // 1. Verificar que el horario existe
        if (!horarioRepository.existsById(id)) {
            throw new RuntimeException("Horario no encontrado con ID: " + id);
        }
        
        // 2. VALIDACIÓN CRÍTICA: Verificar que el horario NO esté siendo usado por calendarios
        List<Calendario> calendariosUsandoHorario = calendarioRepository.findByHorario_IdHorario(id);
        
        if (!calendariosUsandoHorario.isEmpty()) {
            System.out.println("❌ ERROR: El horario está en uso por " + calendariosUsandoHorario.size() + " calendario(s)");
            
            // Listar los calendarios que lo usan
            StringBuilder calendarios = new StringBuilder();
            for (Calendario cal : calendariosUsandoHorario) {
                calendarios.append("\n   - ").append(cal.getNombreCalendario())
                          .append(" (ID: ").append(cal.getIdCalendario()).append(")");
            }
            
            throw new RuntimeException(
                "No se puede eliminar el horario porque está siendo usado por " + 
                calendariosUsandoHorario.size() + " calendario(s):" + calendarios.toString() +
                "\n\nPrimero debes desvincular el horario de estos calendarios o eliminarlos."
            );
        }
        
        // 3. Si no está en uso, proceder con la eliminación
        System.out.println("✅ Horario no está en uso, procediendo a eliminar...");
        horarioRepository.deleteById(id);
        System.out.println("✅ Horario eliminado correctamente");
    }

    @Override
    public HorarioDTO cambiarEstado(Integer id, String nuevoEstado) {
        System.out.println("🕐 Cambiando estado del horario " + id + " a: " + nuevoEstado);
        
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
        
        horario.setActivoHorario(nuevoEstado);
        Horario horarioActualizado = horarioRepository.save(horario);
        
        System.out.println("✅ Estado actualizado correctamente");
        return convertirADTO(horarioActualizado);
    }

    @Override
    public List<HorarioConBloques> obtenerTodosConBloques() {
        System.out.println("🕐 Obteniendo TODOS los horarios con bloques");
        
        List<Horario> todosHorarios = horarioRepository.findAll();
        System.out.println("   📋 " + todosHorarios.size() + " horarios encontrados");
        
        List<HorarioConBloques> resultado = new ArrayList<>();
        
        for (Horario horario : todosHorarios) {
            HorarioConBloques horarioConBloques = obtenerHorarioConBloques(horario.getIdHorario());
            resultado.add(horarioConBloques);
        }
        
        System.out.println("✅ Todos los horarios con bloques obtenidos correctamente");
        return resultado;
    }

    @Override
    public HorarioConBloques obtenerHorarioConBloques(Integer idHorario) {
        System.out.println("🕐 Obteniendo horario con bloques - ID: " + idHorario);
        
        // 1. Buscar el horario
        Horario horario = horarioRepository.findById(idHorario)
            .orElseThrow(() -> new RuntimeException("Horario no encontrado: " + idHorario));
        
        // 2. Obtener los 7 días del horario ordenados
        List<DiaHorario> diasHorario = diaHorarioRepository.findByHorario_IdHorarioOrderByDiaSemana_OrdenDia(idHorario);
        
        System.out.println("   - Horario: " + horario.getNombreHorario());
        System.out.println("   - Días encontrados: " + diasHorario.size());
        
        // 3. Construir la respuesta (SOLO datos de HORARIO, sin calendario)
        HorarioConBloques response = new HorarioConBloques();
        response.setIdHorario(horario.getIdHorario());
        response.setNombreHorario(horario.getNombreHorario());
        response.setDescripcion(horario.getDescripcion());
        response.setActivoHorario(horario.getActivoHorario());
        
        // 4. Mapear cada día con sus bloques (via tabla intermedia)
        List<HorarioConBloques.DiaConBloques> dias = new ArrayList<>();
        for (DiaHorario diaHorario : diasHorario) {
            HorarioConBloques.DiaConBloques dia = new HorarioConBloques.DiaConBloques();
            dia.setIdDia(diaHorario.getDiaSemana().getIdDia());
            dia.setNombreDia(diaHorario.getDiaSemana().getNombreDia());
            dia.setOrdenDia(diaHorario.getDiaSemana().getOrdenDia());
            
            // Obtener ASIGNACIONES de bloques para este día
            List<BloqueDiaAsignacion> asignaciones = bloqueDiaAsignacionRepository
                    .findByDiaHorario_IdDiaHorario(diaHorario.getIdDiaHorario());
            
            List<BloqueHorarioDTO> bloquesDTO = asignaciones.stream()
                .map(asig -> {
                    BloqueHorario b = asig.getBloqueHorario();
                    return new BloqueHorarioDTO(
                        b.getIdBloque(),
                        diaHorario.getIdDiaHorario(),
                        b.getArea().getIdArea(),
                        b.getArea().getNombreArea(),
                        b.getNombreBloque(),
                        b.getHoraInicio(),
                        b.getHoraFin()
                    );
                })
                .collect(Collectors.toList());
            
            dia.setBloques(bloquesDTO);
            dias.add(dia);
            
            System.out.println("   - " + dia.getNombreDia() + ": " + bloquesDTO.size() + " bloques (reutilizados)");
        }
        
        response.setDias(dias);
        System.out.println("✅ Horario con bloques obtenido correctamente");
        
        return response;
    }

    @Override
    @Transactional
    public HorarioSemanalResponseDTO crearHorarioSemanal(HorarioSemanalCreateDTO dto) {
        System.out.println("🕐 Creando horario semanal completo");
        System.out.println("   - Nombre: " + dto.getNombreHorario());
        
        // 1. Crear UN SOLO registro HORARIO (representa la semana completa)
        // HORARIO ahora es independiente - NO tiene calendario
        Horario horario = new Horario();
        horario.setNombreHorario(dto.getNombreHorario());
        horario.setDescripcion(dto.getDescripcion());
        horario.setActivoHorario(dto.getActivoHorario() != null ? dto.getActivoHorario() : "Activo");
        
        Horario horarioGuardado = horarioRepository.save(horario);
        System.out.println("   ✅ HORARIO creado con ID: " + horarioGuardado.getIdHorario());
        
        // 2. Crear 7 registros DIA_HORARIO (uno por cada día fijo del catálogo)
        int totalBloquesCreados = 0;
        
        for (int idDia = 1; idDia <= 7; idDia++) {
            // 3.1 Buscar el día en el catálogo DIAS_SEMANA
            final int idDiaFinal = idDia; // Variable final para usar en lambda
            DiaSemana diaSemana = diaSemanaRepository.findById(idDia)
                    .orElseThrow(() -> new RuntimeException("Día no encontrado en catálogo: " + idDiaFinal));
            
            System.out.println("\n   📅 Procesando " + diaSemana.getNombreDia() + "...");
            
            // 3.2 Crear registro DIA_HORARIO (enlace entre horario y día fijo)
            DiaHorario diaHorario = new DiaHorario();
            diaHorario.setHorario(horarioGuardado);
            diaHorario.setDiaSemana(diaSemana);
            
            DiaHorario diaHorarioGuardado = diaHorarioRepository.save(diaHorario);
            System.out.println("      ✅ DIA_HORARIO creado con ID: " + diaHorarioGuardado.getIdDiaHorario());
            
            // 3.3 Obtener los IDs de bloques asignados a este día
            List<Integer> idsBloques = dto.getBloquesPorDia().get(diaSemana.getNombreDia());
            
            if (idsBloques != null && !idsBloques.isEmpty()) {
                System.out.println("      📦 Asignando " + idsBloques.size() + " bloques...");
                
                // 3.4 Crear ASIGNACIONES de bloques (tabla intermedia)
                for (Integer idBloque : idsBloques) {
                    // Buscar el bloque maestro (NO copiar, solo referenciar)
                    BloqueHorario bloqueMaestro = bloqueHorarioRepository.findById(idBloque)
                            .orElseThrow(() -> new RuntimeException("Bloque no encontrado con ID: " + idBloque));
                    
                    // Crear ASIGNACIÓN (tabla intermedia)
                    BloqueDiaAsignacion asignacion = new BloqueDiaAsignacion();
                    asignacion.setDiaHorario(diaHorarioGuardado);
                    asignacion.setBloqueHorario(bloqueMaestro);
                    
                    bloqueDiaAsignacionRepository.save(asignacion);
                    totalBloquesCreados++;
                }
                
                System.out.println("      ✅ " + idsBloques.size() + " bloques asignados (REUTILIZADOS, no copiados)");
            } else {
                System.out.println("      ⚠️ Sin bloques asignados (día de descanso)");
            }
        }
        
        System.out.println("\n✅ Horario semanal creado completamente:");
        System.out.println("   - 1 HORARIO creado");
        System.out.println("   - 7 DIA_HORARIO creados (7 días fijos)");
        System.out.println("   - " + totalBloquesCreados + " bloques asignados en total");
        
        // 4. Preparar respuesta (SOLO información de HORARIO)
        HorarioSemanalResponseDTO response = new HorarioSemanalResponseDTO();
        response.setIdHorario(horarioGuardado.getIdHorario());
        response.setNombreHorario(dto.getNombreHorario());
        response.setDescripcion(dto.getDescripcion());
        response.setActivoHorario(dto.getActivoHorario() != null ? dto.getActivoHorario() : "Activo");
        response.setDiasCreados(7); // Siempre 7 días
        response.setTotalBloques(totalBloquesCreados);
        
        return response;
    }

    @Override
    @Transactional
    public HorarioSemanalResponseDTO actualizarHorarioSemanal(Integer id, HorarioSemanalCreateDTO dto) {
        System.out.println("🕐 Actualizando horario semanal ID: " + id);
        System.out.println("   - Nombre: " + dto.getNombreHorario());
        
        // 1. Actualizar datos básicos del HORARIO
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
        
        horario.setNombreHorario(dto.getNombreHorario());
        horario.setDescripcion(dto.getDescripcion());
        horario.setActivoHorario(dto.getActivoHorario() != null ? dto.getActivoHorario() : "Activo");
        horarioRepository.save(horario);
        System.out.println("   ✅ Datos del horario actualizados");
        
        // 2. Obtener los 7 días del horario
        List<DiaHorario> diasHorario = diaHorarioRepository.findByHorarioIdHorario(id);
        int totalBloquesAsignados = 0;
        
        // 3. Para cada día, SOLO actualizar las asignaciones que cambiaron
        for (DiaHorario diaHorario : diasHorario) {
            String nombreDia = diaHorario.getDiaSemana().getNombreDia();
            
            // 3.1 Obtener asignaciones ACTUALES del día
            List<BloqueDiaAsignacion> asignacionesActuales = bloqueDiaAsignacionRepository
                    .findByDiaHorario_IdDiaHorario(diaHorario.getIdDiaHorario());
            
            // IDs de bloques actuales
            List<Integer> idsActuales = asignacionesActuales.stream()
                    .map(a -> a.getBloqueHorario().getIdBloque())
                    .collect(Collectors.toList());
            
            // 3.2 Obtener IDs de bloques NUEVOS que el usuario quiere asignar
            final List<Integer> idsNuevos = dto.getBloquesPorDia().get(nombreDia) != null 
                    ? dto.getBloquesPorDia().get(nombreDia) 
                    : new ArrayList<>();
            
            System.out.println("   📅 " + nombreDia + ": " + idsActuales.size() + " → " + idsNuevos.size() + " bloques");
            
            // 3.3 Eliminar SOLO las asignaciones que ya NO están en la nueva lista
            List<BloqueDiaAsignacion> asignacionesAEliminar = asignacionesActuales.stream()
                    .filter(a -> !idsNuevos.contains(a.getBloqueHorario().getIdBloque()))
                    .collect(Collectors.toList());
            
            if (!asignacionesAEliminar.isEmpty()) {
                bloqueDiaAsignacionRepository.deleteAll(asignacionesAEliminar);
                System.out.println("      🗑️ Eliminadas " + asignacionesAEliminar.size() + " asignaciones");
            }
            
            // 3.4 Agregar SOLO las asignaciones que NO existen todavía
            for (Integer idBloque : idsNuevos) {
                // Si el bloque YA está asignado, saltarlo (REUTILIZAR)
                if (idsActuales.contains(idBloque)) {
                    System.out.println("      ♻️ Bloque " + idBloque + " ya asignado (reutilizado)");
                    totalBloquesAsignados++;
                    continue;
                }
                
                // Bloque NUEVO: crear la asignación
                BloqueHorario bloque = bloqueHorarioRepository.findById(idBloque)
                        .orElseThrow(() -> new RuntimeException("Bloque no encontrado: " + idBloque));
                
                BloqueDiaAsignacion nuevaAsignacion = new BloqueDiaAsignacion();
                nuevaAsignacion.setDiaHorario(diaHorario);
                nuevaAsignacion.setBloqueHorario(bloque);
                bloqueDiaAsignacionRepository.save(nuevaAsignacion);
                
                System.out.println("      ➕ Bloque " + idBloque + " asignado");
                totalBloquesAsignados++;
            }
        }
        
        System.out.println("✅ Horario actualizado: " + totalBloquesAsignados + " bloques totales");
        
        // 4. Respuesta
        HorarioSemanalResponseDTO response = new HorarioSemanalResponseDTO();
        response.setIdHorario(horario.getIdHorario());
        response.setNombreHorario(dto.getNombreHorario());
        response.setDescripcion(dto.getDescripcion());
        response.setActivoHorario(dto.getActivoHorario());
        response.setDiasCreados(7);
        response.setTotalBloques(totalBloquesAsignados);
        
        return response;
    }

    private HorarioDTO convertirADTO(Horario horario) {
        // HORARIO es independiente - NO tiene FK a calendario
        // Si necesitas saber qué calendarios usan este horario, busca en CalendarioRepository
        return new HorarioDTO(
                horario.getIdHorario(),
                horario.getNombreHorario(),
                horario.getDescripcion(),
                horario.getActivoHorario()
        );
    }
}
