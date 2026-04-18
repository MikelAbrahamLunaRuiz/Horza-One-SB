package com.example.demo.service.impl;

import com.example.demo.dto.RolDTO;
import com.example.demo.model.Rol;
import com.example.demo.respository.RolRepository;
import com.example.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<RolDTO> obtenerTodos() {
        return rolRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return convertirADTO(rol);
    }

    @Override
    public RolDTO crear(RolDTO rolDTO) {
        Rol rol = convertirAEntidad(rolDTO);
        Rol rolGuardado = rolRepository.save(rol);
        return convertirADTO(rolGuardado);
    }

    @Override
    public RolDTO actualizar(Integer id, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setTipoPermiso(rolDTO.getTipoPermiso());
        Rol rolActualizado = rolRepository.save(rol);
        return convertirADTO(rolActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }

    private RolDTO convertirADTO(Rol rol) {
        return new RolDTO(rol.getIdRol(), rol.getNombreRol(), rol.getTipoPermiso());
    }

    private Rol convertirAEntidad(RolDTO dto) {
        return new Rol(dto.getIdRol(), dto.getNombreRol(), dto.getTipoPermiso());
    }
}
