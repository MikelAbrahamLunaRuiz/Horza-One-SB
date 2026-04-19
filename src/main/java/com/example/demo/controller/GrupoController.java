package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GrupoDTO;
import com.example.demo.dto.GrupoDetalleDTO;
import com.example.demo.dto.GrupoIntegrantesRequest;
import com.example.demo.service.GrupoService;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "*")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> obtenerTodos() {
        return ResponseEntity.ok(grupoService.obtenerTodos());
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<GrupoDTO> obtenerPorId(@PathVariable Integer idGrupo) {
        try {
            return ResponseEntity.ok(grupoService.obtenerPorId(idGrupo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> crear(@RequestBody GrupoDTO grupoDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.crear(grupoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<GrupoDTO> actualizar(@PathVariable Integer idGrupo, @RequestBody GrupoDTO grupoDTO) {
        try {
            return ResponseEntity.ok(grupoService.actualizar(idGrupo, grupoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{idGrupo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idGrupo) {
        try {
            grupoService.eliminar(idGrupo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/lider/{matriculaLider}")
    public ResponseEntity<List<GrupoDTO>> obtenerPorLider(@PathVariable Integer matriculaLider) {
        try {
            return ResponseEntity.ok(grupoService.obtenerPorLider(matriculaLider));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuario/{matriculaUsuario}/detalle")
    public ResponseEntity<List<GrupoDetalleDTO>> obtenerDetallePorUsuario(@PathVariable Integer matriculaUsuario) {
        try {
            return ResponseEntity.ok(grupoService.obtenerDetallePorUsuario(matriculaUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/detalle")
    public ResponseEntity<List<GrupoDetalleDTO>> obtenerDetalleCompleto() {
        return ResponseEntity.ok(grupoService.obtenerDetalleCompleto());
    }

    @GetMapping("/{idGrupo}/detalle")
    public ResponseEntity<GrupoDetalleDTO> obtenerDetallePorGrupo(@PathVariable Integer idGrupo) {
        try {
            return ResponseEntity.ok(grupoService.obtenerDetallePorGrupo(idGrupo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{idGrupo}/integrantes")
    public ResponseEntity<GrupoDetalleDTO> actualizarIntegrantes(
            @PathVariable Integer idGrupo,
            @RequestBody GrupoIntegrantesRequest request
    ) {
        try {
            List<Integer> matriculas = request != null ? request.getMatriculas() : Collections.emptyList();
            return ResponseEntity.ok(grupoService.actualizarIntegrantes(idGrupo, matriculas));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
