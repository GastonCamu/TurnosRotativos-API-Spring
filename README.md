
# API REST Turnos Rotativos
Spring Initializr: https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=17&groupId=ApiRest&artifactId=TurnosRotativos&name=TurnosRotativos&description=API%20REST%20de%20turnos%20rotativos&packageName=ApiRest.TurnosRotativos&dependencies=web,data-jpa,h2,validation,devtools

# Dependencias

- Spring Web
- Spring Data JPA
- H2 Database
- Validation
- Spring Boot DevTools

## Se considero utilizar las siguientes dependencias:

- **Lombok** pero se descarto debido a la obligación de descargar un plugin en los diferentes IDE y en la posible colisión con otras dependencias así de como desconocer la implementación de los métodos.
- **MapStruct** pero se descarto debido a las colisiones que puede tener con las otras dependencias y desconocer la implementación de los métodos.

# Estructura de directorios

## /controller
### EmpleadoController

## /dto
### EmpleadoDTO

## /entity
### Empleado
El manejo de la creacion de la fechaCreacion se hizo con PrePersist ya que se toma la fecha del momento exacto en el que se guarda el empleado en la base de datos.

## /exception
En este proyecto decidi trabajar de manera que atraves de el servicio puedo retornar un mensaje y un HttpStatus. Ademas de manejar las validaciones en el DTO.
### BusinessException
### GlobalExceptionHandler

## /mapper
Este directorio es donde se encuentran las clases que sirven para convertir la entidad en dto y viceversa. 
### EmpleadoMapper

## /repository
### EmpleadoRepository

## /service
Las validaciones que se solicitaron, actualmente se estan trabajando directamente en los servicios.
### EmpleadoService
Interfaz de EmpleadoService
### impl
#### EmpleadoServiceImpl
