package com.example.demo.service.impl;

import com.example.demo.dto.AreaDTO;
import com.example.demo.model.Area;
import com.example.demo.respository.AreaRepository;
import com.example.demo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<AreaDTO> obtenerTodas() {
        return areaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public AreaDTO obtenerPorId(Integer id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        return convertirADTO(area);
    }

    @Override
    public AreaDTO crear(AreaDTO areaDTO) {
        Area area = convertirAEntidad(areaDTO);
        Area areaGuardada = areaRepository.save(area);
        return convertirADTO(areaGuardada);
    }

    @Override
    public AreaDTO actualizar(Integer id, AreaDTO areaDTO) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        area.setNombreArea(areaDTO.getNombreArea());
        area.setDescripcionArea(areaDTO.getDescripcionArea());
        area.setActivoArea(areaDTO.getActivoArea());
        area.setUbicacion(areaDTO.getUbicacion());
        Area areaActualizada = areaRepository.save(area);
        return convertirADTO(areaActualizada);
    }

    @Override
    public void eliminar(Integer id) {
        areaRepository.deleteById(id);
    }

    private AreaDTO convertirADTO(Area area) {
        return new AreaDTO(
                area.getIdArea(),
                area.getNombreArea(),
                area.getDescripcionArea(),
                area.getActivoArea(),
                area.getUbicacion()
        );
    }

    private Area convertirAEntidad(AreaDTO dto) {
        return new Area(
                dto.getIdArea(),
                dto.getNombreArea(),
                dto.getDescripcionArea(),
                dto.getActivoArea(),
                dto.getUbicacion()
        );
    }
}
