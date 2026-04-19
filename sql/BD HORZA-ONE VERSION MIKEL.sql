drop database HorzaOne;
-- ===============================================
--  CREACIÓN DE BASE DE DATOS HORIZON_ONE
-- ===============================================
CREATE DATABASE IF NOT EXISTS HorzaOne
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE HorzaOne;

-- ===============================================
--  1. TABLA ROL
-- ===============================================
CREATE TABLE rol (
  id_rol INT PRIMARY KEY AUTO_INCREMENT,
  nombre_rol VARCHAR(50) NOT NULL,
  tipo_permiso ENUM('ADMIN','PERSONAL') NOT NULL
) ENGINE=InnoDB;

-- ===============================================
--  2. TABLA USUARIOS
-- ===============================================
CREATE TABLE usuarios (
  matricula INT  primary key  NOT NULL,
  id_rol INT NOT NULL,
  rfc VARCHAR(20) NOT NULL,
  curp VARCHAR(20) NOT NULL,
  fecha_alta DATE,
  nombre_usuario VARCHAR(40) NOT NULL,
  apellido_paterno_usuario VARCHAR(20) NOT NULL,
  apellido_materno_usuario VARCHAR(20) NOT NULL,
  telefono VARCHAR(12),
  tipo_contrato VARCHAR(40),
  correo VARCHAR(50),
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  estado_presencia ENUM('Dentro', 'Fuera') DEFAULT 'Fuera' COMMENT 'Indica si está físicamente en las instalaciones',
  cp_usuario VARCHAR(7),
  calle_usuario VARCHAR (40),
  contrasenia varchar(12),
  foto_perfil VARCHAR(255) NULL COMMENT 'Ruta o URL de foto de perfil',
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
) ENGINE=InnoDB;

-- ===============================================
--  3. TABLA dias_semana (CATÁLOGO FIJO)
-- ===============================================
CREATE TABLE dias_semana (
  id_dia INT PRIMARY KEY,
  nombre_dia VARCHAR(20) NOT NULL,
  orden_dia INT NOT NULL COMMENT 'Orden en la semana (1=Lunes, 7=Domingo)'
) ENGINE=InnoDB;

-- Insertar los 7 días de la semana (FIJOS, SIEMPRE EXISTEN)
INSERT INTO dias_semana (id_dia, nombre_dia, orden_dia) VALUES
(1, 'Lunes', 1),
(2, 'Martes', 2),
(3, 'Miércoles', 3),
(4, 'Jueves', 4),
(5, 'Viernes', 5),
(6, 'Sábado', 6),
(7, 'Domingo', 7);

-- ===============================================
--  4. TABLA horario (INDEPENDIENTE)
-- ===============================================
CREATE TABLE horario (
  id_horario INT PRIMARY KEY AUTO_INCREMENT,
  nombre_horario VARCHAR(60) NOT NULL,
  descripcion TEXT,
  activo_horario ENUM('Activo', 'Inactivo') DEFAULT 'Activo'
) ENGINE=InnoDB;

-- ===============================================
--  5. TABLA calendario
-- ===============================================
-- Un calendario puede tener UN horario asignado
-- Un horario puede estar en MUCHOS calendarios (N:1)
CREATE TABLE calendario (
  id_calendario INT PRIMARY KEY AUTO_INCREMENT,
  id_horario INT NULL COMMENT 'FK al horario asignado a este calendario (puede ser NULL)',
  nombre_calendario VARCHAR(60) NOT NULL,
  fecha_inicio DATE,
  fecha_fin DATE,
  descripcion TEXT,
  activo_calendario ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  FOREIGN KEY (id_horario) REFERENCES horario(id_horario) ON DELETE SET NULL
) ENGINE=InnoDB;





