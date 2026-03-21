package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BitacoraDTO;
import com.example.demo.model.Bitacora;
import com.example.demo.model.Usuario;
import com.example.demo.respository.BitacoraRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.BitacoraService;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<BitacoraDTO> obtenerTodas() {
        return bitacoraRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public BitacoraDTO obtenerPorId(Integer id) {
        Bitacora bitacora = bitacoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        return convertirADTO(bitacora);
    }

    @Override
    public BitacoraDTO crear(BitacoraDTO bitacoraDTO) {
        Bitacora bitacora = convertirAEntidad(bitacoraDTO);
        Bitacora bitacoraGuardada = bitacoraRepository.save(bitacora);
        return convertirADTO(bitacoraGuardada);
    }

    @Override
    public BitacoraDTO actualizar(Integer id, BitacoraDTO bitacoraDTO) {
        Bitacora bitacora = bitacoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        
        Usuario usuario = usuarioRepository.findById(bitacoraDTO.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        bitacora.setUsuario(usuario);
        bitacora.setNumEntradas(bitacoraDTO.getNumEntradas());
        bitacora.setNumInasistencias(bitacoraDTO.getNumInasistencias());
        bitacora.setNumRetardos(bitacoraDTO.getNumRetardos());
        bitacora.setNumEntradasAnticipadas(bitacoraDTO.getNumEntradasAnticipadas());
        
        Bitacora bitacoraActualizada = bitacoraRepository.save(bitacora);
        return convertirADTO(bitacoraActualizada);
    }

    @Override
    public void eliminar(Integer id) {
        bitacoraRepository.deleteById(id);
    }

    private BitacoraDTO convertirADTO(Bitacora bitacora) {
        return new BitacoraDTO(
                bitacora.getIdBitacora(),
                bitacora.getUsuario().getMatricula(),
                bitacora.getNumEntradas(),
                bitacora.getNumInasistencias(),
                bitacora.getNumRetardos(),
                bitacora.getNumEntradasAnticipadas()
        );
    }

    private Bitacora convertirAEntidad(BitacoraDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return new Bitacora(
                dto.getIdBitacora(),
                usuario,
                dto.getNumEntradas(),
                dto.getNumInasistencias(),
                dto.getNumRetardos(),
                dto.getNumEntradasAnticipadas()
        );
    }
}
