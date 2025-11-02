# Microservicio de Reseñas - Level-Up Gamer

## Descripción
Microservicio para gestionar reseñas y calificaciones de productos en la tienda Level-Up Gamer. Arquitectura simple sin JWT, Feign ni Eureka.

## Tecnologías
- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- MySQL 8.0
- Maven
- Lombok

## Configuración

### Requisitos Previos
- Java 17
- Maven 3.8+
- MySQL 8.0

### Base de Datos
- Database: levelup_resenas
- Usuario: root
- Password: admin
- Puerto: 3306

### Puerto de Ejecución
- server.port=8084

## Estructura del Proyecto

src/main/java/com/levelupgamer/resenas_service/
├── controller/
│ └── ReviewController.java
├── service/
│ └── ReviewService.java
├── repository/
│ └── ReviewRepository.java
├── entity/
│ └── Review.java
├── dto/
│ ├── CreateReviewDTO.java
│ ├── UpdateReviewDTO.java
│ ├── ReviewDTO.java
│ └── AverageRatingDTO.java
└── ResenasServiceApplication.java

text

## Endpoints

### Crear Reseña
- POST /api/reviews/create
- Body: { userId, productId, rating (1-5), comment }
- Respuesta: 201 Created con ReviewDTO

### Obtener Reseñas por Producto
- GET /api/reviews/product/{productId}
- Respuesta: 200 OK con lista de ReviewDTO

### Obtener Reseñas por Usuario
- GET /api/reviews/user/{userId}
- Respuesta: 200 OK con lista de ReviewDTO

### Actualizar Reseña
- PUT /api/reviews/update/{id}
- Body: { userId, rating, comment }
- Respuesta: 200 OK con ReviewDTO actualizado

### Eliminar Reseña
- DELETE /api/reviews/delete/{id}/user/{userId}
- Respuesta: 204 No Content

### Obtener Promedio de Calificación
- GET /api/reviews/product/{productId}/average
- Respuesta: 200 OK con { productId, averageRating, totalReviews }

## Reglas de Negocio

- Un usuario solo puede crear una reseña por producto
- Rating válido: 1 a 5 estrellas
- Solo el autor puede actualizar o eliminar su reseña
- Comentario máximo: 1000 caracteres
- Timestamps automáticos (createdAt, updatedAt)

## Compilación y Ejecución


mvn clean install -DskipTests
mvn spring-boot:run
Validaciones
userId y productId: obligatorios y positivos

rating: obligatorio, entre 1 y 5

comment: máximo 1000 caracteres

No se permite reseña duplicada (mismo usuario y producto)

Solo el autor puede modificar su reseña

Testing con Postman
Importar colección JSON

Configurar variables:

baseUrl: http://localhost:8084

userId: 1001

productId: 101

reviewId: 1

Ejecutar casos de prueba en orden

Queries Implementadas
Queries Objetuales (JPQL)
Buscar reseñas por producto

Buscar reseñas por usuario

Verificar existencia de reseña

Buscar reseña específica por usuario y producto

Queries Nativas (SQL)
Buscar reseñas por producto

Calcular promedio de calificación

Contar reseñas por producto

Casos de Prueba
Crear reseña válida

Obtener reseñas por producto

Obtener reseñas por usuario

Actualizar reseña propia

Eliminar reseña propia

Obtener promedio de calificación

Error: Rating inválido (< 1 o > 5)

Error: Reseña duplicada

Error: Usuario no autorizado para editar

Autor
Microservicio desarrollado para Level-Up Gamer - Evaluación Universitaria

text

---

## PASOS PARA CONFIGURAR EN VISUAL STUDIO CODE

1. Crea la carpeta del proyecto: `resenas-service`
2. Copia el `pom.xml` en la raíz
3. Crea la estructura de carpetas:
   - `src/main/java/com/levelupgamer/resenas_service/`
   - `src/main/resources/`
4. Copia cada archivo Java en su respectiva carpeta según las rutas indicadas
5. Copia `application.properties` en `src/main/resources/`
6. Abre MySQL y la base de datos se creará automáticamente
7. Ejecuta: `mvn clean install -DskipTests`
8. Ejecuta: `mvn spring-boot:run`
9. Importa la colección de Postman y prueba los endpoints

---

## ENDPOINTS DISPONIBLES

- POST   http://localhost:8084/api/reviews/create
- GET    http://localhost:8084/api/reviews/product/{productId}
- GET    http://localhost:8084/api/reviews/user/{userId}
- PUT    http://localhost:8084/api/reviews/update/{id}
- DELETE http://localhost:8084/api/reviews/delete/{id}/user/{userId}
- GET    http://localhost:8084/api/reviews/product/{productId}/average