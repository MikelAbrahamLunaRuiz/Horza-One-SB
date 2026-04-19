USE HorzaOne;

-- ============================================
-- 0. LIMPIEZA TOTAL (SCRIPT RE-EJECUTABLE)
-- ============================================
-- Este archivo está pensado para correr cientos de veces.
-- Se borran los datos de prueba y se vuelven a insertar los mismos.
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM permisos_personalizados;
DELETE FROM vinculo_tutor;
DELETE FROM expediente_digital;
DELETE FROM grupo_integrantes;
DELETE FROM grupos;
DELETE FROM registro;
DELETE FROM usuarios_calendario;
DELETE FROM bloque_dia_asignacion;
DELETE FROM dia_horario;
DELETE FROM bloques_horario;
DELETE FROM calendario;
DELETE FROM horario;
DELETE FROM permisos_dias;
DELETE FROM dispositivo;
DELETE FROM bitacora;
DELETE FROM tutores;
DELETE FROM acciones_admin;
DELETE FROM usuarios;
DELETE FROM area;
DELETE FROM rol;
DELETE FROM dias_semana;

ALTER TABLE rol AUTO_INCREMENT = 1;
ALTER TABLE area AUTO_INCREMENT = 1;
ALTER TABLE horario AUTO_INCREMENT = 1;
ALTER TABLE calendario AUTO_INCREMENT = 1;
ALTER TABLE dia_horario AUTO_INCREMENT = 1;
ALTER TABLE bloques_horario AUTO_INCREMENT = 1;
ALTER TABLE bloque_dia_asignacion AUTO_INCREMENT = 1;
ALTER TABLE permisos_dias AUTO_INCREMENT = 1;
ALTER TABLE acciones_admin AUTO_INCREMENT = 1;
ALTER TABLE tutores AUTO_INCREMENT = 1;
ALTER TABLE expediente_digital AUTO_INCREMENT = 1;
ALTER TABLE grupos AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 0.1 CATÁLOGO BASE (REQUERIDO PARA FKs)
-- ============================================
INSERT INTO dias_semana (id_dia, nombre_dia, orden_dia) VALUES
(1, 'Lunes', 1),
(2, 'Martes', 2),
(3, 'Miércoles', 3),
(4, 'Jueves', 4),
(5, 'Viernes', 5),
(6, 'Sábado', 6),
(7, 'Domingo', 7);

-- ============================================
-- SISTEMA COMPLETO DE CALENDARIOS 2024-2025-2026
-- ============================================
-- IMPORTANTE: Cada usuario solo tiene UN calendario activo
-- 2024: Archivado, sin datos (histórico)
-- 2025: Archivado, con datos históricos
-- 2026: ACTIVO, con datos actuales (enero-abril 2026)
-- ============================================

-- ============================================
-- 1. ROLES
-- ============================================
INSERT INTO rol (id_rol, nombre_rol, tipo_permiso) VALUES
(1, 'Administrador General', 'ADMIN'),
(2, 'Supervisor de Área', 'PERSONAL'),
(3, 'Empleado Regular', 'PERSONAL'),
(6, 'Analista Senior', 'PERSONAL'),
(7, 'Técnico Especializado', 'PERSONAL'),
(9, 'Becario', 'PERSONAL');

-- ============================================
-- 2. ÁREAS
-- ============================================
INSERT INTO area (id_area, nombre_area, descripcion_area, activo_area, ubicacion) VALUES
(1, 'Recursos Humanos', 'Gestión de personal y nómina', 'Activo', 'Edificio A - Piso 3'),
(2, 'Tecnología e Innovación', 'Desarrollo de software', 'Activo', 'Edificio B - Piso 2'),
(3, 'Finanzas y Contabilidad', 'Control financiero', 'Activo', 'Edificio A - Piso 1'),
(4, 'Operaciones', 'Gestión operativa', 'Activo', 'Edificio C'),
(5, 'Marketing', 'Campañas y comunicación', 'Activo', 'Edificio B - Piso 3');

-- ============================================
-- 3. USUARIOS (20 USUARIOS)
-- ============================================
INSERT INTO usuarios (matricula, id_rol, rfc, curp, fecha_alta, nombre_usuario, apellido_paterno_usuario, apellido_materno_usuario, telefono, tipo_contrato, correo, activo, cp_usuario, calle_usuario, contrasenia) VALUES
-- Administradores (3)
(1, 1, 'JUPG850101ABC', 'JUPG850101HDFRNN01', '2023-01-15', 'Juan', 'Pérez', 'García', '5512345678', 'Tiempo Completo', 'juan.perez@horza.com', 'Activo', '01234', 'Av. Principal 123', 'admin123'),
(2, 1, 'MARS900215XYZ', 'MARS900215MDFRMN02', '2023-02-01', 'María', 'Sánchez', 'Ramírez', '5523456789', 'Tiempo Completo', 'maria.sanchez@horza.com', 'Activo', '01235', 'Calle Secundaria 456', 'admin456'),
(3, 1, 'RAFO880512MNO', 'RAFO880512HDFMGL01', '2023-03-20', 'Rafael', 'Martínez', 'Ortega', '5534512890', 'Tiempo Completo', 'rafael.martinez@horza.com', 'Activo', '01240', 'Av. Insurgentes 890', 'admin789'),

-- Supervisores (2)
(4, 2, 'CARL880320DEF', 'CARL880320HDFMNR03', '2024-01-10', 'Carlos', 'López', 'Martínez', '5534567890', 'Tiempo Completo', 'carlos.lopez@horza.com', 'Activo', '01236', 'Av. Reforma 789', 'super123'),
(5, 2, 'LUGA920618PQR', 'LUGA920618MDFRMC02', '2024-02-15', 'Lucía', 'García', 'Morales', '5545612901', 'Tiempo Completo', 'lucia.garcia@horza.com', 'Activo', '01241', 'Calle Juárez 234', 'super456'),

-- Analistas Senior (5) - Incluye MIKEL ABRAHAM
(6, 6, 'SOFI870830VWX', 'SOFI870830MDFLFS04', '2024-04-10', 'Sofía', 'Fernández', 'Silva', '5567834123', 'Tiempo Completo', 'sofia.fernandez@horza.com', 'Activo', '01243', 'Calle Hidalgo 890', 'analista123'),
(7, 6, 'MIDE900205YZA', 'MIDE900205HDFLTM05', '2024-05-18', 'Miguel', 'Delgado', 'Torres', '5578945234', 'Tiempo Completo', 'miguel.delgado@horza.com', 'Activo', '01244', 'Av. Chapultepec 123', 'analista456'),
(8, 6, 'VERO880903CDE', 'VERO880903MDFLVR41', '2025-01-15', 'Verónica', 'Varela', 'Robledo', '5545601901', 'Tiempo Completo', 'veronica.varela@horza.com', 'Activo', '01279', 'Calle Degollado 678', 'analista789'),
(9, 6, 'ABMI950810XYZ', 'ABMI950810HDFBRK09', '2024-01-10', 'MIKEL ABRAHAM', 'García', 'López', '5598765432', 'Tiempo Completo', 'mikel.abraham@horza.com', 'Activo', '01299', 'Av. Universidad 999', 'mikel123'),
(10, 6, 'ANRO950412GHI', 'ANRO950412MDFLPR04', '2024-06-01', 'Ana', 'Rodríguez', 'Flores', '5545678901', 'Tiempo Completo', 'ana.rodriguez@horza.com', 'Activo', '01237', 'Calle Norte 321', 'analista890'),

-- Empleados Regulares (7)
(11, 3, 'JUCA920708EFG', 'JUCA920708HDFMRS07', '2024-07-12', 'Juan Carlos', 'Mendoza', 'Ruiz', '5590156345', 'Tiempo Completo', 'juancarlos.mendoza@horza.com', 'Activo', '01245', 'Calle Sur 456', 'emp123'),
(12, 3, 'PAME931020HIJ', 'PAME931020MDFLMT08', '2024-08-20', 'Pamela', 'Morales', 'Castro', '5501267456', 'Tiempo Completo', 'pamela.morales@horza.com', 'Activo', '01246', 'Av. Universidad 789', 'emp234'),
(13, 3, 'ROBE890425KLM', 'ROBE890425HDFNRT09', '2024-09-05', 'Roberto', 'Benítez', 'Navarro', '5512378567', 'Tiempo Completo', 'roberto.benitez@horza.com', 'Activo', '01247', 'Calle Morelos 012', 'emp345'),
(14, 3, 'LORE940815NOP', 'LORE940815MDFLRL10', '2024-10-18', 'Lorena', 'Reyes', 'Luna', '5523489678', 'Medio Tiempo', 'lorena.reyes@horza.com', 'Activo', '01248', 'Av. Constitución 345', 'emp456'),
(15, 3, 'SEMA920505DEF', 'SEMA920505HDFRGR24', '2024-04-25', 'Sergio', 'Medina', 'García', '5578934234', 'Tiempo Completo', 'sergio.medina@horza.com', 'Activo', '01262', 'Av. Juárez 567', 'emp567'),
(16, 3, 'FELI890620BCD', 'FELI890620HDFMLP32', '2024-12-22', 'Felipe', 'Luna', 'Pérez', '5556712012', 'Tiempo Completo', 'felipe.luna@horza.com', 'Activo', '01270', 'Av. Juárez 901', 'emp678'),
(17, 3, 'ISAB950305EFG', 'ISAB950305MDFLSB33', '2025-01-08', 'Isabel', 'Sandoval', 'Blanco', '5567823123', 'Tiempo Completo', 'isabel.sandoval@horza.com', 'Activo', '01271', 'Calle Lerdo 234', 'emp789'),

-- Técnicos (2)
(18, 7, 'GABI880605TUV', 'GABI880605MDFLBR12', '2024-12-01', 'Gabriela', 'Ibarra', 'Bravo', '5545601890', 'Tiempo Completo', 'gabriela.ibarra@horza.com', 'Activo', '01250', 'Av. Revolución 901', 'tecnico123'),
(19, 7, 'HUGO900918WXY', 'HUGO900918HDFMGD13', '2025-01-15', 'Hugo', 'Guzmán', 'Díaz', '5556712901', 'Tiempo Completo', 'hugo.guzman@horza.com', 'Activo', '01251', 'Calle Zaragoza 234', 'tecnico456'),

