# 📚 Sistema de Gestión de Reservas de Aulas - Universidad CAECE

## 🧠 Descripción del proyecto

Aplicación Java desarrollada en el marco del Trabajo Práctico Grupal 2024 (Algoritmos y Estructuras de Datos II, Universidad CAECE).  
El sistema permite a una universidad gestionar la reserva de aulas para asignaturas, cursos de extensión y eventos (internos y externos), a través de una interfaz gráfica intuitiva desarrollada con **Swing**.

El proyecto incorpora carga de datos desde archivos externos, generación automática de reservas semanales, control de disponibilidad, reportes económicos y estadísticas de uso.

---

## 🛠️ Tecnologías utilizadas

- **Java SE 17**
- **Swing** (Interfaz Gráfica)
- **Colecciones Java**: `ArrayList`, `TreeSet`, `TreeMap`, etc.
- **Persistencia**: Serialización clásica y XML
- **Manejo de archivos**: JSON, CSV o TXT delimitado
- **POO, Excepciones, Enums, Separación UI/Lógica de negocio**

---

## 🧩 Funcionalidades principales

### ✅ Gestión de Entidades

- **Aulas**: identificadas por número (ej: 204 = 2° piso), con capacidad máxima y reservas asociadas.
- **Asignaturas**: con código, nombre, día semanal de cursada, horario, fechas de cursada y cantidad de inscriptos.
- **Cursos de extensión**: código, descripción, cantidad de clases, inscriptos y costo por alumno.
- **Eventos**: internos o externos, con código, descripción, cantidad de participantes y, si es externo, nombre de la organización y costo de alquiler.

---

### 📥 Carga de datos

- Permite importar datos desde archivos XML.
- Verificación automática de **consistencia, completitud y validez** de los datos.
- Informe detallado de errores encontrados durante la carga.
- Muestra los datos cargados por tipo (aulas, asignaturas, etc.).

---

### 📅 Reservas

- Generación de reservas automáticas para asignaturas y cursos.
- Validación de:
  - Disponibilidad horaria del aula.
  - Capacidad mínima según cantidad de inscriptos o participantes.
- Códigos de reservas generados automáticamente (únicos e incrementales).
- Cancelación de reservas por código y número de aula.
- Soporte para eventos múltiples por organización.

---

### 🔍 Consultas y filtros

- Búsqueda de reservas por:
  - Número de piso.
  - Código de asignatura, curso o evento.

---

### 📊 Reportes automáticos

- **Monto recaudado** por:
  - Aula
  - Piso
  - Total institucional
- **Listado de aulas** ordenadas por cantidad de reservas (descendente).
- **Promedio de reservas** por aula.
- Reportes exportables a archivos de texto (`.txt`).

---

## 🖥️ Interfaz de usuario

- Aplicación de escritorio con interfaz **gráfica en Swing**.
- Navegación clara y segmentada por tipo de acción: carga, consulta, reserva, reportes.
- Diálogos de confirmación y validación.
- Sistema desacoplado: la lógica de negocio lanza excepciones específicas que son manejadas por la interfaz.

---

## 💾 Persistencia

- Implementada con **serialización clásica y/o XML**.
- Permite guardar y cargar todo el estado del sistema (aulas, reservas, etc.).
- Posibilidad de reanudar el uso desde el último guardado.

---

## 🚀 Cómo correr el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-reservas-aulas.git
   cd proyecto-reservas-aulas
   ```

2. Compilar y ejecutar:
   - Desde un IDE como IntelliJ o Eclipse, ejecutar `Main.java`
   - O desde terminal:
     ```bash
     javac -d bin src/**/*.java
     java -cp bin Main
     ```

---

## 🧪 Pruebas y validaciones

- El sistema ha sido probado con múltiples archivos de carga con datos válidos e inválidos.
- Se han simulado colisiones horarias, superación de capacidad, códigos duplicados y demás validaciones.
- Los reportes han sido verificados manualmente y con datos de prueba conocidos.

---

## 📄 Autores

Trabajo práctico grupal realizado por estudiantes de la Universidad CAECE - MdP, 1° cuatrimestre 2024.  
Materia: **Algoritmos y Estructuras de Datos II**  
