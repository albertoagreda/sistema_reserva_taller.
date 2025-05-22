
# 🚗 TurboDesk - Sistema de Gestión de Taller de Repuestos

**TurboDesk** es una aplicación de escritorio desarrollada en **Java (Swing)** para la gestión integral de un **taller de repuestos de coches**. Gestiona clientes, coches, citas y soporte técnico, con integración a una base de datos **MySQL** que incluye funcionalidades avanzadas como procedimientos almacenados, trigger, cursor y campos JSON.

---

## 🧩 Funcionalidades Principales

🔧 **Gestión de Coches**  
- Alta, edición, listado y eliminación.
- Campo `detalles` en JSON (🎨 color, ⛽ combustible).

📅 **Gestión de Citas**  
- Registro, consulta y eliminación.
- Validación de disponibilidad, fechas futuras y existencia de cliente/coche.

📨 **Soporte Técnico**  
- Envío de mensajes desde clientes.
- Registro automático vía procedimiento almacenado.

🕵️ **Auditoría de Coches**  
- Cada eliminación se guarda automáticamente en la tabla `log_coches` mediante un **trigger**.

---

## ⚙️ Tecnologías

| 🛠️ Componente | 🔍 Detalle |
|---------------|------------|
| 🧑‍💻 Lenguaje | Java SE 17 |
| 🖼️ GUI        | Swing |
| 🐬 BD         | MySQL 10.4 (MariaDB) |
| 🔗 Persistencia | JDBC (mysql-connector) |
| 🧪 Testing     | JUnit 5 |
| 📄 Documentación | JavaDoc |
| 🌐 Control de versiones | Git & GitHub |
| 🧠 Modelado | MySQL Workbench |

---

## 🗂️ Estructura del Proyecto

```
TurboDesk/
├── src/TurboDesk/              # Código fuente
│   ├── MenuInicio.java         # Menú principal
│   ├── Coches.java             # Módulo coches
│   ├── NuevaCita.java          # Módulo citas
│   ├── Soporte.java            # Módulo soporte
│   └── ConexionMySQL.java      # Conexión JDBC
├── test/TurboDesk/             # Tests con JUnit 5
├── sql/                        # Script SQL (base de datos)
├── docs/                       # JavaDoc y Diagrama E/R
├── lib/                        # JARs de JDBC y JUnit
└── src/Fotos/                  # Recursos gráficos
```

---

## 🧠 Base de Datos: taller_repuestos

📦 Contiene:

- **clientes**: nombre, apellidos, correo (único).
- **coches**: modelo, marca, año, kilometraje, `detalles` (JSON).
- **citas**: relación entre cliente y coche con fecha programada.
- **soporte**: mensajes de clientes.
- **log_coches**: auditoría de eliminaciones.

📌 **Avanzado**:

- ✅ `RegistrarCita`: valida y crea citas.
- ✅ `RegistrarMensajeSoporte`: registra mensajes con validaciones.
- 🔄 Trigger `after_coche_delete`: guarda automáticamente en log.
- 🧾 `detalles`: campo JSON validado con `CHECK(json_valid(...))`.

---

## 🚀 Instrucciones de Ejecución

### 1. Clona el repositorio
```bash
git clone https://github.com/[TU_USUARIO]/TurboDesk.git
```

### 2. Configura la Base de Datos

- Crea la BD:
```sql
CREATE DATABASE taller_repuestos;
```

- Ejecuta `sql/taller_repuestos.sql` con MySQL Workbench u otro cliente.

- Asegúrate de configurar `ConexionMySQL.java`:
```java
private String USUARIO = "tu_usuario";
private String PASS = "tu_contraseña";
```

### 3. Importa el Proyecto en Eclipse

- Ve a `File > Import > General > Existing Projects into Workspace`
- Añade:
  - `mysql-connector-java.jar`
  - `junit-jupiter-api.jar`, `junit-jupiter-engine.jar` (desde `/lib`)

### 4. Ejecuta

- Botón derecho en `MenuInicio.java` → `Run As > Java Application`

---

## 🧪 Pruebas Unitarias

Incluye tests JUnit 5 para:

✅ Conexión a BD  
✅ Gestión de coches  
✅ Gestión de citas  
✅ Registro de soporte

**Para ejecutar:**
- Haz clic derecho en cada test (ej. `CochesTest.java`) → `Run As > JUnit Test`

---

## 📚 Documentación

📄 **JavaDoc**:  
`/docs/javadoc/index.html`

📊 **Diagrama E/R**:  
`/docs/DiagramaER_TurboDesk.png`

---

## 🌱 Recomendaciones

- Ejecuta los tests antes de cada entrega.  
- Mejora continua: valida nuevos campos, añade filtros, etc.  
- Mantén tu repo actualizado en GitHub.

---

## 🧑‍🏫 Evaluadores

Pasos recomendados:
1. Clonar el repo ✅  
2. Ejecutar script SQL en `sql/` 🐬  
3. Abrir el proyecto en Eclipse 💻  
4. Probar la aplicación con `MenuInicio.java` 🎯  
5. Ejecutar las pruebas JUnit 🧪  
6. Revisar la documentación en `/docs/` 📚

---

## ✅ Estado del Proyecto

- [x] CRUD completo con validaciones
- [x] Interfaz gráfica intuitiva (Swing)
- [x] Base de datos optimizada y documentada
- [x] Pruebas JUnit 5 funcionando
- [x] Control de versiones en GitHub
- [x] JavaDoc + Diagrama E/R

---

**Desarrollado con ❤️ por [Tu Nombre o Equipo]**
