# API REST - Gestión de Biblioteca (Spring Boot, Java y MySQL)

API RESTful desarrollada con **Java**, **Spring Boot** y **MySQL** para gestionar el catálogo de libros, el registro de socios (usuarios) y el control de préstamos y devoluciones de una biblioteca.

---

## Tecnologías Utilizadas

* **Lenguaje:** Java
* **Framework:** Spring Boot (Spring Web, Spring Data JPA / Hibernate)
* **Base de Datos:** MySQL
* **Gestor de Dependencias:** Maven
* **Pruebas de API:** Postman

## Configuración e Instalación

### 1. Base de Datos MySQL
Ejecuta el siguiente script en MySQL para crear la base de datos:

```sql
CREATE DATABASE IF NOT EXISTS biblioteca_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### 2. Configurar `application.properties`
Verifica las credenciales de tu servidor MySQL en `src/main/resources/application.properties`:

```properties
spring.application.name=API-Biblioteca
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Ejecutar la Aplicación
Desde la terminal en la raíz del proyecto, ejecuta:

```powershell
.\mvnw.cmd clean spring-boot:run
```

El servidor iniciará en: `http://localhost:8080`

---

## Endpoints de la API y Ejemplos para Postman

### 1. Libros (`/api/libros`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/libros` | Obtener todos los libros |
| `GET` | `/api/libros/{isbn}` | Obtener un libro por su ISBN |
| `POST` | `/api/libros` | Registrar un nuevo libro |
| `PUT` | `/api/libros/{isbn}` | Actualizar los datos de un libro |
| `DELETE` | `/api/libros/{isbn}` | Eliminar un libro |

**Ejemplo `POST /api/libros` (Body JSON):**
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

### 2. Usuarios (`/api/usuarios`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/usuarios` | Obtener todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Obtener un usuario por su ID |
| `POST` | `/api/usuarios` | Crear un nuevo usuario |
| `PUT` | `/api/usuarios/{id}` | Actualizar un usuario existente |
| `DELETE` | `/api/usuarios/{id}` | Eliminar un usuario |

**Ejemplo `POST /api/usuarios` (Body JSON):**
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

---

### 3. Préstamos (`/api/prestamos`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/prestamos` | Obtener el historial completo de préstamos |
| `GET` | `/api/prestamos/activos` | Obtener únicamente los préstamos activos (`fechaDevolucion = null`) |
| `GET` | `/api/prestamos/{id}` | Obtener un préstamo por su ID |
| `POST` | `/api/prestamos` | Registrar un nuevo préstamo |
| `PUT` | `/api/prestamos/{id}/devolver` | Finalizar préstamo (asigna la fecha actual a `fechaDevolucion`) |
| `DELETE` | `/api/prestamos/{id}` | Eliminar un registro de préstamo |

**Ejemplo `POST /api/prestamos` (Body JSON):**
```json
{
  "isbn": "978-84-376-0494-7",
  "usuarioId": 1
}
```