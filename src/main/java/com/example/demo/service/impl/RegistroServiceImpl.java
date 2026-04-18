package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.model.Area;
import com.example.demo.model.Bitacora;
import com.example.demo.model.Dispositivo;
import com.example.demo.model.Registro;
import com.example.demo.model.Usuario;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.BitacoraRepository;
import com.example.demo.respository.DispositivoRepository;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.RegistroService;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BitacoraRepository bitacoraRepository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<RegistroDTO> obtenerTodos() {
        return registroRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistroDTO obtenerPorId(Integer id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        return convertirADTO(registro);
    }

    @Override
    public RegistroDTO crear(RegistroDTO registroDTO) {
        Registro registro = convertirAEntidad(registroDTO);
        Registro registroGuardado = registroRepository.save(registro);
        return convertirADTO(registroGuardado);
    }

    @Override
    public RegistroDTO actualizar(Integer id, RegistroDTO registroDTO) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        
        Usuario usuario = usuarioRepository.findById(registroDTO.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bitacora bitacora = bitacoraRepository.findById(registroDTO.getIdBitacora())
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        Dispositivo dispositivo = dispositivoRepository.findById(registroDTO.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        Area area = areaRepository.findById(registroDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        registro.setUsuario(usuario);
        registro.setBitacora(bitacora);
        registro.setDispositivo(dispositivo);
        registro.setArea(area);
        registro.setTipoRegistro(registroDTO.getTipoRegistro());
        registro.setFecha(registroDTO.getFecha());
        registro.setHora(registroDTO.getHora());
        registro.setObservacion(registroDTO.getObservacion());
        registro.setEstadoRegistro(registroDTO.getEstadoRegistro());
        
        Registro registroActualizado = registroRepository.save(registro);
        return convertirADTO(registroActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        registroRepository.deleteById(id);
    }

    @Override
    public ConsultaRegistrosResponse consultarRegistrosPorFiltros(ConsultaRegistrosRequest request) {
        // Obtener usuario para el nombre completo
        Usuario usuario = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String nombreCompleto = usuario.getNombreUsuario() + " " +
                usuario.getApellidoPaternoUsuario() + " " +
                usuario.getApellidoMaternoUsuario();
        
        // Obtener registros filtrados
        List<Registro> registros;
        
        // Caso 1: Sin filtro de fechas - TODOS los registros históricos del usuario
        if (request.getFechaInicio() == null && request.getFechaFin() == null) {
            if (request.getTipoRegistro() != null && !request.getTipoRegistro().isEmpty()) {
                registros = registroRepository.findByUsuario_MatriculaAndTipoRegistroOrderByFechaDescHoraDesc(
                        request.getMatricula(),
                        request.getTipoRegistro()
                );
            } else {
                registros = registroRepository.findByUsuario_MatriculaOrderByFechaDescHoraDesc(
                        request.getMatricula()
                );
            }
        }
        // Caso 2: Con rango de fechas - Filtrar por rango
        else {
            if (request.getTipoRegistro() != null && !request.getTipoRegistro().isEmpty()) {
                registros = registroRepository.findByUsuario_MatriculaAndFechaBetweenAndTipoRegistro(
                        request.getMatricula(),
                        request.getFechaInicio(),
                        request.getFechaFin(),
                        request.getTipoRegistro()
                );
            } else {
                registros = registroRepository.findByUsuario_MatriculaAndFechaBetween(
                        request.getMatricula(),
                        request.getFechaInicio(),
                        request.getFechaFin()
                );
            }
        }
        
        // Convertir a DTOs
        List<RegistroDTO> registrosDTO = registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        
        // Calcular estadísticas
        int totalRegistros = registros.size();
        int totalEntradas = (int) registros.stream().filter(r -> "Entrada".equals(r.getTipoRegistro())).count();
        int totalSalidas = (int) registros.stream().filter(r -> "Salida".equals(r.getTipoRegistro())).count();
        int totalPuntuales = (int) registros.stream().filter(r -> "Puntual".equals(r.getEstadoRegistro())).count();
        int totalRetardos = (int) registros.stream().filter(r -> "Retardo".equals(r.getEstadoRegistro())).count();
        int totalAnticipados = (int) registros.stream().filter(r -> "Anticipado".equals(r.getEstadoRegistro())).count();
        
        return new ConsultaRegistrosResponse(
                request.getMatricula(),
                nombreCompleto,
                totalRegistros,
                totalEntradas,
                totalSalidas,
                totalPuntuales,
                totalRetardos,
                totalAnticipados,
                registrosDTO
        );
    }

    @Override
    public List<RegistroDTO> obtenerUltimos3PorDispositivo(Integer idDispositivo) {
        List<Registro> registros = registroRepository.findTop3ByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(idDispositivo);
        return registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RegistroDTO> obtenerTodosPorDispositivo(Integer idDispositivo) {
        List<Registro> registros = registroRepository.findByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(idDispositivo);
        return registros.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public RegistroDTO obtenerUltimoRegistroUsuario(Integer matricula) {
        Registro ultimoRegistro = registroRepository.findTopByUsuario_MatriculaOrderByFechaDescHoraDesc(matricula);
        if (ultimoRegistro != null) {
            return convertirADTO(ultimoRegistro);
        }
        return null;
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

    private Registro convertirAEntidad(RegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bitacora bitacora = bitacoraRepository.findById(dto.getIdBitacora())
                .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        Dispositivo dispositivo = dispositivoRepository.findById(dto.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));
        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        return new Registro(
                dto.getIdRegistro(),
                usuario,
                bitacora,
                dispositivo,
                area,
                dto.getTipoRegistro(),
                dto.getFecha(),
                dto.getHora(),
                dto.getObservacion(),
                dto.getEstadoRegistro()
        );
    }

    @Override
    public byte[] exportarBitacoraExcel(ConsultaRegistrosRequest request) throws Exception {
        System.out.println("📊 EXPORTANDO BITÁCORA A EXCEL...");
        
        // Obtener los registros filtrados
        ConsultaRegistrosResponse response = consultarRegistrosPorFiltros(request);
        List<RegistroDTO> registros = response.getRegistros();
        
        // Obtener información del usuario
        Usuario usuario = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Crear libro de Excel
        org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Bitácora de Asistencia");
        
        // Estilos
        org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        
        org.apache.poi.ss.usermodel.CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        
        // Estilo para estados (colores)
        org.apache.poi.ss.usermodel.CellStyle puntualStyle = workbook.createCellStyle();
        puntualStyle.cloneStyleFrom(dataStyle);
        puntualStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.LIGHT_GREEN.getIndex());
        puntualStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        
        org.apache.poi.ss.usermodel.CellStyle retardoStyle = workbook.createCellStyle();
        retardoStyle.cloneStyleFrom(dataStyle);
        retardoStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.LIGHT_YELLOW.getIndex());
        retardoStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        
        org.apache.poi.ss.usermodel.CellStyle anticipadoStyle = workbook.createCellStyle();
        anticipadoStyle.cloneStyleFrom(dataStyle);
        anticipadoStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.LIGHT_ORANGE.getIndex());
        anticipadoStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        
        // Título del documento
        org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BITÁCORA DE ASISTENCIA - " + usuario.getNombreUsuario() + " " + usuario.getApellidoPaternoUsuario());
        
        org.apache.poi.ss.usermodel.CellStyle titleStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 6));
        
        // Información del usuario
        int rowNum = 1;
        org.apache.poi.ss.usermodel.Row infoRow1 = sheet.createRow(rowNum++);
        infoRow1.createCell(0).setCellValue("Matrícula:");
        infoRow1.createCell(1).setCellValue(usuario.getMatricula());
        
        org.apache.poi.ss.usermodel.Row infoRow2 = sheet.createRow(rowNum++);
        infoRow2.createCell(0).setCellValue("Período:");
        if (request.getFechaInicio() != null && request.getFechaFin() != null) {
            infoRow2.createCell(1).setCellValue(request.getFechaInicio().toString() + " a " + request.getFechaFin().toString());
        }
        
        // Estadísticas
        rowNum++;
        org.apache.poi.ss.usermodel.Row statsHeaderRow = sheet.createRow(rowNum++);
        statsHeaderRow.createCell(0).setCellValue("ESTADÍSTICAS");
        org.apache.poi.ss.usermodel.CellStyle statsHeaderStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font statsFont = workbook.createFont();
        statsFont.setBold(true);
        statsFont.setFontHeightInPoints((short) 12);
        statsHeaderStyle.setFont(statsFont);
        statsHeaderRow.getCell(0).setCellStyle(statsHeaderStyle);
        
        org.apache.poi.ss.usermodel.Row statsRow = sheet.createRow(rowNum++);
        statsRow.createCell(0).setCellValue("Puntuales:");
        statsRow.createCell(1).setCellValue(response.getTotalPuntuales());
        statsRow.createCell(2).setCellValue("Retardos:");
        statsRow.createCell(3).setCellValue(response.getTotalRetardos());
        statsRow.createCell(4).setCellValue("Anticipados:");
        statsRow.createCell(5).setCellValue(response.getTotalAnticipados());
        
        // Espacio
        rowNum++;
        
        // Encabezados de registros
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(rowNum++);
        String[] headers = {"Fecha", "Hora", "Tipo", "Área", "Estado", "Observación"};
        for (int i = 0; i < headers.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Datos de registros
        for (RegistroDTO registro : registros) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(registro.getFecha().toString());
            row.createCell(1).setCellValue(registro.getHora().toString());
            row.createCell(2).setCellValue(registro.getTipoRegistro());
            row.createCell(3).setCellValue(registro.getNombreArea() != null ? registro.getNombreArea() : "");
            
            org.apache.poi.ss.usermodel.Cell estadoCell = row.createCell(4);
            estadoCell.setCellValue(registro.getEstadoRegistro());
            
            // Aplicar estilo según el estado
            switch (registro.getEstadoRegistro()) {
                case "Puntual":
                    estadoCell.setCellStyle(puntualStyle);
                    break;
                case "Retardo":
                    estadoCell.setCellStyle(retardoStyle);
                    break;
                case "Anticipado":
                    estadoCell.setCellStyle(anticipadoStyle);
                    break;
                default:
                    estadoCell.setCellStyle(dataStyle);
                    break;
            }
            
            row.createCell(5).setCellValue(registro.getObservacion() != null ? registro.getObservacion() : "");
            
            // Aplicar estilo a las demás celdas
            for (int i = 0; i < 6; i++) {
                if (i != 4) { // Excepto la celda de estado que ya tiene estilo
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
        }
        
        // Ajustar ancho de columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 500);
        }
        
        // Convertir a bytes
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        System.out.println("✅ Excel generado exitosamente con " + registros.size() + " registros");
        return outputStream.toByteArray();
    }
}
