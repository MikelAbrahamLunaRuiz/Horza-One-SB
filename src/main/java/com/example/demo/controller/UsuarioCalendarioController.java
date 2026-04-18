package com.example.demo.controller;

import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.UsuarioCalendarioCompleto;
import com.example.demo.service.UsuarioCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/usuarios-calendario")
@CrossOrigin(origins = "*")
public class UsuarioCalendarioController {

    @Autowired
    private UsuarioCalendarioService usuarioCalendarioService;

    /**
     * Obtiene todos los calendarios asignados a un usuario con sus horarios y bloques
     */
    @GetMapping("/usuario/{matricula}/completo")
    public ResponseEntity<UsuarioCalendarioCompleto> obtenerCalendariosCompletos(@PathVariable Integer matricula) {
        System.out.println("📅 GET /api/usuarios-calendario/usuario/" + matricula + "/completo");
        try {
            return ResponseEntity.ok(usuarioCalendarioService.obtenerCalendariosCompletos(matricula));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Asigna un calendario a un usuario (valida que no haya traslape de fechas)
     */
    @PostMapping("/usuario/{matricula}/calendario/{idCalendario}")
    public ResponseEntity<String> asignarCalendario(
            @PathVariable Integer matricula, 
            @PathVariable Integer idCalendario) {
        System.out.println("📅 POST /api/usuarios-calendario/usuario/" + matricula + "/calendario/" + idCalendario);
        try {
            usuarioCalendarioService.asignarCalendario(matricula, idCalendario);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("✅ Calendario asignado correctamente al usuario");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }

    /**
     * Obtiene el calendario activo de un usuario en una fecha específica
     */
    @GetMapping("/usuario/{matricula}/calendario-activo")
    public ResponseEntity<?> obtenerCalendarioActivoEnFecha(
            @PathVariable Integer matricula,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        System.out.println("📅 GET /api/usuarios-calendario/usuario/" + matricula + "/calendario-activo?fecha=" + fecha);
        try {
            CalendarioConHorarios calendario = usuarioCalendarioService.obtenerCalendarioActivoEnFecha(matricula, fecha);
            return ResponseEntity.ok(calendario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    /**
     * Desasigna un calendario de un usuario
     */
    @DeleteMapping("/usuario/{matricula}/calendario/{idCalendario}")
    public ResponseEntity<String> desasignarCalendario(
            @PathVariable Integer matricula, 
            @PathVariable Integer idCalendario) {
        System.out.println("📅 DELETE /api/usuarios-calendario/usuario/" + matricula + "/calendario/" + idCalendario);
        try {
            usuarioCalendarioService.desasignarCalendario(matricula, idCalendario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }
}
