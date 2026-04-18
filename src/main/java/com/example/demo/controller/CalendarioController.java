package com.example.demo.controller;

import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.CalendarioCreateDTO;
import com.example.demo.dto.CalendarioDTO;
import com.example.demo.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendarios")
@CrossOrigin(origins = "*")
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    /**
     * GET /api/calendarios
     * Obtener todos los calendarios
     */
    @GetMapping
    public ResponseEntity<List<CalendarioDTO>> obtenerTodos() {
        System.out.println("📅 GET /api/calendarios - Listar todos los calendarios");
        return ResponseEntity.ok(calendarioService.obtenerTodos());
    }

    /**
     * GET /api/calendarios/{id}
     * Obtener calendario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalendarioDTO> obtenerPorId(@PathVariable Integer id) {
        System.out.println("📅 GET /api/calendarios/" + id);
        return ResponseEntity.ok(calendarioService.obtenerPorId(id));
    }

    /**
     * GET /api/calendarios/{id}/con-horarios
     * Obtener calendario con todos sus horarios
     */
    @GetMapping("/{id}/con-horarios")
    public ResponseEntity<CalendarioConHorarios> obtenerConHorarios(@PathVariable Integer id) {
        System.out.println("📅 GET /api/calendarios/" + id + "/con-horarios");
        return ResponseEntity.ok(calendarioService.obtenerCalendarioConHorarios(id));
    }

    /**
     * POST /api/calendarios
     * Crear nuevo calendario
     */
    @PostMapping
    public ResponseEntity<CalendarioDTO> crear(@RequestBody CalendarioCreateDTO calendarioDTO) {
        System.out.println("📅 POST /api/calendarios - Crear nuevo calendario");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calendarioService.crear(calendarioDTO));
    }

    /**
     * PUT /api/calendarios/{id}
     * Actualizar calendario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalendarioDTO> actualizar(@PathVariable Integer id, 
                                                    @RequestBody CalendarioCreateDTO calendarioDTO) {
        System.out.println("📅 PUT /api/calendarios/" + id + " - Actualizar calendario");
        return ResponseEntity.ok(calendarioService.actualizar(id, calendarioDTO));
    }

    /**
     * PATCH /api/calendarios/{id}/estado
     * Cambiar estado del calendario (Activo/Inactivo)
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CalendarioDTO> cambiarEstado(@PathVariable Integer id,
                                                       @RequestBody Map<String, String> payload) {
        System.out.println("📅 PATCH /api/calendarios/" + id + "/estado");
        String nuevoEstado = payload.get("estado");
        return ResponseEntity.ok(calendarioService.cambiarEstado(id, nuevoEstado));
    }

    /**
     * DELETE /api/calendarios/{id}
     * Eliminar calendario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        System.out.println("📅 DELETE /api/calendarios/" + id);
        calendarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
