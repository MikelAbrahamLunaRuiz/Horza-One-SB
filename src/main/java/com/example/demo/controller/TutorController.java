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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TutorDTO;
import com.example.demo.dto.VinculoTutorDTO;
import com.example.demo.service.TutorService;

@RestController
@RequestMapping("/api/tutores")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public ResponseEntity<List<TutorDTO>> obtenerTodos() {
        return ResponseEntity.ok(tutorService.obtenerTodos());
    }

    @GetMapping("/{idTutor}")
    public ResponseEntity<TutorDTO> obtenerPorId(@PathVariable Integer idTutor) {
        try {
            return ResponseEntity.ok(tutorService.obtenerPorId(idTutor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<TutorDTO> crear(@RequestBody TutorDTO tutorDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.crear(tutorDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{idTutor}")
    public ResponseEntity<TutorDTO> actualizar(@PathVariable Integer idTutor, @RequestBody TutorDTO tutorDTO) {
        try {
            return ResponseEntity.ok(tutorService.actualizar(idTutor, tutorDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{idTutor}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idTutor) {
        try {
            tutorService.eliminar(idTutor);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{idTutor}/estudiantes/{matriculaEstudiante}")
    public ResponseEntity<VinculoTutorDTO> vincularTutorAEstudiante(
            @PathVariable Integer idTutor,
            @PathVariable Integer matriculaEstudiante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tutorService.vincularTutorAEstudiante(idTutor, matriculaEstudiante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{idTutor}/estudiantes/{matriculaEstudiante}")
    public ResponseEntity<Void> desvincularTutorDeEstudiante(
            @PathVariable Integer idTutor,
            @PathVariable Integer matriculaEstudiante) {
        try {
            tutorService.desvincularTutorDeEstudiante(idTutor, matriculaEstudiante);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/estudiantes/{matriculaEstudiante}/tutores")
    public ResponseEntity<List<TutorDTO>> obtenerTutoresPorEstudiante(@PathVariable Integer matriculaEstudiante) {
        try {
            return ResponseEntity.ok(tutorService.obtenerTutoresPorEstudiante(matriculaEstudiante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{idTutor}/estudiantes")
    public ResponseEntity<List<VinculoTutorDTO>> obtenerVinculosPorTutor(@PathVariable Integer idTutor) {
        try {
            return ResponseEntity.ok(tutorService.obtenerVinculosPorTutor(idTutor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
