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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccionAdminDTO;
import com.example.demo.dto.PermisoPersonalizadoDTO;
import com.example.demo.service.AdminPermisoService;

@RestController
@RequestMapping("/api/admin-permisos")
@CrossOrigin(origins = "*")
public class AdminPermisoController {

    @Autowired
    private AdminPermisoService adminPermisoService;

    @GetMapping("/acciones")
    public ResponseEntity<List<AccionAdminDTO>> obtenerAccionesAdmin() {
        return ResponseEntity.ok(adminPermisoService.obtenerAccionesAdmin());
    }

    @PostMapping("/acciones")
    public ResponseEntity<AccionAdminDTO> crearAccionAdmin(@RequestBody AccionAdminDTO accionAdminDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminPermisoService.crearAccionAdmin(accionAdminDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuarios/{matricula}/acciones")
    public ResponseEntity<List<PermisoPersonalizadoDTO>> obtenerPermisosPorMatricula(@PathVariable Integer matricula) {
        try {
            return ResponseEntity.ok(adminPermisoService.obtenerPermisosPorMatricula(matricula));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/usuarios/{matricula}/acciones/{idAccion}")
    public ResponseEntity<PermisoPersonalizadoDTO> asignarPermiso(
            @PathVariable Integer matricula,
            @PathVariable Integer idAccion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminPermisoService.asignarPermiso(matricula, idAccion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/usuarios/{matricula}/acciones/{idAccion}")
    public ResponseEntity<Void> quitarPermiso(
            @PathVariable Integer matricula,
            @PathVariable Integer idAccion) {
        try {
            adminPermisoService.quitarPermiso(matricula, idAccion);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuarios/{matricula}/acciones/{nombreAccion}/tiene")
    public ResponseEntity<Boolean> usuarioTieneAccion(
            @PathVariable Integer matricula,
            @PathVariable String nombreAccion) {
        return ResponseEntity.ok(adminPermisoService.usuarioTieneAccion(matricula, nombreAccion));
    }

    @GetMapping("/usuarios/{matricula}/tiene")
    public ResponseEntity<Boolean> usuarioTieneAccionPorQuery(
            @PathVariable Integer matricula,
            @RequestParam("accion") String accion) {
        return ResponseEntity.ok(adminPermisoService.usuarioTieneAccion(matricula, accion));
    }
}
