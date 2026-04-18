-- ===============================================
-- DROPEO DE LA BASE HORIZON_ONE
-- ===============================================
DROP DATABASE IF EXISTS HORIZON_ONE;

-- ===============================================
-- DROPEO DE LOS ROLES
-- ===============================================
DROP ROLE IF EXISTS 'ROL_PERSONAL';
DROP ROLE IF EXISTS 'ROL_PERSONAL';

-- ===============================================
--  CREACIÓN DE BASE DE DATOS HORIZON_ONE
-- ===============================================
CREATE DATABASE IF NOT EXISTS HORIZON_ONE
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE HORIZON_ONE;

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
CREATE TABLE USUARIOS (
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
  FOREIGN KEY (id_rol) REFERENCES ROL(id_rol)
) ENGINE=InnoDB;

-- ===============================================
--  3. TABLA DIAS_SEMANA (CATÁLOGO FIJO)
-- ===============================================
CREATE TABLE DIAS_SEMANA (
  id_dia INT PRIMARY KEY,
  nombre_dia VARCHAR(20) NOT NULL,
  orden_dia INT NOT NULL COMMENT 'Orden en la semana (1=Lunes, 7=Domingo)'
) ENGINE=InnoDB;

-- Insertar los 7 días de la semana (FIJOS, SIEMPRE EXISTEN)
INSERT INTO DIAS_SEMANA (id_dia, nombre_dia, orden_dia) VALUES
(1, 'Lunes', 1),
(2, 'Martes', 2),
(3, 'Miércoles', 3),
(4, 'Jueves', 4),
(5, 'Viernes', 5),
(6, 'Sábado', 6),
(7, 'Domingo', 7);

-- ===============================================
--  4. TABLA HORARIO (INDEPENDIENTE)
-- ===============================================
CREATE TABLE HORARIO (
  id_horario INT PRIMARY KEY AUTO_INCREMENT,
  nombre_horario VARCHAR(60) NOT NULL,
  descripcion TEXT,
  activo_horario ENUM('Activo', 'Inactivo') DEFAULT 'Activo'
) ENGINE=InnoDB;

-- ===============================================
--  5. TABLA CALENDARIO
-- ===============================================
-- Un calendario puede tener UN horario asignado
-- Un horario puede estar en MUCHOS calendarios (N:1)
CREATE TABLE CALENDARIO (
  id_calendario INT PRIMARY KEY AUTO_INCREMENT,
  id_horario INT NULL COMMENT 'FK al horario asignado a este calendario (puede ser NULL)',
  nombre_calendario VARCHAR(60) NOT NULL,
  fecha_inicio DATE,
  fecha_fin DATE,
  descripcion TEXT,
  activo_calendario ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  FOREIGN KEY (id_horario) REFERENCES HORARIO(id_horario) ON DELETE SET NULL
) ENGINE=InnoDB;





-- ===============================================
--  5.1. TABLA DIA_HORARIO (RELACIÓN HORARIO-DÍA)
-- ===============================================
CREATE TABLE DIA_HORARIO (
  id_dia_horario INT PRIMARY KEY AUTO_INCREMENT,
  id_horario INT NOT NULL,
  id_dia INT NOT NULL COMMENT 'FK a DIAS_SEMANA (1=Lunes, 2=Martes...)',
  FOREIGN KEY (id_horario) REFERENCES HORARIO(id_horario) ON DELETE CASCADE,
  FOREIGN KEY (id_dia) REFERENCES DIAS_SEMANA(id_dia),
  UNIQUE KEY unique_horario_dia (id_horario, id_dia)
) ENGINE=InnoDB;

-- ===============================================
--  6. TABLA AREA
-- ===============================================
CREATE TABLE AREA (
  id_area INT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nombre_area VARCHAR(60) NOT NULL,
  descripcion_area TEXT,
  activo_area ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  ubicacion VARCHAR (100)
) ENGINE=InnoDB;

