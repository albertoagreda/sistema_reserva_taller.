Sistema de Gestión de Taller de Repuestos - TurboDesk
Descripción del Proyecto
TurboDesk es una aplicación de escritorio desarrollada en Java con Swing para la gestión integral de un taller de repuestos de coches. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las entidades principales: clientes, coches, citas y mensajes de soporte. La aplicación utiliza MySQL como sistema gestor de bases de datos (SGBD) y se conecta mediante JDBC. La base de datos incluye procedimientos almacenados, un trigger, un cursor, y un campo JSON para modelado de datos complejos. La interfaz gráfica es intuitiva, con manejo de eventos y validaciones, y el proyecto está completamente documentado con JavaDoc, pruebas unitarias con JUnit, y control de versiones en GitHub.
Funcionalidades Principales

Gestión de Coches: Registrar, listar, editar y eliminar coches, con soporte para datos JSON (color, combustible).
Gestión de Citas: Crear, listar y eliminar citas, asociando clientes y coches, con validación de fechas.
Gestión de Soporte: Enviar mensajes de soporte, generando correos automáticamente.
Auditoría: Registro automático de eliminaciones de coches en una tabla de log.
Base de Datos: Esquema normalizado, procedimientos almacenados (RegistrarCita, RegistrarMensajeSoporte), un cursor para procesar datos, y un trigger para auditoría.

Tecnologías Utilizadas

Lenguaje: Java SE 17
Interfaz Gráfica: Swing
SGBD: MySQL (versión 10.4.32-MariaDB)
Persistencia: JDBC (mysql-connector-java.jar)
Control de Versiones: Git, GitHub
Pruebas: JUnit 5
IDE: Eclipse
Documentación: JavaDoc
Modelado: MySQL Workbench (para el diagrama E/R)

Requisitos Previos

Java SE 17: Descarga desde Oracle.
MySQL: Instala MySQL (versión 10.4 o superior) desde MySQL.
Driver JDBC: Descarga mysql-connector-java.jar (versión compatible con MySQL 10.4) desde MySQL Connector/J.
Eclipse IDE: Descarga desde Eclipse.
JUnit 5: Incluido en el proyecto (configurado en Eclipse).
Imágenes: Las imágenes de la interfaz (fondo.jpg, calendario.jpg, soporte.jpg, etc.) están en src/Fotos/.

Configuración

Clonar el Repositorio:
git clone https://github.com/[TU_USUARIO]/TurboDesk.git


El repositorio contiene el código fuente, pruebas, documentación, y el script SQL.


Configurar la Base de Datos:

Abre MySQL Workbench o un cliente similar.

Crea la base de datos taller_repuestos:
CREATE DATABASE taller_repuestos;


Ejecuta el script sql/taller_repuestos.sql para crear las tablas, restricciones, procedimientos, trigger, cursor, y cargar datos de ejemplo.

Verifica que el usuario root no tenga contraseña. Si usas otras credenciales, actualiza src/TurboDesk/ConexionMySQL.java:
private String USUARIO = "tu_usuario";
private String PASS = "tu_contraseña";




Importar el Proyecto en Eclipse:

Abre Eclipse.
Ve a File > Import > General > Existing Projects into Workspace.
Selecciona la carpeta raíz del repositorio clonado (TurboDesk).


Añadir el Driver JDBC:

Descarga mysql-connector-java.jar.
En Eclipse:
Haz clic derecho en el proyecto > Properties > Java Build Path > Libraries > Modulepath.
Selecciona Add External JARs y elige mysql-connector-java.jar.




Configurar JUnit 5:

JUnit 5 está incluido en el proyecto (carpeta lib/ contiene los JARs necesarios).
Si Eclipse no lo reconoce, añade los JARs de JUnit 5 al build path:
Properties > Java Build Path > Libraries > Modulepath > Add External JARs.
Selecciona junit-jupiter-api.jar y junit-jupiter-engine.jar desde lib/.




Verificar Imágenes:

Asegúrate de que las imágenes estén en src/Fotos/:
fondo.jpg, calendario.jpg, soporte.jpg, etc.


Las rutas en el código usan getClass().getResource("/Fotos/[imagen]") para cargarlas correctamente.



Ejecución

Abre el proyecto en Eclipse.
Compila y ejecuta la clase principal: src/TurboDesk/MenuInicio.java.
Haz clic derecho en MenuInicio.java > Run As > Java Application.


Navega por el menú principal para:
Coches: Registrar, listar, editar, o eliminar coches.
Citas: Crear, listar, o eliminar citas.
Soporte: Enviar mensajes de soporte.


Ejecuta las pruebas unitarias:
Ve a la carpeta test/TurboDesk/.
Haz clic derecho en un archivo de prueba (ej. ConexionMySQLTest.java) > Run As > JUnit Test.
Verifica que todas las pruebas pasen correctamente.



Estructura del Repositorio