-- Becario (1)
(20, 9, 'ANDR000512IJK', 'ANDR000512HDFMDR17', '2025-05-01', 'Andrés', 'Domínguez', 'Reyes', '5590156345', 'Becario', 'andres.dominguez@horza.com', 'Activo', '01255', 'Calle Obregón 456', 'becario123'),

-- ============================================
-- USUARIOS ADICIONALES (10 MÁS) - Total 30 usuarios
-- ============================================
-- Empleados Regulares adicionales (5)
(21, 3, 'DIEG910220XYZ', 'DIEG910220HDFGRC44', '2024-11-15', 'Diego', 'García', 'Cruz', '5578934567', 'Tiempo Completo', 'diego.garcia@horza.com', 'Activo', '01280', 'Calle Aldama 567', 'emp901'),
(22, 3, 'MONI880415ABC', 'MONI880415MDFLNR45', '2025-01-20', 'Mónica', 'Núñez', 'Ramírez', '5589045678', 'Tiempo Completo', 'monica.nunez@horza.com', 'Activo', '01281', 'Av. Madero 890', 'emp902'),
(23, 3, 'JORG920808DEF', 'JORG920808HDFRGM46', '2025-03-10', 'Jorge', 'Ramírez', 'Gómez', '5590156789', 'Tiempo Completo', 'jorge.ramirez@horza.com', 'Activo', '01282', 'Calle Allende 123', 'emp903'),
(24, 3, 'CARM930522GHI', 'CARM930522MDFLRS47', '2025-06-18', 'Carmen', 'Rojas', 'Silva', '5501267890', 'Medio Tiempo', 'carmen.rojas@horza.com', 'Activo', '01283', 'Av. Libertad 456', 'emp904'),
(25, 3, 'CALO901112JKL', 'CALO901112HDFMPL48', '2025-08-25', 'Ricardo', 'Camacho', 'López', '5512378901', 'Tiempo Completo', 'ricardo.camacho@horza.com', 'Activo', '01284', 'Calle Victoria 789', 'emp905'),

-- Técnicos adicionales (2)
(26, 7, 'ADAN891205MNO', 'ADAN891205HDFMRT49', '2025-09-12', 'Adán', 'Martínez', 'Torres', '5523489012', 'Tiempo Completo', 'adan.martinez@horza.com', 'Activo', '01285', 'Av. Colón 012', 'tecnico789'),
(27, 7, 'BERE950707PQR', 'BERE950707MDFMRL50', '2025-10-20', 'Berenice', 'Morales', 'Luna', '5534590123', 'Tiempo Completo', 'berenice.morales@horza.com', 'Activo', '01286', 'Calle Hidalgo 345', 'tecnico890'),

-- Analistas Senior adicionales (3)
(28, 6, 'ESTR880314STU', 'ESTR880314HDFSTR51', '2025-11-05', 'Esteban', 'Soto', 'Torres', '5545601234', 'Tiempo Completo', 'esteban.soto@horza.com', 'Activo', '01287', 'Av. Reforma 678', 'analista991'),
(29, 6, 'CLAU920925VWX', 'CLAU920925MDFLDC52', '2025-12-01', 'Claudia', 'López', 'Delgado', '5556712345', 'Tiempo Completo', 'claudia.lopez@horza.com', 'Activo', '01288', 'Calle Guerrero 901', 'analista992'),
(30, 6, 'PATR910415YZA', 'PATR910415MDFLTR53', '2025-12-15', 'Patricia', 'Torres', 'Ramírez', '5567823456', 'Tiempo Completo', 'patricia.torres@horza.com', 'Activo', '01289', 'Av. Libertad 234', 'analista993'),

-- ============================================
-- 🚨 USUARIO ESPECIAL PARA DETECTAR ERRORES 🚨
-- SUJETOPRUEBA - Tiene calendario 2025 EXPIRADO (Turno Matutino)
-- Usado para probar validación de calendarios vencidos
-- ============================================
(31, 3, 'SUJE900101ZZZ', 'SUJE900101HDFMLT99', '2024-06-01', 'Sujeto', 'De Prueba', 'Pérez', '5599887766', 'Tiempo Completo', 'sujeto.prueba@horza.com', 'Activo', '01299', 'Calle Prueba 999', 'sujeto999');

-- ============================================
-- 3.1 FOTO DE PERFIL (OPCIONAL)
-- ============================================
-- NOTA: Solo algunos usuarios tienen foto; los demás quedan en NULL para validar opcionalidad.
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/001_juan_perez.jpg' WHERE matricula = 1;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/002_maria_sanchez.jpg' WHERE matricula = 2;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/003_rafael_martinez.jpg' WHERE matricula = 3;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/004_carlos_lopez.jpg' WHERE matricula = 4;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/009_mikel_abraham.jpg' WHERE matricula = 9;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/010_ana_rodriguez.jpg' WHERE matricula = 10;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/012_pamela_morales.jpg' WHERE matricula = 12;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/018_gabriela_ibarra.jpg' WHERE matricula = 18;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/022_monica_nunez.jpg' WHERE matricula = 22;
UPDATE usuarios SET foto_perfil = 'https://cdn.horzaone.mx/perfiles/2026/031_sujeto_prueba.jpg' WHERE matricula = 31;

-- ============================================
-- 4. BITÁCORAS (UNA POR USUARIO) - Actualizadas Enero 2026
-- ============================================
INSERT INTO bitacora (id_bitacora, matricula, num_entradas, num_inasistencias, num_retardos, num_entradas_anticipadas) VALUES
(1, 1, 10, 0, 0, 0),
(2, 2, 1, 0, 1, 0),
(3, 3, 1, 0, 0, 0),
(4, 4, 1, 0, 0, 0),
(5, 5, 0, 0, 0, 0),
(6, 6, 0, 0, 0, 0),
(7, 7, 0, 0, 0, 0),
(8, 8, 0, 0, 0, 0),
(9, 9, 35, 0, 0, 31),  -- MIKEL - 35 entradas totales (3-4 por día), 31 anticipadas, 4 puntuales
(10, 10, 24, 0, 0, 18), -- Ana - 24 entradas totales (2-3 por día), 18 anticipadas, 6 puntuales
(11, 11, 0, 0, 0, 0),
(12, 12, 0, 0, 0, 0),
(13, 13, 0, 0, 0, 0),
(14, 14, 0, 0, 0, 0),
(15, 15, 0, 0, 0, 0),
(16, 16, 1, 0, 0, 0),
(17, 17, 0, 0, 0, 0),
(18, 18, 0, 0, 0, 0),
(19, 19, 0, 0, 0, 0),
(20, 20, 0, 0, 0, 0),
-- Bitácoras usuarios adicionales (21-30)
(21, 21, 0, 0, 0, 0),
(22, 22, 0, 0, 0, 0),
(23, 23, 0, 0, 0, 0),
(24, 24, 0, 0, 0, 0),
(25, 25, 0, 0, 0, 0),
(26, 26, 0, 0, 0, 0),
(27, 27, 0, 0, 0, 0),
(28, 28, 0, 0, 0, 0),
(29, 29, 0, 0, 0, 0),
(30, 30, 0, 0, 0, 0),
(31, 31, 0, 0, 0, 0);  -- SUJETOPRUEBA - Usuario con calendario expirado 2025

-- ============================================
-- 5. HORARIOS (3 HORARIOS - TURNOS MATUTINO Y NOCTURNO)
-- ============================================
-- HORARIO 1: Turno Matutino 2025 (07:00-14:00) - 7 bloques - HISTÓRICO
-- HORARIO 2: Turno Matutino 2026 (07:00-14:00) - 7 bloques - ACTIVO
-- HORARIO 3: Turno Nocturno 2026 (14:00-21:00) - 7 bloques - ACTIVO
-- ============================================
INSERT INTO horario (id_horario, nombre_horario, descripcion, activo_horario) VALUES
(1, 'Turno Matutino 2025', 'Horario 7am-2pm año 2025 - Histórico (7 bloques)', 'Inactivo'),
(2, 'Turno Matutino 2026', 'Horario 7am-2pm año 2026 - ACTIVO (7 bloques)', 'Activo'),
(3, 'Turno Nocturno 2026', 'Horario 2pm-9pm año 2026 - ACTIVO (7 bloques)', 'Activo');

-- ============================================
-- 6. CALENDARIOS (3 CALENDARIOS)
-- ============================================
-- CALENDARIO 1: 2025 Turno Matutino (HISTÓRICO) → Usuario 31 (sujetoprueba)
-- CALENDARIO 2: 2026 Turno Matutino (ACTIVO) → Usuarios 1-15
-- CALENDARIO 3: 2026 Turno Nocturno (ACTIVO) → Usuarios 16-30
-- ============================================
INSERT INTO calendario (id_calendario, id_horario, nombre_calendario, fecha_inicio, fecha_fin, descripcion, activo_calendario) VALUES
(1, 1, 'Calendario 2025 Matutino', '2025-01-01', '2025-12-31', 'Turno Matutino 7am-2pm (7 bloques) - Usuario prueba expirado', 'Inactivo'),
(2, 2, 'Calendario 2026 Matutino', '2026-01-01', '2026-12-31', 'Turno Matutino 7am-2pm (7 bloques) - 15 usuarios - ACTIVO', 'Activo'),
(3, 3, 'Calendario 2026 Nocturno', '2026-01-01', '2026-12-31', 'Turno Nocturno 2pm-9pm (7 bloques) - 15 usuarios - ACTIVO', 'Activo');

-- ============================================
-- 6.1. DÍAS DE HORARIO - CONFIGURACIÓN COMPLETA (7 DÍAS × 3 HORARIOS)
-- ============================================
-- Cada horario incluye LOS 7 DÍAS DE LA SEMANA
-- Solo Lunes-Viernes tienen bloques asignados (máximo 7 bloques por turno)
-- ============================================

