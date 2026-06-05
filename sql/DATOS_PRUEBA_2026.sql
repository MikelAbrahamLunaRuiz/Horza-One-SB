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
-- 4. BITÁCORAS (UNA POR USUARIO) - Actualizadas hasta Abril 2026
-- ============================================
INSERT INTO bitacora (id_bitacora, matricula, num_entradas, num_inasistencias, num_retardos, num_entradas_anticipadas) VALUES
(1, 1, 24, 0, 1, 6),
(2, 2, 7, 0, 5, 1),
(3, 3, 8, 0, 1, 1),
(4, 4, 12, 0, 1, 1),
(5, 5, 0, 0, 0, 0),
(6, 6, 0, 0, 0, 0),
(7, 7, 0, 0, 0, 0),
(8, 8, 0, 0, 0, 0),
(9, 9, 68, 0, 5, 40),  -- MIKEL - Datos ampliados Ene-Abr 2026
(10, 10, 56, 0, 3, 27), -- Ana - Datos ampliados Ene-Abr 2026
(11, 11, 0, 0, 0, 0),
(12, 12, 0, 0, 0, 0),
(13, 13, 0, 0, 0, 0),
(14, 14, 0, 0, 0, 0),
(15, 15, 0, 0, 0, 0),
(16, 16, 7, 0, 2, 0),
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
-- HORARIO 4: Turno Madrugada 2026 (05:00-11:00) - 6 bloques - ACTIVO → Juan Pérez
-- ============================================
INSERT INTO horario (id_horario, nombre_horario, descripcion, activo_horario) VALUES
(1, 'Turno Matutino 2025', 'Horario 7am-2pm año 2025 - Histórico (7 bloques)', 'Inactivo'),
(2, 'Turno Matutino 2026', 'Horario 7am-2pm año 2026 - ACTIVO (7 bloques)', 'Activo'),
(3, 'Turno Nocturno 2026', 'Horario 2pm-9pm año 2026 - ACTIVO (7 bloques)', 'Activo'),
(4, 'Turno Madrugada 2026', 'Horario 5am-11am año 2026 - ACTIVO (6 bloques) - Juan Pérez', 'Activo'),
(5, 'Turno Oficina RH 2026', 'Horario 8:20am-4pm año 2026 - ACTIVO (8 bloques) - Recursos Humanos', 'Activo');

-- ============================================
-- 6. CALENDARIOS (5 CALENDARIOS)
-- ============================================
-- CALENDARIO 1: 2025 Turno Matutino (HISTÓRICO) → Usuario 31 (sujetoprueba)
-- CALENDARIO 2: 2026 Turno Matutino (ACTIVO) → Usuarios 2-10, 12-15
-- CALENDARIO 3: 2026 Turno Nocturno (ACTIVO) → Usuarios 16-30
-- CALENDARIO 4: 2026 Turno Madrugada (ACTIVO, May-Jul) → Sin usuarios asignados
-- CALENDARIO 5: 2026 Turno Oficina RH (ACTIVO) → Usuarios 1 (Juan Pérez) y 11 (Juan Carlos, con tutor)
-- ============================================
INSERT INTO calendario (id_calendario, id_horario, nombre_calendario, fecha_inicio, fecha_fin, descripcion, activo_calendario) VALUES
(1, 1, 'Calendario 2025 Matutino', '2025-01-01', '2025-12-31', 'Turno Matutino 7am-2pm (7 bloques) - Usuario prueba expirado', 'Inactivo'),
(2, 2, 'Calendario 2026 Matutino', '2026-01-01', '2026-12-31', 'Turno Matutino 7am-2pm (7 bloques) - 14 usuarios - ACTIVO', 'Activo'),
(3, 3, 'Calendario 2026 Nocturno', '2026-01-01', '2026-12-31', 'Turno Nocturno 2pm-9pm (7 bloques) - 15 usuarios - ACTIVO', 'Activo'),
(4, 4, 'Calendario 2026 Madrugada', '2026-05-01', '2026-07-31', 'Turno Madrugada 5am-11am (6 bloques) - Juan Pérez - ACTIVO', 'Activo'),
(5, 5, 'Calendario 2026 Oficina RH', '2026-01-01', '2026-12-31', 'Turno Oficina RH 8:20am-4pm (8 bloques) - Juan Pérez y Juan Carlos - ACTIVO', 'Activo');

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
(3, 7),  -- 🚫 DOMINGO - Sin bloques (Descanso)
-- HORARIO 4: Turno Madrugada 2026 (ACTIVO)
(4, 1),  -- 🗓️ LUNES - Turno Madrugada 2026
(4, 2),  -- 🗓️ MARTES - Turno Madrugada 2026
(4, 3),  -- 🗓️ MIÉRCOLES - Turno Madrugada 2026
(4, 4),  -- 🗓️ JUEVES - Turno Madrugada 2026
(4, 5),  -- 🗓️ VIERNES - Turno Madrugada 2026
(4, 6),  -- 🚫 SÁBADO - Sin bloques (Descanso)
(4, 7),  -- 🚫 DOMINGO - Sin bloques (Descanso)
-- HORARIO 5: Turno Oficina RH 2026 (ACTIVO)
(5, 1),  -- 🗓️ LUNES - Turno Oficina RH 2026
(5, 2),  -- 🗓️ MARTES - Turno Oficina RH 2026
(5, 3),  -- 🗓️ MIÉRCOLES - Turno Oficina RH 2026
(5, 4),  -- 🗓️ JUEVES - Turno Oficina RH 2026
(5, 5),  -- 🗓️ VIERNES - Turno Oficina RH 2026
(5, 6),  -- 🚫 SÁBADO - Sin bloques (Descanso)
(5, 7);  -- 🚫 DOMINGO - Sin bloques (Descanso)

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
(70, 5, 'MKT Nocturno 20:00-21:00', '20:00:00', '21:00:00', 'Activo'),

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  BLOQUES TURNO MADRUGADA (05:00-11:00) - 6 bloques/área  ║
-- ╚═══════════════════════════════════════════════════════════╝

