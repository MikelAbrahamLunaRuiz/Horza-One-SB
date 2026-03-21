package com.example.demo.controller;

import com.example.demo.dto.PermisoDiaDTO;
import com.example.demo.dto.PermisoDiaRequest;
import com.example.demo.service.PermisoDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/permisos-dias")
@CrossOrigin(origins = "*")
public class PermisoDiaController {

    @Autowired
    private PermisoDiaService permisoDiaService;

    /**
     * GET /api/permisos-dias
     * Obtener todos los permisos activos
     */
    @GetMapping
    public ResponseEntity<List<PermisoDiaDTO>> obtenerTodosLosPermisos() {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerTodosLosPermisos();
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/{id}
     * Obtener permiso por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermisoDiaDTO> obtenerPermisoPorId(@PathVariable Integer id) {
        try {
            PermisoDiaDTO permiso = permisoDiaService.obtenerPermisoPorId(id);
            return ResponseEntity.ok(permiso);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/generales
     * Obtener todos los permisos generales (aplican a todos los usuarios)
     */
    @GetMapping("/generales")
    public ResponseEntity<List<PermisoDiaDTO>> obtenerPermisosGenerales() {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerPermisosGenerales();
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/usuario/{matricula}
     * Obtener permisos de un usuario específico
     */
    @GetMapping("/usuario/{matricula}")
    public ResponseEntity<List<PermisoDiaDTO>> obtenerPermisosPorUsuario(@PathVariable Integer matricula) {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerPermisosPorUsuario(matricula);
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/fecha?fecha=2025-12-25
     * Obtener permisos en una fecha específica
     */
    @GetMapping("/fecha")
    public ResponseEntity<List<PermisoDiaDTO>> obtenerPermisosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerPermisosPorFecha(fecha);
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/rango?fechaInicio=2025-01-01&fechaFin=2025-12-31
     * Obtener permisos en un rango de fechas
     */
    @GetMapping("/rango")
    public ResponseEntity<List<PermisoDiaDTO>> obtenerPermisosPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerPermisosPorRangoFechas(fechaInicio, fechaFin);
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/tipo/{tipo}
     * Obtener permisos por tipo (Descanso, Feriado, No Laborable)
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PermisoDiaDTO>> obtenerPermisosPorTipo(@PathVariable String tipo) {
        try {
            List<PermisoDiaDTO> permisos = permisoDiaService.obtenerPermisosPorTipo(tipo);
            return ResponseEntity.ok(permisos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/permisos-dias
     * Crear un nuevo permiso
     */
    @PostMapping
    public ResponseEntity<PermisoDiaDTO> crearPermiso(@RequestBody PermisoDiaRequest request) {
        try {
            if (!request.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            PermisoDiaDTO permisoCreado = permisoDiaService.crearPermiso(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(permisoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/permisos-dias/{id}
     * Actualizar un permiso existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermisoDiaDTO> actualizarPermiso(
            @PathVariable Integer id,
            @RequestBody PermisoDiaRequest request) {
        try {
            if (!request.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            PermisoDiaDTO permisoActualizado = permisoDiaService.actualizarPermiso(id, request);
            return ResponseEntity.ok(permisoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/permisos-dias/{id}
     * Eliminar un permiso (soft delete - cambia estado a Inactivo)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Integer id) {
        try {
            permisoDiaService.eliminarPermiso(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/permisos-dias/verificar?matricula=1&fecha=2025-12-25
     * Verificar si un usuario tiene permiso en una fecha específica
     */
    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarPermisoUsuario(
            @RequestParam Integer matricula,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            boolean tienePermiso = permisoDiaService.usuarioTienePermisoEnFecha(matricula, fecha);
            return ResponseEntity.ok(tienePermiso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