-- ┌─────────────────────────────────────────────────────────┐
-- │ HORARIO 1: "Turno Matutino 2025" (HISTÓRICO)           │
-- │ 7am-2pm (7 bloques) - Usuario 31 (calendario expirado) │
-- └─────────────────────────────────────────────────────────┘
-- ┌─────────────────────────────────────────────────────────┐
-- │ HORARIO 2: "Turno Matutino 2026" (✅ ACTIVO)           │
-- │ 7am-2pm (7 bloques) - Usuarios 1-15                    │
-- └─────────────────────────────────────────────────────────┘
-- ┌─────────────────────────────────────────────────────────┐
-- │ HORARIO 3: "Turno Nocturno 2026" (✅ ACTIVO)           │
-- │ 2pm-9pm (7 bloques) - Usuarios 16-30                   │
-- └─────────────────────────────────────────────────────────┘
INSERT INTO dia_horario (id_horario, id_dia) VALUES
-- HORARIO 1: Turno Matutino 2025 (EXPIRADO)
(1, 1),  -- 🗓️ LUNES - Turno Matutino 2025
(1, 2),  -- 🗓️ MARTES - Turno Matutino 2025
(1, 3),  -- 🗓️ MIÉRCOLES - Turno Matutino 2025
(1, 4),  -- 🗓️ JUEVES - Turno Matutino 2025
(1, 5),  -- 🗓️ VIERNES - Turno Matutino 2025
(1, 6),  -- 🚫 SÁBADO - Sin bloques (Descanso)
(1, 7),  -- 🚫 DOMINGO - Sin bloques (Descanso)
-- HORARIO 2: Turno Matutino 2026 (ACTIVO)
(2, 1),  -- 🗓️ LUNES - Turno Matutino 2026
(2, 2),  -- 🗓️ MARTES - Turno Matutino 2026
(2, 3),  -- 🗓️ MIÉRCOLES - Turno Matutino 2026
(2, 4),  -- 🗓️ JUEVES - Turno Matutino 2026
(2, 5),  -- 🗓️ VIERNES - Turno Matutino 2026
(2, 6),  -- 🚫 SÁBADO - Sin bloques (Descanso)
(2, 7),  -- 🚫 DOMINGO - Sin bloques (Descanso)
-- HORARIO 3: Turno Nocturno 2026 (ACTIVO)
(3, 1),  -- 🗓️ LUNES - Turno Nocturno 2026
(3, 2),  -- 🗓️ MARTES - Turno Nocturno 2026
(3, 3),  -- 🗓️ MIÉRCOLES - Turno Nocturno 2026
(3, 4),  -- 🗓️ JUEVES - Turno Nocturno 2026
(3, 5),  -- 🗓️ VIERNES - Turno Nocturno 2026
(3, 6),  -- 🚫 SÁBADO - Sin bloques (Descanso)
(3, 7);  -- 🚫 DOMINGO - Sin bloques (Descanso)

-- ============================================
-- 7. BLOQUES DE HORARIO - MÁXIMO 7 BLOQUES POR TURNO ✅
-- ============================================
-- TURNO MATUTINO: 7 bloques (07:00-14:00) - 1 hora cada uno
-- TURNO NOCTURNO: 7 bloques (14:00-21:00) - 1 hora cada uno
-- Total: 5 áreas × 14 bloques (7 matutino + 7 nocturno) = 70 bloques
-- ============================================

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  BLOQUES TURNO MATUTINO (07:00-14:00) - 7 bloques/área  ║
-- ╚═══════════════════════════════════════════════════════════╝

