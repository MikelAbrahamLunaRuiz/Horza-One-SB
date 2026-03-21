package com.example.demo.controller;

import com.example.demo.dto.DispositivoDTO;
import com.example.demo.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
@CrossOrigin(origins = "*")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping
    public ResponseEntity<List<DispositivoDTO>> obtenerTodos() {
        return ResponseEntity.ok(dispositivoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(dispositivoService.obtenerPorId(id));
    }

    @GetMapping("/area/{idArea}")
    public ResponseEntity<List<DispositivoDTO>> obtenerPorArea(@PathVariable Integer idArea) {
        return ResponseEntity.ok(dispositivoService.obtenerPorArea(idArea));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody DispositivoDTO dispositivoDTO) {
        try {
            DispositivoDTO creado = dispositivoService.crear(dispositivoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            // Si el ID ya existe, devolver error 409 Conflict
            if (e.getMessage().contains("Ya existe un dispositivo con el ID")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponse(e.getMessage()));
            }
            // Otros errores como 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
    
    // Clase interna para respuestas de error
    private static class ErrorResponse {
        private String mensaje;
        private long timestamp;
        
        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoDTO> actualizar(@PathVariable Integer id, @RequestBody DispositivoDTO dispositivoDTO) {
        return ResponseEntity.ok(dispositivoService.actualizar(id, dispositivoDTO));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<DispositivoDTO>> obtenerDispositivosInactivos() {
        return ResponseEntity.ok(dispositivoService.obtenerDispositivosInactivos());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<DispositivoDTO> cambiarEstado(@PathVariable Integer id, @RequestBody EstadoRequest estadoRequest) {
        return ResponseEntity.ok(dispositivoService.cambiarEstado(id, estadoRequest.getEstado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        dispositivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Clase interna para recibir el estado
    private static class EstadoRequest {
        private String estado;
        
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}
