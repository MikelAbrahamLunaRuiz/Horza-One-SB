package com.example.demo.service.impl;

import com.example.demo.dto.UsuarioEmergenciaDTO;
import com.example.demo.model.Area;
import com.example.demo.model.Dispositivo;
import com.example.demo.model.Registro;
import com.example.demo.model.Usuario;
import com.example.demo.model.VinculoTutor;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.respository.VinculoTutorRepository;
import com.example.demo.service.EmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmergenciaServiceImpl implements EmergenciaService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private VinculoTutorRepository vinculoTutorRepository;

    @Override
    public List<UsuarioEmergenciaDTO> obtenerUsuariosDentro() {
        System.out.println("🚨 CONTROL DE EMERGENCIAS - Obteniendo usuarios dentro...");
        return obtenerUsuariosDentroFiltrados(null);
    }

    @Override
    public List<UsuarioEmergenciaDTO> obtenerUsuariosDentroPorTutor(Integer idTutor) {
        System.out.println("🚨 CONTROL DE EMERGENCIAS - Obteniendo usuarios dentro para tutor: " + idTutor);

        List<VinculoTutor> vinculos = vinculoTutorRepository.findByIdTutor(idTutor);
        Set<Integer> matriculasPermitidas = new HashSet<>();
        for (VinculoTutor vinculo : vinculos) {
            if (vinculo.getMatriculaEstudiante() != null) {
                matriculasPermitidas.add(vinculo.getMatriculaEstudiante());
            }
        }

        if (matriculasPermitidas.isEmpty()) {
            System.out.println("ℹ️ Tutor sin estudiantes vinculados o sin usuarios dentro");
            return new ArrayList<>();
        }

        return obtenerUsuariosDentroFiltrados(matriculasPermitidas);
    }

    private List<UsuarioEmergenciaDTO> obtenerUsuariosDentroFiltrados(Set<Integer> matriculasPermitidas) {
        List<UsuarioEmergenciaDTO> usuariosEmergencia = new ArrayList<>();

        // Obtener todos los usuarios con estado "Dentro"
        List<Usuario> usuariosDentro = usuarioRepository.findByEstadoPresencia("Dentro");
        System.out.println("📋 Total de usuarios con estado 'Dentro': " + usuariosDentro.size());

        for (Usuario usuario : usuariosDentro) {
            if (matriculasPermitidas != null && !matriculasPermitidas.contains(usuario.getMatricula())) {
                continue;
            }

            // Buscar el último registro de ENTRADA del usuario
            Registro ultimoRegistro = registroRepository
                    .findTopByUsuario_MatriculaAndTipoRegistroOrderByFechaDescHoraDesc(
                            usuario.getMatricula(), "Entrada");

            if (ultimoRegistro != null) {
                // Obtener información del área directamente del registro
                Area area = ultimoRegistro.getArea();
                String nombreArea = (area != null) ? area.getNombreArea() : "Área Desconocida";

                // Obtener información del dispositivo directamente del registro
                Dispositivo dispositivo = ultimoRegistro.getDispositivo();
                String nombreDispositivo = (dispositivo != null) ? dispositivo.getNombreDispositivo() : "Dispositivo Desconocido";

                // Construir nombre completo
                String nombreCompleto = usuario.getNombreUsuario() + " " +
                        usuario.getApellidoPaternoUsuario() + " " +
                        usuario.getApellidoMaternoUsuario();

                // Crear DTO
                UsuarioEmergenciaDTO dto = new UsuarioEmergenciaDTO(
                        usuario.getMatricula(),
                        nombreCompleto,
                        nombreArea,
                        ultimoRegistro.getHora().toString(),
                        nombreDispositivo,
                        ultimoRegistro.getFecha().toString()
                );

                usuariosEmergencia.add(dto);

                System.out.println("✅ Usuario: " + nombreCompleto +
                        " | Área: " + nombreArea +
                        " | Hora: " + ultimoRegistro.getHora() +
                        " | Dispositivo: " + nombreDispositivo);
            } else {
                System.out.println("⚠️ Usuario " + usuario.getMatricula() +
                        " tiene estado 'Dentro' pero no se encontró registro de entrada");
            }
        }

        System.out.println("🚨 Total de usuarios en emergencia: " + usuariosEmergencia.size());
        return usuariosEmergencia;
    }

    @Override
    public byte[] exportarUsuariosDentroExcel() throws Exception {
        System.out.println("📊 EXPORTANDO USUARIOS A EXCEL...");
        
        List<UsuarioEmergenciaDTO> usuarios = obtenerUsuariosDentro();
        
        // Crear libro de Excel
        org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Usuarios en Instalaciones");
        
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
        headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        
        org.apache.poi.ss.usermodel.CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        
        // Crear encabezado de información
        org.apache.poi.ss.usermodel.Row infoRow = sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell infoCell = infoRow.createCell(0);
        infoCell.setCellValue("🚨 REPORTE DE EMERGENCIA - USUARIOS EN INSTALACIONES");
        org.apache.poi.ss.usermodel.CellStyle titleStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        infoCell.setCellStyle(titleStyle);
        
        org.apache.poi.ss.usermodel.Row fechaRow = sheet.createRow(1);
        org.apache.poi.ss.usermodel.Cell fechaCell = fechaRow.createCell(0);
        fechaCell.setCellValue("Fecha de generación: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        
        org.apache.poi.ss.usermodel.Row totalRow = sheet.createRow(2);
        org.apache.poi.ss.usermodel.Cell totalCell = totalRow.createCell(0);
        totalCell.setCellValue("Total de personas dentro: " + usuarios.size());
        org.apache.poi.ss.usermodel.CellStyle totalStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font totalFont = workbook.createFont();
        totalFont.setBold(true);
        totalFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.getIndex());
        totalStyle.setFont(totalFont);
        totalCell.setCellStyle(totalStyle);
        
        // Crear encabezado de tabla (fila 4)
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(4);
        String[] columnas = {"Matrícula", "Nombre Completo", "Área Actual", "Hora de Entrada", "Dispositivo", "Fecha"};
        
        for (int i = 0; i < columnas.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Llenar datos
        int rowNum = 5;
        for (UsuarioEmergenciaDTO usuario : usuarios) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            
            org.apache.poi.ss.usermodel.Cell cell0 = row.createCell(0);
            cell0.setCellValue(usuario.getMatricula());
            cell0.setCellStyle(dataStyle);
            
            org.apache.poi.ss.usermodel.Cell cell1 = row.createCell(1);
            cell1.setCellValue(usuario.getNombreCompleto());
            cell1.setCellStyle(dataStyle);
            
            org.apache.poi.ss.usermodel.Cell cell2 = row.createCell(2);
            cell2.setCellValue(usuario.getNombreArea());
            cell2.setCellStyle(dataStyle);
            
            org.apache.poi.ss.usermodel.Cell cell3 = row.createCell(3);
            cell3.setCellValue(usuario.getHoraIngreso());
            cell3.setCellStyle(dataStyle);
            
            org.apache.poi.ss.usermodel.Cell cell4 = row.createCell(4);
            cell4.setCellValue(usuario.getNombreDispositivo());
            cell4.setCellStyle(dataStyle);
            
            org.apache.poi.ss.usermodel.Cell cell5 = row.createCell(5);
            cell5.setCellValue(usuario.getFechaIngreso());
            cell5.setCellStyle(dataStyle);
        }
        
        // Ajustar ancho de columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }
        
        // Convertir a bytes
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        System.out.println("✅ Excel generado correctamente con " + usuarios.size() + " registros");
        return outputStream.toByteArray();
    }
}