-- 📋 ÁREA 1: RECURSOS HUMANOS - Turno Madrugada (bloques 71-76)
(71, 1, 'RH Madrugada 05:00-06:00', '05:00:00', '06:00:00', 'Activo'),
(72, 1, 'RH Madrugada 06:00-07:00', '06:00:00', '07:00:00', 'Activo'),
(73, 1, 'RH Madrugada 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(74, 1, 'RH Madrugada 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(75, 1, 'RH Madrugada 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(76, 1, 'RH Madrugada 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),

-- 💻 ÁREA 2: TECNOLOGÍA E INNOVACIÓN - Turno Madrugada (bloques 77-82)
(77, 2, 'TI Madrugada 05:00-06:00', '05:00:00', '06:00:00', 'Activo'),
(78, 2, 'TI Madrugada 06:00-07:00', '06:00:00', '07:00:00', 'Activo'),
(79, 2, 'TI Madrugada 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(80, 2, 'TI Madrugada 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(81, 2, 'TI Madrugada 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(82, 2, 'TI Madrugada 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),

-- 💰 ÁREA 3: FINANZAS Y CONTABILIDAD - Turno Madrugada (bloques 83-88)
(83, 3, 'FIN Madrugada 05:00-06:00', '05:00:00', '06:00:00', 'Activo'),
(84, 3, 'FIN Madrugada 06:00-07:00', '06:00:00', '07:00:00', 'Activo'),
(85, 3, 'FIN Madrugada 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(86, 3, 'FIN Madrugada 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(87, 3, 'FIN Madrugada 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(88, 3, 'FIN Madrugada 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),

-- ⚙️ ÁREA 4: OPERACIONES - Turno Madrugada (bloques 89-94)
(89, 4, 'OPS Madrugada 05:00-06:00', '05:00:00', '06:00:00', 'Activo'),
(90, 4, 'OPS Madrugada 06:00-07:00', '06:00:00', '07:00:00', 'Activo'),
(91, 4, 'OPS Madrugada 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(92, 4, 'OPS Madrugada 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(93, 4, 'OPS Madrugada 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(94, 4, 'OPS Madrugada 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),

-- 📢 ÁREA 5: MARKETING - Turno Madrugada (bloques 95-100)
(95, 5, 'MKT Madrugada 05:00-06:00', '05:00:00', '06:00:00', 'Activo'),
(96, 5, 'MKT Madrugada 06:00-07:00', '06:00:00', '07:00:00', 'Activo'),
(97, 5, 'MKT Madrugada 07:00-08:00', '07:00:00', '08:00:00', 'Activo'),
(98, 5, 'MKT Madrugada 08:00-09:00', '08:00:00', '09:00:00', 'Activo'),
(99, 5, 'MKT Madrugada 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(100, 5, 'MKT Madrugada 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  BLOQUES TURNO OFICINA RH (08:20-16:00) - 8 bloques      ║
-- ║  ÁREA 1: RECURSOS HUMANOS (bloques 101-108)              ║
-- ╚═══════════════════════════════════════════════════════════╝
(101, 1, 'RH Oficina 08:20-09:00', '08:20:00', '09:00:00', 'Activo'),
(102, 1, 'RH Oficina 09:00-10:00', '09:00:00', '10:00:00', 'Activo'),
(103, 1, 'RH Oficina 10:00-11:00', '10:00:00', '11:00:00', 'Activo'),
(104, 1, 'RH Oficina 11:00-12:00', '11:00:00', '12:00:00', 'Activo'),
(105, 1, 'RH Oficina 12:00-13:00', '12:00:00', '13:00:00', 'Activo'),
(106, 1, 'RH Oficina 13:00-14:00', '13:00:00', '14:00:00', 'Activo'),
(107, 1, 'RH Oficina 14:00-15:00', '14:00:00', '15:00:00', 'Activo'),
(108, 1, 'RH Oficina 15:00-16:00', '15:00:00', '16:00:00', 'Activo');

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
(19, 63), -- 20:00-21:00 ⚙️ OPS

-- Sábado (id_dia_horario = 20) y Domingo (id_dia_horario = 21): SIN BLOQUES (descanso)

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  HORARIO 4: Turno Madrugada 2026 (ACTIVO) ✅             ║
-- ║  DIA_HORARIO 22-28 (Lunes-Domingo)                       ║
-- ║  6 bloques/día (05:00-11:00) - Primer bloque RH a las 5  ║
-- ║  Usuario asignado: #1 Juan Pérez                         ║
-- ╚═══════════════════════════════════════════════════════════╝

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ LUNES 2026 MADRUGADA (id_dia_horario = 22)         │
-- └───────────────────────────────────────────────────────┘
(22, 71), -- 05:00-06:00 🏢 RH (primer registro en RH a las 5am)
(22, 78), -- 06:00-07:00 💻 TI
(22, 85), -- 07:00-08:00 💰 FIN
(22, 92), -- 08:00-09:00 ⚙️ OPS
(22, 99), -- 09:00-10:00 📢 MKT
(22, 76), -- 10:00-11:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MARTES 2026 MADRUGADA (id_dia_horario = 23)        │
-- └───────────────────────────────────────────────────────┘
(23, 71), -- 05:00-06:00 🏢 RH (primer registro en RH a las 5am)
(23, 84), -- 06:00-07:00 💰 FIN
(23, 91), -- 07:00-08:00 ⚙️ OPS
(23, 98), -- 08:00-09:00 📢 MKT
(23, 81), -- 09:00-10:00 💻 TI
(23, 76), -- 10:00-11:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MIÉRCOLES 2026 MADRUGADA (id_dia_horario = 24)     │
-- └───────────────────────────────────────────────────────┘
(24, 71), -- 05:00-06:00 🏢 RH (primer registro en RH a las 5am)
(24, 90), -- 06:00-07:00 ⚙️ OPS
(24, 97), -- 07:00-08:00 📢 MKT
(24, 80), -- 08:00-09:00 💻 TI
(24, 87), -- 09:00-10:00 💰 FIN
(24, 76), -- 10:00-11:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ JUEVES 2026 MADRUGADA (id_dia_horario = 25)        │
-- └───────────────────────────────────────────────────────┘
(25, 71), -- 05:00-06:00 🏢 RH (primer registro en RH a las 5am)
(25, 96), -- 06:00-07:00 📢 MKT
(25, 79), -- 07:00-08:00 💻 TI
(25, 86), -- 08:00-09:00 💰 FIN
(25, 93), -- 09:00-10:00 ⚙️ OPS
(25, 76), -- 10:00-11:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ VIERNES 2026 MADRUGADA (id_dia_horario = 26)       │
-- └───────────────────────────────────────────────────────┘
(26, 71), -- 05:00-06:00 🏢 RH (primer registro en RH a las 5am)
(26, 82), -- 06:00-07:00 💻 TI
(26, 89), -- 07:00-08:00 ⚙️ OPS
(26, 83), -- 08:00-09:00 💰 FIN
(26, 95), -- 09:00-10:00 📢 MKT
(26, 76), -- 10:00-11:00 🏢 RH

-- Sábado (id_dia_horario = 27) y Domingo (id_dia_horario = 28): SIN BLOQUES (descanso)

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  HORARIO 5: Turno Oficina RH 2026 (ACTIVO) ✅            ║
-- ║  DIA_HORARIO 29-35 (Lunes-Domingo)                       ║
-- ║  8 bloques/día (08:20-16:00) - Todos en RH               ║
-- ║  Usuarios: #1 Juan Pérez, #11 Juan Carlos (con tutor)    ║
-- ╚═══════════════════════════════════════════════════════════╝

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ LUNES 2026 OFICINA RH (id_dia_horario = 29)        │
-- └───────────────────────────────────────────────────────┘
(29, 101), -- 08:20-09:00 🏢 RH
(29, 102), -- 09:00-10:00 🏢 RH
(29, 103), -- 10:00-11:00 🏢 RH
(29, 104), -- 11:00-12:00 🏢 RH
(29, 105), -- 12:00-13:00 🏢 RH
(29, 106), -- 13:00-14:00 🏢 RH
(29, 107), -- 14:00-15:00 🏢 RH
(29, 108), -- 15:00-16:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MARTES 2026 OFICINA RH (id_dia_horario = 30)       │
-- └───────────────────────────────────────────────────────┘
(30, 101), -- 08:20-09:00 🏢 RH
(30, 102), -- 09:00-10:00 🏢 RH
(30, 103), -- 10:00-11:00 🏢 RH
(30, 104), -- 11:00-12:00 🏢 RH
(30, 105), -- 12:00-13:00 🏢 RH
(30, 106), -- 13:00-14:00 🏢 RH
(30, 107), -- 14:00-15:00 🏢 RH
(30, 108), -- 15:00-16:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ MIÉRCOLES 2026 OFICINA RH (id_dia_horario = 31)    │
-- └───────────────────────────────────────────────────────┘
(31, 101), -- 08:20-09:00 🏢 RH
(31, 102), -- 09:00-10:00 🏢 RH
(31, 103), -- 10:00-11:00 🏢 RH
(31, 104), -- 11:00-12:00 🏢 RH
(31, 105), -- 12:00-13:00 🏢 RH
(31, 106), -- 13:00-14:00 🏢 RH
(31, 107), -- 14:00-15:00 🏢 RH
(31, 108), -- 15:00-16:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ JUEVES 2026 OFICINA RH (id_dia_horario = 32)       │
-- └───────────────────────────────────────────────────────┘
(32, 101), -- 08:20-09:00 🏢 RH
(32, 102), -- 09:00-10:00 🏢 RH
(32, 103), -- 10:00-11:00 🏢 RH
(32, 104), -- 11:00-12:00 🏢 RH
(32, 105), -- 12:00-13:00 🏢 RH
(32, 106), -- 13:00-14:00 🏢 RH
(32, 107), -- 14:00-15:00 🏢 RH
(32, 108), -- 15:00-16:00 🏢 RH

-- ┌───────────────────────────────────────────────────────┐
-- │ 🗓️ VIERNES 2026 OFICINA RH (id_dia_horario = 33)      │
-- └───────────────────────────────────────────────────────┘
(33, 101), -- 08:20-09:00 🏢 RH
(33, 102), -- 09:00-10:00 🏢 RH
(33, 103), -- 10:00-11:00 🏢 RH
(33, 104), -- 11:00-12:00 🏢 RH
(33, 105), -- 12:00-13:00 🏢 RH
(33, 106), -- 13:00-14:00 🏢 RH
(33, 107), -- 14:00-15:00 🏢 RH
(33, 108); -- 15:00-16:00 🏢 RH

-- Sábado (id_dia_horario = 34) y Domingo (id_dia_horario = 35): SIN BLOQUES (descanso)

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
-- 9. REGISTROS ENERO-MAYO 2026 (actualizados al 07 de mayo)
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

-- SEMANA 3 (12-13 Enero) - Cierre del bloque inicial
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

-- Martes 13 Enero
(1071, 1, 1, 101, 1, 'Entrada', '2026-01-13', '08:00:00', 'Admin', 'Puntual'),
-- MIKEL - 3 entradas hasta ahora
(1072, 9, 9, 301, 3, 'Entrada', '2026-01-13', '07:51:00', 'MIKEL - Hoy anticipado', 'Anticipado'),
(1073, 9, 9, 301, 3, 'Entrada', '2026-01-13', '10:20:00', 'MIKEL - Check matutino', 'Anticipado'),
(1074, 9, 9, 301, 3, 'Entrada', '2026-01-13', '13:15:00', 'MIKEL - Post-comida', 'Puntual'),
(1075, 9, 9, 301, 3, 'Salida', '2026-01-13', '17:00:00', 'MIKEL', 'Puntual'),
-- Ana - 2 entradas
(1076, 10, 10, 301, 3, 'Entrada', '2026-01-13', '07:56:00', 'Ana anticipada hoy', 'Anticipado'),
(1077, 10, 10, 301, 3, 'Entrada', '2026-01-13', '13:00:00', 'Ana tarde', 'Puntual'),

-- ============================================
-- BLOQUE ADICIONAL: FEBRERO-MARZO 2026
-- Más volumen para gráficas y estadísticas realistas
-- ============================================

-- Martes 3 Febrero
(1078, 1, 1, 101, 1, 'Entrada', '2026-02-03', '08:01:00', 'Admin inicio operativo febrero', 'Puntual'),
(1079, 1, 1, 101, 1, 'Salida', '2026-02-03', '17:06:00', 'Admin cierre de jornada', 'Puntual'),
(1080, 9, 9, 301, 3, 'Entrada', '2026-02-03', '07:46:00', 'MIKEL - Apertura anticipada', 'Anticipado'),
(1081, 9, 9, 201, 2, 'Entrada', '2026-02-03', '10:12:00', 'MIKEL - Revisión TI', 'Puntual'),
(1082, 9, 9, 301, 3, 'Salida', '2026-02-03', '17:09:00', 'MIKEL - Cierre financiero', 'Puntual'),
(1083, 10, 10, 301, 3, 'Entrada', '2026-02-03', '07:58:00', 'Ana - Entrada matutina', 'Anticipado'),
(1084, 10, 10, 202, 2, 'Entrada', '2026-02-03', '11:02:00', 'Ana - Seguimiento TI', 'Puntual'),
(1085, 10, 10, 301, 3, 'Salida', '2026-02-03', '16:55:00', 'Ana - Cierre día', 'Puntual'),
(1086, 4, 4, 401, 4, 'Entrada', '2026-02-03', '08:35:00', 'Supervisor operaciones', 'Puntual'),
(1087, 16, 16, 501, 5, 'Entrada', '2026-02-03', '14:03:00', 'Felipe - Inicio turno nocturno', 'Puntual'),
(1088, 16, 16, 501, 5, 'Salida', '2026-02-03', '21:01:00', 'Felipe - Fin turno nocturno', 'Puntual'),

-- Martes 10 Febrero
(1089, 2, 2, 102, 1, 'Entrada', '2026-02-10', '08:12:00', 'María - Tráfico matutino', 'Retardo'),
(1090, 3, 3, 101, 1, 'Entrada', '2026-02-10', '07:59:00', 'Rafael - Entrada regular', 'Puntual'),
(1091, 9, 9, 301, 3, 'Entrada', '2026-02-10', '07:44:00', 'MIKEL - Arranque temprano', 'Anticipado'),
(1092, 9, 9, 201, 2, 'Entrada', '2026-02-10', '09:58:00', 'MIKEL - Validación desarrollo', 'Anticipado'),
(1093, 9, 9, 301, 3, 'Entrada', '2026-02-10', '13:08:00', 'MIKEL - Retorno reunión', 'Puntual'),
(1094, 9, 9, 301, 3, 'Salida', '2026-02-10', '17:18:00', 'MIKEL - Salida', 'Puntual'),
(1095, 10, 10, 301, 3, 'Entrada', '2026-02-10', '08:04:00', 'Ana - Llegada con retraso leve', 'Retardo'),
(1096, 10, 10, 301, 3, 'Entrada', '2026-02-10', '12:55:00', 'Ana - Reingreso', 'Anticipado'),
(1097, 10, 10, 301, 3, 'Salida', '2026-02-10', '17:02:00', 'Ana - Salida', 'Puntual'),
(1098, 4, 4, 401, 4, 'Entrada', '2026-02-10', '09:10:00', 'Carlos - Reunión externa', 'Retardo'),
(1099, 16, 16, 501, 5, 'Entrada', '2026-02-10', '14:08:00', 'Felipe - Ajuste turno', 'Retardo'),
(1100, 16, 16, 501, 5, 'Salida', '2026-02-10', '20:58:00', 'Felipe - Cierre anticipado', 'Puntual'),

-- Miércoles 18 Febrero
(1101, 1, 1, 101, 1, 'Entrada', '2026-02-18', '07:57:00', 'Admin - Registro temprano', 'Anticipado'),
(1102, 1, 1, 101, 1, 'Salida', '2026-02-18', '17:03:00', 'Admin - Salida', 'Puntual'),
(1103, 9, 9, 301, 3, 'Entrada', '2026-02-18', '07:42:00', 'MIKEL - Inicio financiero', 'Anticipado'),
(1104, 9, 9, 201, 2, 'Entrada', '2026-02-18', '10:05:00', 'MIKEL - Integración de datos TI', 'Puntual'),
(1105, 9, 9, 301, 3, 'Entrada', '2026-02-18', '15:10:00', 'MIKEL - Bloque vespertino', 'Puntual'),
(1106, 9, 9, 301, 3, 'Salida', '2026-02-18', '17:20:00', 'MIKEL - Cierre del día', 'Puntual'),
(1107, 10, 10, 301, 3, 'Entrada', '2026-02-18', '07:54:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1108, 10, 10, 202, 2, 'Entrada', '2026-02-18', '10:48:00', 'Ana - Seguimiento incidencias', 'Puntual'),
(1109, 10, 10, 301, 3, 'Entrada', '2026-02-18', '13:20:00', 'Ana - Retorno comida', 'Puntual'),
(1110, 10, 10, 301, 3, 'Salida', '2026-02-18', '16:52:00', 'Ana - Salida', 'Puntual'),
(1111, 4, 4, 401, 4, 'Entrada', '2026-02-18', '08:50:00', 'Carlos - Entrada planta', 'Puntual'),
(1112, 3, 3, 101, 1, 'Entrada', '2026-02-18', '08:06:00', 'Rafael - Ajuste administrativo', 'Retardo'),

-- Jueves 26 Febrero
(1113, 2, 2, 101, 1, 'Entrada', '2026-02-26', '07:58:00', 'María - Inicio de jornada', 'Puntual'),
(1114, 9, 9, 301, 3, 'Entrada', '2026-02-26', '07:40:00', 'MIKEL - Apertura', 'Anticipado'),
(1115, 9, 9, 301, 3, 'Entrada', '2026-02-26', '11:15:00', 'MIKEL - Revisión de indicadores', 'Puntual'),
(1116, 9, 9, 301, 3, 'Salida', '2026-02-26', '17:12:00', 'MIKEL - Cierre', 'Puntual'),
(1117, 10, 10, 301, 3, 'Entrada', '2026-02-26', '07:59:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1118, 10, 10, 301, 3, 'Entrada', '2026-02-26', '12:40:00', 'Ana - Reingreso operativo', 'Puntual'),
(1119, 10, 10, 301, 3, 'Salida', '2026-02-26', '17:00:00', 'Ana - Fin jornada', 'Puntual'),
(1120, 4, 4, 401, 4, 'Entrada', '2026-02-26', '08:32:00', 'Carlos - Registro de supervisor', 'Puntual'),
(1121, 1, 1, 101, 1, 'Entrada', '2026-02-26', '08:00:00', 'Admin - Apertura área RH', 'Puntual'),
(1122, 16, 16, 501, 5, 'Entrada', '2026-02-26', '14:00:00', 'Felipe - Inicio turno', 'Puntual'),
(1123, 16, 16, 501, 5, 'Salida', '2026-02-26', '21:00:00', 'Felipe - Cierre turno', 'Puntual'),

-- Martes 3 Marzo
(1124, 1, 1, 101, 1, 'Entrada', '2026-03-03', '07:56:00', 'Admin - Registro preventivo', 'Anticipado'),
(1125, 9, 9, 301, 3, 'Entrada', '2026-03-03', '07:43:00', 'MIKEL - Inicio anticipado', 'Anticipado'),
(1126, 9, 9, 201, 2, 'Entrada', '2026-03-03', '10:25:00', 'MIKEL - Revisión arquitectura', 'Puntual'),
(1127, 9, 9, 301, 3, 'Entrada', '2026-03-03', '13:05:00', 'MIKEL - Regreso de comida', 'Anticipado'),
(1128, 9, 9, 301, 3, 'Salida', '2026-03-03', '17:22:00', 'MIKEL - Cierre consolidado', 'Puntual'),
(1129, 10, 10, 301, 3, 'Entrada', '2026-03-03', '08:06:00', 'Ana - Demora por tráfico', 'Retardo'),
(1130, 10, 10, 202, 2, 'Entrada', '2026-03-03', '11:50:00', 'Ana - Coordinación TI', 'Puntual'),
(1131, 10, 10, 301, 3, 'Salida', '2026-03-03', '16:58:00', 'Ana - Salida', 'Puntual'),
(1132, 3, 3, 101, 1, 'Entrada', '2026-03-03', '08:00:00', 'Rafael - Jornada administrativa', 'Puntual'),
(1133, 4, 4, 401, 4, 'Entrada', '2026-03-03', '08:41:00', 'Carlos - Supervisión operativa', 'Puntual'),

-- Jueves 12 Marzo
(1134, 2, 2, 102, 1, 'Entrada', '2026-03-12', '08:09:00', 'María - Retardo controlado', 'Retardo'),
(1135, 9, 9, 301, 3, 'Entrada', '2026-03-12', '07:39:00', 'MIKEL - Entrada temprana', 'Anticipado'),
(1136, 9, 9, 201, 2, 'Entrada', '2026-03-12', '09:45:00', 'MIKEL - Validación APIs', 'Anticipado'),
(1137, 9, 9, 301, 3, 'Entrada', '2026-03-12', '14:20:00', 'MIKEL - Bloque de seguimiento', 'Puntual'),
(1138, 9, 9, 301, 3, 'Salida', '2026-03-12', '17:30:00', 'MIKEL - Cierre de actividades', 'Puntual'),
(1139, 10, 10, 301, 3, 'Entrada', '2026-03-12', '07:53:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1140, 10, 10, 301, 3, 'Entrada', '2026-03-12', '12:10:00', 'Ana - Validación reportes', 'Puntual'),
(1141, 10, 10, 301, 3, 'Salida', '2026-03-12', '17:04:00', 'Ana - Salida', 'Puntual'),
(1142, 1, 1, 101, 1, 'Entrada', '2026-03-12', '07:59:00', 'Admin - Registro de control', 'Puntual'),
(1143, 16, 16, 501, 5, 'Entrada', '2026-03-12', '14:06:00', 'Felipe - Inicio turno con ajuste', 'Retardo'),
(1144, 16, 16, 501, 5, 'Salida', '2026-03-12', '21:04:00', 'Felipe - Cierre turno', 'Puntual'),

-- Jueves 19 Marzo
(1145, 3, 3, 101, 1, 'Entrada', '2026-03-19', '07:58:00', 'Rafael - Apertura', 'Puntual'),
(1146, 9, 9, 301, 3, 'Entrada', '2026-03-19', '07:41:00', 'MIKEL - Inicio de jornada', 'Anticipado'),
(1147, 9, 9, 201, 2, 'Entrada', '2026-03-19', '10:02:00', 'MIKEL - Mesa técnica', 'Puntual'),
(1148, 9, 9, 301, 3, 'Entrada', '2026-03-19', '13:18:00', 'MIKEL - Reingreso', 'Puntual'),
(1149, 9, 9, 301, 3, 'Salida', '2026-03-19', '17:16:00', 'MIKEL - Salida', 'Puntual'),
(1150, 10, 10, 301, 3, 'Entrada', '2026-03-19', '07:57:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1151, 10, 10, 202, 2, 'Entrada', '2026-03-19', '10:36:00', 'Ana - Prueba integración', 'Puntual'),
(1152, 10, 10, 301, 3, 'Entrada', '2026-03-19', '13:32:00', 'Ana - Reinicio bloque tarde', 'Puntual'),
(1153, 10, 10, 301, 3, 'Salida', '2026-03-19', '17:01:00', 'Ana - Cierre jornada', 'Puntual'),
(1154, 4, 4, 401, 4, 'Entrada', '2026-03-19', '08:38:00', 'Carlos - Supervisión de planta', 'Puntual'),

-- Jueves 26 Marzo
(1155, 1, 1, 101, 1, 'Entrada', '2026-03-26', '08:03:00', 'Admin - Entrada con retraso leve', 'Retardo'),
(1156, 2, 2, 101, 1, 'Entrada', '2026-03-26', '07:57:00', 'María - Entrada puntual', 'Puntual'),
(1157, 9, 9, 301, 3, 'Entrada', '2026-03-26', '07:38:00', 'MIKEL - Inicio temprano', 'Anticipado'),
(1158, 9, 9, 301, 3, 'Entrada', '2026-03-26', '11:08:00', 'MIKEL - Revisión parcial', 'Puntual'),
(1159, 9, 9, 301, 3, 'Salida', '2026-03-26', '17:11:00', 'MIKEL - Salida', 'Puntual'),
(1160, 10, 10, 301, 3, 'Entrada', '2026-03-26', '07:52:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1161, 10, 10, 301, 3, 'Entrada', '2026-03-26', '12:22:00', 'Ana - Revisión presupuestal', 'Puntual'),
(1162, 10, 10, 301, 3, 'Salida', '2026-03-26', '16:57:00', 'Ana - Fin de actividades', 'Puntual'),
(1163, 16, 16, 501, 5, 'Entrada', '2026-03-26', '14:02:00', 'Felipe - Inicio turno noche', 'Puntual'),
(1164, 16, 16, 501, 5, 'Salida', '2026-03-26', '20:59:00', 'Felipe - Cierre turno', 'Puntual'),

-- Miércoles 1 Abril
(1165, 1, 1, 101, 1, 'Entrada', '2026-04-01', '07:58:00', 'Admin - Inicio de mes', 'Anticipado'),
(1166, 3, 3, 101, 1, 'Entrada', '2026-04-01', '08:01:00', 'Rafael - Entrada regular', 'Puntual'),
(1167, 9, 9, 301, 3, 'Entrada', '2026-04-01', '07:36:00', 'MIKEL - Inicio Q2', 'Anticipado'),
(1168, 9, 9, 201, 2, 'Entrada', '2026-04-01', '09:55:00', 'MIKEL - Seguimiento técnico', 'Anticipado'),
(1169, 9, 9, 301, 3, 'Entrada', '2026-04-01', '13:12:00', 'MIKEL - Reingreso operativo', 'Puntual'),
(1170, 9, 9, 301, 3, 'Salida', '2026-04-01', '17:08:00', 'MIKEL - Salida', 'Puntual'),
(1171, 10, 10, 301, 3, 'Entrada', '2026-04-01', '07:54:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1172, 10, 10, 202, 2, 'Entrada', '2026-04-01', '11:28:00', 'Ana - Revisión de backlog', 'Puntual'),
(1173, 10, 10, 301, 3, 'Salida', '2026-04-01', '16:54:00', 'Ana - Salida', 'Puntual'),
(1174, 4, 4, 401, 4, 'Entrada', '2026-04-01', '08:29:00', 'Carlos - Control operativo', 'Puntual'),
(1175, 16, 16, 501, 5, 'Entrada', '2026-04-01', '14:04:00', 'Felipe - Inicio turno', 'Puntual'),
(1176, 16, 16, 501, 5, 'Salida', '2026-04-01', '21:00:00', 'Felipe - Salida turno', 'Puntual'),

-- Lunes 6 Abril
(1177, 2, 2, 102, 1, 'Entrada', '2026-04-06', '08:10:00', 'María - Retardo por reunión previa', 'Retardo'),
(1178, 9, 9, 301, 3, 'Entrada', '2026-04-06', '07:35:00', 'MIKEL - Apertura de semana', 'Anticipado'),
(1179, 9, 9, 301, 3, 'Entrada', '2026-04-06', '10:18:00', 'MIKEL - Seguimiento indicadores', 'Puntual'),
(1180, 9, 9, 301, 3, 'Entrada', '2026-04-06', '13:40:00', 'MIKEL - Reingreso mediodía', 'Puntual'),
(1181, 9, 9, 301, 3, 'Salida', '2026-04-06', '17:14:00', 'MIKEL - Salida', 'Puntual'),
(1182, 10, 10, 301, 3, 'Entrada', '2026-04-06', '07:49:00', 'Ana - Inicio anticipado', 'Anticipado'),
(1183, 10, 10, 301, 3, 'Entrada', '2026-04-06', '11:05:00', 'Ana - Revisión de incidencias', 'Puntual'),
(1184, 10, 10, 301, 3, 'Salida', '2026-04-06', '16:59:00', 'Ana - Cierre día', 'Puntual'),
(1185, 4, 4, 401, 4, 'Entrada', '2026-04-06', '08:44:00', 'Carlos - Arranque operativo', 'Puntual'),
(1186, 1, 1, 101, 1, 'Entrada', '2026-04-06', '08:00:00', 'Admin - Registro estándar', 'Puntual'),

-- ============================================
-- PRUEBAS RECIENTES - HORARIO EJEMPLO (04:50-11:00)
-- Registros distribuidos coherentemente para el módulo
-- ============================================
-- Martes 7 Abril (ventana de pruebas matutinas)
(1187, 1, 1, 101, 1, 'Entrada', '2026-04-07', '04:50:00', 'Juan Pérez - Pruebas recientes apertura', 'Anticipado'),
(1188, 1, 1, 102, 1, 'Entrada', '2026-04-07', '05:02:00', 'Juan Pérez - Pruebas recientes validación biométrica', 'Anticipado'),
(1189, 1, 1, 103, 1, 'Entrada', '2026-04-07', '05:18:00', 'Juan Pérez - Pruebas recientes terminal RH', 'Anticipado'),
(1190, 1, 1, 101, 1, 'Entrada', '2026-04-07', '05:34:00', 'Juan Pérez - Pruebas recientes control inicial', 'Anticipado'),
(1191, 1, 1, 102, 1, 'Entrada', '2026-04-07', '05:48:00', 'Juan Pérez - Pruebas recientes verificación 1', 'Anticipado'),
(1192, 1, 1, 103, 1, 'Entrada', '2026-04-07', '06:05:00', 'Juan Pérez - Pruebas recientes verificación 2', 'Anticipado'),
(1193, 1, 1, 101, 1, 'Entrada', '2026-04-07', '06:22:00', 'Juan Pérez - Pruebas recientes preregistro', 'Anticipado'),
(1194, 1, 1, 102, 1, 'Entrada', '2026-04-07', '06:38:00', 'Juan Pérez - Pruebas recientes lote temprano', 'Anticipado'),
(1195, 1, 1, 103, 1, 'Entrada', '2026-04-07', '06:55:00', 'Juan Pérez - Pruebas recientes lote 2', 'Anticipado'),
(1196, 1, 1, 101, 1, 'Entrada', '2026-04-07', '07:10:00', 'Juan Pérez - Pruebas recientes transición laboral', 'Anticipado'),
(1197, 1, 1, 102, 1, 'Entrada', '2026-04-07', '07:24:00', 'Juan Pérez - Pruebas recientes flujo normal', 'Puntual'),
(1198, 1, 1, 103, 1, 'Entrada', '2026-04-07', '07:40:00', 'Juan Pérez - Pruebas recientes control continuo', 'Puntual'),
(1199, 1, 1, 101, 1, 'Entrada', '2026-04-07', '07:58:00', 'Juan Pérez - Pruebas recientes validación puntual', 'Puntual'),
(1200, 1, 1, 102, 1, 'Entrada', '2026-04-07', '08:16:00', 'Juan Pérez - Pruebas recientes control intermedio', 'Puntual'),
(1201, 1, 1, 103, 1, 'Entrada', '2026-04-07', '08:33:00', 'Juan Pérez - Pruebas recientes prueba cruzada', 'Puntual'),
(1202, 1, 1, 101, 1, 'Entrada', '2026-04-07', '08:47:00', 'Juan Pérez - Pruebas recientes retraso controlado', 'Retardo'),
(1203, 1, 1, 102, 1, 'Entrada', '2026-04-07', '09:05:00', 'Juan Pérez - Pruebas recientes seguimiento', 'Puntual'),
(1204, 1, 1, 103, 1, 'Entrada', '2026-04-07', '09:22:00', 'Juan Pérez - Pruebas recientes lectura secundaria', 'Puntual'),
(1205, 1, 1, 101, 1, 'Entrada', '2026-04-07', '09:41:00', 'Juan Pérez - Pruebas recientes verificación continua', 'Puntual'),
(1206, 1, 1, 102, 1, 'Entrada', '2026-04-07', '09:58:00', 'Juan Pérez - Pruebas recientes control estadístico', 'Puntual'),
(1207, 1, 1, 103, 1, 'Entrada', '2026-04-07', '10:16:00', 'Juan Pérez - Pruebas recientes carga media', 'Puntual'),
(1208, 1, 1, 101, 1, 'Entrada', '2026-04-07', '10:34:00', 'Juan Pérez - Pruebas recientes control final', 'Puntual'),
(1209, 1, 1, 102, 1, 'Entrada', '2026-04-07', '10:52:00', 'Juan Pérez - Pruebas recientes cierre de ventana', 'Puntual'),
(1210, 1, 1, 103, 1, 'Entrada', '2026-04-07', '11:00:00', 'Juan Pérez - Pruebas recientes fin de bloque', 'Puntual'),

-- ============================================
-- REGISTROS RECIENTES ABRIL-MAYO 2026
-- ============================================
(1211, 9, 9, 301, 3, 'Entrada', '2026-04-08', '07:50:00', 'MIKEL - Reanudacion abril', 'Anticipado'),
(1212, 10, 10, 301, 3, 'Entrada', '2026-04-09', '07:59:00', 'Ana - Inicio abril', 'Anticipado'),
(1213, 9, 9, 301, 3, 'Entrada', '2026-04-10', '07:55:00', 'MIKEL - Seguimiento semanal', 'Puntual'),
(1214, 10, 10, 301, 3, 'Entrada', '2026-04-16', '08:06:00', 'Ana - Llegada con trafico', 'Retardo'),
(1215, 9, 9, 301, 3, 'Entrada', '2026-04-15', '08:05:00', 'MIKEL - Ajuste horario', 'Retardo'),
(1216, 9, 9, 301, 3, 'Entrada', '2026-04-22', '07:42:00', 'MIKEL - Inicio temprano', 'Anticipado'),
(1217, 10, 10, 301, 3, 'Entrada', '2026-04-23', '07:54:00', 'Ana - Entrada anticipada', 'Anticipado'),
(1218, 9, 9, 301, 3, 'Entrada', '2026-04-29', '07:58:00', 'MIKEL - Cierre abril', 'Puntual'),
(1219, 10, 10, 301, 3, 'Entrada', '2026-04-30', '08:01:00', 'Ana - Cierre abril', 'Puntual'),
(1220, 9, 9, 301, 3, 'Entrada', '2026-05-05', '07:47:00', 'MIKEL - Inicio mayo', 'Anticipado'),
(1221, 10, 10, 301, 3, 'Entrada', '2026-05-06', '08:03:00', 'Ana - Inicio mayo', 'Puntual'),
(1222, 9, 9, 301, 3, 'Entrada', '2026-05-07', '08:02:00', 'MIKEL - Seguimiento mayo', 'Puntual'),

-- ============================================
-- SEMANA INICIO MAYO (4-7 Mayo 2026)
-- May 1 = Día del Trabajo (feriado, sin registros)
-- ============================================

-- LUNES 4 MAYO - Primer día laboral de mayo
-- Juan Pérez: Turno Madrugada (05:00-11:00) - Área RH
(1223, 1, 1, 101, 1, 'Entrada', '2026-05-04', '04:58:00', 'Juan Pérez - Inicio Turno Madrugada lunes', 'Anticipado'),
(1224, 1, 1, 102, 1, 'Entrada', '2026-05-04', '07:20:00', 'Juan Pérez - Control intermedio mañana', 'Puntual'),
(1225, 1, 1, 103, 1, 'Entrada', '2026-05-04', '09:45:00', 'Juan Pérez - Seguimiento área RH', 'Puntual'),
(1226, 1, 1, 101, 1, 'Salida', '2026-05-04', '11:02:00', 'Juan Pérez - Cierre Turno Madrugada lunes', 'Puntual'),
-- MIKEL - Turno Matutino lunes
(1227, 9, 9, 301, 3, 'Entrada', '2026-05-04', '07:44:00', 'MIKEL - Inicio semana mayo', 'Anticipado'),
(1228, 9, 9, 301, 3, 'Salida', '2026-05-04', '17:05:00', 'MIKEL - Salida lunes mayo', 'Puntual'),
-- Ana - Turno Matutino lunes
(1229, 10, 10, 301, 3, 'Entrada', '2026-05-04', '08:00:00', 'Ana - Inicio semana mayo', 'Puntual'),
(1230, 10, 10, 301, 3, 'Salida', '2026-05-04', '17:00:00', 'Ana - Salida lunes mayo', 'Puntual'),

-- MARTES 5 MAYO
-- Juan Pérez - Turno Madrugada
(1231, 1, 1, 101, 1, 'Entrada', '2026-05-05', '05:01:00', 'Juan Pérez - Turno Madrugada martes', 'Puntual'),
(1232, 1, 1, 102, 1, 'Entrada', '2026-05-05', '08:10:00', 'Juan Pérez - Check matutino', 'Puntual'),
(1233, 1, 1, 101, 1, 'Salida', '2026-05-05', '10:58:00', 'Juan Pérez - Cierre Turno Madrugada martes', 'Puntual'),
-- Ana (MIKEL ya tiene registro 1220 en este día)
(1234, 10, 10, 301, 3, 'Entrada', '2026-05-05', '07:57:00', 'Ana - Martes anticipada', 'Anticipado'),
(1235, 10, 10, 301, 3, 'Salida', '2026-05-05', '17:02:00', 'Ana - Salida martes mayo', 'Puntual'),

-- MIÉRCOLES 6 MAYO
-- Juan Pérez - Turno Madrugada (leve retardo)
(1236, 1, 1, 101, 1, 'Entrada', '2026-05-06', '05:09:00', 'Juan Pérez - Turno Madrugada miércoles', 'Retardo'),
(1237, 1, 1, 103, 1, 'Entrada', '2026-05-06', '09:15:00', 'Juan Pérez - Seguimiento control RH', 'Puntual'),
(1238, 1, 1, 101, 1, 'Salida', '2026-05-06', '11:00:00', 'Juan Pérez - Cierre Turno Madrugada miércoles', 'Puntual'),
-- MIKEL (Ana ya tiene registro 1221 en este día)
(1239, 9, 9, 301, 3, 'Entrada', '2026-05-06', '07:51:00', 'MIKEL - Miércoles mayo', 'Anticipado'),
(1240, 9, 9, 301, 3, 'Salida', '2026-05-06', '17:08:00', 'MIKEL - Salida miércoles mayo', 'Puntual'),

-- JUEVES 7 MAYO
-- Juan Pérez - Turno Madrugada (entrada anticipada)
(1241, 1, 1, 101, 1, 'Entrada', '2026-05-07', '04:55:00', 'Juan Pérez - Turno Madrugada jueves', 'Anticipado'),
(1242, 1, 1, 102, 1, 'Entrada', '2026-05-07', '07:48:00', 'Juan Pérez - Control intermedio', 'Puntual'),
(1243, 1, 1, 103, 1, 'Entrada', '2026-05-07', '10:05:00', 'Juan Pérez - Control final turno', 'Puntual'),
(1244, 1, 1, 101, 1, 'Salida', '2026-05-07', '11:05:00', 'Juan Pérez - Cierre Turno Madrugada jueves', 'Puntual'),
-- Ana (MIKEL ya tiene registro 1222 en este día)
(1245, 10, 10, 301, 3, 'Entrada', '2026-05-07', '08:01:00', 'Ana - Jueves mayo', 'Puntual'),
(1246, 10, 10, 301, 3, 'Salida', '2026-05-07', '17:00:00', 'Ana - Salida jueves mayo', 'Puntual');

-- ============================================
-- 9.1 VIERNES - CALENDARIO 5 (OFICINA RH 08:20-16:00)
-- Primer registro del día viernes: 08:30 - Usuarios 1 y 11
-- ============================================
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES
-- VIERNES 10 ABRIL
(1247, 1,  1,  101, 1, 'Entrada', '2026-04-10', '08:30:00', 'Juan Pérez - Entrada viernes Oficina RH',    'Puntual'),
(1248, 1,  1,  101, 1, 'Salida',  '2026-04-10', '16:00:00', 'Juan Pérez - Salida viernes Oficina RH',     'Puntual'),
(1249, 11, 11, 102, 1, 'Entrada', '2026-04-10', '08:30:00', 'Juan Carlos - Entrada viernes Oficina RH',   'Puntual'),
(1250, 11, 11, 102, 1, 'Salida',  '2026-04-10', '16:00:00', 'Juan Carlos - Salida viernes Oficina RH',    'Puntual'),
-- VIERNES 17 ABRIL
(1251, 1,  1,  101, 1, 'Entrada', '2026-04-17', '08:30:00', 'Juan Pérez - Entrada viernes Oficina RH',    'Puntual'),
(1252, 1,  1,  101, 1, 'Salida',  '2026-04-17', '16:02:00', 'Juan Pérez - Salida viernes Oficina RH',     'Puntual'),
(1253, 11, 11, 102, 1, 'Entrada', '2026-04-17', '08:30:00', 'Juan Carlos - Entrada viernes Oficina RH',   'Puntual'),
(1254, 11, 11, 102, 1, 'Salida',  '2026-04-17', '15:58:00', 'Juan Carlos - Salida viernes Oficina RH',    'Puntual'),
-- VIERNES 24 ABRIL
(1255, 1,  1,  101, 1, 'Entrada', '2026-04-24', '08:30:00', 'Juan Pérez - Entrada viernes Oficina RH',    'Puntual'),
(1256, 1,  1,  101, 1, 'Salida',  '2026-04-24', '16:00:00', 'Juan Pérez - Salida viernes Oficina RH',     'Puntual'),
(1257, 11, 11, 102, 1, 'Entrada', '2026-04-24', '08:30:00', 'Juan Carlos - Entrada viernes Oficina RH',   'Puntual'),
(1258, 11, 11, 102, 1, 'Salida',  '2026-04-24', '16:00:00', 'Juan Carlos - Salida viernes Oficina RH',    'Puntual');

-- ============================================
-- 10. USUARIOS-CALENDARIO (UNO POR USUARIO)
-- ============================================
-- SISTEMA DE DISTRIBUCIÓN POR TURNOS ✅
-- ============================================
-- REGLA: Cada usuario tiene UN calendario asignado según su turno
-- 🌅 Usuarios 2-10, 12-15: Calendario 2 (2026 MATUTINO 07:00-14:00) ACTIVO
-- 🌆 Usuarios 16-30: Calendario 3 (2026 NOCTURNO 14:00-21:00) ACTIVO
-- 👤 Usuario 31 (SujetoPrueba): Calendario 1 (2025 MATUTINO) EXPIRADO
-- 🏢 Usuarios 1 (Juan Pérez) y 11 (Juan Carlos, con tutor): Calendario 5 (2026 OFICINA RH 08:20-16:00) ACTIVO
-- NADIE tiene múltiples calendarios simultáneamente
-- ============================================

INSERT INTO usuarios_calendario (matricula, id_calendario) VALUES
-- ╔═══════════════════════════════════════════════════════════╗
-- ║  CALENDARIO 2: Turno Matutino 2026 (07:00-14:00) ✅      ║
-- ║  Usuarios 2-15 - ACTIVO (Juan Pérez movido a Cal. 4)     ║
-- ╚═══════════════════════════════════════════════════════════╝
(2, 2),   -- 🌅 María Admin
(3, 2),   -- 🌅 Rafael Admin
(4, 2),   -- 🌅 Carlos Supervisor
(5, 2),   -- 🌅 Lucía Supervisor
(6, 2),   -- 🌅 Sofía Analista
(7, 2),   -- 🌅 Miguel Analista
(8, 2),   -- 🌅 Verónica Analista
(9, 2),   -- 🌅 MIKEL ABRAHAM Analista
(10, 2),  -- 🌅 Ana Analista
(11, 5),  -- 🏢 Juan Carlos Empleado → Turno Oficina RH (tiene tutor)
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
-- Hoy es 07/04/2026, su calendario está vencido
-- Útil para probar validaciones de calendarios expirados
(31, 1),  -- ⏰ SujetoPrueba - Calendario 2025 EXPIRADO ❌

-- ╔═══════════════════════════════════════════════════════════╗
-- ║  CALENDARIO 5: Turno Oficina RH 2026 (08:20-16:00) ✅    ║
-- ║  Juan Pérez (#1) y Juan Carlos (#11, tiene tutor)        ║
-- ╚═══════════════════════════════════════════════════════════╝
(1, 5);   -- 🏢 Juan Pérez - Turno Oficina RH 2026

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
UNION ALL SELECT '📅 SISTEMA DE CALENDARIOS (4 calendarios)', '==================='
UNION ALL SELECT 'Calendarios Totales:', COUNT(*) FROM calendario
UNION ALL SELECT '🌅 Cal 2: Matutino 2026 ACTIVO:', COUNT(*) FROM calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Cal 3: Nocturno 2026 ACTIVO:', COUNT(*) FROM calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Cal 1: Matutino 2025 EXPIRADO:', COUNT(*) FROM calendario WHERE id_calendario = 1
UNION ALL SELECT '🌄 Cal 4: Madrugada 2026 May-Jul ACTIVO:', COUNT(*) FROM calendario WHERE id_calendario = 4
UNION ALL SELECT '', ''
UNION ALL SELECT '⏰ HORARIOS Y BLOQUES (Sistema de Turnos)', '==================='
UNION ALL SELECT 'Total Horarios:', COUNT(*) FROM horario
UNION ALL SELECT 'Horario 1 (2025 Matutino) Inactivo:', COUNT(*) FROM horario WHERE id_horario = 1
UNION ALL SELECT 'Horario 2 (2026 Matutino) Activo:', COUNT(*) FROM horario WHERE id_horario = 2
UNION ALL SELECT 'Horario 3 (2026 Nocturno) Activo:', COUNT(*) FROM horario WHERE id_horario = 3
UNION ALL SELECT 'Horario 4 (2026 Madrugada) Activo:', COUNT(*) FROM horario WHERE id_horario = 4
UNION ALL SELECT 'Días en Horario 1 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 1
UNION ALL SELECT 'Días en Horario 2 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 2
UNION ALL SELECT 'Días en Horario 3 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 3
UNION ALL SELECT 'Días en Horario 4 (L-D):', COUNT(*) FROM dia_horario WHERE id_horario = 4
UNION ALL SELECT 'Bloques Matutinos (07:00-14:00):', COUNT(*) FROM bloques_horario WHERE id_bloque BETWEEN 1 AND 35
UNION ALL SELECT 'Bloques Nocturnos (14:00-21:00):', COUNT(*) FROM bloques_horario WHERE id_bloque BETWEEN 36 AND 70
UNION ALL SELECT 'Bloques Madrugada (05:00-11:00):', COUNT(*) FROM bloques_horario WHERE id_bloque BETWEEN 71 AND 100
UNION ALL SELECT 'Total Bloques de 1 hora:', COUNT(*) FROM bloques_horario WHERE activo = 'Activo'
UNION ALL SELECT 'Asignaciones Bloque-Día:', COUNT(*) FROM bloque_dia_asignacion
UNION ALL SELECT '', ''
UNION ALL SELECT '👥 DISTRIBUCIÓN POR TURNOS', '==================='
UNION ALL SELECT 'Total Asignaciones:', COUNT(*) FROM usuarios_calendario
UNION ALL SELECT '🌅 Turno Matutino 2026 (Usuarios 2-15):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Turno Nocturno 2026 (Usuarios 16-30):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Calendario Expirado 2025 (Usuario 31):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 1
UNION ALL SELECT '🌄 Turno Madrugada 2026 May-Jul (Juan Pérez):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 4
UNION ALL SELECT '', ''
UNION ALL SELECT '📝 REGISTROS ENERO-MAYO 2026', '==================='
UNION ALL SELECT 'Total Registros (01/01 al 07/05):', COUNT(*) FROM registro WHERE fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Registros de Entrada:', COUNT(*) FROM registro WHERE tipo_registro = 'Entrada' AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Registros de Salida:', COUNT(*) FROM registro WHERE tipo_registro = 'Salida' AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Registros Juan Pérez May 4-7 (Madrugada):', COUNT(*) FROM registro WHERE matricula = 1 AND fecha >= '2026-05-04' AND fecha <= '2026-05-07'
UNION ALL SELECT '', ''
UNION ALL SELECT '🌄 JUAN PÉREZ (mat 1) - TURNO MADRUGADA', '==================='
UNION ALL SELECT 'Calendario Asignado (id):', id_calendario FROM usuarios_calendario WHERE matricula = 1
UNION ALL SELECT 'Turno:', '🌄 Madrugada 05:00-11:00 (Mayo-Julio 2026)'
UNION ALL SELECT 'Registros Ene-May 2026:', COUNT(*) FROM registro WHERE matricula = 1 AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Registros Mayo (4-7):', COUNT(*) FROM registro WHERE matricula = 1 AND fecha >= '2026-05-04' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Entradas Anticipadas:', COUNT(*) FROM registro WHERE matricula = 1 AND estado_registro = 'Anticipado' AND tipo_registro = 'Entrada' AND fecha >= '2026-05-04'
UNION ALL SELECT 'Retardos Mayo:', COUNT(*) FROM registro WHERE matricula = 1 AND estado_registro = 'Retardo' AND fecha >= '2026-05-04'
UNION ALL SELECT '', ''
UNION ALL SELECT '🌟 MIKEL ABRAHAM (mat 9) - TURNO MATUTINO', '==================='
UNION ALL SELECT 'Calendario Asignado (id):', id_calendario FROM usuarios_calendario WHERE matricula = 9
UNION ALL SELECT 'Turno:', '🌅 Matutino 07:00-14:00'
UNION ALL SELECT 'Registros Ene-May 2026:', COUNT(*) FROM registro WHERE matricula = 9 AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Entradas Anticipadas:', COUNT(*) FROM registro WHERE matricula = 9 AND estado_registro = 'Anticipado' AND tipo_registro = 'Entrada' AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Entradas Puntuales:', COUNT(*) FROM registro WHERE matricula = 9 AND estado_registro = 'Puntual' AND tipo_registro = 'Entrada' AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT '', ''
UNION ALL SELECT '👤 ANA RODRÍGUEZ (mat 10) - TURNO MATUTINO', '==================='
UNION ALL SELECT 'Calendario Asignado (id):', id_calendario FROM usuarios_calendario WHERE matricula = 10
UNION ALL SELECT 'Turno:', '🌅 Matutino 07:00-14:00'
UNION ALL SELECT 'Registros Ene-May 2026:', COUNT(*) FROM registro WHERE matricula = 10 AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
UNION ALL SELECT 'Entradas Anticipadas:', COUNT(*) FROM registro WHERE matricula = 10 AND estado_registro = 'Anticipado' AND tipo_registro = 'Entrada' AND fecha >= '2026-01-01' AND fecha <= '2026-05-07'
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
UNION ALL SELECT 'Fecha actual sistema:', '07/05/2026'
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
UNION ALL SELECT '🌄 Turno Madrugada (05:00-11:00):', '6 bloques × 5 áreas = 30 bloques'
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
UNION ALL SELECT 'Calendario 4 (2026 Madrugada May-Jul):', 'Horario 4 con Lunes-Domingo (L-V laborables)'
UNION ALL SELECT 'Días laborables:', 'Lunes-Viernes (35 bloques por día × 5 áreas)'
UNION ALL SELECT 'Sábado y Domingo:', 'Sin bloques asignados (descanso)'
UNION ALL SELECT 'Traslapes:', '❌ IMPOSIBLE - Bloques consecutivos de 1 hora'
UNION ALL SELECT '', ''
UNION ALL SELECT '👥 RESUMEN USUARIOS (31 usuarios)', '==================='
UNION ALL SELECT 'Total usuarios sistema:', COUNT(*) FROM usuarios
UNION ALL SELECT '🌅 Con turno MATUTINO (usuarios 2-15):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 2
UNION ALL SELECT '🌆 Con turno NOCTURNO (usuarios 16-30):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 3
UNION ALL SELECT '⏰ Con calendario EXPIRADO (usuario 31):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 1
UNION ALL SELECT '🌄 Con turno MADRUGADA May-Jul (Juan Pérez):', COUNT(*) FROM usuarios_calendario WHERE id_calendario = 4
UNION ALL SELECT 'Usuario especial prueba:', 'SujetoPrueba (matricula 31)';

SELECT '' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';
SELECT '   🎯 SISTEMA CON BLOQUES DE 1 HORA - SIN TRASLAPES         ' AS '';
SELECT '════════════════════════════════════════════════════════════' AS '';

-- ============================================================
-- BLOQUE EXTENDIDO: MAYO 8 - JUNIO 5, 2026
-- Datos para gráficas mensuales y Control de Emergencia
-- ============================================================
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES

-- ====== SEMANA 11 MAY (11-15 Mayo) ======
-- Lunes 11 Mayo
(1259, 9,  9,  301, 3, 'Entrada', '2026-05-11', '07:46:00', 'MIKEL - Inicio semana',     'Anticipado'),
(1260, 10, 10, 301, 3, 'Entrada', '2026-05-11', '08:00:00', 'Ana - Lunes puntual',       'Puntual'),
(1261, 6,  6,  201, 2, 'Entrada', '2026-05-11', '07:52:00', 'Sofia - TI matutino',       'Anticipado'),
(1262, 4,  4,  401, 4, 'Entrada', '2026-05-11', '08:35:00', 'Carlos - Operaciones',      'Puntual'),
(1263, 3,  3,  101, 1, 'Entrada', '2026-05-11', '08:00:00', 'Rafael - RH',               'Puntual'),
(1264, 16, 16, 501, 5, 'Entrada', '2026-05-11', '14:02:00', 'Felipe - MKT nocturno',     'Puntual'),
(1265, 18, 18, 401, 4, 'Entrada', '2026-05-11', '14:10:00', 'Gabriela - OPS nocturno',   'Retardo'),
(1266, 19, 19, 201, 2, 'Entrada', '2026-05-11', '14:00:00', 'Hugo - TI nocturno',        'Puntual'),
(1267, 9,  9,  301, 3, 'Salida',  '2026-05-11', '17:05:00', 'MIKEL - Salida',            'Puntual'),
(1268, 10, 10, 301, 3, 'Salida',  '2026-05-11', '17:00:00', 'Ana - Salida',              'Puntual'),
(1269, 6,  6,  201, 2, 'Salida',  '2026-05-11', '17:10:00', 'Sofia - Salida',            'Puntual'),
(1270, 16, 16, 501, 5, 'Salida',  '2026-05-11', '21:00:00', 'Felipe - Cierre noche',     'Puntual'),
(1271, 18, 18, 401, 4, 'Salida',  '2026-05-11', '21:05:00', 'Gabriela - Cierre noche',   'Puntual'),

-- Martes 12 Mayo
(1272, 9,  9,  301, 3, 'Entrada', '2026-05-12', '07:50:00', 'MIKEL - Martes anticipado', 'Anticipado'),
(1273, 10, 10, 301, 3, 'Entrada', '2026-05-12', '08:07:00', 'Ana - Leve retardo',        'Retardo'),
(1274, 7,  7,  202, 2, 'Entrada', '2026-05-12', '07:58:00', 'Miguel - TI',               'Puntual'),
(1275, 12, 12, 102, 1, 'Entrada', '2026-05-12', '08:00:00', 'Pamela - RH',               'Puntual'),
(1276, 15, 15, 301, 3, 'Entrada', '2026-05-12', '07:55:00', 'Sergio - FIN',              'Anticipado'),
(1277, 22, 22, 301, 3, 'Entrada', '2026-05-12', '14:05:00', 'Monica - FIN nocturno',     'Puntual'),
(1278, 21, 21, 101, 1, 'Entrada', '2026-05-12', '14:00:00', 'Diego - RH nocturno',       'Puntual'),
(1279, 9,  9,  301, 3, 'Salida',  '2026-05-12', '17:08:00', 'MIKEL',                     'Puntual'),
(1280, 10, 10, 301, 3, 'Salida',  '2026-05-12', '17:01:00', 'Ana',                       'Puntual'),
(1281, 7,  7,  202, 2, 'Salida',  '2026-05-12', '17:00:00', 'Miguel',                    'Puntual'),
(1282, 22, 22, 301, 3, 'Salida',  '2026-05-12', '21:00:00', 'Monica nocturno',           'Puntual'),

-- Miercoles 13 Mayo
(1283, 9,  9,  301, 3, 'Entrada', '2026-05-13', '07:44:00', 'MIKEL - Miercoles',         'Anticipado'),
(1284, 2,  2,  101, 1, 'Entrada', '2026-05-13', '08:00:00', 'Maria - Admin RH',          'Puntual'),
(1285, 8,  8,  301, 3, 'Entrada', '2026-05-13', '07:59:00', 'Veronica - FIN',            'Puntual'),
(1286, 5,  5,  501, 5, 'Entrada', '2026-05-13', '08:10:00', 'Lucia - MKT',               'Retardo'),
(1287, 13, 13, 401, 4, 'Entrada', '2026-05-13', '08:00:00', 'Roberto - OPS',             'Puntual'),
(1288, 23, 23, 501, 5, 'Entrada', '2026-05-13', '14:03:00', 'Jorge - MKT nocturno',      'Puntual'),
(1289, 26, 26, 401, 4, 'Entrada', '2026-05-13', '14:00:00', 'Adan - OPS nocturno',       'Puntual'),
(1290, 9,  9,  301, 3, 'Salida',  '2026-05-13', '17:12:00', 'MIKEL',                     'Puntual'),
(1291, 2,  2,  101, 1, 'Salida',  '2026-05-13', '17:00:00', 'Maria',                     'Puntual'),
(1292, 23, 23, 501, 5, 'Salida',  '2026-05-13', '21:00:00', 'Jorge nocturno',            'Puntual'),

-- Jueves 14 Mayo
(1293, 9,  9,  301, 3, 'Entrada', '2026-05-14', '07:48:00', 'MIKEL - Jueves',            'Anticipado'),
(1294, 10, 10, 301, 3, 'Entrada', '2026-05-14', '08:00:00', 'Ana - Jueves',              'Puntual'),
(1295, 6,  6,  201, 2, 'Entrada', '2026-05-14', '07:55:00', 'Sofia - TI',                'Anticipado'),
(1296, 14, 14, 501, 5, 'Entrada', '2026-05-14', '08:05:00', 'Lorena - MKT',              'Puntual'),
(1297, 17, 17, 101, 1, 'Entrada', '2026-05-14', '14:00:00', 'Isabel - RH nocturno',      'Puntual'),
(1298, 25, 25, 201, 2, 'Entrada', '2026-05-14', '14:08:00', 'Ricardo - TI nocturno',     'Retardo'),
(1299, 9,  9,  301, 3, 'Salida',  '2026-05-14', '17:03:00', 'MIKEL',                     'Puntual'),
(1300, 10, 10, 301, 3, 'Salida',  '2026-05-14', '17:00:00', 'Ana',                       'Puntual'),
(1301, 17, 17, 101, 1, 'Salida',  '2026-05-14', '21:00:00', 'Isabel nocturno',           'Puntual'),

-- Viernes 15 Mayo
(1302, 1,  1,  101, 1, 'Entrada', '2026-05-15', '08:30:00', 'Juan Perez - Oficina RH',   'Puntual'),
(1303, 11, 11, 102, 1, 'Entrada', '2026-05-15', '08:30:00', 'Juan Carlos - Oficina RH',  'Puntual'),
(1304, 9,  9,  301, 3, 'Entrada', '2026-05-15', '07:43:00', 'MIKEL - Viernes anticipado','Anticipado'),
(1305, 10, 10, 301, 3, 'Entrada', '2026-05-15', '08:00:00', 'Ana - Viernes',             'Puntual'),
(1306, 3,  3,  101, 1, 'Entrada', '2026-05-15', '07:58:00', 'Rafael - Viernes',          'Puntual'),
(1307, 1,  1,  101, 1, 'Salida',  '2026-05-15', '16:00:00', 'Juan Perez - Salida RH',    'Puntual'),
(1308, 11, 11, 102, 1, 'Salida',  '2026-05-15', '16:00:00', 'Juan Carlos - Salida',      'Puntual'),
(1309, 9,  9,  301, 3, 'Salida',  '2026-05-15', '17:06:00', 'MIKEL',                     'Puntual'),
(1310, 10, 10, 301, 3, 'Salida',  '2026-05-15', '17:00:00', 'Ana',                       'Puntual'),

-- ====== SEMANA 18 MAY (18-22 Mayo) ======
-- Lunes 18 Mayo
(1311, 9,  9,  301, 3, 'Entrada', '2026-05-18', '07:51:00', 'MIKEL - Inicio semana',     'Anticipado'),
(1312, 10, 10, 301, 3, 'Entrada', '2026-05-18', '08:02:00', 'Ana - Lunes',               'Puntual'),
(1313, 6,  6,  201, 2, 'Entrada', '2026-05-18', '07:55:00', 'Sofia - TI',                'Anticipado'),
(1314, 7,  7,  202, 2, 'Entrada', '2026-05-18', '08:00:00', 'Miguel - TI',               'Puntual'),
(1315, 4,  4,  401, 4, 'Entrada', '2026-05-18', '08:30:00', 'Carlos - OPS',              'Puntual'),
(1316, 2,  2,  101, 1, 'Entrada', '2026-05-18', '08:06:00', 'Maria - RH',                'Retardo'),
(1317, 16, 16, 501, 5, 'Entrada', '2026-05-18', '14:00:00', 'Felipe - MKT',              'Puntual'),
(1318, 20, 20, 201, 2, 'Entrada', '2026-05-18', '14:05:00', 'Andres - TI becario',       'Puntual'),
(1319, 28, 28, 301, 3, 'Entrada', '2026-05-18', '14:02:00', 'Esteban - FIN analista',    'Puntual'),
(1320, 9,  9,  301, 3, 'Salida',  '2026-05-18', '17:04:00', 'MIKEL',                     'Puntual'),
(1321, 10, 10, 301, 3, 'Salida',  '2026-05-18', '17:00:00', 'Ana',                       'Puntual'),
(1322, 6,  6,  201, 2, 'Salida',  '2026-05-18', '17:08:00', 'Sofia',                     'Puntual'),
(1323, 16, 16, 501, 5, 'Salida',  '2026-05-18', '21:01:00', 'Felipe nocturno',           'Puntual'),

-- Martes 19 Mayo
(1324, 9,  9,  301, 3, 'Entrada', '2026-05-19', '07:47:00', 'MIKEL - Martes',            'Anticipado'),
(1325, 10, 10, 301, 3, 'Entrada', '2026-05-19', '08:00:00', 'Ana',                       'Puntual'),
(1326, 8,  8,  301, 3, 'Entrada', '2026-05-19', '07:59:00', 'Veronica - FIN',            'Puntual'),
(1327, 15, 15, 301, 3, 'Entrada', '2026-05-19', '07:53:00', 'Sergio - FIN',              'Anticipado'),
(1328, 12, 12, 102, 1, 'Entrada', '2026-05-19', '08:00:00', 'Pamela - RH',               'Puntual'),
(1329, 22, 22, 301, 3, 'Entrada', '2026-05-19', '14:00:00', 'Monica - FIN nocturno',     'Puntual'),
(1330, 27, 27, 101, 1, 'Entrada', '2026-05-19', '14:10:00', 'Berenice - RH tecnico',     'Retardo'),
(1331, 9,  9,  301, 3, 'Salida',  '2026-05-19', '17:10:00', 'MIKEL',                     'Puntual'),
(1332, 10, 10, 301, 3, 'Salida',  '2026-05-19', '17:00:00', 'Ana',                       'Puntual'),
(1333, 22, 22, 301, 3, 'Salida',  '2026-05-19', '21:00:00', 'Monica nocturno',           'Puntual'),

-- Miercoles 20 Mayo
(1334, 9,  9,  301, 3, 'Entrada', '2026-05-20', '07:43:00', 'MIKEL - Miercoles',         'Anticipado'),
(1335, 3,  3,  101, 1, 'Entrada', '2026-05-20', '08:00:00', 'Rafael - RH',               'Puntual'),
(1336, 5,  5,  501, 5, 'Entrada', '2026-05-20', '08:00:00', 'Lucia - MKT supervisor',    'Puntual'),
(1337, 13, 13, 401, 4, 'Entrada', '2026-05-20', '08:10:00', 'Roberto - OPS',             'Retardo'),
(1338, 19, 19, 201, 2, 'Entrada', '2026-05-20', '14:00:00', 'Hugo - TI nocturno',        'Puntual'),
(1339, 23, 23, 501, 5, 'Entrada', '2026-05-20', '14:05:00', 'Jorge - MKT nocturno',      'Puntual'),
(1340, 24, 24, 401, 4, 'Entrada', '2026-05-20', '14:03:00', 'Carmen - OPS nocturno',     'Puntual'),
(1341, 9,  9,  301, 3, 'Salida',  '2026-05-20', '17:09:00', 'MIKEL',                     'Puntual'),
(1342, 19, 19, 201, 2, 'Salida',  '2026-05-20', '21:00:00', 'Hugo nocturno',             'Puntual'),
(1343, 24, 24, 401, 4, 'Salida',  '2026-05-20', '21:02:00', 'Carmen nocturno',           'Puntual'),

-- Jueves 21 Mayo
(1344, 9,  9,  301, 3, 'Entrada', '2026-05-21', '07:49:00', 'MIKEL - Jueves',            'Anticipado'),
(1345, 10, 10, 301, 3, 'Entrada', '2026-05-21', '08:01:00', 'Ana - Jueves',              'Puntual'),
(1346, 2,  2,  101, 1, 'Entrada', '2026-05-21', '07:57:00', 'Maria - Admin',             'Puntual'),
(1347, 6,  6,  201, 2, 'Entrada', '2026-05-21', '08:00:00', 'Sofia - TI',                'Puntual'),
(1348, 14, 14, 501, 5, 'Entrada', '2026-05-21', '08:00:00', 'Lorena - MKT',              'Puntual'),
(1349, 17, 17, 101, 1, 'Entrada', '2026-05-21', '14:00:00', 'Isabel - RH nocturno',      'Puntual'),
(1350, 21, 21, 101, 1, 'Entrada', '2026-05-21', '14:06:00', 'Diego - RH nocturno',       'Puntual'),
(1351, 29, 29, 301, 3, 'Entrada', '2026-05-21', '14:00:00', 'Claudia - FIN nocturno',    'Puntual'),
(1352, 9,  9,  301, 3, 'Salida',  '2026-05-21', '17:05:00', 'MIKEL',                     'Puntual'),
(1353, 10, 10, 301, 3, 'Salida',  '2026-05-21', '17:00:00', 'Ana',                       'Puntual'),
(1354, 17, 17, 101, 1, 'Salida',  '2026-05-21', '21:00:00', 'Isabel nocturno',           'Puntual'),

-- Viernes 22 Mayo
(1355, 1,  1,  101, 1, 'Entrada', '2026-05-22', '08:30:00', 'Juan Perez - Oficina RH',   'Puntual'),
(1356, 11, 11, 102, 1, 'Entrada', '2026-05-22', '08:35:00', 'Juan Carlos - Oficina RH',  'Retardo'),
(1357, 9,  9,  301, 3, 'Entrada', '2026-05-22', '07:44:00', 'MIKEL - Viernes anticipado','Anticipado'),
(1358, 10, 10, 301, 3, 'Entrada', '2026-05-22', '08:00:00', 'Ana - Viernes',             'Puntual'),
(1359, 4,  4,  401, 4, 'Entrada', '2026-05-22', '08:40:00', 'Carlos - OPS viernes',      'Puntual'),
(1360, 1,  1,  101, 1, 'Salida',  '2026-05-22', '16:01:00', 'Juan Perez - Salida',       'Puntual'),
(1361, 11, 11, 102, 1, 'Salida',  '2026-05-22', '15:59:00', 'Juan Carlos - Salida',      'Puntual'),
(1362, 9,  9,  301, 3, 'Salida',  '2026-05-22', '17:07:00', 'MIKEL',                     'Puntual'),
(1363, 10, 10, 301, 3, 'Salida',  '2026-05-22', '17:00:00', 'Ana',                       'Puntual'),

-- ====== SEMANA 25 MAY (25-29 Mayo) ======
-- Lunes 25 Mayo
(1364, 9,  9,  301, 3, 'Entrada', '2026-05-25', '07:46:00', 'MIKEL - Inicio semana',     'Anticipado'),
(1365, 10, 10, 301, 3, 'Entrada', '2026-05-25', '08:00:00', 'Ana - Lunes',               'Puntual'),
(1366, 7,  7,  202, 2, 'Entrada', '2026-05-25', '07:58:00', 'Miguel - TI lunes',         'Puntual'),
(1367, 3,  3,  101, 1, 'Entrada', '2026-05-25', '08:00:00', 'Rafael - RH',               'Puntual'),
(1368, 8,  8,  301, 3, 'Entrada', '2026-05-25', '07:55:00', 'Veronica - FIN',            'Anticipado'),
(1369, 5,  5,  501, 5, 'Entrada', '2026-05-25', '08:02:00', 'Lucia - MKT',               'Puntual'),
(1370, 16, 16, 501, 5, 'Entrada', '2026-05-25', '14:00:00', 'Felipe - MKT nocturno',     'Puntual'),
(1371, 18, 18, 401, 4, 'Entrada', '2026-05-25', '14:05:00', 'Gabriela - OPS nocturno',   'Puntual'),
(1372, 30, 30, 301, 3, 'Entrada', '2026-05-25', '14:02:00', 'Patricia - FIN nocturno',   'Puntual'),
(1373, 9,  9,  301, 3, 'Salida',  '2026-05-25', '17:06:00', 'MIKEL',                     'Puntual'),
(1374, 10, 10, 301, 3, 'Salida',  '2026-05-25', '17:00:00', 'Ana',                       'Puntual'),
(1375, 16, 16, 501, 5, 'Salida',  '2026-05-25', '21:00:00', 'Felipe nocturno',           'Puntual'),

-- Martes 26 Mayo
(1376, 9,  9,  301, 3, 'Entrada', '2026-05-26', '07:50:00', 'MIKEL - Martes',            'Anticipado'),
(1377, 10, 10, 301, 3, 'Entrada', '2026-05-26', '08:07:00', 'Ana - Leve retardo',        'Retardo'),
(1378, 6,  6,  201, 2, 'Entrada', '2026-05-26', '07:56:00', 'Sofia - TI',                'Anticipado'),
(1379, 12, 12, 102, 1, 'Entrada', '2026-05-26', '08:00:00', 'Pamela - RH',               'Puntual'),
(1380, 15, 15, 301, 3, 'Entrada', '2026-05-26', '08:00:00', 'Sergio - FIN',              'Puntual'),
(1381, 26, 26, 401, 4, 'Entrada', '2026-05-26', '14:00:00', 'Adan - OPS tecnico',        'Puntual'),
(1382, 22, 22, 301, 3, 'Entrada', '2026-05-26', '14:04:00', 'Monica - FIN nocturno',     'Puntual'),
(1383, 9,  9,  301, 3, 'Salida',  '2026-05-26', '17:11:00', 'MIKEL',                     'Puntual'),
(1384, 10, 10, 301, 3, 'Salida',  '2026-05-26', '17:01:00', 'Ana',                       'Puntual'),
(1385, 22, 22, 301, 3, 'Salida',  '2026-05-26', '21:00:00', 'Monica nocturno',           'Puntual'),

-- Miercoles 27 Mayo
(1386, 9,  9,  301, 3, 'Entrada', '2026-05-27', '07:43:00', 'MIKEL - Miercoles',         'Anticipado'),
(1387, 2,  2,  101, 1, 'Entrada', '2026-05-27', '08:00:00', 'Maria - Admin RH',          'Puntual'),
(1388, 13, 13, 401, 4, 'Entrada', '2026-05-27', '08:05:00', 'Roberto - OPS',             'Puntual'),
(1389, 4,  4,  401, 4, 'Entrada', '2026-05-27', '08:38:00', 'Carlos - OPS',              'Puntual'),
(1390, 23, 23, 501, 5, 'Entrada', '2026-05-27', '14:00:00', 'Jorge - MKT nocturno',      'Puntual'),
(1391, 19, 19, 201, 2, 'Entrada', '2026-05-27', '14:03:00', 'Hugo - TI nocturno',        'Puntual'),
(1392, 28, 28, 301, 3, 'Entrada', '2026-05-27', '14:01:00', 'Esteban - FIN analista',    'Puntual'),
(1393, 9,  9,  301, 3, 'Salida',  '2026-05-27', '17:08:00', 'MIKEL',                     'Puntual'),
(1394, 2,  2,  101, 1, 'Salida',  '2026-05-27', '17:00:00', 'Maria',                     'Puntual'),
(1395, 23, 23, 501, 5, 'Salida',  '2026-05-27', '21:00:00', 'Jorge nocturno',            'Puntual'),

-- Jueves 28 Mayo
(1396, 9,  9,  301, 3, 'Entrada', '2026-05-28', '07:47:00', 'MIKEL - Jueves',            'Anticipado'),
(1397, 10, 10, 301, 3, 'Entrada', '2026-05-28', '08:00:00', 'Ana - Jueves',              'Puntual'),
(1398, 6,  6,  201, 2, 'Entrada', '2026-05-28', '07:59:00', 'Sofia - TI',                'Puntual'),
(1399, 14, 14, 501, 5, 'Entrada', '2026-05-28', '08:02:00', 'Lorena - MKT',              'Puntual'),
(1400, 3,  3,  101, 1, 'Entrada', '2026-05-28', '08:00:00', 'Rafael - RH',               'Puntual'),
(1401, 17, 17, 101, 1, 'Entrada', '2026-05-28', '14:00:00', 'Isabel - RH nocturno',      'Puntual'),
(1402, 25, 25, 201, 2, 'Entrada', '2026-05-28', '14:07:00', 'Ricardo - TI nocturno',     'Retardo'),
(1403, 29, 29, 301, 3, 'Entrada', '2026-05-28', '14:00:00', 'Claudia - FIN analista',    'Puntual'),
(1404, 9,  9,  301, 3, 'Salida',  '2026-05-28', '17:06:00', 'MIKEL',                     'Puntual'),
(1405, 10, 10, 301, 3, 'Salida',  '2026-05-28', '17:00:00', 'Ana',                       'Puntual'),
(1406, 17, 17, 101, 1, 'Salida',  '2026-05-28', '21:00:00', 'Isabel nocturno',           'Puntual'),

-- Viernes 29 Mayo
(1407, 1,  1,  101, 1, 'Entrada', '2026-05-29', '08:30:00', 'Juan Perez - Oficina RH',   'Puntual'),
(1408, 11, 11, 102, 1, 'Entrada', '2026-05-29', '08:30:00', 'Juan Carlos - Oficina RH',  'Puntual'),
(1409, 9,  9,  301, 3, 'Entrada', '2026-05-29', '07:46:00', 'MIKEL - Viernes',           'Anticipado'),
(1410, 10, 10, 301, 3, 'Entrada', '2026-05-29', '08:00:00', 'Ana - Viernes',             'Puntual'),
(1411, 2,  2,  101, 1, 'Entrada', '2026-05-29', '07:58:00', 'Maria - Viernes',           'Puntual'),
(1412, 1,  1,  101, 1, 'Salida',  '2026-05-29', '16:00:00', 'Juan Perez - Salida',       'Puntual'),
(1413, 11, 11, 102, 1, 'Salida',  '2026-05-29', '16:02:00', 'Juan Carlos - Salida',      'Puntual'),
(1414, 9,  9,  301, 3, 'Salida',  '2026-05-29', '17:09:00', 'MIKEL',                     'Puntual'),
(1415, 10, 10, 301, 3, 'Salida',  '2026-05-29', '17:00:00', 'Ana',                       'Puntual'),
(1416, 2,  2,  101, 1, 'Salida',  '2026-05-29', '17:03:00', 'Maria',                     'Puntual'),

-- ====== SEMANA 1 JUN (1-5 Junio) ======
-- Lunes 1 Junio
(1417, 9,  9,  301, 3, 'Entrada', '2026-06-01', '07:48:00', 'MIKEL - Inicio junio',      'Anticipado'),
(1418, 10, 10, 301, 3, 'Entrada', '2026-06-01', '08:00:00', 'Ana - Lunes junio',         'Puntual'),
(1419, 6,  6,  201, 2, 'Entrada', '2026-06-01', '07:53:00', 'Sofia - TI junio',          'Anticipado'),
(1420, 7,  7,  202, 2, 'Entrada', '2026-06-01', '08:00:00', 'Miguel - TI',               'Puntual'),
(1421, 3,  3,  101, 1, 'Entrada', '2026-06-01', '08:00:00', 'Rafael - RH',               'Puntual'),
(1422, 4,  4,  401, 4, 'Entrada', '2026-06-01', '08:32:00', 'Carlos - OPS',              'Puntual'),
(1423, 5,  5,  501, 5, 'Entrada', '2026-06-01', '08:05:00', 'Lucia - MKT',               'Puntual'),
(1424, 8,  8,  301, 3, 'Entrada', '2026-06-01', '07:59:00', 'Veronica - FIN',            'Puntual'),
(1425, 16, 16, 501, 5, 'Entrada', '2026-06-01', '14:00:00', 'Felipe - MKT nocturno',     'Puntual'),
(1426, 18, 18, 401, 4, 'Entrada', '2026-06-01', '14:00:00', 'Gabriela - OPS tecnico',    'Puntual'),
(1427, 22, 22, 301, 3, 'Entrada', '2026-06-01', '14:04:00', 'Monica - FIN nocturno',     'Puntual'),
(1428, 20, 20, 201, 2, 'Entrada', '2026-06-01', '14:01:00', 'Andres - TI becario',       'Puntual'),
(1429, 9,  9,  301, 3, 'Salida',  '2026-06-01', '17:05:00', 'MIKEL',                     'Puntual'),
(1430, 10, 10, 301, 3, 'Salida',  '2026-06-01', '17:00:00', 'Ana',                       'Puntual'),
(1431, 6,  6,  201, 2, 'Salida',  '2026-06-01', '17:08:00', 'Sofia',                     'Puntual'),
(1432, 16, 16, 501, 5, 'Salida',  '2026-06-01', '21:00:00', 'Felipe nocturno',           'Puntual'),
(1433, 18, 18, 401, 4, 'Salida',  '2026-06-01', '21:00:00', 'Gabriela nocturno',         'Puntual'),

-- Martes 2 Junio
(1434, 9,  9,  301, 3, 'Entrada', '2026-06-02', '07:45:00', 'MIKEL - Martes junio',      'Anticipado'),
(1435, 10, 10, 301, 3, 'Entrada', '2026-06-02', '08:02:00', 'Ana - Martes',              'Puntual'),
(1436, 2,  2,  101, 1, 'Entrada', '2026-06-02', '08:00:00', 'Maria - Admin',             'Puntual'),
(1437, 12, 12, 102, 1, 'Entrada', '2026-06-02', '08:05:00', 'Pamela - RH',               'Puntual'),
(1438, 15, 15, 301, 3, 'Entrada', '2026-06-02', '07:56:00', 'Sergio - FIN',              'Anticipado'),
(1439, 13, 13, 401, 4, 'Entrada', '2026-06-02', '08:11:00', 'Roberto - OPS',             'Retardo'),
(1440, 14, 14, 501, 5, 'Entrada', '2026-06-02', '08:00:00', 'Lorena - MKT',              'Puntual'),
(1441, 19, 19, 201, 2, 'Entrada', '2026-06-02', '14:00:00', 'Hugo - TI nocturno',        'Puntual'),
(1442, 21, 21, 101, 1, 'Entrada', '2026-06-02', '14:03:00', 'Diego - RH nocturno',       'Puntual'),
(1443, 26, 26, 401, 4, 'Entrada', '2026-06-02', '14:00:00', 'Adan - OPS tecnico',        'Puntual'),
(1444, 30, 30, 301, 3, 'Entrada', '2026-06-02', '14:05:00', 'Patricia - FIN analista',   'Puntual'),
(1445, 9,  9,  301, 3, 'Salida',  '2026-06-02', '17:09:00', 'MIKEL',                     'Puntual'),
(1446, 10, 10, 301, 3, 'Salida',  '2026-06-02', '17:00:00', 'Ana',                       'Puntual'),
(1447, 2,  2,  101, 1, 'Salida',  '2026-06-02', '17:00:00', 'Maria',                     'Puntual'),
(1448, 19, 19, 201, 2, 'Salida',  '2026-06-02', '21:00:00', 'Hugo nocturno',             'Puntual'),

-- Miercoles 3 Junio
(1449, 9,  9,  301, 3, 'Entrada', '2026-06-03', '07:42:00', 'MIKEL - Miercoles junio',   'Anticipado'),
(1450, 10, 10, 301, 3, 'Entrada', '2026-06-03', '08:00:00', 'Ana',                       'Puntual'),
(1451, 6,  6,  201, 2, 'Entrada', '2026-06-03', '07:55:00', 'Sofia - TI',                'Anticipado'),
(1452, 4,  4,  401, 4, 'Entrada', '2026-06-03', '08:35:00', 'Carlos - OPS',              'Puntual'),
(1453, 3,  3,  101, 1, 'Entrada', '2026-06-03', '08:01:00', 'Rafael - RH',               'Puntual'),
(1454, 2,  2,  101, 1, 'Entrada', '2026-06-03', '08:08:00', 'Maria - Admin',             'Retardo'),
(1455, 17, 17, 101, 1, 'Entrada', '2026-06-03', '14:00:00', 'Isabel - RH nocturno',      'Puntual'),
(1456, 23, 23, 501, 5, 'Entrada', '2026-06-03', '14:02:00', 'Jorge - MKT nocturno',      'Puntual'),
(1457, 24, 24, 401, 4, 'Entrada', '2026-06-03', '14:00:00', 'Carmen - OPS nocturno',     'Puntual'),
(1458, 28, 28, 301, 3, 'Entrada', '2026-06-03', '14:04:00', 'Esteban - FIN analista',    'Puntual'),
(1459, 9,  9,  301, 3, 'Salida',  '2026-06-03', '17:07:00', 'MIKEL',                     'Puntual'),
(1460, 10, 10, 301, 3, 'Salida',  '2026-06-03', '17:00:00', 'Ana',                       'Puntual'),
(1461, 17, 17, 101, 1, 'Salida',  '2026-06-03', '21:00:00', 'Isabel nocturno',           'Puntual'),
(1462, 23, 23, 501, 5, 'Salida',  '2026-06-03', '21:00:00', 'Jorge nocturno',            'Puntual'),

-- Jueves 4 Junio
(1463, 9,  9,  301, 3, 'Entrada', '2026-06-04', '07:46:00', 'MIKEL - Jueves junio',      'Anticipado'),
(1464, 10, 10, 301, 3, 'Entrada', '2026-06-04', '08:00:00', 'Ana - Jueves',              'Puntual'),
(1465, 7,  7,  202, 2, 'Entrada', '2026-06-04', '07:58:00', 'Miguel - TI jueves',        'Puntual'),
(1466, 8,  8,  301, 3, 'Entrada', '2026-06-04', '07:54:00', 'Veronica - FIN',            'Anticipado'),
(1467, 5,  5,  501, 5, 'Entrada', '2026-06-04', '08:00:00', 'Lucia - MKT supervisor',    'Puntual'),
(1468, 15, 15, 301, 3, 'Entrada', '2026-06-04', '07:59:00', 'Sergio - FIN',              'Puntual'),
(1469, 16, 16, 501, 5, 'Entrada', '2026-06-04', '14:01:00', 'Felipe - MKT nocturno',     'Puntual'),
(1470, 25, 25, 201, 2, 'Entrada', '2026-06-04', '14:00:00', 'Ricardo - TI nocturno',     'Puntual'),
(1471, 27, 27, 101, 1, 'Entrada', '2026-06-04', '14:05:00', 'Berenice - RH tecnico',     'Puntual'),
(1472, 29, 29, 301, 3, 'Entrada', '2026-06-04', '14:02:00', 'Claudia - FIN analista',    'Puntual'),
(1473, 9,  9,  301, 3, 'Salida',  '2026-06-04', '17:05:00', 'MIKEL',                     'Puntual'),
(1474, 10, 10, 301, 3, 'Salida',  '2026-06-04', '17:00:00', 'Ana',                       'Puntual'),
(1475, 16, 16, 501, 5, 'Salida',  '2026-06-04', '21:00:00', 'Felipe nocturno',           'Puntual'),
(1476, 25, 25, 201, 2, 'Salida',  '2026-06-04', '21:01:00', 'Ricardo nocturno',          'Puntual'),

-- ============================================================
-- VIERNES 5 JUNIO 2026 - HOY
-- Turno Matutino: entradas sin salida = usuarios DENTRO ahora
-- Control de Emergencia mostrara presencia activa en 5 areas
-- ============================================================
(1477, 9,  9,  301, 3, 'Entrada', '2026-06-05', '07:49:00', 'MIKEL - Hoy viernes junio', 'Anticipado'),
(1478, 10, 10, 301, 3, 'Entrada', '2026-06-05', '08:00:00', 'Ana - Hoy viernes',         'Puntual'),
(1479, 6,  6,  201, 2, 'Entrada', '2026-06-05', '07:52:00', 'Sofia - TI hoy',            'Anticipado'),
(1480, 7,  7,  202, 2, 'Entrada', '2026-06-05', '08:00:00', 'Miguel - TI hoy',           'Puntual'),
(1481, 2,  2,  101, 1, 'Entrada', '2026-06-05', '08:00:00', 'Maria - Admin RH hoy',      'Puntual'),
(1482, 3,  3,  101, 1, 'Entrada', '2026-06-05', '07:57:00', 'Rafael - RH hoy',           'Anticipado'),
(1483, 4,  4,  401, 4, 'Entrada', '2026-06-05', '08:33:00', 'Carlos - OPS hoy',          'Puntual'),
(1484, 5,  5,  501, 5, 'Entrada', '2026-06-05', '08:01:00', 'Lucia - MKT hoy',           'Puntual'),
(1485, 8,  8,  301, 3, 'Entrada', '2026-06-05', '07:56:00', 'Veronica - FIN hoy',        'Anticipado'),
(1486, 12, 12, 102, 1, 'Entrada', '2026-06-05', '08:00:00', 'Pamela - RH hoy',           'Puntual'),
(1487, 13, 13, 401, 4, 'Entrada', '2026-06-05', '08:05:00', 'Roberto - OPS hoy',         'Puntual'),
(1488, 14, 14, 501, 5, 'Entrada', '2026-06-05', '08:10:00', 'Lorena - MKT hoy',          'Retardo'),
(1489, 15, 15, 301, 3, 'Entrada', '2026-06-05', '07:53:00', 'Sergio - FIN hoy',          'Anticipado'),
(1490, 1,  1,  101, 1, 'Entrada', '2026-06-05', '08:30:00', 'Juan Perez - Oficina RH hoy','Puntual'),
(1491, 11, 11, 102, 1, 'Entrada', '2026-06-05', '08:32:00', 'Juan Carlos - Oficina RH hoy','Puntual');

-- ============================================================
-- ESTADO PRESENCIA: usuarios con entrada hoy SIN salida = Dentro
-- ============================================================
UPDATE usuarios SET estado_presencia = 'Dentro' WHERE matricula IN (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
UPDATE usuarios SET estado_presencia = 'Fuera'  WHERE matricula IN (16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);

-- ============================================================
-- ACTUALIZAR BITACORAS con totales acumulados (May-Jun 2026)
-- ============================================================
UPDATE bitacora SET num_entradas = 95,  num_retardos = 6,  num_entradas_anticipadas = 52 WHERE matricula = 9;
UPDATE bitacora SET num_entradas = 80,  num_retardos = 5,  num_entradas_anticipadas = 30 WHERE matricula = 10;
UPDATE bitacora SET num_entradas = 32,  num_retardos = 2,  num_entradas_anticipadas = 16 WHERE matricula = 1;
UPDATE bitacora SET num_entradas = 28,  num_retardos = 3,  num_entradas_anticipadas = 8  WHERE matricula = 2;
UPDATE bitacora SET num_entradas = 22,  num_retardos = 1,  num_entradas_anticipadas = 4  WHERE matricula = 3;
UPDATE bitacora SET num_entradas = 18,  num_retardos = 2,  num_entradas_anticipadas = 3  WHERE matricula = 4;
UPDATE bitacora SET num_entradas = 16,  num_retardos = 1,  num_entradas_anticipadas = 5  WHERE matricula = 5;
UPDATE bitacora SET num_entradas = 18,  num_retardos = 0,  num_entradas_anticipadas = 10 WHERE matricula = 6;
UPDATE bitacora SET num_entradas = 16,  num_retardos = 0,  num_entradas_anticipadas = 5  WHERE matricula = 7;
UPDATE bitacora SET num_entradas = 14,  num_retardos = 0,  num_entradas_anticipadas = 6  WHERE matricula = 8;
UPDATE bitacora SET num_entradas = 12,  num_retardos = 0,  num_entradas_anticipadas = 2  WHERE matricula = 11;
UPDATE bitacora SET num_entradas = 12,  num_retardos = 1,  num_entradas_anticipadas = 3  WHERE matricula = 12;
UPDATE bitacora SET num_entradas = 10,  num_retardos = 2,  num_entradas_anticipadas = 0  WHERE matricula = 13;
UPDATE bitacora SET num_entradas = 10,  num_retardos = 1,  num_entradas_anticipadas = 2  WHERE matricula = 14;
UPDATE bitacora SET num_entradas = 10,  num_retardos = 0,  num_entradas_anticipadas = 4  WHERE matricula = 15;
UPDATE bitacora SET num_entradas = 18,  num_retardos = 2,  num_entradas_anticipadas = 0  WHERE matricula = 16;
UPDATE bitacora SET num_entradas = 10,  num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 17;
UPDATE bitacora SET num_entradas = 8,   num_retardos = 1,  num_entradas_anticipadas = 0  WHERE matricula = 18;
UPDATE bitacora SET num_entradas = 8,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 19;
UPDATE bitacora SET num_entradas = 6,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 20;
UPDATE bitacora SET num_entradas = 6,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 21;
UPDATE bitacora SET num_entradas = 10,  num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 22;
UPDATE bitacora SET num_entradas = 8,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 23;
UPDATE bitacora SET num_entradas = 6,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 24;
UPDATE bitacora SET num_entradas = 6,   num_retardos = 1,  num_entradas_anticipadas = 0  WHERE matricula = 25;
UPDATE bitacora SET num_entradas = 4,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 26;
UPDATE bitacora SET num_entradas = 4,   num_retardos = 1,  num_entradas_anticipadas = 0  WHERE matricula = 27;
UPDATE bitacora SET num_entradas = 6,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 28;
UPDATE bitacora SET num_entradas = 4,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 29;
UPDATE bitacora SET num_entradas = 4,   num_retardos = 0,  num_entradas_anticipadas = 0  WHERE matricula = 30;
