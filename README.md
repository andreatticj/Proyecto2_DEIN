# Proyecto2_DEIN

## 📖 Descripción
Este proyecto es una aplicación Java diseñada para la gestión de préstamos de libros. Utiliza **JavaFX** para la interfaz de usuario, una base de datos para la gestión de información y **JasperReports** para la generación de informes.

## 📂 Estructura del Proyecto

El proyecto sigue una estructura modular organizada en paquetes según su funcionalidad:

### 📌 **1. Aplicación**
- `📁 eu/andreatt/proyecto2_dein/application`
    - 📌 `MenuPrincipal.java` → Punto de entrada de la aplicación.

### 📌 **2. Base de Datos**
- `📁 eu/andreatt/proyecto2_dein/bbdd`
    - 📌 `ConexionBD.java` → Clase para gestionar la conexión con la base de datos.

### 📌 **3. Controladores**
Manejan la lógica de la UI en JavaFX.
- `📁 eu/andreatt/proyecto2_dein/controllers`
    - 📌 `AgregarAlumnoController.java`
    - 📌 `AgregarHistoricoController.java`
    - 📌 `AgregarLibroController.java`
    - 📌 `AgregarPrestamoController.java`
    - 📌 `MenuPrincipalController.java`
    - 📌 `VisorAyudaOfflineController.java`

### 📌 **4. Data Access Object (DAO)**
Gestión de datos entre la aplicación y la base de datos.
- `📁 eu/andreatt/proyecto2_dein/dao`
    - 📌 `AlumnoDao.java`
    - 📌 `HistoricoDao.java`
    - 📌 `LibroDao.java`
    - 📌 `PrestamoDao.java`

### 📌 **5. Modelos**
Representan las entidades del sistema.
- `📁 eu/andreatt/proyecto2_dein/model`
    - 📌 `Alumno.java`
    - 📌 `Help.java`
    - 📌 `HistoricoPrestamo.java`
    - 📌 `Libro.java`
    - 📌 `Prestamo.java`

### 📌 **6. Utilidades**
- `📁 eu/andreatt/proyecto2_dein/util`
    - 📌 `Propiedades.java` → Gestión de propiedades de configuración.

### 📌 **7. Archivos de Configuración**
- 📌 `module-info.java` → Configuración del módulo Java.
- 📌 `configuration.properties` → Propiedades de configuración.
- 📌 `logback.xml` → Configuración de logs.

### 📌 **8. Estilos y UI**
- `📁 eu/andreatt/proyecto2_dein/css`
    - 🎨 `application.css` → Estilos CSS.
- `📁 eu/andreatt/proyecto2_dein/fxml`
    - 🖼️ Archivos FXML para la UI.
- `📁 eu/andreatt/proyecto2_dein/images`
    - 🖼️ Recursos gráficos.

### 📌 **9. Archivos de Ayuda**
- `📁 eu/andreatt/proyecto2_dein/help`
    - 📜 Documentación HTML de ayuda.

### 📌 **10. Internacionalización**
- `📁 eu/andreatt/proyecto2_dein/idiomas`
    - 🌍 `messages_en_EN.properties`
    - 🌍 `messages_es_ES.properties`

### 📌 **11. Informes JasperReports**
- `📁 eu/andreatt/proyecto2_dein/jasper`
    - 📊 Informes en formato `.jasper` y `.jrxml`.

## ⚙️ Requisitos
- ☕ **JDK 11+**
- 🎭 **JavaFX 17+**
- 🗄️ **Base de datos compatible** (MySQL, PostgreSQL, etc.)
- 📑 **JasperReports**

## 🚀 Instalación y Ejecución
1. Clona el repositorio:
   ```sh
   git clone https://github.com/tu_usuario/proyecto2_dein.git
   ```
2. Importa el proyecto en tu IDE preferido.
3. Configura la base de datos en `configuration.properties`.
4. Ejecuta `MenuPrincipal.java` para iniciar la aplicación.

## ✨ Autores
- 👤 **AndreaTT**
