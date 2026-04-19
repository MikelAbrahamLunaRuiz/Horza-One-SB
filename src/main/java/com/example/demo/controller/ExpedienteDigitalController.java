package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ExpedienteDigitalDTO;
import com.example.demo.service.ExpedienteDigitalService;

@RestController
@RequestMapping("/api/expedientes")
@CrossOrigin(origins = "*")
public class ExpedienteDigitalController {

    @Autowired
    private ExpedienteDigitalService expedienteDigitalService;

    @GetMapping
    public ResponseEntity<List<ExpedienteDigitalDTO>> obtenerTodos() {
        return ResponseEntity.ok(expedienteDigitalService.obtenerTodos());
    }

    @GetMapping("/usuario/{matricula}")
    public ResponseEntity<List<ExpedienteDigitalDTO>> obtenerPorMatricula(@PathVariable Integer matricula) {
        try {
            return ResponseEntity.ok(expedienteDigitalService.obtenerPorMatricula(matricula));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<ExpedienteDigitalDTO> crear(@RequestBody ExpedienteDigitalDTO expedienteDigitalDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(expedienteDigitalService.crear(expedienteDigitalDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{idArchivo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idArchivo) {
        try {
            expedienteDigitalService.eliminar(idArchivo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
