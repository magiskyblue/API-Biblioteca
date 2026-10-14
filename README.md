# API REST - Gestión de Biblioteca

## 📝 Descripción de la API
API RESTful desarrollada con **Java**, **Spring Boot** y **MySQL** para la administración de una biblioteca[cite: 1]. Permite gestionar el catálogo de **libros** (identificados por ISBN), el registro de **usuarios** (socios) y el control de **préstamos** activos e históricos (registrando automáticamente la fecha de préstamo y gestionando la fecha de devolución)[cite: 1].

---

## Tecnologías Utilizadas
* **Lenguaje:** Java 21 / 25[cite: 3]
* **Framework:** Spring Boot 4.1.1 (Spring Web, Spring Data JPA)[cite: 1, 3]
* **ORM:** Hibernate
* **Base de Datos:** MySQL[cite: 1]
* **Gestor de Dependencias:** Maven[cite: 2, 3]
* **Cliente de Pruebas:** Postman

---

## Variables de Entorno Necesarias
Para proteger las credenciales de acceso y no exponer datos sensibles en el repositorio, la aplicación utiliza las siguientes variables de entorno en `application.properties`:

| Variable | Descripción | Valor por defecto (Local) |
| :--- | :--- | :--- |
| `PORT` | Puerto de ejecución del servidor | `8080` |
| `DB_URL` | URL de conexión JDBC a la base de datos MySQL | `jdbc:mysql://localhost:3306/biblioteca_db?...` |
| `DB_USERNAME` | Usuario de la base de datos MySQL | `root` |
| `DB_PASSWORD` | Contraseña de la base de datos MySQL | *(Vacío / Configurado localmente)* |

---

## Pasos de Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/magiskyblue/API-Biblioteca.git](https://github.com/magiskyblue/API-Biblioteca.git)
   cd API-Biblioteca
   ```

2. **Crear la base de datos en MySQL:**
   Ejecuta la siguiente sentencia en tu servidor MySQL:
   ```sql
   CREATE DATABASE IF NOT EXISTS biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Configurar las variables de entorno:**
   Define las variables `DB_USERNAME` y `DB_PASSWORD` en tu sistema operativo o configúralas localmente en `src/main/resources/application.properties` (sin subir credenciales reales al repositorio).

4. **Compilar y ejecutar el proyecto:**
   ```powershell
   .\mvnw.cmd clean spring-boot:run
   ```
   La API estará disponible en `http://localhost:8080`.

---

## Listado de Endpoints

### 1. Libros (`/api/libros`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/libros` | Lista todos los libros registrados |
| `GET` | `/api/libros/{isbn}` | Obtiene el detalle de un libro por su ISBN |
| `POST` | `/api/libros` | Crea un nuevo libro en el catálogo |
| `PUT` | `/api/libros/{isbn}` | Actualiza la información de un libro existente |
| `DELETE` | `/api/libros/{isbn}` | Elimina un libro del catálogo |

### 2. Usuarios (`/api/usuarios`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/usuarios` | Lista todos los usuarios registrados |
| `GET` | `/api/usuarios/{id}` | Obtiene un usuario por su identificador |
| `POST` | `/api/usuarios` | Registra un nuevo socio/usuario |
| `PUT` | `/api/usuarios/{id}` | Actualiza los datos de un usuario |
| `DELETE` | `/api/usuarios/{id}` | Elimina un usuario por su ID |

### 3. Préstamos (`/api/prestamos`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/prestamos` | Lista el historial completo de préstamos |
| `GET` | `/api/prestamos/activos` | Lista únicamente los préstamos activos (`fechaDevolucion: null`) |
| `GET` | `/api/prestamos/{id}` | Obtiene la información de un préstamo por su ID |
| `POST` | `/api/prestamos` | Registra un nuevo préstamo de libro a un socio |
| `PUT` | `/api/prestamos/{id}/devolver` | Registra la devolución del libro (asigna la fecha actual) |
| `DELETE` | `/api/prestamos/{id}` | Elimina el registro de un préstamo |

---

## 🧪 Ejemplos de Peticiones y Respuestas

### 1. Crear un Libro (`POST /api/libros`)

**Petición (Request Body):**
```json
{
  "isbn": "978-84-376-0494-7",
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "anioPublicacion": 1967,
  "totalEjemplares": 5
}
```

**Respuesta (Response `200 OK`):**
```json
{
  "isbn": "978-84-376-0494-7",
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "anioPublicacion": 1967,
  "totalEjemplares": 5
}
```

---

### 2. Crear un Usuario (`POST /api/usuarios`)

**Petición (Request Body):**
```json
{
  "nombre": "Alejandro",
  "apellidos": "García López",
  "correoElectronico": "alejandro.garcia@correo.com",
  "contrasena": "miPassword123",
  "rol": "SOCIO",
  "estado": "ACTIVO",
  "fechaNacimiento": "2003-08-15",
  "telefono": "2291122334",
  "direccion": "Av. Colón 450"
}
```

**Respuesta (Response `200 OK`):**
```json
{
  "id": 1,
  "nombre": "Alejandro",
  "apellidos": "García López",
  "correoElectronico": "alejandro.garcia@correo.com",
  "contrasena": "miPassword123",
  "rol": "SOCIO",
  "estado": "ACTIVO",
  "fechaCreacion": "2026-10-14T01:05:12.451234",
  "fechaNacimiento": "2003-08-15",
  "telefono": "2291122334",
  "direccion": "Av. Colón 450"
}
```

---

### 3. Registrar un Préstamo (`POST /api/prestamos`)

**Petición (Request Body):**
```json
{
  "isbn": "978-84-376-0494-7",
  "usuarioId": 1
}
```

**Respuesta (Response `200 OK`):**
```json
{
  "id": 1,
  "libro": {
    "isbn": "978-84-376-0494-7",
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "anioPublicacion": 1967,
    "totalEjemplares": 5
  },
  "usuario": {
    "id": 1,
    "nombre": "Alejandro",
    "apellidos": "García López",
    "correoElectronico": "alejandro.garcia@correo.com",
    "contrasena": "miPassword123",
    "rol": "SOCIO",
    "estado": "ACTIVO",
    "fechaCreacion": "2026-10-14T01:05:12.451234",
    "fechaNacimiento": "2003-08-15",
    "telefono": "2291122334",
    "direccion": "Av. Colón 450"
  },
  "fechaPrestamo": "2026-10-14",
  "fechaDevolucion": null
}
```

---

### 4. Registrar Devolución de Préstamo (`PUT /api/prestamos/1/devolver`)

**Petición:** Sin cuerpo (`Body: None`)

**Respuesta (Response `200 OK`):**
```json
{
  "id": 1,
  "libro": {
    "isbn": "978-84-376-0494-7",
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "anioPublicacion": 1967,
    "totalEjemplares": 5
  },
  "usuario": {
    "id": 1,
    "nombre": "Alejandro",
    "apellidos": "García López",
    "correoElectronico": "alejandro.garcia@correo.com",
    "contrasena": "miPassword123",
    "rol": "SOCIO",
    "estado": "ACTIVO",
    "fechaCreacion": "2026-10-14T01:05:12.451234",
    "fechaNacimiento": "2003-08-15",
    "telefono": "2291122334",
    "direccion": "Av. Colón 450"
  },
  "fechaPrestamo": "2026-10-14",
  "fechaDevolucion": "2026-10-14"
}
```
