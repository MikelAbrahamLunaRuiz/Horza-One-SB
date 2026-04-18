package com.example.demo.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    // Consulta con rango de fechas
    List<Registro> findByUsuario_MatriculaAndFechaBetween(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin);
    List<Registro> findByUsuario_MatriculaAndFechaBetweenAndTipoRegistro(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin, String tipoRegistro);
    
    // Consulta TODOS los registros de un usuario (sin filtro de fecha) ordenados cronológicamente descendente
    List<Registro> findByUsuario_MatriculaOrderByFechaDescHoraDesc(Integer matricula);
    List<Registro> findByUsuario_MatriculaAndTipoRegistroOrderByFechaDescHoraDesc(Integer matricula, String tipoRegistro);
    
    // Consultas por dispositivo
    List<Registro> findTop3ByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(Integer idDispositivo);
    List<Registro> findByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(Integer idDispositivo);
    
    // Consulta por fecha específica
    List<Registro> findByUsuario_MatriculaAndFecha(Integer matricula, LocalDate fecha);
    
    // Último registro
    Registro findTopByUsuario_MatriculaAndTipoRegistroOrderByFechaDescHoraDesc(Integer matricula, String tipoRegistro);
    Registro findTopByUsuario_MatriculaOrderByFechaDescHoraDesc(Integer matricula);
    
    // ========== MÉTODOS PARA GRÁFICAS Y ESTADÍSTICAS ==========
    
    /**
     * Obtiene todos los registros en un rango de fechas (para análisis y gráficas)
     */
    List<Registro> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Obtiene registros por área en un rango de fechas
     */
    List<Registro> findByArea_IdAreaAndFechaBetween(Integer idArea, LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Obtiene registros por tipo en un rango de fechas (útil para filtrar entradas/salidas)
     */
    List<Registro> findByTipoRegistroAndFechaBetween(String tipoRegistro, LocalDate fechaInicio, LocalDate fechaFin);
}