src/TurboDesk/:
MenuInicio.java: Menú principal con botones para acceder a las funcionalidades.
NuevaCita.java: Gestión de citas (crear, listar, eliminar).
Coches.java: Gestión de coches (crear, listar, editar, eliminar).
Soporte.java: Gestión de mensajes de soporte (crear).
ConexionMySQL.java: Clase para la conexión a MySQL mediante JDBC.


test/TurboDesk/:
ConexionMySQLTest.java: Pruebas unitarias para la conexión a la base de datos.
NuevaCitaTest.java: Pruebas para la gestión de citas.
CochesTest.java: Pruebas para la gestión de coches.
SoporteTest.java: Pruebas para los mensajes de soporte.


sql/:
taller_repuestos.sql: Script SQL con el esquema, procedimientos, trigger, cursor, y datos de ejemplo.


docs/:
DiagramaER_TurboDesk.png: Diagrama E/R generado con MySQL Workbench.
javadoc/: Documentación JavaDoc generada para todas las clases.


src/Fotos/:
Imágenes usadas en la interfaz gráfica (fondo.jpg, calendario.jpg, soporte.jpg, etc.).


lib/:
mysql-connector-java.jar: Driver JDBC para MySQL.
junit-jupiter-api.jar, junit-jupiter-engine.jar: Bibliotecas de JUnit 5.



Pruebas Unitarias
El proyecto incluye pruebas unitarias con JUnit 5 para garantizar la robustez del código:

ConexionMySQLTest: Verifica la conexión y desconexión a la base de datos, ejecución de consultas SELECT, e INSERT/UPDATE/DELETE.
NuevaCitaTest: Prueba la creación, listado, y eliminación de citas, incluyendo validaciones de campos y fechas.
CochesTest: Prueba la creación, listado, edición, y eliminación de coches, incluyendo el manejo del campo JSON detalles.
SoporteTest: Prueba la generación de correos y el registro de mensajes de soporte.

Para ejecutar las pruebas:

En Eclipse, ve a la carpeta test/TurboDesk/.
Haz clic derecho en un archivo de prueba > Run As > JUnit Test.
Alternativamente, ejecuta todas las pruebas: Run > Run Configurations > JUnit > New Configuration > Test All.

Documentación

JavaDoc: Documentación completa generada para todas las clases y métodos públicos.
Ubicación: docs/javadoc/.
Generado con Eclipse: Project > Generate Javadoc.


Diagrama E/R: Representación visual de las entidades (clientes, coches, citas, soporte, log_coches) y sus relaciones.
Generado con MySQL Workbench.
Ubicación: docs/DiagramaER_TurboDesk.png.


Script SQL: Incluye comentarios detallados explicando cada tabla, procedimiento, trigger, y cursor.
Ubicación: sql/taller_repuestos.sql.



Control de Versiones
El proyecto está alojado en GitHub: https://github.com/[TU_USUARIO]/TurboDesk.git.

Ramas:
main: Rama principal con la versión estable.
feature/citas: Desarrollo de la gestión de citas.
feature/coches: Desarrollo de la gestión de coches.
feature/soporte: Desarrollo de los mensajes de soporte.
feature/tests: Implementación de pruebas JUnit.


Commits: Commits frecuentes con mensajes descriptivos, siguiendo convenciones de Git (ej. "Add CRUD operations for citas", "Implement JUnit tests for ConexionMySQL").

Notas

Estado del Proyecto: Completado, cumpliendo todos los requisitos del caso práctico:
Interfaz gráfica con Swing y manejo de eventos.
Conexión JDBC con operaciones CRUD.
Base de datos normalizada con procedimientos, trigger, cursor, y campo JSON.
Pruebas unitarias con JUnit 5.
Documentación completa (JavaDoc, diagrama E/R, README).
Control de versiones con Git/GitHub.


Calidad de Código:
Uso de layouts dinámicos (GridBagLayout en lugar de setLayout(null)).
Encapsulamiento con campos privados y getters/setters.
Manejo de recursos con try-with-resources.
Rutas relativas para imágenes (getClass().getResource()).


Base de Datos:
El script taller_repuestos.sql usa CREATE TABLE IF NOT EXISTS para evitar errores como #1050 - Table already exists.
Incluye un cursor para procesar datos (ej. listar citas por cliente).


Recomendaciones:
Mantén el repositorio actualizado con nuevas mejoras o correcciones.
Revisa las pruebas unitarias antes de cada entrega para asegurar que sigan pasando.



Instrucciones para Evaluadores

Clona el repositorio: git clone https://github.com/[TU_USUARIO]/TurboDesk.git.
Configura la base de datos con sql/taller_repuestos.sql.
Importa el proyecto en Eclipse y añade mysql-connector-java.jar y JUnit 5 al build path.
Ejecuta MenuInicio.java para probar la aplicación.
Ejecuta las pruebas unitarias desde test/TurboDesk/.
Revisa la documentación en docs/ y el diagrama E/R en docs/DiagramaER_TurboDesk.png.

