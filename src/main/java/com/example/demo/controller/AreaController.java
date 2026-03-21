package com.example.demo.controller;

import com.example.demo.dto.AreaDTO;
import com.example.demo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaDTO>> obtenerTodas() {
        return ResponseEntity.ok(areaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(areaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AreaDTO> crear(@RequestBody AreaDTO areaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(areaService.crear(areaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDTO> actualizar(@PathVariable Integer id, @RequestBody AreaDTO areaDTO) {
        return ResponseEntity.ok(areaService.actualizar(id, areaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        areaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
