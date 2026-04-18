package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.HorarioConBloques;
import com.example.demo.dto.UsuarioCalendarioCompleto;
import com.example.demo.model.BloqueDiaAsignacion;
import com.example.demo.model.BloqueHorario;
import com.example.demo.model.Calendario;
import com.example.demo.model.DiaHorario;
import com.example.demo.model.DiaSemana;
import com.example.demo.model.Horario;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioCalendario;
import com.example.demo.model.UsuarioCalendarioId;
import com.example.demo.respository.BloqueDiaAsignacionRepository;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.respository.DiaHorarioRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.respository.UsuarioCalendarioRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.UsuarioCalendarioService;

@Service
public class UsuarioCalendarioServiceImpl implements UsuarioCalendarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioCalendarioRepository usuarioCalendarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private BloqueDiaAsignacionRepository bloqueDiaAsignacionRepository;
    
    @Autowired
    private CalendarioRepository calendarioRepository;
    
    @Autowired
    private DiaHorarioRepository diaHorarioRepository;

    @Override
    public UsuarioCalendarioCompleto obtenerCalendariosCompletos(Integer matricula) {
        System.out.println("📅 Obteniendo calendarios completos para matrícula: " + matricula);
        
        // 1. Buscar usuario
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matricula));
        
        // 2. Obtener asignaciones de calendarios
        List<UsuarioCalendario> asignacionesCalendario = usuarioCalendarioRepository.findById_Matricula(matricula);
        System.out.println("   📋 " + asignacionesCalendario.size() + " calendarios asignados");
        
        // 3. Construir DTOs de calendarios con horarios
        List<CalendarioConHorarios> calendariosDTO = new ArrayList<>();
        
        for (UsuarioCalendario asignacionCal : asignacionesCalendario) {
            Calendario calendario = asignacionCal.getCalendario();
            System.out.println("   📅 Procesando calendario: " + calendario.getNombreCalendario());
            
            // 3.1 Obtener horario del calendario (relación N:1 - calendario tiene FK a horario)
            Horario horario = calendario.getHorario();
            
            if (horario == null) {
                System.out.println("      ⚠️ Calendario sin horario asignado");
                continue; // Saltar este calendario
            }
            
            System.out.println("      🕐 Horario encontrado: " + horario.getNombreHorario());
            
            // 3.2 Construir HorarioConBloques para el horario
            List<HorarioConBloques> horariosConBloques = new ArrayList<>();
            {
                System.out.println("      🕐 Procesando horario: " + horario.getNombreHorario());
                
                // 3.2.1 Obtener los 7 días del horario
                List<DiaHorario> diasHorario = diaHorarioRepository
                        .findByHorario_IdHorarioOrderByDiaSemana_OrdenDia(horario.getIdHorario());
                System.out.println("         📅 " + diasHorario.size() + " días encontrados");
                
                // 3.2.2 Construir DiaConBloques para cada día
                List<HorarioConBloques.DiaConBloques> dias = new ArrayList<>();
                
                for (DiaHorario diaHorario : diasHorario) {
                    DiaSemana diaSemana = diaHorario.getDiaSemana();
                    
                    // 3.2.3 Obtener asignaciones de bloques del día (tabla intermedia)
                    List<BloqueDiaAsignacion> asignacionesBloques = bloqueDiaAsignacionRepository
                            .findByDiaHorario_IdDiaHorario(diaHorario.getIdDiaHorario());
                    
                    // 3.2.4 Convertir bloques a DTOs
                    List<BloqueHorarioDTO> bloquesDTO = asignacionesBloques.stream()
                            .map(asigBloque -> {
                                BloqueHorario bloque = asigBloque.getBloqueHorario();
                                return new BloqueHorarioDTO(
                                        bloque.getIdBloque(),
                                        diaHorario.getIdDiaHorario(),
                                        bloque.getArea() != null ? bloque.getArea().getIdArea() : null,
                                        bloque.getArea() != null ? bloque.getArea().getNombreArea() : null,
                                        bloque.getNombreBloque(),
                                        bloque.getHoraInicio(),
                                        bloque.getHoraFin()
                                );
                            })
                            .collect(Collectors.toList());
                    
                    // 3.2.5 Crear DiaConBloques
                    HorarioConBloques.DiaConBloques diaDTO = new HorarioConBloques.DiaConBloques(
                            diaSemana.getIdDia(),
                            diaSemana.getNombreDia(),
                            diaSemana.getOrdenDia(),
                            bloquesDTO
                    );
                    dias.add(diaDTO);
                    
                    System.out.println("            ✅ " + diaSemana.getNombreDia() + ": " + bloquesDTO.size() + " bloques");
                }
                
                // 3.2.6 Crear HorarioConBloques (solo info de HORARIO - el calendario ya lo sabemos del contexto)
                HorarioConBloques horarioDTO = new HorarioConBloques(
                        horario.getIdHorario(),
                        horario.getNombreHorario(),
                        horario.getDescripcion(),
                        horario.getActivoHorario(),
                        dias
                );
                horariosConBloques.add(horarioDTO);
            }
            
            // 3.3 Crear CalendarioConHorarios
            CalendarioConHorarios calendarioDTO = new CalendarioConHorarios(
                    calendario.getIdCalendario(),
                    calendario.getNombreCalendario(),
                    calendario.getFechaInicio(),
                    calendario.getFechaFin(),
                    calendario.getDescripcion(),
                    calendario.getActivoCalendario(),
                    horariosConBloques
            );
            calendariosDTO.add(calendarioDTO);
        }
        
        // 4. Construir respuesta completa
        UsuarioCalendarioCompleto respuesta = new UsuarioCalendarioCompleto(
                usuario.getMatricula(),
                usuario.getNombreUsuario() + " " + usuario.getApellidoPaternoUsuario() + " " + 
                usuario.getApellidoMaternoUsuario(),
                usuario.getCorreo(),
                calendariosDTO
        );
        
        System.out.println("✅ Calendarios completos obtenidos correctamente");
        return respuesta;
    }

    @Override
    public void asignarCalendario(Integer matricula, Integer idCalendario) {
        System.out.println("📅 Asignando calendario " + idCalendario + " al usuario " + matricula);
        
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matricula));

        Calendario calendarioNuevo = calendarioRepository.findById(idCalendario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + idCalendario));

        List<UsuarioCalendario> asignacionesActuales = usuarioCalendarioRepository.findById_Matricula(matricula);

        // Validar que no haya traslape de fechas
        for (UsuarioCalendario asignacion : asignacionesActuales) {
            Calendario calendarioExistente = asignacion.getCalendario();
            
            if (hayTraslape(calendarioExistente, calendarioNuevo)) {
                throw new RuntimeException(
                    "❌ ERROR: El calendario '" + calendarioNuevo.getNombreCalendario() + 
                    "' (" + calendarioNuevo.getFechaInicio() + " al " + calendarioNuevo.getFechaFin() + ")" +
                    " se traslapa con el calendario '" + calendarioExistente.getNombreCalendario() + 
                    "' (" + calendarioExistente.getFechaInicio() + " al " + calendarioExistente.getFechaFin() + ")"
                );
            }
        }

        UsuarioCalendarioId id = new UsuarioCalendarioId(matricula, idCalendario);
        UsuarioCalendario nuevaAsignacion = new UsuarioCalendario();
        nuevaAsignacion.setId(id);
        nuevaAsignacion.setUsuario(usuario);
        nuevaAsignacion.setCalendario(calendarioNuevo);

        usuarioCalendarioRepository.save(nuevaAsignacion);
        System.out.println("✅ Calendario asignado correctamente");
    }

    private boolean hayTraslape(Calendario cal1, Calendario cal2) {
        LocalDate inicio1 = cal1.getFechaInicio();
        LocalDate fin1 = cal1.getFechaFin();
        LocalDate inicio2 = cal2.getFechaInicio();
        LocalDate fin2 = cal2.getFechaFin();

        return (inicio1.isBefore(fin2) || inicio1.isEqual(fin2)) &&
               (fin1.isAfter(inicio2) || fin1.isEqual(inicio2));
    }

    @Override
    public CalendarioConHorarios obtenerCalendarioActivoEnFecha(Integer matricula, LocalDate fecha) {
        // TODO: Reimplementar para nueva estructura
        throw new UnsupportedOperationException("Método pendiente de actualización a nueva estructura");
    }

    @Override
    public void desasignarCalendario(Integer matricula, Integer idCalendario) {
        System.out.println("📅 Desasignando calendario " + idCalendario + " del usuario " + matricula);
        
        UsuarioCalendarioId id = new UsuarioCalendarioId(matricula, idCalendario);
        
        if (!usuarioCalendarioRepository.existsById(id)) {
            throw new RuntimeException("La asignación no existe");
        }
        
        usuarioCalendarioRepository.deleteById(id);
        System.out.println("✅ Calendario desasignado correctamente");
    }
}
