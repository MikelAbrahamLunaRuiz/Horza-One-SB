package com.example.demo.controller;

import com.example.demo.dto.BajaUsuarioRequest;
import com.example.demo.dto.CambioContrasenaRequest;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer matricula) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(matricula));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuarioDTO));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer matricula, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizar(matricula, usuarioDTO));
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer matricula) {
        usuarioService.eliminar(matricula);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<Map<String, Object>> cambiarContrasena(@RequestBody CambioContrasenaRequest request) {
        boolean exitoso = usuarioService.cambiarContrasena(request);
        Map<String, Object> response = new HashMap<>();
        
        if (exitoso) {
            response.put("success", true);
            response.put("mensaje", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("mensaje", "Contraseña actual incorrecta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/eliminar-con-validacion")
    public ResponseEntity<Map<String, Object>> eliminarConValidacion(@RequestBody BajaUsuarioRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean exitoso = usuarioService.eliminarConValidacion(request);
            
            if (exitoso) {
                response.put("success", true);
                response.put("mensaje", "Usuario eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("mensaje", "Contraseña de administrador incorrecta");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
