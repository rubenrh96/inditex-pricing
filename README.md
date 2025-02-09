# Inditex Prueba Capitole

Este proyecto es un servicio de consulta de precios para Inditex, desarrollado en **Spring Boot** siguiendo los principios de **Arquitectura Hexagonal** y **Domain Driven Design (DDD)**. 
El servicio expone un endpoint REST que, a partir de la fecha de aplicación, el identificador de producto y el identificador de cadena, devuelve el precio final aplicable y la tarifa correspondiente.

## Características

- **Spring Boot 3.3.2:** Utiliza la versión 3.3.2 de Spring Boot para aprovechar las últimas características y mejoras.
- **Arquitectura Hexagonal / DDD:** Separa la lógica de dominio, la capa de aplicación y la infraestructura para facilitar el mantenimiento y la escalabilidad.
- **Base de datos H2 en memoria:** Permite un rápido desarrollo y pruebas sin necesidad de instalar un servidor de base de datos.
- **Clave primaria compuesta:** La entidad `PriceEntity` utiliza una clave compuesta basada en los campos `BRAND_ID`, `PRODUCT_ID` y `PRICE_LIST`.
- **Manejo global de excepciones:** Se utiliza un `@RestControllerAdvice` para centralizar el manejo de errores.
- **Mapper manual:** Se implementa un mapper para transformar entre la entidad de persistencia, el modelo de dominio y el DTO de salida.
- **Pruebas unitarias:** Se incluyen tests para el endpoint REST utilizando **MockMvc** y **@MockBean** para validar distintos escenarios.

## Estructura del Proyecto

El proyecto se organiza en los siguientes paquetes:

- **domain:**  
  Contiene el modelo de dominio (por ejemplo, `Price`).

- **application:**  
  Incluye la lógica de negocio y servicios (por ejemplo, `PriceService`).

- **infrastructure:**
  - **persistence:**  
    Contiene las entidades JPA (por ejemplo, `PriceEntity` y la clave compuesta `PricePK`).
  - **repository:**  
    Define los repositorios JPA (por ejemplo, `PriceRepository`).
  - **controller:**  
    Expone los endpoints REST (por ejemplo, `PriceController`).
  - **dto:**  
    Incluye los objetos de transferencia (por ejemplo, `PriceResponse`).
  - **mapper:**  
    Implementa la conversión entre entidades, dominio y DTO (por ejemplo, `PriceMapperManual`).
  - **advice:**  
    Maneja las excepciones globalmente (por ejemplo, `GlobalExceptionHandler` y la excepción personalizada `ResourceNotFoundException`).

## Requisitos

- **Java 17**
- **Maven 3.6+**

# Endpoint REST

## GET /prices

### Parámetros de Entrada

- **applicationDate:** Fecha de aplicación (formato ISO, por ejemplo, `2020-06-14T10:00:00`).
- **productId:** Identificador del producto (por ejemplo, `35455`).
- **brandId:** Identificador de la cadena (por ejemplo, `1` para ZARA).

### Respuesta de Salida

Devuelve un objeto JSON con los siguientes campos:

- **productId:** Identificador del producto.
- **brandId:** Identificador de la cadena.
- **priceList:** Tarifa a aplicar.
- **startDate:** Fecha de inicio de la aplicación de la tarifa.
- **endDate:** Fecha de fin de la aplicación.
- **price:** Precio final a aplicar.

### Ejemplo de Llamada
GET http://localhost:8080/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00

Ejemplo de Respuesta
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50
}

Pruebas
Se han desarrollado tests que validan diferentes escenarios de consulta. Los casos de prueba son:

Test 1:
Petición a las 10:00 del día 14 para el producto 35455 y brand 1.
Debe devolver tarifa 1 y precio 35.50.

Test 2:
Petición a las 16:00 del día 14 para el producto 35455 y brand 1.
Debe devolver tarifa 2 y precio 25.45.

Test 3:
Petición a las 21:00 del día 14 para el producto 35455 y brand 1.
Debe devolver tarifa 1 y precio 35.50 (ya que tarifa 2 aplica solo de 15:00 a 18:30).

Test 4:
Petición a las 10:00 del día 15 para el producto 35455 y brand 1.
Debe devolver tarifa 3 y precio 30.50.

Test 5:
Petición a las 21:00 del día 16 para el producto 35455 y brand 1.
Debe devolver tarifa 4 y precio 38.95.

Los tests se encuentran en la clase PriceControllerTest y utilizan MockMvc junto con @MockBean para simular las respuestas del servicio y del mapper.

Manejo de Excepciones
El proyecto cuenta con un manejo global de excepciones mediante @RestControllerAdvice (clase GlobalExceptionHandler), que centraliza el manejo de errores y devuelve respuestas JSON consistentes en caso de fallo.
