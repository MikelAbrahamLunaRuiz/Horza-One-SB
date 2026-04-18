package com.example.demo.controller;

import com.example.demo.dto.BloqueHorarioCreateDTO;
import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.service.BloqueHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bloques")
@CrossOrigin(origins = "*")
public class BloqueHorarioController {

    @Autowired
    private BloqueHorarioService bloqueHorarioService;

    /**
     * GET /api/bloques
     * Obtener todos los bloques de horario
     */
    @GetMapping
    public ResponseEntity<List<BloqueHorarioDTO>> obtenerTodos() {
        System.out.println("⏰ GET /api/bloques - Listar todos los bloques");
        return ResponseEntity.ok(bloqueHorarioService.obtenerTodos());
    }

    /**
     * GET /api/bloques/{id}
     * Obtener bloque por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BloqueHorarioDTO> obtenerPorId(@PathVariable Integer id) {
        System.out.println("⏰ GET /api/bloques/" + id);
        return ResponseEntity.ok(bloqueHorarioService.obtenerPorId(id));
    }

    /**
     * GET /api/bloques/plantillas
     * Obtener solo bloques PLANTILLA (templates) para crear nuevos horarios
     * Estos bloques pertenecen al horario especial ID 999 "PLANTILLAS"
     */
    @GetMapping("/plantillas")
    public ResponseEntity<List<BloqueHorarioDTO>> obtenerPlantillas() {
        System.out.println("📋 GET /api/bloques/plantillas - Obtener bloques plantilla");
        return ResponseEntity.ok(bloqueHorarioService.obtenerPlantillas());
    }

    /**
     * GET /api/bloques/horario/{idHorario}
     * Obtener bloques por horario
     */
    @GetMapping("/horario/{idHorario}")
    public ResponseEntity<List<BloqueHorarioDTO>> obtenerPorHorario(@PathVariable Integer idHorario) {
        System.out.println("⏰ GET /api/bloques/horario/" + idHorario);
        return ResponseEntity.ok(bloqueHorarioService.obtenerPorHorario(idHorario));
    }

    /**
     * POST /api/bloques
     * Crear nuevo bloque de horario (con días de la semana)
     */
    @PostMapping
    public ResponseEntity<BloqueHorarioDTO> crear(@RequestBody BloqueHorarioCreateDTO bloqueDTO) {
        System.out.println("⏰ POST /api/bloques - Crear nuevo bloque");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bloqueHorarioService.crear(bloqueDTO));
    }

    /**
     * PUT /api/bloques/{id}
     * Actualizar bloque existente (con días de la semana)
     */
    @PutMapping("/{id}")
    public ResponseEntity<BloqueHorarioDTO> actualizar(@PathVariable Integer id, 
                                                       @RequestBody BloqueHorarioCreateDTO bloqueDTO) {
        System.out.println("⏰ PUT /api/bloques/" + id + " - Actualizar bloque");
        return ResponseEntity.ok(bloqueHorarioService.actualizar(id, bloqueDTO));
    }

    /**
     * DELETE /api/bloques/{id}
     * Eliminar bloque
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        System.out.println("⏰ DELETE /api/bloques/" + id);
        bloqueHorarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