-- ===============================================
--  5.1. TABLA dia_horario (RELACIÓN horario-DÍA)
-- ===============================================
CREATE TABLE dia_horario (
  id_dia_horario INT PRIMARY KEY AUTO_INCREMENT,
  id_horario INT NOT NULL,
  id_dia INT NOT NULL COMMENT 'FK a dias_semana (1=Lunes, 2=Martes...)',
  FOREIGN KEY (id_horario) REFERENCES horario(id_horario) ON DELETE CASCADE,
  FOREIGN KEY (id_dia) REFERENCES dias_semana(id_dia),
  UNIQUE KEY unique_horario_dia (id_horario, id_dia)
) ENGINE=InnoDB;

-- ===============================================
--  6. TABLA area
-- ===============================================
CREATE TABLE area (
  id_area INT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nombre_area VARCHAR(60) NOT NULL,
  descripcion_area TEXT,
  activo_area ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  ubicacion VARCHAR (100)
) ENGINE=InnoDB;

-- ===============================================
--  7. TABLA dispositivo
-- ===============================================
CREATE TABLE dispositivo (
  id_dispositivo INT PRIMARY KEY,
  id_area INT NOT NULL,
  nombre_dispositivo VARCHAR(100) NOT NULL,
  descripcion_dispositivo TEXT,
  activo_dispositivo ENUM('Activo', 'Inactivo') DEFAULT 'Inactivo',
  FOREIGN KEY (id_area) REFERENCES area(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  8. TABLA bitacora
-- ===============================================
CREATE TABLE bitacora (
  id_bitacora INT  PRIMARY KEY NOT NULL,
  matricula INT NOT NULL,
  num_entradas INT DEFAULT 0,
  num_inasistencias INT DEFAULT 0,
  num_retardos INT DEFAULT 0,
  num_entradas_anticipadas INT DEFAULT 0,
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  9. TABLA registro
-- ===============================================
CREATE TABLE registro (
  id_registro INT  PRIMARY KEY,
  matricula INT  NOT NULL,
  id_bitacora INT NOT NULL,
  id_dispositivo INT NOT NULL,
  id_area INT NOT NULL,
  tipo_registro ENUM('Entrada','Salida') NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  observacion TEXT,
  estado_registro ENUM('Puntual','Retardo','Anticipado'),
  FOREIGN KEY (id_bitacora) REFERENCES bitacora(id_bitacora) ON DELETE CASCADE,
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_dispositivo) REFERENCES dispositivo(id_dispositivo) ON DELETE CASCADE,
  FOREIGN KEY (id_area) REFERENCES area(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  10. TABLA bloques_horario (BLOQUES INDEPENDIENTES REUTILIZABLES)
-- ===============================================
-- Los bloques YA NO están vinculados a un día específico
-- Se crean una vez y se ASIGNAN a los días mediante tabla intermedia
-- Esto permite que un mismo bloque esté en múltiples días/horarios
CREATE TABLE bloques_horario (
  id_bloque INT PRIMARY KEY AUTO_INCREMENT,
  id_area INT NOT NULL,
  nombre_bloque VARCHAR(60) NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  FOREIGN KEY (id_area) REFERENCES area(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  10.1. TABLA bloque_dia_asignacion (TABLA INTERMEDIA - MUCHOS A MUCHOS)
-- ===============================================
-- Esta es la tabla que permite REUTILIZAR bloques
-- En lugar de copiar bloques, solo creamos asignaciones
-- Un bloque puede estar asignado a múltiples días
-- Un día puede tener múltiples bloques asignados
CREATE TABLE bloque_dia_asignacion (
  id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
  id_dia_horario INT NOT NULL,
  id_bloque INT NOT NULL,
  FOREIGN KEY (id_dia_horario) REFERENCES dia_horario(id_dia_horario) ON DELETE CASCADE,
  FOREIGN KEY (id_bloque) REFERENCES bloques_horario(id_bloque) ON DELETE CASCADE,
  UNIQUE KEY unique_dia_bloque (id_dia_horario, id_bloque)
) ENGINE=InnoDB;


-- ===============================================
--  11. TABLA usuarios_calendario
-- ===============================================
CREATE TABLE usuarios_calendario (
  matricula INT NOT NULL,
  id_calendario INT NOT NULL,
  PRIMARY KEY (matricula, id_calendario),
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_calendario) REFERENCES calendario(id_calendario) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
-- 12. TABLA permisos_dias
-- ===============================================
CREATE TABLE IF NOT EXISTS permisos_dias (
  id_permiso INT PRIMARY KEY AUTO_INCREMENT,
  tipo_permiso ENUM('Descanso', 'Feriado', 'No Laborable') NOT NULL COMMENT 'Tipo de permiso: Descanso (personal), Feriado (oficial), No Laborable (especial)',
  fecha DATE NOT NULL COMMENT 'Fecha del permiso',
  descripcion VARCHAR(200) COMMENT 'Descripción o motivo del permiso',
  es_general BOOLEAN DEFAULT FALSE COMMENT 'TRUE = Aplica a todos los usuarios, FALSE = Usuario específico',
  matricula INT NULL COMMENT 'Matrícula del usuario (NULL si es general)',
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  INDEX idx_fecha (fecha),
  INDEX idx_es_general (es_general),
  INDEX idx_matricula (matricula),
  CONSTRAINT chk_usuario_especifico CHECK (
    (es_general = TRUE AND matricula IS NULL) OR 
    (es_general = FALSE AND matricula IS NOT NULL) -- Corregido aquí
  )
) ENGINE=InnoDB;

-- ===============================================
-- 13. TABLA acciones_admin
-- ===============================================
CREATE TABLE IF NOT EXISTS acciones_admin (
  id_accion INT PRIMARY KEY AUTO_INCREMENT,
  nombre_accion VARCHAR(80) NOT NULL,
  UNIQUE KEY uk_nombre_accion_admin (nombre_accion)
) ENGINE=InnoDB;

-- ===============================================
-- 14. TABLA permisos_personalizados
-- ===============================================
-- Relación matrícula <-> acción para permisos de administradores
CREATE TABLE IF NOT EXISTS permisos_personalizados (
  matricula INT NOT NULL,
  id_accion INT NOT NULL,
  PRIMARY KEY (matricula, id_accion),
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_accion) REFERENCES acciones_admin(id_accion) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
-- 15. TABLA tutores
-- ===============================================
CREATE TABLE IF NOT EXISTS tutores (
  id_tutor INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(120) NOT NULL,
  contrasenia VARCHAR(100) NOT NULL,
  UNIQUE KEY uk_tutor_correo (correo)
) ENGINE=InnoDB;

-- ===============================================
-- 16. TABLA vinculo_tutor
-- ===============================================
CREATE TABLE IF NOT EXISTS vinculo_tutor (
  id_tutor INT NOT NULL,
  matricula_estudiante INT NOT NULL,
  PRIMARY KEY (id_tutor, matricula_estudiante),
  FOREIGN KEY (id_tutor) REFERENCES tutores(id_tutor) ON DELETE CASCADE,
  FOREIGN KEY (matricula_estudiante) REFERENCES usuarios(matricula) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
-- 17. TABLA expediente_digital
-- ===============================================
CREATE TABLE IF NOT EXISTS expediente_digital (
  id_archivo INT PRIMARY KEY AUTO_INCREMENT,
  matricula INT NOT NULL,
  url_pdf VARCHAR(500) NOT NULL,
  tipo_doc VARCHAR(80) NOT NULL,
  fecha_carga TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  INDEX idx_expediente_matricula (matricula),
  INDEX idx_expediente_tipo_doc (tipo_doc)
) ENGINE=InnoDB;

-- ===============================================
-- 18. TABLA grupos
-- ===============================================
CREATE TABLE IF NOT EXISTS grupos (
  id_grupo INT PRIMARY KEY AUTO_INCREMENT,
  nombre_grupo VARCHAR(100) NOT NULL,
  matricula_lider INT NOT NULL COMMENT 'Usuario existente que lidera el grupo',
  FOREIGN KEY (matricula_lider) REFERENCES usuarios(matricula) ON DELETE RESTRICT,
  UNIQUE KEY uk_nombre_grupo (nombre_grupo),
  INDEX idx_grupo_lider (matricula_lider)
) ENGINE=InnoDB;


