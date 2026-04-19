package com.example.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/archivos")
@CrossOrigin(origins = "*")
public class ArchivoController {

    private final Path directorioUploads = Paths.get("uploads");
    private final Path directorioFotos = directorioUploads.resolve("fotos");
    private final Path directorioPdfs = directorioUploads.resolve("pdfs");

    public ArchivoController() throws IOException {
        Files.createDirectories(directorioFotos);
        Files.createDirectories(directorioPdfs);
    }

    @PostMapping(value = "/fotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> subirFotoPerfil(@RequestPart("archivo") MultipartFile archivo) {
        try {
            validarArchivo(archivo);

            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "El archivo debe ser una imagen"));
            }

            String nombreArchivo = generarNombreArchivo(archivo.getOriginalFilename());
            Path destino = directorioFotos.resolve(nombreArchivo).normalize();
            guardarArchivo(archivo, destino, directorioFotos);

            String url = "/api/archivos/fotos/" + nombreArchivo;
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("url", url, "nombreArchivo", nombreArchivo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", mensajeSeguro(e.getMessage(), "Solicitud inválida")));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "No se pudo guardar la foto"));
        }
    }

    @PostMapping(value = "/pdfs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> subirPdf(@RequestPart("archivo") MultipartFile archivo) {
        try {
            validarArchivo(archivo);

            String contentType = archivo.getContentType();
            String nombreOriginal = archivo.getOriginalFilename() == null ? "" : archivo.getOriginalFilename().toLowerCase();
            boolean esPdf = "application/pdf".equalsIgnoreCase(contentType) || nombreOriginal.endsWith(".pdf");
            if (!esPdf) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensaje", "El archivo debe ser PDF"));
            }

            String nombreArchivo = generarNombreArchivo(archivo.getOriginalFilename());
            if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
                nombreArchivo = nombreArchivo + ".pdf";
            }

            Path destino = directorioPdfs.resolve(nombreArchivo).normalize();
            guardarArchivo(archivo, destino, directorioPdfs);

            String url = "/api/archivos/pdfs/" + nombreArchivo;
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("url", url, "nombreArchivo", nombreArchivo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", mensajeSeguro(e.getMessage(), "Solicitud inválida")));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "No se pudo guardar el PDF"));
        }
    }

    @GetMapping("/fotos/{nombreArchivo:.+}")
    public ResponseEntity<Resource> obtenerFoto(@PathVariable String nombreArchivo) {
        return servirArchivo(directorioFotos, nombreArchivo, MediaType.IMAGE_JPEG);
    }

    @GetMapping("/pdfs/{nombreArchivo:.+}")
    public ResponseEntity<Resource> obtenerPdf(@PathVariable String nombreArchivo) {
        return servirArchivo(directorioPdfs, nombreArchivo, MediaType.APPLICATION_PDF);
    }

    private ResponseEntity<Resource> servirArchivo(Path directorioBase, String nombreArchivo, MediaType mediaTypePorDefecto) {
        try {
            validarNombreArchivo(nombreArchivo);
            Path ruta = directorioBase.resolve(nombreArchivo).normalize();
            if (!ruta.startsWith(directorioBase) || !Files.exists(ruta)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(ruta.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            MediaType mediaType = mediaTypePorDefecto;
            String detectado = Files.probeContentType(ruta);
            if (detectado != null) {
                try {
                    mediaType = MediaType.parseMediaType(detectado);
                } catch (Exception ignored) {
                    mediaType = mediaTypePorDefecto;
                }
            }

            return ResponseEntity.ok().contentType(mediaType).body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void validarArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new RuntimeException("Debe seleccionar un archivo");
        }
    }

    private void validarNombreArchivo(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new RuntimeException("Nombre de archivo inválido");
        }
        if (nombreArchivo.contains("..") || nombreArchivo.contains("/") || nombreArchivo.contains("\\")) {
            throw new RuntimeException("Nombre de archivo inválido");
        }
    }

    private String generarNombreArchivo(String nombreOriginal) {
        String extension = "";
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private void guardarArchivo(MultipartFile archivo, Path destino, Path directorioBase) throws IOException {
        if (!destino.normalize().startsWith(directorioBase.normalize())) {
            throw new RuntimeException("Ruta de archivo inválida");
        }
        Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
    }

    private String mensajeSeguro(String mensaje, String fallback) {
        return mensaje == null || mensaje.trim().isEmpty() ? fallback : mensaje;
    }
}
