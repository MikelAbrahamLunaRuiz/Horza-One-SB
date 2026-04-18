package com.example.demo.controller;

import com.example.demo.dto.HorarioConBloques;
import com.example.demo.dto.HorarioCreateDTO;
import com.example.demo.dto.HorarioDTO;
import com.example.demo.dto.HorarioSemanalCreateDTO;
import com.example.demo.dto.HorarioSemanalResponseDTO;
import com.example.demo.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    /**
     * GET /api/horarios
     * Obtener todos los horarios
     */
    @GetMapping
    public ResponseEntity<List<HorarioDTO>> obtenerTodos() {
        System.out.println("🕐 GET /api/horarios - Listar todos los horarios");
        return ResponseEntity.ok(horarioService.obtenerTodos());
    }
    
    /**
     * GET /api/horarios/con-bloques
     * Obtener todos los horarios con sus 7 días y bloques
     */
    @GetMapping("/con-bloques")
    public ResponseEntity<List<HorarioConBloques>> obtenerTodosConBloques() {
        System.out.println("🕐 GET /api/horarios/con-bloques - Listar todos con estructura completa");
        return ResponseEntity.ok(horarioService.obtenerTodosConBloques());
    }

    /**
     * GET /api/horarios/{id}
     * Obtener horario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> obtenerPorId(@PathVariable Integer id) {
        System.out.println("🕐 GET /api/horarios/" + id);
        return ResponseEntity.ok(horarioService.obtenerPorId(id));
    }

    /**
     * GET /api/horarios/calendario/{idCalendario}
     * Obtener horarios por calendario
     */
    @GetMapping("/calendario/{idCalendario}")
    public ResponseEntity<List<HorarioDTO>> obtenerPorCalendario(@PathVariable Integer idCalendario) {
        System.out.println("🕐 GET /api/horarios/calendario/" + idCalendario);
        return ResponseEntity.ok(horarioService.obtenerPorCalendario(idCalendario));
    }

    /**
     * GET /api/horarios/{id}/con-bloques
     * Obtener horario con todos sus bloques
     */
    @GetMapping("/{id}/con-bloques")
    public ResponseEntity<HorarioConBloques> obtenerConBloques(@PathVariable Integer id) {
        System.out.println("🕐 GET /api/horarios/" + id + "/con-bloques");
        return ResponseEntity.ok(horarioService.obtenerHorarioConBloques(id));
    }

    /**
     * POST /api/horarios
     * Crear nuevo horario
     */
    @PostMapping
    public ResponseEntity<HorarioDTO> crear(@RequestBody HorarioCreateDTO horarioDTO) {
        System.out.println("🕐 POST /api/horarios - Crear nuevo horario");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.crear(horarioDTO));
    }

    /**
     * PUT /api/horarios/{id}
     * Actualizar horario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> actualizar(@PathVariable Integer id, 
                                                 @RequestBody HorarioCreateDTO horarioDTO) {
        System.out.println("🕐 PUT /api/horarios/" + id + " - Actualizar horario");
        return ResponseEntity.ok(horarioService.actualizar(id, horarioDTO));
    }

    /**
     * PATCH /api/horarios/{id}/estado
     * Cambiar estado del horario (Activo/Inactivo)
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<HorarioDTO> cambiarEstado(@PathVariable Integer id,
                                                    @RequestBody Map<String, String> payload) {
        System.out.println("🕐 PATCH /api/horarios/" + id + "/estado");
        String nuevoEstado = payload.get("estado");
        return ResponseEntity.ok(horarioService.cambiarEstado(id, nuevoEstado));
    }

    /**
     * DELETE /api/horarios/{id}
     * Eliminar horario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        System.out.println("🕐 DELETE /api/horarios/" + id);
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/horarios/semanal
     * Crear horario semanal completo (7 días)
     * Recibe un HorarioSemanalCreateDTO con:
     * - nombreHorario
     * - descripcion
     * - activoHorario
     * - idCalendario
     * - bloquesPorDia (Map<String, List<Integer>>)
     * 
     * Crea 7 registros HORARIO (uno por día) y asigna los bloques correspondientes
     */
    @PostMapping("/semanal")
    public ResponseEntity<HorarioSemanalResponseDTO> crearHorarioSemanal(
            @RequestBody HorarioSemanalCreateDTO dto) {
        System.out.println("🕐 POST /api/horarios/semanal - Crear horario semanal completo");
        System.out.println("   - Nombre: " + dto.getNombreHorario());
        System.out.println("   - Bloques por día: " + dto.getBloquesPorDia());
        
        HorarioSemanalResponseDTO response = horarioService.crearHorarioSemanal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * PUT /api/horarios/{id}/semanal
     * Actualizar horario semanal completo
     */
    @PutMapping("/{id}/semanal")
    public ResponseEntity<HorarioSemanalResponseDTO> actualizarHorarioSemanal(
            @PathVariable Integer id,
            @RequestBody HorarioSemanalCreateDTO dto) {
        System.out.println("🕐 PUT /api/horarios/" + id + "/semanal - Actualizar horario semanal");
        System.out.println("   - Nombre: " + dto.getNombreHorario());
        System.out.println("   - Bloques por día: " + dto.getBloquesPorDia());
        
        HorarioSemanalResponseDTO response = horarioService.actualizarHorarioSemanal(id, dto);
        return ResponseEntity.ok(response);
    }
}