-- ===============================================
--  7. TABLA DISPOSITIVO
-- ===============================================
CREATE TABLE DISPOSITIVO (
  id_dispositivo INT PRIMARY KEY,
  id_area INT NOT NULL,
  nombre_dispositivo VARCHAR(100) NOT NULL,
  descripcion_dispositivo TEXT,
  activo_dispositivo ENUM('Activo', 'Inactivo') DEFAULT 'Inactivo',
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  8. TABLA BITACORA
-- ===============================================
CREATE TABLE BITACORA (
  id_bitacora INT  PRIMARY KEY NOT NULL,
  matricula INT NOT NULL,
  num_entradas INT DEFAULT 0,
  num_inasistencias INT DEFAULT 0,
  num_retardos INT DEFAULT 0,
  num_entradas_anticipadas INT DEFAULT 0,
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  9. TABLA REGISTRO
-- ===============================================
CREATE TABLE REGISTRO (
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
  FOREIGN KEY (id_bitacora) REFERENCES BITACORA(id_bitacora) ON DELETE CASCADE,
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_dispositivo) REFERENCES DISPOSITIVO(id_dispositivo) ON DELETE CASCADE,
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  10. TABLA BLOQUES_HORARIO (BLOQUES INDEPENDIENTES REUTILIZABLES)
-- ===============================================
-- Los bloques YA NO están vinculados a un día específico
-- Se crean una vez y se ASIGNAN a los días mediante tabla intermedia
-- Esto permite que un mismo bloque esté en múltiples días/horarios
CREATE TABLE BLOQUES_HORARIO (
  id_bloque INT PRIMARY KEY AUTO_INCREMENT,
  id_area INT NOT NULL,
  nombre_bloque VARCHAR(60) NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  10.1. TABLA BLOQUE_DIA_ASIGNACION (TABLA INTERMEDIA - MUCHOS A MUCHOS)
-- ===============================================
-- Esta es la tabla que permite REUTILIZAR bloques
-- En lugar de copiar bloques, solo creamos asignaciones
-- Un bloque puede estar asignado a múltiples días
-- Un día puede tener múltiples bloques asignados
CREATE TABLE BLOQUE_DIA_ASIGNACION (
  id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
  id_dia_horario INT NOT NULL,
  id_bloque INT NOT NULL,
  FOREIGN KEY (id_dia_horario) REFERENCES DIA_HORARIO(id_dia_horario) ON DELETE CASCADE,
  FOREIGN KEY (id_bloque) REFERENCES BLOQUES_HORARIO(id_bloque) ON DELETE CASCADE,
  UNIQUE KEY unique_dia_bloque (id_dia_horario, id_bloque)
) ENGINE=InnoDB;


-- ===============================================
--  11. TABLA USUARIOS_CALENDARIO
-- ===============================================
CREATE TABLE USUARIOS_CALENDARIO (
  matricula INT NOT NULL,
  id_calendario INT NOT NULL,
  PRIMARY KEY (matricula, id_calendario),
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_calendario) REFERENCES CALENDARIO(id_calendario) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
-- 12. TABLA PERMISOS_DIAS
-- ===============================================
CREATE TABLE IF NOT EXISTS PERMISOS_DIAS (
  id_permiso INT PRIMARY KEY AUTO_INCREMENT,
  tipo_permiso ENUM('Descanso', 'Feriado', 'No Laborable') NOT NULL COMMENT 'Tipo de permiso: Descanso (personal), Feriado (oficial), No Laborable (especial)',
  fecha DATE NOT NULL COMMENT 'Fecha del permiso',
  descripcion VARCHAR(200) COMMENT 'Descripción o motivo del permiso',
  es_general BOOLEAN DEFAULT FALSE COMMENT 'TRUE = Aplica a todos los usuarios, FALSE = Usuario específico',
  matricula INT NULL COMMENT 'Matrícula del usuario (NULL si es general)',
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  INDEX idx_fecha (fecha),
  INDEX idx_es_general (es_general),
  INDEX idx_matricula (matricula),
  CONSTRAINT chk_usuario_especifico CHECK (
    (es_general = TRUE AND matricula IS NULL) OR 
    (es_general = FALSE AND matricula IS NOT NULL)
  )
) ENGINE=InnoDB;

-- ===============================================
--  2.1. CREACION DE LOS ROLES PREDETERMINADOS POR EL SISTEMA
-- ===============================================

CREATE ROLE IF NOT EXISTS 'ROL_ADMIN';
CREATE ROLE IF NOT EXISTS 'ROL_PERSONAL';

-- ===============================================
--  2.2. ASIGNACION DE PRIVILEGIOS A LOS ROLES 
-- ===============================================

GRANT ALL PRIVILEGES ON horizon_one.* TO 'ROL_ADMIN';
GRANT SELECT ON horizon_one.* TO 'ROL_PERSONAL';

-- ===============================================
--  2.3. TRIGGERS 
-- ===============================================

CREATE TABLE auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_mysql VARCHAR(50),
    tabla_afectada VARCHAR(50),
    momento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accion VARCHAR(20)
);

DELIMITER $$

CREATE TRIGGER aud_usuarios
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (usuario_mysql, tabla_afectada, accion)
    VALUES (CURRENT_USER(), 'usuarios', 'INSERTO');
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER aud_usuarios_del
AFTER DELETE ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (usuario_mysql, tabla_afectada, accion)
    VALUES (CURRENT_USER(), 'usuarios', 'ELIMINO');
END$$

DELIMITER ;

