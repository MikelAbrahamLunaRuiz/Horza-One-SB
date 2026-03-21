package com.example.demo.service.impl;

import com.example.demo.dto.DispositivoDTO;
import com.example.demo.model.Area;
import com.example.demo.model.Dispositivo;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.DispositivoRepository;
import com.example.demo.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispositivoServiceImpl implements DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<DispositivoDTO> obtenerTodos() {
        return dispositivoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public DispositivoDTO obtenerPorId(Integer id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        return convertirADTO(dispositivo);
    }

    @Override
    public List<DispositivoDTO> obtenerPorArea(Integer idArea) {
        Area area = areaRepository.findById(idArea)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        return dispositivoRepository.findByArea(area).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public DispositivoDTO crear(DispositivoDTO dispositivoDTO) {
        // Validar que el ID no esté ocupado
        if (dispositivoDTO.getIdDispositivo() != null && 
            dispositivoRepository.existsById(dispositivoDTO.getIdDispositivo())) {
            throw new RuntimeException("Ya existe un dispositivo con el ID: " + dispositivoDTO.getIdDispositivo());
        }
        
        Dispositivo dispositivo = convertirAEntidad(dispositivoDTO);
        Dispositivo dispositivoGuardado = dispositivoRepository.save(dispositivo);
        return convertirADTO(dispositivoGuardado);
    }

    @Override
    public DispositivoDTO actualizar(Integer id, DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        
        Area area = areaRepository.findById(dispositivoDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        dispositivo.setArea(area);
        dispositivo.setNombreDispositivo(dispositivoDTO.getNombreDispositivo());
        dispositivo.setDescripcionDispositivo(dispositivoDTO.getDescripcionDispositivo());
        dispositivo.setActivoDispositivo(dispositivoDTO.getActivoDispositivo());
        
        Dispositivo dispositivoActualizado = dispositivoRepository.save(dispositivo);
        return convertirADTO(dispositivoActualizado);
    }

    @Override
    public List<DispositivoDTO> obtenerDispositivosInactivos() {
        return dispositivoRepository.findAll().stream()
                .filter(d -> "Inactivo".equals(d.getActivoDispositivo()))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public DispositivoDTO cambiarEstado(Integer id, String nuevoEstado) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        
        if (!"Activo".equals(nuevoEstado) && !"Inactivo".equals(nuevoEstado)) {
            throw new RuntimeException("Estado inválido. Debe ser 'Activo' o 'Inactivo'");
        }
        
        dispositivo.setActivoDispositivo(nuevoEstado);
        Dispositivo dispositivoActualizado = dispositivoRepository.save(dispositivo);
        return convertirADTO(dispositivoActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        dispositivoRepository.deleteById(id);
    }

    private DispositivoDTO convertirADTO(Dispositivo dispositivo) {
        return new DispositivoDTO(
                dispositivo.getIdDispositivo(),
                dispositivo.getArea().getIdArea(),
                dispositivo.getNombreDispositivo(),
                dispositivo.getDescripcionDispositivo(),
                dispositivo.getActivoDispositivo(),
                dispositivo.getArea().getNombreArea(), // nombreArea del JOIN
                dispositivo.getArea().getUbicacion() // ubicacionArea del JOIN
        );
    }

    private Dispositivo convertirAEntidad(DispositivoDTO dto) {
        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        // Usar constructor con ID - el ID debe proporcionarse en el JSON
        return new Dispositivo(
                dto.getIdDispositivo(),
                area,
                dto.getNombreDispositivo(),
                dto.getDescripcionDispositivo(),
                dto.getActivoDispositivo()
        );
    }
}
