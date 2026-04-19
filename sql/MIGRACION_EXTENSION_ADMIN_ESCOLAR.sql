USE HorzaOne;

-- ===========================================================
-- EXTENSIÓN MÍNIMA: ADMIN FLEXIBLE + FUNCIONES ESCOLARES
-- No modifica la estructura base de roles (ADMIN/PERSONAL)
-- ===========================================================

-- 1) Extensión en usuarios: foto de perfil
ALTER TABLE usuarios
ADD COLUMN IF NOT EXISTS foto_perfil VARCHAR(255) NULL COMMENT 'Ruta o URL de foto de perfil';

-- 2) Acciones para administradores
CREATE TABLE IF NOT EXISTS acciones_admin (
  id_accion INT PRIMARY KEY AUTO_INCREMENT,
  nombre_accion VARCHAR(80) NOT NULL,
  UNIQUE KEY uk_nombre_accion_admin (nombre_accion)
) ENGINE=InnoDB;

-- 3) Permisos personalizados por matrícula (admin)
CREATE TABLE IF NOT EXISTS permisos_personalizados (
  matricula INT NOT NULL,
  id_accion INT NOT NULL,
  PRIMARY KEY (matricula, id_accion),
  FOREIGN KEY (matricula) REFERENCES usuarios(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_accion) REFERENCES acciones_admin(id_accion) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4) Tutores (solo lectura de asistencia vía vínculo)
CREATE TABLE IF NOT EXISTS tutores (
  id_tutor INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(120) NOT NULL,
  contrasenia VARCHAR(100) NOT NULL,
  UNIQUE KEY uk_tutor_correo (correo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS vinculo_tutor (
  id_tutor INT NOT NULL,
  matricula_estudiante INT NOT NULL,
  PRIMARY KEY (id_tutor, matricula_estudiante),
  FOREIGN KEY (id_tutor) REFERENCES tutores(id_tutor) ON DELETE CASCADE,
  FOREIGN KEY (matricula_estudiante) REFERENCES usuarios(matricula) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5) Expediente digital
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

-- 6) Grupos con líder (usuario existente)
CREATE TABLE IF NOT EXISTS grupos (
  id_grupo INT PRIMARY KEY AUTO_INCREMENT,
  nombre_grupo VARCHAR(100) NOT NULL,
  matricula_lider INT NOT NULL COMMENT 'Usuario existente que lidera el grupo',
  FOREIGN KEY (matricula_lider) REFERENCES usuarios(matricula) ON DELETE RESTRICT,
  UNIQUE KEY uk_nombre_grupo (nombre_grupo),
  INDEX idx_grupo_lider (matricula_lider)
) ENGINE=InnoDB;

-- -----------------------------------------------------------
-- Query de validación para Dashboard Admin (UI):
-- Mostrar botón solo si tiene acción asignada
-- -----------------------------------------------------------
-- SELECT EXISTS (
--   SELECT 1
--   FROM permisos_personalizados pp
--   JOIN acciones_admin aa ON aa.id_accion = pp.id_accion
--   WHERE pp.matricula = :matricula_admin
--     AND aa.nombre_accion = :accion
-- ) AS permitido;
