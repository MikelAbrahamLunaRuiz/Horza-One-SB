# 📊 DATOS DE PRUEBA HORZA-ONE - VERSIÓN MEJORADA

## 🎯 Resumen de Mejoras

### ✅ 1. Más Usuarios (30 Total)
Se agregaron **10 usuarios adicionales** a los 20 existentes:

#### Usuarios 21-30 (Nuevos):
- **21-25**: Empleados Regulares (Diego, Mónica, Jorge, Carmen, Ricardo)
- **26-27**: Técnicos (Adán, Berenice)
- **28-29**: Analistas Senior (Esteban, Claudia)
- **30**: 🚨 **FULANITO DE TAL** - Usuario especial para pruebas de error

---

## 🕐 2. Bloques de Horario SIN TRASLAPES ✅

### ❌ Problema Anterior:
Los bloques tenían traslapes y horarios inconsistentes.

### ✅ Solución Implementada:
Todos los bloques son **consecutivos** y **NO se traslapan**:

#### Área 1 - Recursos Humanos (4 bloques de 6 horas):
```
00:00 - 06:00  →  RH Turno Madrugada
06:00 - 12:00  →  RH Turno Matutino
12:00 - 18:00  →  RH Turno Vespertino
18:00 - 00:00  →  RH Turno Nocturno
```

#### Área 2 - Tecnología (4 bloques de 6 horas):
```
00:00 - 06:00  →  TI Turno Madrugada
06:00 - 12:00  →  TI Turno Matutino
12:00 - 18:00  →  TI Turno Vespertino
18:00 - 00:00  →  TI Turno Nocturno
```

#### Área 3 - Finanzas (4 bloques de 6 horas):
```
00:00 - 06:00  →  FIN Turno Madrugada
06:00 - 12:00  →  FIN Turno Matutino
12:00 - 18:00  →  FIN Turno Vespertino
18:00 - 00:00  →  FIN Turno Nocturno
```

#### Área 4 - Operaciones (6 bloques de 4 horas):
```
00:00 - 04:00  →  OPS Turno 1 Madrugada
04:00 - 08:00  →  OPS Turno 2 Amanecer
08:00 - 12:00  →  OPS Turno 3 Mañana
12:00 - 16:00  →  OPS Turno 4 Tarde
16:00 - 20:00  →  OPS Turno 5 Vespertino
20:00 - 00:00  →  OPS Turno 6 Noche
```

#### Área 5 - Marketing (3 bloques de 8 horas):
```
00:00 - 08:00  →  MKT Turno Matutino
08:00 - 16:00  →  MKT Turno Diurno
16:00 - 00:00  →  MKT Turno Nocturno
```

### 🎯 Características:
- ✅ **Sin traslapes**: Cada bloque termina exactamente donde comienza el siguiente
- ✅ **Cobertura 24/7**: Todos los bloques cubren las 24 horas del día
- ✅ **Consecutivos**: No hay espacios vacíos entre bloques
- ✅ **Validados**: Cada área tiene su configuración optimizada

---

## 🚨 3. Caso de Prueba para Detección de Errores

### Usuario Especial: FULANITO DE TAL
**Matrícula:** 30  
**Propósito:** Detectar errores de calendario expirado

#### 📋 Datos del Usuario:
```sql
Nombre: Fulanito De Tal Pérez
RFC: FULA900101ZZZ
CURP: FULA900101HDFMLT99
Rol: Empleado Regular (id_rol: 3)
Contraseña: fulanito999
Teléfono: 5599887766
Email: fulanito.detal@horza.com
```

#### 📅 Calendario Asignado:
- **Calendario ID:** 2 (Calendario Corporativo 2025)
- **Vigencia:** Del 01/01/2025 al 31/12/2025
- **Estado:** ❌ **EXPIRADO**
- **Fecha actual del sistema:** 15/01/2026

#### 🧪 Pruebas Recomendadas:

1. **Intento de Registro de Asistencia:**
   ```
   Resultado esperado: ❌ ERROR
   Mensaje: "Calendario expirado o inválido"
   ```

2. **Validación de Calendario:**
   ```sql
   SELECT * FROM calendario WHERE id_calendario = 2;
   -- Debe mostrar fecha_fin = '2025-12-31'
   -- activo_calendario = 'Inactivo'
   ```

3. **Verificación de Bloques:**
   ```sql
   SELECT b.*, ba.* 
   FROM bloques_horario b
   JOIN bloque_dia_asignacion ba ON b.id_bloque = ba.id_bloque
   JOIN dia_horario dh ON ba.id_dia_horario = dh.id_dia_horario
   WHERE dh.id_horario = 2;  -- Horario 2 (2025)
   ```
   Los bloques existen y son válidos, pero el calendario está expirado.

4. **Prueba de Acceso al Sistema:**
   ```
   Login: fulanito.detal@horza.com
   Password: fulanito999
   Resultado: Debería permitir login pero rechazar registro de asistencia
   ```

#### 🎯 Validaciones del Backend:

El sistema debe validar:
- ✅ Que el calendario del usuario esté activo
- ✅ Que la fecha actual esté dentro del rango (fecha_inicio, fecha_fin)
- ✅ Que el horario asociado al calendario esté activo
- ✅ Que el bloque horario seleccionado pertenezca al calendario vigente

#### 📝 Mensajes de Error Esperados:

