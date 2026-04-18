package com.example.demo.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RegistroAccesoRequest;
import com.example.demo.dto.RegistroAccesoResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.model.Bitacora;
import com.example.demo.model.BloqueDiaAsignacion;
import com.example.demo.model.BloqueHorario;
import com.example.demo.model.Calendario;
import com.example.demo.model.DiaHorario;
import com.example.demo.model.Dispositivo;
import com.example.demo.model.Horario;
import com.example.demo.model.Registro;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioCalendario;
import com.example.demo.respository.BitacoraRepository;
import com.example.demo.respository.BloqueDiaAsignacionRepository;
import com.example.demo.respository.BloqueHorarioRepository;
import com.example.demo.respository.DiaHorarioRepository;
import com.example.demo.respository.DispositivoRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.respository.PermisoDiaRepository;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.respository.UsuarioCalendarioRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.RegistroAccesoService;

@Service
public class RegistroAccesoServiceImpl implements RegistroAccesoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private UsuarioCalendarioRepository usuarioCalendarioRepository;

    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private DiaHorarioRepository diaHorarioRepository;
    
    @Autowired
    private BloqueDiaAsignacionRepository bloqueDiaAsignacionRepository;
    
    @Autowired
    private PermisoDiaRepository permisoDiaRepository;

    @Override
    @Transactional
    public RegistroAccesoResponse registrarAcceso(RegistroAccesoRequest request) {
        try {
            // 1. Validar que el usuario exista
            Usuario usuario = usuarioRepository.findById(request.getMatricula())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + request.getMatricula()));

            // 2. Validar que el dispositivo exista
            Dispositivo dispositivo = dispositivoRepository.findById(request.getIdDispositivo())
                    .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

            // 3. Verificar que el usuario esté activo
            if (!"Activo".equals(usuario.getActivo())) {
                return new RegistroAccesoResponse(false, "Usuario inactivo. No puede registrar acceso.", "", 0, null);
            }

            // 4. Obtener o crear bitácora del usuario
            Bitacora bitacora = bitacoraRepository.findByUsuario(usuario)
                    .orElseGet(() -> {
                        Bitacora nuevaBitacora = new Bitacora();
                        nuevaBitacora.setUsuario(usuario);
                        nuevaBitacora.setNumEntradas(0);
                        nuevaBitacora.setNumInasistencias(0);
                        nuevaBitacora.setNumRetardos(0);
                        nuevaBitacora.setNumEntradasAnticipadas(0);
                        return bitacoraRepository.save(nuevaBitacora);
                    });

            // 5. Obtener la hora y fecha actual
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();
            
            // 6. VALIDACIÓN DE SEGURIDAD: Verificar último registro del usuario
            ValidacionSeguridadResult validacionSeguridad = validarSeguridadAcceso(
                usuario, dispositivo, fechaActual, horaActual, bitacora
            );
            
            // Si la validación requiere una salida automática, se generó
            // Actualizar el tipo de registro según la validación
            String tipoRegistroFinal = validacionSeguridad.tipoRegistroSugerido;
            String observacionSeguridad = validacionSeguridad.observacion;
            boolean esAlertaSeguridad = validacionSeguridad.esAlerta;

            // 7. Obtener el calendario asignado al usuario
            List<UsuarioCalendario> usuarioCalendarios = usuarioCalendarioRepository.findById_Matricula(request.getMatricula());
            
            if (usuarioCalendarios.isEmpty()) {
                return new RegistroAccesoResponse(false, "Usuario sin calendario asignado. Contacte al administrador.", "", 0, null);
            }

            // 8. Buscar el horario activo del calendario
            UsuarioCalendario usuarioCalendario = usuarioCalendarios.get(0);
            Calendario calendario = usuarioCalendario.getCalendario();
            
            if (!"Activo".equals(calendario.getActivoCalendario())) {
                return new RegistroAccesoResponse(false, "El calendario asignado no está activo.", "", 0, null);
            }

            // 9. Obtener horario del calendario (relación N:1 - calendario tiene FK a horario)
            Horario horario = calendario.getHorario();
            
            if (horario == null) {
                return new RegistroAccesoResponse(false, "El calendario no tiene horario asignado. Contacte al administrador.", "", 0, null);
            }
            
            if (!"Activo".equals(horario.getActivoHorario())) {
                return new RegistroAccesoResponse(false, "El horario asignado no está activo.", "", 0, null);
            }
            
            // 10. VALIDAR BLOQUE HORARIO Y ÁREA
            ValidacionBloqueResult validacionBloque = validarBloqueHorario(
                usuario, dispositivo, horario, fechaActual, horaActual, tipoRegistroFinal
            );
            
            if (!validacionBloque.esValido) {
                return new RegistroAccesoResponse(false, validacionBloque.mensajeError, "", 0, null);
            }
            
            // 11. Determinar el estado del registro y minutos de diferencia
            String estadoRegistro = validacionBloque.estadoRegistro;
            String observacion = observacionSeguridad != null ? 
                observacionSeguridad + " - " + validacionBloque.observacion : 
                validacionBloque.observacion;
            Integer minutosDiferencia = validacionBloque.minutosDiferencia;
            
            // 12. Actualizar contadores de bitácora según el estado
            if ("Entrada".equals(tipoRegistroFinal)) {
                bitacora.setNumEntradas(bitacora.getNumEntradas() + 1);
                
                if ("Anticipado".equals(estadoRegistro)) {
                    bitacora.setNumEntradasAnticipadas(bitacora.getNumEntradasAnticipadas() + 1);
                } else if ("Retardo".equals(estadoRegistro)) {
                    bitacora.setNumRetardos(bitacora.getNumRetardos() + 1);
                }
            }
            
            // 13. Actualizar estado_presencia del usuario
            String estadoPresenciaAnterior = usuario.getEstadoPresencia();
            if ("Entrada".equals(tipoRegistroFinal)) {
                usuario.setEstadoPresencia("Dentro");
                System.out.println("✅ Usuario " + usuario.getMatricula() + " cambiado de '" + estadoPresenciaAnterior + "' a 'Dentro'");
            } else if ("Salida".equals(tipoRegistroFinal)) {
                usuario.setEstadoPresencia("Fuera");
                System.out.println("✅ Usuario " + usuario.getMatricula() + " cambiado de '" + estadoPresenciaAnterior + "' a 'Fuera'");
            }
            usuarioRepository.save(usuario);
            System.out.println("✅ Registro " + tipoRegistroFinal + " creado para usuario " + usuario.getMatricula());

            // 14. Actualizar bitácora
            bitacoraRepository.save(bitacora);

            // 15. Generar ID único para el registro
            Integer nuevoIdRegistro = registroRepository.findAll().stream()
                    .mapToInt(Registro::getIdRegistro)
                    .max()
                    .orElse(0) + 1;

            // 16. Crear el registro
            Registro registro = new Registro();
            registro.setIdRegistro(nuevoIdRegistro);
            registro.setUsuario(usuario);
            registro.setBitacora(bitacora);
            registro.setDispositivo(dispositivo);
            registro.setArea(dispositivo.getArea());
            registro.setTipoRegistro(tipoRegistroFinal);
            registro.setFecha(fechaActual);
            registro.setHora(horaActual);
            registro.setObservacion(observacion);
            registro.setEstadoRegistro(estadoRegistro);

            Registro registroGuardado = registroRepository.save(registro);

            // 17. Convertir a DTO y retornar
            RegistroDTO registroDTO = convertirADTO(registroGuardado);
            
            // Mensaje de respuesta
            String mensajeRespuesta = esAlertaSeguridad 
                ? "⚠️ ALERTA: " + observacion
                : "✅ Registro de " + tipoRegistroFinal.toLowerCase() + " exitoso";

            // Crear respuesta con información del bloque horario
            RegistroAccesoResponse response = new RegistroAccesoResponse(
                    true,
                    mensajeRespuesta,
                    estadoRegistro,
                    minutosDiferencia,
                    registroDTO,
                    validacionBloque.nombreBloque,
                    validacionBloque.horaInicio,
                    validacionBloque.horaFin,
                    validacionBloque.nombreArea
            );
            
            return response;

        } catch (RuntimeException e) {
            return new RegistroAccesoResponse(false, "Error: " + e.getMessage(), null, 0, null);
        } catch (Exception e) {
            return new RegistroAccesoResponse(false, "Error inesperado al registrar acceso: " + e.getMessage(), null, 0, null);
        }
    }

    /**
     * Clase interna para resultado de validación de seguridad
     */
    private static class ValidacionSeguridadResult {
        String tipoRegistroSugerido;
        String observacion;
        boolean esAlerta;
        
        ValidacionSeguridadResult(String tipo, String obs, boolean alerta) {
            this.tipoRegistroSugerido = tipo;
            this.observacion = obs;
            this.esAlerta = alerta;
        }
    }
    
    /**
     * VALIDACIÓN DE SEGURIDAD AVANZADA
     * Valida que el usuario no intente entrar a un área sin haber salido del área anterior
     */
    private ValidacionSeguridadResult validarSeguridadAcceso(
            Usuario usuario, 
            Dispositivo dispositivoActual, 
            LocalDate fechaActual, 
            LocalTime horaActual,
            Bitacora bitacora) {
        
        // Buscar el último registro del usuario de hoy
        List<Registro> registrosHoy = registroRepository.findByUsuario_MatriculaAndFecha(
                usuario.getMatricula(), fechaActual);
        
        // Si no hay registros hoy, debe ser una ENTRADA
        if (registrosHoy.isEmpty()) {
            System.out.println("🔵 Usuario " + usuario.getMatricula() + " - Primer registro del día: ENTRADA");
            return new ValidacionSeguridadResult("Entrada", "Primer acceso del día", false);
        }
        
        // Ordenar por hora descendente y obtener el último registro
        registrosHoy.sort((r1, r2) -> r2.getHora().compareTo(r1.getHora()));
        Registro ultimoRegistro = registrosHoy.get(0);
        
        Integer areaAnterior = ultimoRegistro.getArea().getIdArea();
        Integer areaActual = dispositivoActual.getArea().getIdArea();
        String tipoUltimoRegistro = ultimoRegistro.getTipoRegistro();
        
        System.out.println("🔍 VALIDACIÓN SEGURIDAD - Usuario: " + usuario.getMatricula());
        System.out.println("   Último registro: " + tipoUltimoRegistro + " en Área " + areaAnterior);
        System.out.println("   Dispositivo actual: Área " + areaActual);
        
        // CASO 1: Último registro fue ENTRADA
        if ("Entrada".equals(tipoUltimoRegistro)) {
            
            // SUBCASO 1A: Intenta ENTRADA en la MISMA área donde ya tiene entrada activa
            if (areaAnterior.equals(areaActual)) {
                System.out.println("⚠️ ALERTA: Usuario ya tiene ENTRADA activa en Área " + areaActual);
                System.out.println("   Se sugiere SALIDA en lugar de ENTRADA");
                return new ValidacionSeguridadResult(
                    "Salida", 
                    "Salida del área " + ultimoRegistro.getArea().getNombreArea(), 
                    false
                );
            }
            
            // SUBCASO 1B: Intenta ENTRADA en ÁREA DIFERENTE sin haber marcado SALIDA del área anterior
            else {
                System.out.println("🚨 ALERTA DE SEGURIDAD CRÍTICA!");
                System.out.println("   Usuario intenta entrar a Área " + areaActual + " sin salir de Área " + areaAnterior);
                System.out.println("   Generando SALIDA AUTOMÁTICA del área anterior...");
                
                // GENERAR SALIDA AUTOMÁTICA DEL ÁREA ANTERIOR
                generarSalidaAutomatica(usuario, ultimoRegistro, fechaActual, horaActual, bitacora);
                
                // Ahora permitir la ENTRADA al área nueva con ALERTA
                return new ValidacionSeguridadResult(
                    "Entrada", 
                    "⚠️ Entrada a " + dispositivoActual.getArea().getNombreArea() + 
                    " (Salida automática generada de " + ultimoRegistro.getArea().getNombreArea() + ")", 
                    true
                );
            }
        }
        
        // CASO 2: Último registro fue SALIDA
        else if ("Salida".equals(tipoUltimoRegistro)) {
            
            // SUBCASO 2A: Intenta SALIDA cuando ya está fuera (último fue salida)
            if (areaAnterior.equals(areaActual)) {
                System.out.println("⚠️ ALERTA: Usuario ya marcó SALIDA de Área " + areaActual);
                System.out.println("   Se sugiere ENTRADA en lugar de SALIDA");
                return new ValidacionSeguridadResult(
                    "Entrada", 
                    "Entrada a " + dispositivoActual.getArea().getNombreArea(), 
                    false
                );
            }
            
            // SUBCASO 2B: Marca en dispositivo de ÁREA DIFERENTE después de una salida (nuevo ingreso)
            else {
                System.out.println("🔵 Usuario está FUERA - Permitiendo ENTRADA a nueva área");
                return new ValidacionSeguridadResult(
                    "Entrada", 
                    "Entrada a " + dispositivoActual.getArea().getNombreArea(), 
                    false
                );
            }
        }
        
        // CASO DEFAULT: No debería llegar aquí, pero por seguridad
        return new ValidacionSeguridadResult("Entrada", "Registro de acceso", false);
    }
    
    /**
     * Genera una SALIDA AUTOMÁTICA del área anterior cuando se detecta 
     * que el usuario intenta entrar a un área diferente sin haber salido
     */
    private void generarSalidaAutomatica(
            Usuario usuario, 
            Registro registroEntradaAnterior, 
            LocalDate fechaActual, 
            LocalTime horaActual,
            Bitacora bitacora) {
        
        try {
            System.out.println("🤖 Generando SALIDA AUTOMÁTICA...");
            
            // Generar ID único para la salida automática
            Integer nuevoIdSalidaAuto = registroRepository.findAll().stream()
                    .mapToInt(Registro::getIdRegistro)
                    .max()
                    .orElse(0) + 1;
            
            // Crear registro de SALIDA AUTOMÁTICA del área anterior
            Registro salidaAutomatica = new Registro();
            salidaAutomatica.setIdRegistro(nuevoIdSalidaAuto);
            salidaAutomatica.setUsuario(usuario);
            salidaAutomatica.setBitacora(bitacora);
            salidaAutomatica.setDispositivo(registroEntradaAnterior.getDispositivo());
            salidaAutomatica.setArea(registroEntradaAnterior.getArea());
            salidaAutomatica.setTipoRegistro("Salida");
            salidaAutomatica.setFecha(fechaActual);
            salidaAutomatica.setHora(horaActual.minusSeconds(5)); // 5 segundos antes del nuevo registro
            salidaAutomatica.setObservacion("🤖 SALIDA AUTOMÁTICA - Usuario ingresó a otra área sin marcar salida");
            salidaAutomatica.setEstadoRegistro("Puntual");
            
            registroRepository.save(salidaAutomatica);
            
            System.out.println("✅ SALIDA AUTOMÁTICA generada exitosamente");
            System.out.println("   Área: " + registroEntradaAnterior.getArea().getNombreArea());
            System.out.println("   Hora: " + salidaAutomatica.getHora());
            
        } catch (Exception e) {
            System.err.println("❌ Error al generar salida automática: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Clase interna para resultado de validación de bloque horario
     */
    private static class ValidacionBloqueResult {
        boolean esValido;
        String mensajeError;
        String estadoRegistro;
        String observacion;
        Integer minutosDiferencia;
        
        // Información del bloque horario
        String nombreBloque;
        String horaInicio;
        String horaFin;
        String nombreArea;
        
        ValidacionBloqueResult(boolean valido, String error, String estado, String obs, Integer minutos) {
            this.esValido = valido;
            this.mensajeError = error;
            this.estadoRegistro = estado;
            this.observacion = obs;
            this.minutosDiferencia = minutos;
        }
        
        ValidacionBloqueResult(boolean valido, String error, String estado, String obs, Integer minutos,
                               String nombreBloque, String horaInicio, String horaFin, String nombreArea) {
            this.esValido = valido;
            this.mensajeError = error;
            this.estadoRegistro = estado;
            this.observacion = obs;
            this.minutosDiferencia = minutos;
            this.nombreBloque = nombreBloque;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.nombreArea = nombreArea;
        }
    }
    
    /**
     * VALIDACIÓN COMPLETA DE BLOQUE HORARIO Y ÁREA
     * Verifica que el usuario tenga un bloque asignado para el área y hora actual
     * Valida restricciones de entrada/salida según horario
     */
    private ValidacionBloqueResult validarBloqueHorario(
            Usuario usuario,
            Dispositivo dispositivo,
            Horario horario,
            LocalDate fechaActual,
            LocalTime horaActual,
            String tipoRegistro) {
        
        try {
            System.out.println("🔍 VALIDANDO BLOQUE HORARIO para usuario " + usuario.getMatricula());
            
            // 1. Obtener el día de la semana actual (1=Lunes, 7=Domingo)
            DayOfWeek dayOfWeek = fechaActual.getDayOfWeek();
            int ordenDia = dayOfWeek.getValue(); // 1-7
            
            System.out.println("📅 Día actual: " + dayOfWeek + " (orden: " + ordenDia + ")");
            
            // 2. Buscar el DIA_HORARIO para este horario y día de la semana
            List<DiaHorario> diasHorario = diaHorarioRepository
                .findByHorario_IdHorarioOrderByDiaSemana_OrdenDia(horario.getIdHorario());
            
            DiaHorario diaHorarioActual = diasHorario.stream()
                .filter(dh -> dh.getDiaSemana().getOrdenDia() == ordenDia)
                .findFirst()
                .orElse(null);
            
            if (diaHorarioActual == null) {
                return new ValidacionBloqueResult(false, 
                    "No hay horario configurado para el día " + dayOfWeek + ". Contacte al administrador.", 
                    null, null, 0);
            }
            
            System.out.println("✅ DiaHorario encontrado: " + diaHorarioActual.getIdDiaHorario());
            
            // 3. Obtener los bloques asignados a este día para el área del dispositivo
            List<BloqueDiaAsignacion> asignacionesBloques = bloqueDiaAsignacionRepository
                .findByDiaHorario_IdDiaHorario(diaHorarioActual.getIdDiaHorario());
            
            List<BloqueHorario> bloquesDelArea = asignacionesBloques.stream()
                .map(BloqueDiaAsignacion::getBloqueHorario)
                .filter(bloque -> bloque.getArea().getIdArea().equals(dispositivo.getArea().getIdArea()))
                .collect(Collectors.toList());
            
            System.out.println("📋 Bloques encontrados para área " + dispositivo.getArea().getNombreArea() + ": " + bloquesDelArea.size());
            
            if (bloquesDelArea.isEmpty()) {
                return new ValidacionBloqueResult(false, 
                    "No tiene bloques horarios asignados para el área " + dispositivo.getArea().getNombreArea() + 
                    " en el día " + dayOfWeek + ". No puede registrar acceso.", 
                    null, null, 0);
            }
            
            // 4. Buscar el bloque que coincida con la hora actual
            BloqueHorario bloqueActivo = null;
            for (BloqueHorario bloque : bloquesDelArea) {
                LocalTime horaInicio = bloque.getHoraInicio();
                LocalTime horaFin = bloque.getHoraFin();
                
                // Tolerancia de 30 minutos antes del inicio y después del fin
                LocalTime inicioConTolerancia = horaInicio.minusMinutes(30);
                LocalTime finConTolerancia = horaFin.plusMinutes(30);
                
                if (!horaActual.isBefore(inicioConTolerancia) && !horaActual.isAfter(finConTolerancia)) {
                    bloqueActivo = bloque;
                    break;
                }
            }
            
            if (bloqueActivo == null) {
                // Mostrar los bloques disponibles
                String bloquesDisponibles = bloquesDelArea.stream()
                    .map(b -> b.getHoraInicio() + " - " + b.getHoraFin())
                    .collect(Collectors.joining(", "));
                
                return new ValidacionBloqueResult(false, 
                    "Hora actual (" + horaActual + ") fuera de los bloques horarios asignados. " +
                    "Bloques disponibles para " + dispositivo.getArea().getNombreArea() + ": " + bloquesDisponibles, 
                    null, null, 0);
            }
            
            System.out.println("✅ Bloque activo: " + bloqueActivo.getNombreBloque() + 
                " (" + bloqueActivo.getHoraInicio() + " - " + bloqueActivo.getHoraFin() + ")");
            
            // 5. VALIDAR TIPO DE REGISTRO SEGÚN HORA
            LocalTime horaInicio = bloqueActivo.getHoraInicio();
            LocalTime horaFin = bloqueActivo.getHoraFin();
            
            if ("Entrada".equals(tipoRegistro)) {
                return validarEntrada(usuario, bloqueActivo, horaActual, horaInicio);
            } else if ("Salida".equals(tipoRegistro)) {
                return validarSalida(usuario, bloqueActivo, horaActual, horaFin, fechaActual);
            }
            
            return new ValidacionBloqueResult(true, null, "Puntual", "Registro procesado", 0);
            
        } catch (Exception e) {
            System.err.println("❌ Error en validación de bloque: " + e.getMessage());
            e.printStackTrace();
            return new ValidacionBloqueResult(false, 
                "Error al validar bloque horario: " + e.getMessage(), 
                null, null, 0);
        }
    }
    
    /**
     * Validar ENTRADA con tolerancias y retardos
     */
    private ValidacionBloqueResult validarEntrada(
            Usuario usuario, 
            BloqueHorario bloque, 
            LocalTime horaActual, 
            LocalTime horaInicio) {
        
        // Tolerancia de 10 minutos antes del inicio (anticipado permitido)
        LocalTime inicioConToleranciaAnticipada = horaInicio.minusMinutes(10);
        // Tolerancia de 10 minutos después del inicio (retardo permitido)
        LocalTime inicioConToleranciaRetardo = horaInicio.plusMinutes(10);
        
        if (horaActual.isBefore(inicioConToleranciaAnticipada)) {
            // ENTRADA ANTICIPADA (más de 10 minutos antes)
            long minutos = Duration.between(horaActual, horaInicio).toMinutes();
            return new ValidacionBloqueResult(true, null, "Anticipado",
                "Entrada anticipada al bloque " + bloque.getNombreBloque() + 
                " (" + minutos + " minutos antes del inicio)", 
                (int) -minutos, // Negativo para anticipado
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        } else if (horaActual.isAfter(inicioConToleranciaRetardo)) {
            // RETARDO (más de 10 minutos después)
            long minutos = Duration.between(horaInicio, horaActual).toMinutes();
            return new ValidacionBloqueResult(true, null, "Retardo",
                "Entrada con retardo al bloque " + bloque.getNombreBloque() + 
                " (" + minutos + " minutos después del inicio)", 
                (int) minutos, // Positivo para retardo
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        } else {
            // PUNTUAL (dentro de la tolerancia de ±10 minutos)
            return new ValidacionBloqueResult(true, null, "Puntual",
                "Entrada puntual al bloque " + bloque.getNombreBloque(), 0,
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        }
    }
    
    /**
     * Validar SALIDA con restricción de salida anticipada
     * NO SE PERMITE SALIR ANTES DEL FIN DEL TURNO (excepto con permiso)
     */
    private ValidacionBloqueResult validarSalida(
            Usuario usuario,
            BloqueHorario bloque,
            LocalTime horaActual,
            LocalTime horaFin,
            LocalDate fechaActual) {
        
        // RESTRICCIÓN CRÍTICA: No permitir salida antes del fin del turno
        if (horaActual.isBefore(horaFin)) {
            // Por ahora, permitir salidas anticipadas pero registrarlas como excepcionales
            // TODO: Implementar validación de permisos cuando esté disponible el sistema de autorizaciones
            long minutosAntes = Duration.between(horaActual, horaFin).toMinutes();
            
            // Si es menos de 15 minutos antes, se considera puntual
            if (minutosAntes <= 15) {
                return new ValidacionBloqueResult(true, null, "Puntual",
                    "Salida puntual del bloque " + bloque.getNombreBloque(), 0,
                    bloque.getNombreBloque(),
                    bloque.getHoraInicio().toString(),
                    bloque.getHoraFin().toString(),
                    bloque.getArea().getNombreArea());
            }
            
            // Si es más de 15 minutos antes, registrar como salida anticipada
            System.out.println("⚠️ ALERTA: Salida anticipada detectada - " + minutosAntes + " minutos antes del fin");
            return new ValidacionBloqueResult(true, null, "Salida Anticipada",
                "⚠️ Salida anticipada del bloque " + bloque.getNombreBloque() +
                " (" + minutosAntes + " minutos antes del fin). Requiere justificación posterior.", 
                (int) -minutosAntes,
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        }
        
        // Tolerancia de 30 minutos después del fin (salida normal)
        LocalTime finConTolerancia = horaFin.plusMinutes(30);
        
        if (horaActual.isAfter(finConTolerancia)) {
            // Salida muy tarde (más de 30 minutos después del fin)
            long minutos = Duration.between(horaFin, horaActual).toMinutes();
            return new ValidacionBloqueResult(true, null, "Salida Tardía",
                "Salida tardía del bloque " + bloque.getNombreBloque() + 
                " (" + minutos + " minutos después del fin)", 
                (int) minutos,
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        } else {
            // SALIDA PUNTUAL (dentro de la tolerancia de 30 minutos después del fin)
            return new ValidacionBloqueResult(true, null, "Puntual",
                "Salida puntual del bloque " + bloque.getNombreBloque(), 0,
                bloque.getNombreBloque(),
                bloque.getHoraInicio().toString(),
                bloque.getHoraFin().toString(),
                bloque.getArea().getNombreArea());
        }
    }

    private RegistroDTO convertirADTO(Registro registro) {
        String nombreCompleto = registro.getUsuario().getNombreUsuario() + " " + 
                                registro.getUsuario().getApellidoPaternoUsuario() + " " + 
                                registro.getUsuario().getApellidoMaternoUsuario();
        
        return new RegistroDTO(
                registro.getIdRegistro(),
                registro.getUsuario().getMatricula(),
                registro.getBitacora().getIdBitacora(),
                registro.getDispositivo().getIdDispositivo(),
                registro.getArea().getIdArea(),
                registro.getArea().getNombreArea(),
                nombreCompleto,
                registro.getTipoRegistro(),
                registro.getFecha(),
                registro.getHora(),
                registro.getObservacion(),
                registro.getEstadoRegistro()
        );
    }
}
