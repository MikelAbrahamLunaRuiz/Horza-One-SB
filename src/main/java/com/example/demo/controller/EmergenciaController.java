package com.example.demo.controller;

import com.example.demo.dto.UsuarioEmergenciaDTO;
import com.example.demo.service.EmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emergencias")
@CrossOrigin(origins = "*")
public class EmergenciaController {

    @Autowired
    private EmergenciaService emergenciaService;

    @GetMapping("/usuarios-dentro")
    public ResponseEntity<List<UsuarioEmergenciaDTO>> obtenerUsuariosDentro() {
        return ResponseEntity.ok(emergenciaService.obtenerUsuariosDentro());
    }

    @GetMapping("/exportar-excel")
    public ResponseEntity<byte[]> exportarUsuariosDentroExcel() {
        try {
            byte[] excelBytes = emergenciaService.exportarUsuariosDentroExcel();
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "usuarios_dentro_emergencia.xlsx");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