```json
{
  "error": true,
  "codigo": "CALENDARIO_EXPIRADO",
  "mensaje": "Tu calendario ha expirado. Contacta a Recursos Humanos.",
  "detalles": {
    "calendarioId": 2,
    "vigencia": "2025-01-01 a 2025-12-31",
    "fechaActual": "2026-01-15",
    "diasExpirado": 15
  }
}
```

---

## 📊 Estadísticas del Sistema

### Usuarios:
- **Total:** 30 usuarios
- **Administradores:** 3
- **Supervisores:** 2
- **Analistas Senior:** 7 (incluye a MIKEL ABRAHAM)
- **Empleados Regulares:** 13 (incluye a Fulanito)
- **Técnicos:** 4
- **Becarios:** 1

### Calendarios:
- **2024:** Inactivo (histórico sin datos)
- **2025:** Inactivo (histórico con datos) - **Usado por Fulanito**
- **2026:** ✅ Activo (operacional actual)

### Asignaciones:
- **29 usuarios** con calendario 2026 activo
- **1 usuario** (Fulanito) con calendario 2025 expirado

### Bloques de Horario:
- **Total:** 21 bloques
- **Área 1 (RH):** 4 bloques
- **Área 2 (TI):** 4 bloques
- **Área 3 (FIN):** 4 bloques
- **Área 4 (OPS):** 6 bloques
- **Área 5 (MKT):** 3 bloques
- **Traslapes:** 0 ✅

---

## 🚀 Cómo Usar los Datos de Prueba

### 1. Cargar la Base de Datos:
```bash
mysql -u root -p HORIZON_ONE < "BD HORZA-ONE VERSION MIKEL.sql"
mysql -u root -p HORIZON_ONE < "DATOS_PRUEBA_2026.sql"
```

### 2. Verificar la Carga:
```sql
USE HORIZON_ONE;

-- Ver total de usuarios
SELECT COUNT(*) as total_usuarios FROM usuarios;
-- Resultado esperado: 30

-- Ver bloques sin traslapes por área
SELECT id_area, COUNT(*) as total_bloques 
FROM bloques_horario 
WHERE activo = 'Activo'
GROUP BY id_area;

-- Ver usuario especial Fulanito
SELECT u.*, c.nombre_calendario, c.fecha_fin, c.activo_calendario
FROM usuarios u
JOIN usuarios_calendario uc ON u.matricula = uc.matricula
JOIN calendario c ON uc.id_calendario = c.id_calendario
WHERE u.matricula = 30;
```

### 3. Probar el Caso de Error:
```sql
-- Intentar simular un registro para Fulanito
-- (Esto debería fallar en la lógica de negocio)
SELECT 
    u.nombre_usuario,
    c.nombre_calendario,
    c.fecha_fin,
    CASE 
        WHEN c.fecha_fin < CURDATE() THEN '❌ EXPIRADO'
        ELSE '✅ VIGENTE'
    END as estado_calendario,
    DATEDIFF(CURDATE(), c.fecha_fin) as dias_expirado
FROM usuarios u
JOIN usuarios_calendario uc ON u.matricula = uc.matricula
JOIN calendario c ON uc.id_calendario = c.id_calendario
WHERE u.matricula = 30;
```

---

## 🔍 Consultas Útiles

### Ver todos los bloques consecutivos de un área:
```sql
SELECT 
    a.nombre_area,
    b.nombre_bloque,
    b.hora_inicio,
    b.hora_fin,
    CONCAT(
        HOUR(b.hora_inicio), ':',
        LPAD(MINUTE(b.hora_inicio), 2, '0'), 
        ' - ',
        HOUR(b.hora_fin), ':',
        LPAD(MINUTE(b.hora_fin), 2, '0')
    ) as horario
FROM bloques_horario b
JOIN area a ON b.id_area = a.id_area
WHERE a.id_area = 1  -- Cambiar por área deseada
ORDER BY b.hora_inicio;
```

### Verificar que no hay traslapes:
```sql
SELECT 
    b1.nombre_bloque as bloque1,
    b1.hora_inicio as inicio1,
    b1.hora_fin as fin1,
    b2.nombre_bloque as bloque2,
    b2.hora_inicio as inicio2,
    b2.hora_fin as fin2,
    'TRASLAPE DETECTADO' as alerta
FROM bloques_horario b1
JOIN bloques_horario b2 ON b1.id_area = b2.id_area AND b1.id_bloque < b2.id_bloque
WHERE b1.activo = 'Activo' AND b2.activo = 'Activo'
AND (
    (b1.hora_inicio < b2.hora_fin AND b1.hora_fin > b2.hora_inicio)
    OR
    (b2.hora_inicio < b1.hora_fin AND b2.hora_fin > b1.hora_inicio)
);
-- Resultado esperado: 0 filas (ningún traslape)
```

---

## ✅ Checklist de Validación

- [x] 30 usuarios creados correctamente
- [x] Bloques de horario sin traslapes
- [x] Bloques consecutivos cubriendo 24 horas
- [x] Usuario Fulanito con calendario expirado
- [x] Bitácoras creadas para todos los usuarios
- [x] Dispositivos asignados por área
- [x] Registros de ejemplo para enero 2026
- [x] Validación de datos completa
- [x] Documentación actualizada

---

## 📞 Información de Contacto

Para más información sobre los datos de prueba o reportar errores:
- **Proyecto:** HORZA-ONE
- **Versión BD:** MIKEL
- **Fecha actualización:** Enero 2026
- **Sprint:** 11 V2

---

**¡Sistema listo para pruebas! 🎉**