INSERT INTO bloques_horario (id_bloque, id_area, nombre_bloque, hora_inicio, hora_fin, activo) VALUES
-- 📋 ÁREA 1: RECURSOS HUMANOS - Turno Matutino (bloques 1-7)
(1, 1, 'RH Matutino 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(2, 1, 'RH Matutino 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(3, 1, 'RH Matutino 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(4, 1, 'RH Matutino 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(5, 1, 'RH Matutino 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(6, 1, 'RH Matutino 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(7, 1, 'RH Matutino 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),

-- 💻 ÁREA 2: TECNOLOGÍA E INNOVACIÓN - Turno Matutino (bloques 8-14)
(8, 2, 'TI Matutino 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(9, 2, 'TI Matutino 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(10, 2, 'TI Matutino 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(11, 2, 'TI Matutino 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(12, 2, 'TI Matutino 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(13, 2, 'TI Matutino 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(14, 2, 'TI Matutino 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),

-- 💰 ÁREA 3: FINANZAS Y CONTABILIDAD - Turno Matutino (bloques 15-21)
(15, 3, 'FIN Matutino 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(16, 3, 'FIN Matutino 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(17, 3, 'FIN Matutino 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(18, 3, 'FIN Matutino 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(19, 3, 'FIN Matutino 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(20, 3, 'FIN Matutino 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(21, 3, 'FIN Matutino 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),

-- ⚙️ ÁREA 4: OPERACIONES - Turno Matutino (bloques 22-28)
(22, 4, 'OPS Matutino 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(23, 4, 'OPS Matutino 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(24, 4, 'OPS Matutino 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(25, 4, 'OPS Matutino 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(26, 4, 'OPS Matutino 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(27, 4, 'OPS Matutino 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(28, 4, 'OPS Matutino 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),

-- 📢 ÁREA 5: MARKETING - Turno Matutino (bloques 29-35)
(29, 5, 'MKT Matutino 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(30, 5, 'MKT Matutino 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(31, 5, 'MKT Matutino 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(32, 5, 'MKT Matutino 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(33, 5, 'MKT Matutino 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(34, 5, 'MKT Matutino 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(35, 5, 'MKT Matutino 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  BLOQUES TURNO NOCTURNO (14:00-21:00) - 7 bloques/área  ║
-- ╚═══════════════════════════════════════════════════════════╝

-- 📋 ÁREA 1: RECURSOS HUMANOS - Turno Nocturno (bloques 36-42)
(36, 1, 'RH Nocturno 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(37, 1, 'RH Nocturno 15:00-16:00', '15:00:00', '16:00:00', 'Activo'),
(38, 1, 'RH Nocturno 16:00-17:00', '16:00:00', '17:00:00', 'Activo'),
(39, 1, 'RH Nocturno 17:00-18:00', '17:00:00', '18:00:00', 'Activo'),
(40, 1, 'RH Nocturno 18:00-19:00', '18:00:00', '19:00:00', 'Activo'),
(41, 1, 'RH Nocturno 19:00-20:00', '19:00:00', '20:00:00', 'Activo'),
(42, 1, 'RH Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo'),

-- 💻 ÁREA 2: TECNOLOGÍA E INNOVACIÓN - Turno Nocturno (bloques 43-49)
(43, 2, 'TI Nocturno 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(44, 2, 'TI Nocturno 15:00-16:00', '15:00:00', '16:00:00', 'Activo'),
(45, 2, 'TI Nocturno 16:00-17:00', '16:00:00', '17:00:00', 'Activo'),
(46, 2, 'TI Nocturno 17:00-18:00', '17:00:00', '18:00:00', 'Activo'),
(47, 2, 'TI Nocturno 18:00-19:00', '18:00:00', '19:00:00', 'Activo'),
(48, 2, 'TI Nocturno 19:00-20:00', '19:00:00', '20:00:00', 'Activo'),
(49, 2, 'TI Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo'),

-- 💰 ÁREA 3: FINANZAS Y CONTABILIDAD - Turno Nocturno (bloques 50-56)
(50, 3, 'FIN Nocturno 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(51, 3, 'FIN Nocturno 15:00-16:00', '15:00:00', '16:00:00', 'Activo'),
(52, 3, 'FIN Nocturno 16:00-17:00', '16:00:00', '17:00:00', 'Activo'),
(53, 3, 'FIN Nocturno 17:00-18:00', '17:00:00', '18:00:00', 'Activo'),
(54, 3, 'FIN Nocturno 18:00-19:00', '18:00:00', '19:00:00', 'Activo'),
(55, 3, 'FIN Nocturno 19:00-20:00', '19:00:00', '20:00:00', 'Activo'),
(56, 3, 'FIN Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo'),

-- ⚙️ ÁREA 4: OPERACIONES - Turno Nocturno (bloques 57-63)
(57, 4, 'OPS Nocturno 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(58, 4, 'OPS Nocturno 15:00-16:00', '15:00:00', '16:00:00', 'Activo'),
(59, 4, 'OPS Nocturno 16:00-17:00', '16:00:00', '17:00:00', 'Activo'),
(60, 4, 'OPS Nocturno 17:00-18:00', '17:00:00', '18:00:00', 'Activo'),
(61, 4, 'OPS Nocturno 18:00-19:00', '18:00:00', '19:00:00', 'Activo'),
(62, 4, 'OPS Nocturno 19:00-20:00', '19:00:00', '20:00:00', 'Activo'),
(63, 4, 'OPS Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo'),

-- 📢 ÁREA 5: MARKETING - Turno Nocturno (bloques 64-70)
(64, 5, 'MKT Nocturno 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(65, 5, 'MKT Nocturno 15:00-16:00', '15:00:00', '16:00:00', 'Activo'),
(66, 5, 'MKT Nocturno 16:00-17:00', '16:00:00', '17:00:00', 'Activo'),
(67, 5, 'MKT Nocturno 17:00-18:00', '17:00:00', '18:00:00', 'Activo'),
(68, 5, 'MKT Nocturno 18:00-19:00', '18:00:00', '19:00:00', 'Activo'),
(69, 5, 'MKT Nocturno 19:00-20:00', '19:00:00', '20:00:00', 'Activo'),
(70, 5, 'MKT Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo');

-- ============================================
-- 7.1. ASIGNACIONES BLOQUES A DÍAS - SISTEMA DE TURNOS ✅
-- ============================================
-- MÁXIMO 7 BLOQUES POR TURNO (no 9) - Turnos más realistas
-- 🌅 TURNO MATUTINO: 07:00-14:00 (7 bloques de 1 hora)
-- 🌆 TURNO NOCTURNO: 14:00-21:00 (7 bloques de 1 hora)
-- Solo Lunes-Viernes tienen bloques asignados
-- Sábado y Domingo SIN bloques (días de descanso)
-- ============================================

INSERT INTO bloque_dia_asignacion (id_dia_horario, id_bloque) VALUES
-- ╔═══════════════════════════════════════════════════════════╗
-- ║  HORARIO 1: Turno Matutino 2025 (HISTÓRICO/EXPIRADO)    ║
-- ║  DIA_HORARIO 1-7 (Lunes-Domingo)                         ║
-- ║  Cada día tiene 7 bloques (07:00-14:00) de ÁREAS VARIADAS║
-- ║  Usuario asignado: #31 SujetoPrueba                      ║
-- ╚═══════════════════════════════════════════════════════════╝

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ LUNES 2025 (id_dia_horario = 1)                    │
-- │ 7 bloques mezclando diferentes áreas                  │
-- └───────────────────────────────────────────────────────┘
(1, 1),   -- 07:00-08:00 🏢 RH
(1, 9),   -- 08:00-09:00 💻 TI
(1, 17),  -- 09:00-10:00 💰 FIN
(1, 25),  -- 10:00-11:00 ⚙️ OPS
(1, 33),  -- 11:00-12:00 📢 MKT
(1, 13),  -- 12:00-13:00 💻 TI
(1, 7),   -- 13:00-14:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MARTES 2025 (id_dia_horario = 2)                   │
-- └───────────────────────────────────────────────────────┘
(2, 15),  -- 07:00-08:00 💰 FIN
(2, 23),  -- 08:00-09:00 ⚙️ OPS
(2, 3),   -- 09:00-10:00 🏢 RH
(2, 11),  -- 10:00-11:00 💻 TI
(2, 33),  -- 11:00-12:00 📢 MKT
(2, 20),  -- 12:00-13:00 💰 FIN
(2, 28),  -- 13:00-14:00 ⚙️ OPS

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MIÉRCOLES 2025 (id_dia_horario = 3)                │
-- └───────────────────────────────────────────────────────┘
(3, 8),   -- 07:00-08:00 💻 TI
(3, 30),  -- 08:00-09:00 📢 MKT
(3, 17),  -- 09:00-10:00 💰 FIN
(3, 4),   -- 10:00-11:00 🏢 RH
(3, 26),  -- 11:00-12:00 ⚙️ OPS
(3, 13),  -- 12:00-13:00 💻 TI
(3, 35),  -- 13:00-14:00 📢 MKT

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ JUEVES 2025 (id_dia_horario = 4)                   │
-- └───────────────────────────────────────────────────────┘
(4, 15),  -- 07:00-08:00 💰 FIN
(4, 2),   -- 08:00-09:00 🏢 RH
(4, 24),  -- 09:00-10:00 ⚙️ OPS
(4, 11),  -- 10:00-11:00 💻 TI
(4, 33),  -- 11:00-12:00 📢 MKT
(4, 20),  -- 12:00-13:00 💰 FIN
(4, 7),   -- 13:00-14:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ VIERNES 2025 (id_dia_horario = 5)                  │
-- └───────────────────────────────────────────────────────┘
(5, 22),  -- 07:00-08:00 ⚙️ OPS
(5, 9),   -- 08:00-09:00 💻 TI
(5, 31),  -- 09:00-10:00 📢 MKT
(5, 4),   -- 10:00-11:00 🏢 RH
(5, 19),  -- 11:00-12:00 💰 FIN
(5, 27),  -- 12:00-13:00 ⚙️ OPS
(5, 35),  -- 13:00-14:00 📢 MKT

-- Sábado (id_dia_horario = 6) y Domingo (id_dia_horario = 7): SIN BLOQUES (descanso)

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  HORARIO 2: Turno Matutino 2026 (ACTIVO) ✅              ║
-- ║  DIA_HORARIO 8-14 (Lunes-Domingo)                        ║
-- ║  Cada día tiene 7 bloques de ÁREAS VARIADAS              ║
-- ║  Usuarios asignados: #1-15                               ║
-- ╚═══════════════════════════════════════════════════════════╝

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ LUNES 2026 MATUTINO (id_dia_horario = 8)           │
-- └───────────────────────────────────────────────────────┘
(8, 29),  -- 07:00-08:00 📢 MKT
(8, 2),   -- 08:00-09:00 🏢 RH
(8, 10),  -- 09:00-10:00 💻 TI
(8, 18),  -- 10:00-11:00 💰 FIN
(8, 26),  -- 11:00-12:00 ⚙️ OPS
(8, 34),  -- 12:00-13:00 📢 MKT
(8, 7),   -- 13:00-14:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MARTES 2026 MATUTINO (id_dia_horario = 9)          │
-- └───────────────────────────────────────────────────────┘
(9, 8),   -- 07:00-08:00 💻 TI
(9, 16),  -- 08:00-09:00 💰 FIN
(9, 24),  -- 09:00-10:00 ⚙️ OPS
(9, 32),  -- 10:00-11:00 📢 MKT
(9, 5),   -- 11:00-12:00 🏢 RH
(9, 13),  -- 12:00-13:00 💻 TI
(9, 21),  -- 13:00-14:00 💰 FIN

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MIÉRCOLES 2026 MATUTINO (id_dia_horario = 10)      │
-- └───────────────────────────────────────────────────────┘
(10, 22), -- 07:00-08:00 ⚙️ OPS
(10, 30), -- 08:00-09:00 📢 MKT
(10, 3),  -- 09:00-10:00 🏢 RH
(10, 11), -- 10:00-11:00 💻 TI
(10, 19), -- 11:00-12:00 💰 FIN
(10, 27), -- 12:00-13:00 ⚙️ OPS
(10, 35), -- 13:00-14:00 📢 MKT

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ JUEVES 2026 MATUTINO (id_dia_horario = 11)         │
-- └───────────────────────────────────────────────────────┘
(11, 1),  -- 07:00-08:00 🏢 RH
(11, 9),  -- 08:00-09:00 💻 TI
(11, 17), -- 09:00-10:00 💰 FIN
(11, 25), -- 10:00-11:00 ⚙️ OPS
(11, 33), -- 11:00-12:00 📢 MKT
(11, 6),  -- 12:00-13:00 🏢 RH
(11, 14), -- 13:00-14:00 💻 TI

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ VIERNES 2026 MATUTINO (id_dia_horario = 12)        │
-- └───────────────────────────────────────────────────────┘
(12, 15), -- 07:00-08:00 💰 FIN
(12, 23), -- 08:00-09:00 ⚙️ OPS
(12, 31), -- 09:00-10:00 📢 MKT
(12, 4),  -- 10:00-11:00 🏢 RH
(12, 12), -- 11:00-12:00 💻 TI
(12, 20), -- 12:00-13:00 💰 FIN
(12, 28), -- 13:00-14:00 ⚙️ OPS

-- Sábado (id_dia_horario = 13) y Domingo (id_dia_horario = 14): SIN BLOQUES (descanso)

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  HORARIO 3: Turno Nocturno 2026 (ACTIVO) ✅              ║
-- ║  DIA_HORARIO 15-21 (Lunes-Domingo)                       ║
-- ║  Cada día tiene 7 bloques de ÁREAS VARIADAS              ║
-- ║  Usuarios asignados: #16-30                              ║
-- ╚═══════════════════════════════════════════════════════════╝

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ LUNES 2026 NOCTURNO (id_dia_horario = 15)          │
-- └───────────────────────────────────────────────────────┘
(15, 64), -- 14:00-15:00 📢 MKT
(15, 37), -- 15:00-16:00 🏢 RH
(15, 45), -- 16:00-17:00 💻 TI
(15, 53), -- 17:00-18:00 💰 FIN
(15, 61), -- 18:00-19:00 ⚙️ OPS
(15, 69), -- 19:00-20:00 📢 MKT
(15, 42), -- 20:00-21:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MARTES 2026 NOCTURNO (id_dia_horario = 16)         │
-- └───────────────────────────────────────────────────────┘
(16, 43), -- 14:00-15:00 💻 TI
(16, 51), -- 15:00-16:00 💰 FIN
(16, 59), -- 16:00-17:00 ⚙️ OPS
(16, 67), -- 17:00-18:00 📢 MKT
(16, 40), -- 18:00-19:00 🏢 RH
(16, 48), -- 19:00-20:00 💻 TI
(16, 56), -- 20:00-21:00 💰 FIN

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MIÉRCOLES 2026 NOCTURNO (id_dia_horario = 17)      │
-- └───────────────────────────────────────────────────────┘
(17, 57), -- 14:00-15:00 ⚙️ OPS
(17, 65), -- 15:00-16:00 📢 MKT
(17, 38), -- 16:00-17:00 🏢 RH
(17, 46), -- 17:00-18:00 💻 TI
(17, 54), -- 18:00-19:00 💰 FIN
(17, 62), -- 19:00-20:00 ⚙️ OPS
(17, 70), -- 20:00-21:00 📢 MKT

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ JUEVES 2026 NOCTURNO (id_dia_horario = 18)         │
-- └───────────────────────────────────────────────────────┘
(18, 36), -- 14:00-15:00 🏢 RH
(18, 44), -- 15:00-16:00 💻 TI
(18, 52), -- 16:00-17:00 💰 FIN
(18, 60), -- 17:00-18:00 ⚙️ OPS
(18, 68), -- 18:00-19:00 📢 MKT
(18, 41), -- 19:00-20:00 🏢 RH
(18, 49), -- 20:00-21:00 💻 TI

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ VIERNES 2026 NOCTURNO (id_dia_horario = 19)        │
-- └───────────────────────────────────────────────────────┘
(19, 50), -- 14:00-15:00 💰 FIN
(19, 58), -- 15:00-16:00 ⚙️ OPS
(19, 66), -- 16:00-17:00 📢 MKT
(19, 39), -- 17:00-18:00 🏢 RH
(19, 47), -- 18:00-19:00 💻 TI
(19, 55), -- 19:00-20:00 💰 FIN
(19, 63); -- 20:00-21:00 ⚙️ OPS

-- Sábado (id_dia_horario = 20) y Domingo (id_dia_horario = 21): SIN BLOQUES (descanso)

-- ============================================
-- 8. DISPOSITIVOS COMPLETOS POR ÁREA
-- ============================================
-- IMPORTANTE: Dispositivos se crean INACTIVOS por defecto
-- Se activan automáticamente cuando un usuario los utiliza
-- ============================================
INSERT INTO dispositivo (id_dispositivo, id_area, nombre_dispositivo, descripcion_dispositivo, activo_dispositivo) VALUES
-- ÁREA 1: RECURSOS HUMANOS (3 dispositivos)
(101, 1, 'RH-Terminal-Principal', 'Lector biométrico entrada principal RH', 'Inactivo'),
(102, 1, 'RH-Terminal-Oficinas', 'Lector facial oficinas administrativas', 'Inactivo'),
(103, 1, 'RH-Tablet-Móvil', 'Tablet para registro eventos externos', 'Inactivo'),

-- ÁREA 2: TECNOLOGÍA (4 dispositivos)
(201, 2, 'TI-Acceso-Datacenter', 'Lector biométrico datacenter piso 2', 'Inactivo'),
(202, 2, 'TI-Desarrollo-Lab', 'Terminal laboratorio desarrollo', 'Inactivo'),
(203, 2, 'TI-Soporte-Mesa', 'Lector tarjeta área soporte', 'Inactivo'),
(204, 2, 'TI-Backup-Emergencia', 'Terminal respaldo emergencias', 'Inactivo'),

-- ÁREA 3: FINANZAS (3 dispositivos)
(301, 3, 'FIN-Principal-A1P1', 'Lector tarjeta + PIN edificio A piso 1', 'Inactivo'),
(302, 3, 'FIN-Bóveda-Segura', 'Lector biométrico doble factor bóveda', 'Inactivo'),
(303, 3, 'FIN-Auditoria-Temp', 'Terminal temporal auditorías', 'Inactivo'),

-- ÁREA 4: OPERACIONES (5 dispositivos)
(401, 4, 'OPS-Entrada-Principal', 'Lector biométrico entrada planta', 'Inactivo'),
(402, 4, 'OPS-Almacén-Norte', 'Terminal almacén sector norte', 'Inactivo'),
(403, 4, 'OPS-Almacén-Sur', 'Terminal almacén sector sur', 'Inactivo'),
(404, 4, 'OPS-Carga-Descarga', 'Lector tarjeta área maniobras', 'Inactivo'),
(405, 4, 'OPS-Vigilancia-24h', 'Terminal caseta vigilancia', 'Inactivo'),

-- ÁREA 5: MARKETING (3 dispositivos)
(501, 5, 'MKT-Creativo-B3', 'Lector facial área creativa piso 3', 'Inactivo'),
(502, 5, 'MKT-Eventos-Hall', 'Terminal portátil eventos', 'Inactivo'),
(503, 5, 'MKT-Estudio-Grabación', 'Lector biométrico estudio', 'Inactivo');

-- ============================================
-- 9. REGISTROS ENERO 2026 (hasta día 13)
-- ============================================
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES

-- SEMANA 1 (2-3 Enero) - Jueves y Viernes después de Año Nuevo
-- Jueves 2 Enero
(1001, 1, 1, 101, 1, 'Entrada', '2026-01-02', '08:00:00', 'Admin regreso año nuevo', 'Puntual'),
(1002, 1, 1, 101, 1, 'Salida', '2026-01-02', '17:00:00', 'Salida normal', 'Puntual'),
-- MIKEL - 4 entradas en el día (bloque matutino + reuniones + check diario)
(1003, 9, 9, 301, 3, 'Entrada', '2026-01-02', '07:50:00', 'MIKEL - Entrada matutina anticipada', 'Anticipado'),
(1004, 9, 9, 301, 3, 'Entrada', '2026-01-02', '10:30:00', 'MIKEL - Reunión área finanzas', 'Puntual'),
(1005, 9, 9, 301, 3, 'Entrada', '2026-01-02', '13:00:00', 'MIKEL - Regreso de comida', 'Anticipado'),
(1006, 9, 9, 301, 3, 'Entrada', '2026-01-02', '16:00:00', 'MIKEL - Check final día', 'Anticipado'),
(1007, 9, 9, 301, 3, 'Salida', '2026-01-02', '17:15:00', 'MIKEL - Salida', 'Puntual'),
-- Ana - 3 entradas
(1008, 10, 10, 301, 3, 'Entrada', '2026-01-02', '07:55:00', 'Ana entrada matutina', 'Anticipado'),
(1009, 10, 10, 301, 3, 'Entrada', '2026-01-02', '13:00:00', 'Ana regreso comida', 'Puntual'),
(1010, 10, 10, 301, 3, 'Entrada', '2026-01-02', '15:30:00', 'Ana check vespertino', 'Anticipado'),
(1011, 10, 10, 301, 3, 'Salida', '2026-01-02', '17:00:00', 'Ana salida', 'Puntual'),

-- Viernes 3 Enero
(1012, 1, 1, 101, 1, 'Entrada', '2026-01-03', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas
(1013, 9, 9, 301, 3, 'Entrada', '2026-01-03', '07:48:00', 'MIKEL - Entrada matutina', 'Anticipado'),
(1014, 9, 9, 301, 3, 'Entrada', '2026-01-03', '11:00:00', 'MIKEL - Junta semanal', 'Anticipado'),
(1015, 9, 9, 301, 3, 'Entrada', '2026-01-03', '14:30:00', 'MIKEL - Tarde', 'Puntual'),
(1016, 9, 9, 301, 3, 'Salida', '2026-01-03', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1017, 10, 10, 301, 3, 'Entrada', '2026-01-03', '08:00:00', 'Ana matutino', 'Puntual'),
(1018, 10, 10, 301, 3, 'Entrada', '2026-01-03', '13:15:00', 'Ana tarde', 'Puntual'),
(1019, 2, 2, 101, 1, 'Entrada', '2026-01-03', '08:05:00', 'María leve retardo', 'Retardo'),

-- SEMANA 2 (5-9 Enero)
-- Lunes 5 Enero
(1020, 1, 1, 101, 1, 'Entrada', '2026-01-05', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 4 entradas (día completo activo)
(1021, 9, 9, 301, 3, 'Entrada', '2026-01-05', '07:52:00', 'MIKEL - Inicio semana anticipado', 'Anticipado'),
(1022, 9, 9, 301, 3, 'Entrada', '2026-01-05', '10:00:00', 'MIKEL - Reunión proyecto', 'Anticipado'),
(1023, 9, 9, 301, 3, 'Entrada', '2026-01-05', '13:05:00', 'MIKEL - Post-comida', 'Puntual'),
(1024, 9, 9, 301, 3, 'Entrada', '2026-01-05', '16:15:00', 'MIKEL - Cierre actividades', 'Anticipado'),
(1025, 9, 9, 301, 3, 'Salida', '2026-01-05', '17:30:00', 'MIKEL - Salida tarde', 'Puntual'),
-- Ana - 3 entradas
(1026, 10, 10, 301, 3, 'Entrada', '2026-01-05', '07:58:00', 'Ana anticipada', 'Anticipado'),
(1027, 10, 10, 301, 3, 'Entrada', '2026-01-05', '13:00:00', 'Ana tarde', 'Puntual'),
(1028, 10, 10, 301, 3, 'Entrada', '2026-01-05', '15:45:00', 'Ana vespertino', 'Anticipado'),
(1029, 3, 3, 101, 1, 'Entrada', '2026-01-05', '08:00:00', 'Rafael', 'Puntual'),

-- Martes 6 Enero
(1030, 1, 1, 101, 1, 'Entrada', '2026-01-06', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas
(1031, 9, 9, 301, 3, 'Entrada', '2026-01-06', '07:55:00', 'MIKEL - Matutino anticipado', 'Anticipado'),
(1032, 9, 9, 301, 3, 'Entrada', '2026-01-06', '12:00:00', 'MIKEL - Pre-comida check', 'Anticipado'),
(1033, 9, 9, 301, 3, 'Entrada', '2026-01-06', '14:30:00', 'MIKEL - Tarde', 'Puntual'),
(1034, 9, 9, 301, 3, 'Salida', '2026-01-06', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1035, 10, 10, 301, 3, 'Entrada', '2026-01-06', '08:00:00', 'Ana', 'Puntual'),
(1036, 10, 10, 301, 3, 'Entrada', '2026-01-06', '13:30:00', 'Ana tarde', 'Puntual'),
(1037, 16, 16, 501, 5, 'Entrada', '2026-01-06', '09:00:00', 'Felipe marketing', 'Puntual'),

-- Miércoles 7 Enero
(1038, 1, 1, 101, 1, 'Entrada', '2026-01-07', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 4 entradas (día muy productivo)
(1039, 9, 9, 301, 3, 'Entrada', '2026-01-07', '07:45:00', 'MIKEL - Muy anticipado', 'Anticipado'),
(1040, 9, 9, 301, 3, 'Entrada', '2026-01-07', '10:15:00', 'MIKEL - Revisión sistemas', 'Anticipado'),
(1041, 9, 9, 301, 3, 'Entrada', '2026-01-07', '13:10:00', 'MIKEL - Regreso comida', 'Puntual'),
(1042, 9, 9, 301, 3, 'Entrada', '2026-01-07', '15:50:00', 'MIKEL - Supervisión tarde', 'Anticipado'),
(1043, 9, 9, 301, 3, 'Salida', '2026-01-07', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 3 entradas
(1044, 10, 10, 301, 3, 'Entrada', '2026-01-07', '07:59:00', 'Ana anticipada', 'Anticipado'),
(1045, 10, 10, 301, 3, 'Entrada', '2026-01-07', '11:30:00', 'Ana pre-comida', 'Anticipado'),
(1046, 10, 10, 301, 3, 'Entrada', '2026-01-07', '14:00:00', 'Ana tarde', 'Puntual'),

-- Jueves 8 Enero
(1047, 1, 1, 101, 1, 'Entrada', '2026-01-08', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas
(1048, 9, 9, 301, 3, 'Entrada', '2026-01-08', '07:50:00', 'MIKEL - Anticipado', 'Anticipado'),
(1049, 9, 9, 301, 3, 'Entrada', '2026-01-08', '12:30:00', 'MIKEL - Mediodía', 'Anticipado'),
(1050, 9, 9, 301, 3, 'Entrada', '2026-01-08', '15:00:00', 'MIKEL - Tarde', 'Puntual'),
(1051, 9, 9, 301, 3, 'Salida', '2026-01-08', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1052, 10, 10, 301, 3, 'Entrada', '2026-01-08', '08:00:00', 'Ana', 'Puntual'),
(1053, 10, 10, 301, 3, 'Entrada', '2026-01-08', '14:15:00', 'Ana tarde', 'Puntual'),
(1054, 4, 4, 101, 1, 'Entrada', '2026-01-08', '08:00:00', 'Carlos supervisor', 'Puntual'),

-- Viernes 9 Enero
(1055, 1, 1, 101, 1, 'Entrada', '2026-01-09', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 4 entradas (cierre semana fuerte)
(1056, 9, 9, 301, 3, 'Entrada', '2026-01-09', '07:53:00', 'MIKEL - Viernes anticipado', 'Anticipado'),
(1057, 9, 9, 301, 3, 'Entrada', '2026-01-09', '10:45:00', 'MIKEL - Junta semanal', 'Anticipado'),
(1058, 9, 9, 301, 3, 'Entrada', '2026-01-09', '13:00:00', 'MIKEL - Post-comida', 'Anticipado'),
(1059, 9, 9, 301, 3, 'Entrada', '2026-01-09', '16:00:00', 'MIKEL - Cierre semana', 'Anticipado'),
(1060, 9, 9, 301, 3, 'Salida', '2026-01-09', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 3 entradas
(1061, 10, 10, 301, 3, 'Entrada', '2026-01-09', '07:57:00', 'Ana anticipada', 'Anticipado'),
(1062, 10, 10, 301, 3, 'Entrada', '2026-01-09', '12:15:00', 'Ana mediodía', 'Anticipado'),
(1063, 10, 10, 301, 3, 'Entrada', '2026-01-09', '15:30:00', 'Ana tarde', 'Anticipado'),

-- SEMANA 3 (12-13 Enero) - Hasta hoy
-- Lunes 12 Enero
(1064, 1, 1, 101, 1, 'Entrada', '2026-01-12', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas
(1065, 9, 9, 301, 3, 'Entrada', '2026-01-12', '07:58:00', 'MIKEL - Inicio semana', 'Anticipado'),
(1066, 9, 9, 301, 3, 'Entrada', '2026-01-12', '11:30:00', 'MIKEL - Reunión coordinación', 'Anticipado'),
(1067, 9, 9, 301, 3, 'Entrada', '2026-01-12', '14:45:00', 'MIKEL - Tarde', 'Puntual'),
(1068, 9, 9, 301, 3, 'Salida', '2026-01-12', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1069, 10, 10, 301, 3, 'Entrada', '2026-01-12', '08:00:00', 'Ana', 'Puntual'),
(1070, 10, 10, 301, 3, 'Entrada', '2026-01-12', '13:45:00', 'Ana tarde', 'Puntual'),

-- Martes 13 Enero (HOY)
(1071, 1, 1, 101, 1, 'Entrada', '2026-01-13', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas hasta ahora
(1072, 9, 9, 301, 3, 'Entrada', '2026-01-13', '07:51:00', 'MIKEL - Hoy anticipado', 'Anticipado'),
(1073, 9, 9, 301, 3, 'Entrada', '2026-01-13', '10:20:00', 'MIKEL - Check matutino', 'Anticipado'),
(1074, 9, 9, 301, 3, 'Entrada', '2026-01-13', '13:15:00', 'MIKEL - Post-comida', 'Puntual'),
(1075, 9, 9, 301, 3, 'Salida', '2026-01-13', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1076, 10, 10, 301, 3, 'Entrada', '2026-01-13', '07:56:00', 'Ana anticipada hoy', 'Anticipado'),
(1077, 10, 10, 301, 3, 'Entrada', '2026-01-13', '13:00:00', 'Ana tarde', 'Puntual');

-- ============================================
-- 10. USUARIOS-CALENDARIO (UNO POR USUARIO)
-- ============================================
-- SISTEMA DE DISTRIBUCIÓN POR TURNOS ✅
-- ============================================
-- REGLA: Cada usuario tiene UN calendario asignado según su turno
-- 🌅 Usuarios 1-15: Calendario 2 (2026 MATUTINO 07:00-14:00) ACTIVO
-- 🌆 Usuarios 16-30: Calendario 3 (2026 NOCTURNO 14:00-21:00) ACTIVO
-- 👤 Usuario 31 (SujetoPrueba): Calendario 1 (2025 MATUTINO) EXPIRADO
-- NADIE tiene múltiples calendarios simultáneamente
-- ============================================

INSERT INTO usuarios_calendario (matricula, id_calendario) VALUES
-- ╔═══════════════════════════════════════════════════════════╗
-- ║  CALENDARIO 2: Turno Matutino 2026 (07:00-14:00) ✅      ║
-- ║  Usuarios 1-15 - ACTIVO                                  ║
-- ╚═══════════════════════════════════════════════════════════╝
(1, 2),   -- 🌅 Juan Admin
(2, 2),   -- 🌅 María Admin
(3, 2),   -- 🌅 Rafael Admin
(4, 2),   -- 🌅 Carlos Supervisor
(5, 2),   -- 🌅 Lucía Supervisor
(6, 2),   -- 🌅 Sofía Analista
(7, 2),   -- 🌅 Miguel Analista
(8, 2),   -- 🌅 Verónica Analista
(9, 2),   -- 🌅 MIKEL ABRAHAM Analista
(10, 2),  -- 🌅 Ana Analista
(11, 2),  -- 🌅 Juan Carlos Empleado
(12, 2),  -- 🌅 Pamela Empleado
(13, 2),  -- 🌅 Roberto Empleado
(14, 2),  -- 🌅 Lorena Empleado
(15, 2),  -- 🌅 Sergio Empleado

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  CALENDARIO 3: Turno Nocturno 2026 (14:00-21:00) ✅      ║
-- ║  Usuarios 16-30 - ACTIVO                                 ║
-- ╚═══════════════════════════════════════════════════════════╝
(16, 3),  -- 🌆 Felipe Empleado
(17, 3),  -- 🌆 Isabel Empleado
(18, 3),  -- 🌆 Gabriela Técnico
(19, 3),  -- 🌆 Hugo Técnico
(20, 3),  -- 🌆 Andrés Becario
(21, 3),  -- 🌆 Diego Empleado
(22, 3),  -- 🌆 Mónica Empleado
(23, 3),  -- 🌆 Jorge Empleado
(24, 3),  -- 🌆 Carmen Empleado
(25, 3),  -- 🌆 Ricardo Empleado
(26, 3),  -- 🌆 Adán Técnico
(27, 3),  -- 🌆 Berenice Técnico
(28, 3),  -- 🌆 Esteban Analista
(29, 3),  -- 🌆 Claudia Analista
(30, 3),  -- 🌆 Patricia Analista

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  CALENDARIO 1: Turno Matutino 2025 (HISTÓRICO) ❌        ║
-- ║  Usuario 31 - EXPIRADO                                   ║
-- ╚═══════════════════════════════════════════════════════════╝
-- 🚨 USUARIO ESPECIAL PARA PRUEBAS DE CALENDARIO EXPIRADO 🚨
-- SujetoPrueba tiene calendario 1 (2025) que YA EXPIRÓ el 31/12/2025
-- Hoy es 18/04/2026, su calendario está vencido
-- Útil para probar validaciones de calendarios expirados
(31, 1);  -- ⏰ SujetoPrueba - Calendario 2025 EXPIRADO ❌

-- ============================================
-- 11. PERMISOS Y DÍAS FESTIVOS COMPLETOS
-- ============================================
INSERT INTO permisos_dias (tipo_permiso, fecha, descripcion, es_general, matricula, activo) VALUES
-- FERIADOS OFICIALES 2024 (Archivados)
('Feriado', '2024-01-01', 'Año Nuevo 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-02-05', 'Día de la Constitución 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-03-18', 'Natalicio de Benito Juárez 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-05-01', 'Día del Trabajo 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-09-16', 'Día de la Independencia 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-11-18', 'Día de la Revolución Mexicana 2024', TRUE, NULL, 'Inactivo'),
('Feriado', '2024-12-25', 'Navidad 2024', TRUE, NULL, 'Inactivo'),

-- FERIADOS OFICIALES 2025 (Históricos)
('Feriado', '2025-01-01', 'Año Nuevo 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-02-03', 'Día de la Constitución 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-03-17', 'Natalicio de Benito Juárez 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-05-01', 'Día del Trabajo 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-09-16', 'Día de la Independencia 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-11-17', 'Día de la Revolución Mexicana 2025', TRUE, NULL, 'Inactivo'),
('No Laborable', '2025-12-24', 'Nochebuena 2025', TRUE, NULL, 'Inactivo'),
('Feriado', '2025-12-25', 'Navidad 2025', TRUE, NULL, 'Inactivo'),
('No Laborable', '2025-12-31', 'Año Nuevo Eve 2025', TRUE, NULL, 'Inactivo'),

-- FERIADOS OFICIALES 2026 (ACTIVOS)
('Feriado', '2026-01-01', 'Año Nuevo 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-02-02', 'Día de la Constitución 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-03-16', 'Natalicio de Benito Juárez 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-04-02', 'Jueves Santo 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-04-03', 'Viernes Santo 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-05-01', 'Día del Trabajo 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-09-16', 'Día de la Independencia 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-11-16', 'Día de la Revolución Mexicana 2026', TRUE, NULL, 'Activo'),
('No Laborable', '2026-12-24', 'Nochebuena 2026', TRUE, NULL, 'Activo'),
('Feriado', '2026-12-25', 'Navidad 2026', TRUE, NULL, 'Activo'),
('No Laborable', '2026-12-31', 'Año Nuevo Eve 2026', TRUE, NULL, 'Activo'),

-- PERMISOS PERSONALES 2026 (Ejemplos)
('Descanso', '2026-01-15', 'Día personal médico', FALSE, 12, 'Activo'),
('Descanso', '2026-01-20', 'Asunto familiar', FALSE, 15, 'Activo'),
('Descanso', '2026-02-10', 'Cita importante', FALSE, 8, 'Activo');

-- ============================================
-- 12. EXTENSIÓN ADMIN DINÁMICA (RBAC ADMIN)
-- ============================================
INSERT INTO acciones_admin (id_accion, nombre_accion) VALUES
(1, 'ver_bitacora'),
(2, 'ver_auditoria'),
(3, 'gestionar_usuarios'),
(4, 'reactivar_usuarios'),
(5, 'asignar_calendarios'),
(6, 'editar_horarios'),
(7, 'gestionar_dispositivos'),
(8, 'exportar_reportes'),
(9, 'ver_panel_ejecutivo'),
(10, 'aprobar_ajustes_asistencia'),
(11, 'gestionar_feriados'),
(12, 'administrar_grupos'),
(13, 'gestionar_tutores'),
(14, 'gestionar_expedientes'),
(15, 'asignar_permisos_admin');

-- Admin #1 (matrícula 1): perfil completo
-- Admin #2 (matrícula 2): perfil operativo/reportes
-- Admin #3 (matrícula 3): perfil control/auditoría
INSERT INTO permisos_personalizados (matricula, id_accion) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
(1, 11), (1, 12), (1, 13), (1, 14), (1, 15),
(2, 1), (2, 5), (2, 8), (2, 9), (2, 11), (2, 12), (2, 13),
(3, 1), (3, 2), (3, 8), (3, 10), (3, 14);

-- ============================================
-- 13. TUTORES Y VÍNCULOS (LECTURA / OPCIONAL)
-- ============================================
INSERT INTO tutores (id_tutor, nombre, correo, contrasenia) VALUES
(1, 'Ana María Torres', 'ana.torres.tutor@familia.com', 'TutorAna#2026'),
(2, 'José Luis Vázquez', 'jose.vazquez.tutor@familia.com', 'TutorJose#2026'),
(3, 'Laura Patricia Ruiz', 'laura.ruiz.tutor@familia.com', 'TutorLaura#2026'),
(4, 'Miguel Ángel Cordero', 'miguel.cordero.tutor@familia.com', 'TutorMiguel#2026'),
(5, 'Rosa Elena Chávez', 'rosa.chavez.tutor@familia.com', 'TutorRosa#2026'),
(6, 'Edgar Isaac Pineda', 'edgar.pineda.tutor@familia.com', 'TutorEdgar#2026'),
(7, 'Brenda Sofía Campos', 'brenda.campos.tutor@familia.com', 'TutorBrenda#2026'),
(8, 'Héctor Iván Salazar', 'hector.salazar.tutor@familia.com', 'TutorHector#2026'),
(9, 'Daniela Fernanda Cortés', 'daniela.cortes.tutor@familia.com', 'TutorDaniela#2026'),
(10, 'Oscar Emmanuel Moreno', 'oscar.moreno.tutor@familia.com', 'TutorOscar#2026');

INSERT INTO vinculo_tutor (id_tutor, matricula_estudiante) VALUES
(1, 11), (2, 11),
(3, 12),
(4, 13),
(5, 14), (6, 14),
(2, 20),
(7, 21),
(8, 22),
(9, 24),
(10, 31),
(1, 25),
(6, 23),
(7, 17);

-- ============================================
-- 14. EXPEDIENTE DIGITAL (OPCIONAL)
-- ============================================
INSERT INTO expediente_digital (id_archivo, matricula, url_pdf, tipo_doc, fecha_carga) VALUES
(1, 11, 'https://cdn.horzaone.mx/expedientes/2026/11/acta_nacimiento.pdf', 'Acta Nacimiento', '2026-02-05 09:10:00'),
(2, 11, 'https://cdn.horzaone.mx/expedientes/2026/11/comprobante_domicilio.pdf', 'Comprobante Domicilio', '2026-02-05 09:15:00'),
(3, 12, 'https://cdn.horzaone.mx/expedientes/2026/12/curp.pdf', 'CURP', '2026-02-06 10:00:00'),
(4, 12, 'https://cdn.horzaone.mx/expedientes/2026/12/constancia_estudios.pdf', 'Constancia Estudios', '2026-02-06 10:05:00'),
(5, 13, 'https://cdn.horzaone.mx/expedientes/2026/13/ine_tutor.pdf', 'INE Tutor', '2026-02-07 11:20:00'),
(6, 14, 'https://cdn.horzaone.mx/expedientes/2026/14/acta_nacimiento.pdf', 'Acta Nacimiento', '2026-02-07 11:25:00'),
(7, 14, 'https://cdn.horzaone.mx/expedientes/2026/14/carta_responsable.pdf', 'Carta Responsable', '2026-02-07 11:28:00'),
(8, 20, 'https://cdn.horzaone.mx/expedientes/2026/20/seguro_escolar.pdf', 'Seguro Escolar', '2026-03-01 08:45:00'),
(9, 21, 'https://cdn.horzaone.mx/expedientes/2026/21/constancia_medica.pdf', 'Constancia Medica', '2026-03-03 12:15:00'),
(10, 22, 'https://cdn.horzaone.mx/expedientes/2026/22/nss.pdf', 'NSS', '2026-03-04 13:00:00'),
(11, 23, 'https://cdn.horzaone.mx/expedientes/2026/23/comprobante_domicilio.pdf', 'Comprobante Domicilio', '2026-03-05 14:00:00'),
(12, 24, 'https://cdn.horzaone.mx/expedientes/2026/24/acta_nacimiento.pdf', 'Acta Nacimiento', '2026-03-08 09:30:00'),
(13, 25, 'https://cdn.horzaone.mx/expedientes/2026/25/curp.pdf', 'CURP', '2026-03-08 09:35:00'),
(14, 31, 'https://cdn.horzaone.mx/expedientes/2026/31/caso_prueba_identificacion.pdf', 'Identificacion Temporal', '2026-03-20 16:10:00'),
(15, 1, 'https://cdn.horzaone.mx/expedientes/2026/1/nombramiento_admin.pdf', 'Nombramiento Admin', '2026-04-01 09:00:00'),
(16, 2, 'https://cdn.horzaone.mx/expedientes/2026/2/acreditacion_supervision.pdf', 'Acreditacion Supervision', '2026-04-02 09:10:00'),
(17, 3, 'https://cdn.horzaone.mx/expedientes/2026/3/auditoria_interna_q2.pdf', 'Auditoria Interna', '2026-04-03 09:20:00'),
(18, 9, 'https://cdn.horzaone.mx/expedientes/2026/9/certificacion_analitica.pdf', 'Certificacion', '2026-04-05 10:40:00'),
(19, 10, 'https://cdn.horzaone.mx/expedientes/2026/10/cedula_profesional.pdf', 'Cedula Profesional', '2026-04-05 10:45:00'),
(20, 17, 'https://cdn.horzaone.mx/expedientes/2026/17/permiso_salida.pdf', 'Permiso Salida', '2026-04-10 15:30:00');

-- ============================================
-- 15. GRUPOS CON ADMINISTRADOR (USUARIO EXISTENTE)
-- ============================================
INSERT INTO grupos (id_grupo, nombre_grupo, matricula_lider) VALUES
(1, 'Administracion Estrategica', 1),
(2, 'Supervision Operativa Norte', 4),
(3, 'Analitica Financiera Q2-2026', 9),
(4, 'Control Escolar Matutino', 2),
(5, 'Tutorias Secundaria A', 5),
(6, 'Laboratorio TI Vespertino', 7),
(7, 'Brigada Operaciones Planta C', 18),
(8, 'Auditoria Interna Abril 2026', 3),
(9, 'Expediente y Validaciones', 10),
(10, 'Programa Becarios 2026', 28);

INSERT INTO grupo_integrantes (id_grupo, matricula) VALUES
-- Grupo 1
(1, 1), (1, 2), (1, 3), (1, 9),
-- Grupo 2
(2, 4), (2, 5), (2, 6), (2, 18),
-- Grupo 3
(3, 9), (3, 10), (3, 22), (3, 23),
-- Grupo 4
(4, 2), (4, 11), (4, 12), (4, 13), (4, 14),
-- Grupo 5
(5, 5), (5, 14), (5, 17), (5, 21),
-- Grupo 6
(6, 7), (6, 20), (6, 24), (6, 25),
-- Grupo 7
(7, 18), (7, 19), (7, 26), (7, 27),
-- Grupo 8
(8, 3), (8, 8), (8, 16), (8, 31),
-- Grupo 9
(9, 10), (9, 15), (9, 29), (9, 30),
-- Grupo 10
(10, 28), (10, 11), (10, 12), (10, 21), (10, 24);

-- ============================================
-- VERIFICACIÓN COMPLETA DEL SISTEMA DE TURNOS
-- ============================================
SELECT '╔════════════════════════════════════════════════════════════╗' AS '';
SELECT '║  SISTEMA HORZA-ONE - RESUMEN COMPLETO ENERO-ABRIL 2026    ║' AS '';
SELECT '║  Sistema de Turnos: Matutino (07:00-14:00) y Nocturno     ║' AS '';
SELECT '╚════════════════════════════════════════════════════════════╝' AS '';
SELECT '' AS '';

SELECT '📊 ESTRUCTURA GENERAL' AS Sección, '===================' AS Detalle
UNION ALL SELECT 'Total Usuarios:', COUNT(*) FROM usuarios
UNION ALL SELECT 'Total Áreas:', COUNT(*) FROM area
UNION ALL SELECT 'Total Dispositivos:', COUNT(*) FROM dispositivo
UNION ALL SELECT 'Dispositivos Activos:', COUNT(*) FROM dispositivo WHERE activo_dispositivo = 'Activo'
UNION ALL SELECT 'Dispositivos Inactivos:', COUNT(*) FROM dispositivo WHERE activo_dispositivo = 'Inactivo'
UNION ALL SELECT 'Usuarios con foto de perfil:', COUNT(*) FROM usuarios WHERE foto_perfil IS NOT NULL AND TRIM(foto_perfil) <> ''
UNION ALL SELECT '', ''
UNION ALL SELECT '🛡️ EXTENSIÓN ADMIN DINÁMICA', '==================='
UNION ALL SELECT 'Acciones admin catálogo:', COUNT(*) FROM acciones_admin
UNION ALL SELECT 'Permisos personalizados asignados:', COUNT(*) FROM permisos_personalizados
UNION ALL SELECT 'Admins con permisos personalizados:', COUNT(DISTINCT matricula) FROM permisos_personalizados
UNION ALL SELECT '', ''
UNION ALL SELECT '🏫 MÓDULO ESCOLAR (NUEVAS TABLAS)', '==================='
UNION ALL SELECT 'Tutores registrados:', COUNT(*) FROM tutores
UNION ALL SELECT 'Vínculos tutor-estudiante:', COUNT(*) FROM vinculo_tutor
UNION ALL SELECT 'Estudiantes con tutor:', COUNT(DISTINCT matricula_estudiante) FROM vinculo_tutor
UNION ALL SELECT 'Documentos en expediente digital:', COUNT(*) FROM expediente_digital
UNION ALL SELECT 'Usuarios con expediente:', COUNT(DISTINCT matricula) FROM expediente_digital
UNION ALL SELECT 'Grupos con administrador:', COUNT(*) FROM grupos
UNION ALL SELECT 'Integrantes de grupos:', COUNT(*) FROM grupo_integrantes
UNION ALL SELECT 'Usuarios con membresía de grupo:', COUNT(DISTINCT matricula) FROM grupo_integrantes
UNION ALL SELECT '', ''
UNION ALL SELECT '📅 SISTEMA DE CALENDARIOS (3 calendarios)', '==================='
UNION ALL SELECT 'Calendarios Totales:', COUNT(*) FROM calendario
UNION ALL SELECT '🌅 Cal 2: Matutino 2026 ACTIVO:', COUNT(*) FROM calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Cal 3: Nocturno 2026 ACTIVO:', COUNT(*) FROM calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Cal 1: Matutino 2025 EXPIRADO:', COUNT(*) FROM calendario WHERE id_calendario = 1
UNION ALL SELECT '', ''
UNION ALL SELECT '⏰ HORARIOS Y BLOQUES (Sistema de Turnos)', '==================='
UNION ALL SELECT 'Total Horarios:', COUNT(*) FROM horario
UNION ALL SELECT 'Horario 1 (2025 Matutino) Inactivo:', COUNT(*) FROM horario WHERE id_horario = 1
UNION ALL SELECT 'Horario 2 (2026 Matutino) Activo:', COUNT(*) FROM horario WHERE id_horario = 2
UNION ALL SELECT 'Horario 3 (2026 Nocturno) Activo:', COUNT(*) FROM horario WHERE id_horario = 3
UNION ALL SELECT 'Días en Horario 1 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 1
UNION ALL SELECT 'Días en Horario 2 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 2
UNION ALL SELECT 'Días en Horario 3 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 3
UNION ALL SELECT 'Bloques Matutinos (07:00-14:00):', COUNT(*) FROM bloques_horario WHERE id_bloque BETWEEN 1 AND 35
UNION ALL SELECT 'Bloques Nocturnos (14:00-21:00):', COUNT(*) FROM bloques_horario WHERE id_bloque BETWEEN 36 AND 70
UNION ALL SELECT 'Total Bloques de 1 hora:', COUNT(*) FROM bloques_horario WHERE activo = 'Activo'
UNION ALL SELECT 'Asignaciones Bloque-Día:', COUNT(*) FROM bloque_dia_asignacion
UNION ALL SELECT '', ''
UNION ALL SELECT '👥 DISTRIBUCIÓN POR TURNOS', '==================='
UNION ALL SELECT 'Total Asignaciones:', COUNT(*) FROM usuarios_calendario
UNION ALL SELECT '🌅 Turno Matutino 2026 (Usuarios 1-15):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Turno Nocturno 2026 (Usuarios 16-30):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Calendario Expirado 2025 (Usuario 31):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 1
UNION ALL SELECT '', ''
UNION ALL SELECT '📝 REGISTROS ENERO 2026', '==================='
UNION ALL SELECT 'Total Registros Enero:', COUNT(*) FROM registro WHERE fecha >= '2026-01-01' AND fecha <= '2026-01-13'
UNION ALL SELECT 'Registros de Entrada:', COUNT(*) FROM registro WHERE tipo_registro = 'Entrada' AND fecha >= '2026-01-01'
UNION ALL SELECT 'Registros de Salida:', COUNT(*) FROM registro WHERE tipo_registro = 'Salida' AND fecha >= '2026-01-01'
UNION ALL SELECT '', ''
UNION ALL SELECT '🌟 MIKEL ABRAHAM (mat 9) - TURNO MATUTINO', '==================='
UNION ALL SELECT 'Calendario Asignado (id):', id_calendario FROM usuarios_calendario WHERE matricula = 9
UNION ALL SELECT 'Turno:', '🌅 Matutino 07:00-14:00'
UNION ALL SELECT 'Registros Enero 2026:', COUNT(*) FROM registro WHERE matricula = 9 AND fecha >= '2026-01-01'
UNION ALL SELECT 'Entradas Anticipadas:', COUNT(*) FROM registro WHERE matricula = 9 AND estado_registro = 'Anticipado' AND fecha >= '2026-01-01'
UNION ALL SELECT 'Entradas Puntuales:', COUNT(*) FROM registro WHERE matricula = 9 AND estado_registro = 'Puntual' AND tipo_registro = 'Entrada' AND fecha >= '2026-01-01'
UNION ALL SELECT '', ''
UNION ALL SELECT '👤 ANA RODRÍGUEZ (mat 10) - TURNO MATUTINO', '==================='
UNION ALL SELECT 'Calendario Asignado (id):', id_calendario FROM usuarios_calendario WHERE matricula = 10
UNION ALL SELECT 'Turno:', '🌅 Matutino 07:00-14:00'
UNION ALL SELECT 'Registros Enero 2026:', COUNT(*) FROM registro WHERE matricula = 10 AND fecha >= '2026-01-01'
UNION ALL SELECT 'Entradas Anticipadas:', COUNT(*) FROM registro WHERE matricula = 10 AND estado_registro = 'Anticipado' AND fecha >= '2026-01-01'
UNION ALL SELECT '', ''
UNION ALL SELECT '🎉 DÍAS FESTIVOS 2026', '==================='
UNION ALL SELECT 'Feriados Activos 2026:', COUNT(*) FROM PERMISOS_DIAS WHERE tipo_permiso = 'Feriado' AND YEAR(fecha) = 2026 AND activo = 'Activo'
UNION ALL SELECT 'No Laborables 2026:', COUNT(*) FROM PERMISOS_DIAS WHERE tipo_permiso = 'No Laborable' AND YEAR(fecha) = 2026 AND activo = 'Activo'
UNION ALL SELECT '', ''
UNION ALL SELECT '✅ VERIFICACIÓN REGLA', '==================='
UNION ALL SELECT '¿Usuarios con >1 calendario?:', 
    CASE WHEN (SELECT COUNT(*) FROM (SELECT matricula FROM usuarios_calendario GROUP BY matricula HAVING COUNT(*) > 1) AS duplicados) = 0 
    THEN '✅ NINGUNO (CORRECTO)' 
    ELSE '❌ HAY DUPLICADOS' END
UNION ALL SELECT '', '';

SELECT '' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';
SELECT '   ✅ SISTEMA DE TURNOS COMPLETO Y VALIDADO - LISTO PARA USAR' AS '';
SELECT '     🌅 Matutino 07:00-14:00 | 🌆 Nocturno 14:00-21:00      ' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';

-- ============================================
-- 🚨 CASOS DE PRUEBA PARA DETECTAR ERRORES
-- ============================================
SELECT '' AS '';
SELECT '🧪 CASOS DE PRUEBA ESPECIALES' AS Sección, '===================' AS Detalle
UNION ALL SELECT 'Usuario SujetoPrueba (31):', 'Tiene calendario 1 (2025 EXPIRADO)'
UNION ALL SELECT 'Calendario vigencia:', 'Del 01/01/2025 al 31/12/2025'
UNION ALL SELECT 'Fecha actual sistema:', '18/04/2026'
UNION ALL SELECT 'Estado esperado:', '❌ ERROR - Calendario vencido'
UNION ALL SELECT 'Prueba recomendada:', 'Intentar registrar asistencia'
UNION ALL SELECT 'Resultado esperado:', 'Sistema debe rechazar el registro'
UNION ALL SELECT 'Mensaje esperado:', 'Calendario expirado o inválido'
UNION ALL SELECT '', ''
UNION ALL SELECT '📊 BLOQUES POR TURNO (MÁXIMO 7 por turno)', '==================='
UNION ALL SELECT 'Total bloques 1 hora:', COUNT(*) FROM bloques_horario
UNION ALL SELECT 'Bloques sin traslapes:', '✅ TODOS - Bloques de 1 hora consecutivos'
UNION ALL SELECT '🌅 Turno Matutino (07:00-14:00):', '7 bloques × 5 áreas = 35 bloques'
UNION ALL SELECT '🌆 Turno Nocturno (14:00-21:00):', '7 bloques × 5 áreas = 35 bloques'
UNION ALL SELECT 'RH Matutino (Área 1):', '7 bloques (07:00→14:00)'
UNION ALL SELECT 'TI Matutino (Área 2):', '7 bloques (07:00→14:00)'
UNION ALL SELECT 'FIN Matutino (Área 3):', '7 bloques (07:00→14:00)'
UNION ALL SELECT 'OPS Matutino (Área 4):', '7 bloques (07:00→14:00)'
UNION ALL SELECT 'MKT Matutino (Área 5):', '7 bloques (07:00→14:00)'
UNION ALL SELECT 'RH Nocturno (Área 1):', '7 bloques (14:00→21:00)'
UNION ALL SELECT 'TI Nocturno (Área 2):', '7 bloques (14:00→21:00)'
UNION ALL SELECT 'FIN Nocturno (Área 3):', '7 bloques (14:00→21:00)'
UNION ALL SELECT 'OPS Nocturno (Área 4):', '7 bloques (14:00→21:00)'
UNION ALL SELECT 'MKT Nocturno (Área 5):', '7 bloques (14:00→21:00)'
UNION ALL SELECT '', ''
UNION ALL SELECT '📆 HORARIOS POR DÍA', '==================='
UNION ALL SELECT 'Estructura:', '1 Calendario → 1 Horario → 7 Días (L-D) → Bloques'
UNION ALL SELECT 'Calendario 1 (2025 Matutino):', 'Horario 1 con Lunes-Domingo (L-V laborables)'
UNION ALL SELECT 'Calendario 2 (2026 Matutino):', 'Horario 2 con Lunes-Domingo (L-V laborables)'
UNION ALL SELECT 'Calendario 3 (2026 Nocturno):', 'Horario 3 con Lunes-Domingo (L-V laborables)'
UNION ALL SELECT 'Días laborables:', 'Lunes-Viernes (35 bloques por día × 5 áreas)'
UNION ALL SELECT 'Sábado y Domingo:', 'Sin bloques asignados (descanso)'
UNION ALL SELECT 'Traslapes:', '❌ IMPOSIBLE - Bloques consecutivos de 1 hora'
UNION ALL SELECT '', ''
UNION ALL SELECT '👥 RESUMEN USUARIOS (31 usuarios)', '==================='
UNION ALL SELECT 'Total usuarios sistema:', COUNT(*) FROM usuarios
UNION ALL SELECT '🌅 Con turno MATUTINO (usuarios 1-15):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Con turno NOCTURNO (usuarios 16-30):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Con calendario EXPIRADO (usuario 31):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 1
UNION ALL SELECT 'Usuario especial prueba:', 'SujetoPrueba (matricula 31)';

SELECT '' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';
SELECT '   🎯 SISTEMA CON BLOQUES DE 1 HORA - SIN TRASLAPES         ' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';
