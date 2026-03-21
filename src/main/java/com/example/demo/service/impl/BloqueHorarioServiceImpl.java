package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BloqueHorarioCreateDTO;
import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.model.BloqueHorario;
import com.example.demo.respository.BloqueHorarioRepository;
import com.example.demo.service.BloqueHorarioService;

@Service
public class BloqueHorarioServiceImpl implements BloqueHorarioService {

    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;

    @Override
    public List<BloqueHorarioDTO> obtenerTodos() {
        System.out.println("⏰ Obteniendo todos los bloques de horario");
        List<BloqueHorario> bloques = bloqueHorarioRepository.findAll();
        System.out.println("📦 Total bloques encontrados: " + bloques.size());
        return bloques.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BloqueHorarioDTO> obtenerPlantillas() {
        System.out.println("📋 Obteniendo bloques disponibles como plantilla");
        
        // TODOS los bloques en BLOQUES_HORARIO son reutilizables
        // Ya no hay concepto de "horario de plantillas" con ID 999
        // Simplemente devolvemos todos los bloques activos
        List<BloqueHorario> plantillas = bloqueHorarioRepository.findAll().stream()
                .filter(bloque -> "Activo".equals(bloque.getActivo()))
                .collect(Collectors.toList());
        
        System.out.println("📋 Total bloques disponibles: " + plantillas.size());
        
        return plantillas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public BloqueHorarioDTO obtenerPorId(Integer id) {
        System.out.println("⏰ Obteniendo bloque con ID: " + id);
        BloqueHorario bloque = bloqueHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque de horario no encontrado con ID: " + id));
        return convertirADTO(bloque);
    }

    @Override
    public List<BloqueHorarioDTO> obtenerPorHorario(Integer idHorario) {
        System.out.println("⏰ Obteniendo bloques del horario: " + idHorario);
        // Los bloques ahora están en DIA_HORARIO via tabla intermedia
        // Este método ya no es necesario con la nueva arquitectura
        throw new UnsupportedOperationException("Método obsoleto. Usar GET /api/horarios/{id}/con-bloques para obtener bloques por horario");
    }

    @Override
    public BloqueHorarioDTO crear(BloqueHorarioCreateDTO bloqueDTO) {
        System.out.println("⏰ Creando nuevo bloque independiente");
        System.out.println("📝 Nombre: " + bloqueDTO.getNombreBloque());
        System.out.println("📝 ID Área: " + bloqueDTO.getIdArea());
        System.out.println("📝 Hora Inicio: " + bloqueDTO.getHoraInicio());
        System.out.println("📝 Hora Fin: " + bloqueDTO.getHoraFin());
        
        // Validar que el área exista
        if (bloqueDTO.getIdArea() == null) {
            throw new RuntimeException("El ID del área es obligatorio");
        }
        
        // Crear el bloque
        BloqueHorario nuevoBloque = new BloqueHorario();
        
        // Buscar y asignar área
        com.example.demo.model.Area area = new com.example.demo.model.Area();
        area.setIdArea(bloqueDTO.getIdArea());
        nuevoBloque.setArea(area);
        
        nuevoBloque.setNombreBloque(bloqueDTO.getNombreBloque());
        nuevoBloque.setHoraInicio(java.time.LocalTime.parse(bloqueDTO.getHoraInicio()));
        nuevoBloque.setHoraFin(java.time.LocalTime.parse(bloqueDTO.getHoraFin()));
        nuevoBloque.setActivo("Activo");
        
        // Guardar en base de datos
        BloqueHorario bloqueGuardado = bloqueHorarioRepository.save(nuevoBloque);
        System.out.println("✅ Bloque creado con ID: " + bloqueGuardado.getIdBloque());
        
        return convertirADTO(bloqueGuardado);
    }

    @Override
    public BloqueHorarioDTO actualizar(Integer id, BloqueHorarioCreateDTO bloqueDTO) {
        System.out.println("⏰ Actualizando bloque: " + id);
        System.out.println("📝 Nuevo nombre: " + bloqueDTO.getNombreBloque());
        System.out.println("📝 Nueva área: " + bloqueDTO.getIdArea());
        
        // Buscar el bloque existente
        BloqueHorario bloqueExistente = bloqueHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque de horario no encontrado con ID: " + id));
        
        // Actualizar los campos
        if (bloqueDTO.getIdArea() != null) {
            com.example.demo.model.Area area = new com.example.demo.model.Area();
            area.setIdArea(bloqueDTO.getIdArea());
            bloqueExistente.setArea(area);
        }
        
        if (bloqueDTO.getNombreBloque() != null) {
            bloqueExistente.setNombreBloque(bloqueDTO.getNombreBloque());
        }
        
        if (bloqueDTO.getHoraInicio() != null) {
            bloqueExistente.setHoraInicio(java.time.LocalTime.parse(bloqueDTO.getHoraInicio()));
        }
        
        if (bloqueDTO.getHoraFin() != null) {
            bloqueExistente.setHoraFin(java.time.LocalTime.parse(bloqueDTO.getHoraFin()));
        }
        
        // Guardar cambios
        BloqueHorario bloqueActualizado = bloqueHorarioRepository.save(bloqueExistente);
        System.out.println("✅ Bloque actualizado correctamente");
        
        return convertirADTO(bloqueActualizado);
    }

    @Autowired
    private com.example.demo.respository.BloqueDiaAsignacionRepository bloqueDiaAsignacionRepository;
    
    @Override
    public void eliminar(Integer id) {
        System.out.println("⏰ Eliminando bloque con ID: " + id);
        
        // Verificar que el bloque exista
        if (!bloqueHorarioRepository.existsById(id)) {
            throw new RuntimeException("Bloque de horario no encontrado con ID: " + id);
        }
        
        // Verificar si el bloque está asignado a algún día de horario
        List<com.example.demo.model.BloqueDiaAsignacion> asignaciones = 
            bloqueDiaAsignacionRepository.findAll().stream()
                .filter(asig -> asig.getBloqueHorario() != null && asig.getBloqueHorario().getIdBloque().equals(id))
                .collect(java.util.stream.Collectors.toList());
        
        if (!asignaciones.isEmpty()) {
            System.out.println("❌ No se puede eliminar: el bloque está siendo usado en " + asignaciones.size() + " día(s)");
            throw new RuntimeException("No se puede eliminar el bloque porque está siendo usado en un horario. Primero debe desasignarlo de todos los días.");
        }
        
        // Eliminar el bloque
        bloqueHorarioRepository.deleteById(id);
        System.out.println("✅ Bloque eliminado correctamente");
    }

    /**
     * Convierte BloqueHorario entity a DTO
     * Los bloques son independientes (sin vínculo directo a día/horario)
     */
    private BloqueHorarioDTO convertirADTO(BloqueHorario bloque) {
        BloqueHorarioDTO dto = new BloqueHorarioDTO();
        dto.setIdBloque(bloque.getIdBloque());
        dto.setIdHorario(null); // Bloques son independientes
        dto.setIdArea(bloque.getArea() != null ? bloque.getArea().getIdArea() : null);
        dto.setNombreBloque(bloque.getNombreBloque());
        dto.setHoraInicio(bloque.getHoraInicio());
        dto.setHoraFin(bloque.getHoraFin());
        return dto;
    }
}
