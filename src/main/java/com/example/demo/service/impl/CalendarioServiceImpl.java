package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.CalendarioCreateDTO;
import com.example.demo.dto.CalendarioDTO;
import com.example.demo.dto.HorarioConBloques;
import com.example.demo.model.BloqueDiaAsignacion;
import com.example.demo.model.BloqueHorario;
import com.example.demo.model.Calendario;
import com.example.demo.model.DiaHorario;
import com.example.demo.model.DiaSemana;
import com.example.demo.model.Horario;
import com.example.demo.respository.BloqueDiaAsignacionRepository;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.respository.DiaHorarioRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.service.CalendarioService;

@Service
public class CalendarioServiceImpl implements CalendarioService {

    @Autowired
    private CalendarioRepository calendarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private DiaHorarioRepository diaHorarioRepository;
    
    @Autowired
    private BloqueDiaAsignacionRepository bloqueDiaAsignacionRepository;

    @Override
    public List<CalendarioDTO> obtenerTodos() {
        System.out.println("📅 Obteniendo todos los calendarios");
        return calendarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CalendarioDTO obtenerPorId(Integer id) {
        System.out.println("📅 Obteniendo calendario con ID: " + id);
        Calendario calendario = calendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + id));
        return convertirADTO(calendario);
    }

    @Override
    public CalendarioDTO crear(CalendarioCreateDTO calendarioDTO) {
        System.out.println("📅 Creando nuevo calendario: " + calendarioDTO.getNombreCalendario());
        
        Calendario calendario = new Calendario();
        calendario.setNombreCalendario(calendarioDTO.getNombreCalendario());
        calendario.setFechaInicio(LocalDate.parse(calendarioDTO.getFechaInicio()));
        calendario.setFechaFin(LocalDate.parse(calendarioDTO.getFechaFin()));
        calendario.setDescripcion(calendarioDTO.getDescripcion());
        calendario.setActivoCalendario(calendarioDTO.getActivoCalendario());
        
        // Vincular el Horario si se proporcionó
        if (calendarioDTO.getIdHorario() != null) {
            Horario horario = horarioRepository.findById(calendarioDTO.getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + calendarioDTO.getIdHorario()));
            calendario.setHorario(horario);
            System.out.println("   🕐 Horario vinculado: " + horario.getNombreHorario());
        }
        
        Calendario calendarioGuardado = calendarioRepository.save(calendario);
        System.out.println("✅ Calendario creado con ID: " + calendarioGuardado.getIdCalendario());
        return convertirADTO(calendarioGuardado);
    }

    @Override
    public CalendarioDTO actualizar(Integer id, CalendarioCreateDTO calendarioDTO) {
        System.out.println("📅 Actualizando calendario con ID: " + id);
        
        Calendario calendario = calendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + id));
        
        calendario.setNombreCalendario(calendarioDTO.getNombreCalendario());
        calendario.setFechaInicio(LocalDate.parse(calendarioDTO.getFechaInicio()));
        calendario.setFechaFin(LocalDate.parse(calendarioDTO.getFechaFin()));
        calendario.setDescripcion(calendarioDTO.getDescripcion());
        calendario.setActivoCalendario(calendarioDTO.getActivoCalendario());
        
        // Actualizar el Horario vinculado
        if (calendarioDTO.getIdHorario() != null) {
            Horario horario = horarioRepository.findById(calendarioDTO.getIdHorario())
                    .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + calendarioDTO.getIdHorario()));
            calendario.setHorario(horario);
            System.out.println("   🕐 Horario actualizado: " + horario.getNombreHorario());
        } else {
            calendario.setHorario(null);
            System.out.println("   ⚠️ Horario desvinculado");
        }
        
        Calendario calendarioActualizado = calendarioRepository.save(calendario);
        System.out.println("✅ Calendario actualizado: " + calendarioActualizado.getNombreCalendario());
        return convertirADTO(calendarioActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        System.out.println("📅 Eliminando calendario con ID: " + id);
        if (!calendarioRepository.existsById(id)) {
            throw new RuntimeException("Calendario no encontrado con ID: " + id);
        }
        calendarioRepository.deleteById(id);
        System.out.println("✅ Calendario eliminado correctamente");
    }

    @Override
    public CalendarioDTO cambiarEstado(Integer id, String nuevoEstado) {
        System.out.println("📅 Cambiando estado del calendario " + id + " a: " + nuevoEstado);
        
        Calendario calendario = calendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + id));
        
        calendario.setActivoCalendario(nuevoEstado);
        Calendario calendarioActualizado = calendarioRepository.save(calendario);
        
        System.out.println("✅ Estado actualizado correctamente");
        return convertirADTO(calendarioActualizado);
    }

    @Override
    public CalendarioConHorarios obtenerCalendarioConHorarios(Integer idCalendario) {
        System.out.println("📅 Obteniendo calendario con horarios para ID: " + idCalendario);
        
        // 1. Obtener el calendario
        Calendario calendario = calendarioRepository.findById(idCalendario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + idCalendario));
        
        // 2. Obtener el horario del calendario
        Horario horario = calendario.getHorario();
        
        if (horario == null) {
            System.out.println("⚠️ Calendario sin horario asignado");
            // Devolver calendario sin horarios
            return new CalendarioConHorarios(
                    calendario.getIdCalendario(),
                    calendario.getNombreCalendario(),
                    calendario.getFechaInicio(),
                    calendario.getFechaFin(),
                    calendario.getDescripcion(),
                    calendario.getActivoCalendario(),
                    new ArrayList<>()
            );
        }
        
        System.out.println("🕐 Horario encontrado: " + horario.getNombreHorario());
        
        // 3. Construir HorarioConBloques
        List<HorarioConBloques> horariosConBloques = new ArrayList<>();
        
        // 3.1 Obtener los días del horario
        List<DiaHorario> diasHorario = diaHorarioRepository
                .findByHorario_IdHorarioOrderByDiaSemana_OrdenDia(horario.getIdHorario());
        System.out.println("📅 " + diasHorario.size() + " días encontrados");
        
        // 3.2 Construir DiaConBloques para cada día
        List<HorarioConBloques.DiaConBloques> dias = new ArrayList<>();
        
        for (DiaHorario diaHorario : diasHorario) {
            DiaSemana diaSemana = diaHorario.getDiaSemana();
            
            // 3.3 Obtener bloques del día
            List<BloqueDiaAsignacion> asignacionesBloques = bloqueDiaAsignacionRepository
                    .findByDiaHorario_IdDiaHorario(diaHorario.getIdDiaHorario());
            
            // 3.4 Convertir bloques a DTOs
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
            
            // 3.5 Crear DiaConBloques
            HorarioConBloques.DiaConBloques diaDTO = new HorarioConBloques.DiaConBloques(
                    diaSemana.getIdDia(),
                    diaSemana.getNombreDia(),
                    diaSemana.getOrdenDia(),
                    bloquesDTO
            );
            dias.add(diaDTO);
            
            System.out.println("   ✅ " + diaSemana.getNombreDia() + ": " + bloquesDTO.size() + " bloques");
        }
        
        // 3.6 Crear HorarioConBloques
        HorarioConBloques horarioDTO = new HorarioConBloques(
                horario.getIdHorario(),
                horario.getNombreHorario(),
                horario.getDescripcion(),
                horario.getActivoHorario(),
                dias
        );
        horariosConBloques.add(horarioDTO);
        
        // 4. Crear y devolver CalendarioConHorarios
        CalendarioConHorarios resultado = new CalendarioConHorarios(
                calendario.getIdCalendario(),
                calendario.getNombreCalendario(),
                calendario.getFechaInicio(),
                calendario.getFechaFin(),
                calendario.getDescripcion(),
                calendario.getActivoCalendario(),
                horariosConBloques
        );
        
        System.out.println("✅ Calendario con horarios construido exitosamente");
        return resultado;
    }

    private CalendarioDTO convertirADTO(Calendario calendario) {
        Integer idHorario = calendario.getHorario() != null ? calendario.getHorario().getIdHorario() : null;
        return new CalendarioDTO(
                calendario.getIdCalendario(),
                idHorario,
                calendario.getNombreCalendario(),
                calendario.getFechaInicio(),
                calendario.getFechaFin(),
                calendario.getDescripcion(),
                calendario.getActivoCalendario()
        );
    }
}
