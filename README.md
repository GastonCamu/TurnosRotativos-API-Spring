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
	Tanto en el put como en el create se trabajo con validaciones que se definieron en EmpleadoDTO

## /dto
### EmpleadoDTO
Hay definidas diferentes validaciones entre estas están las Pattern en nombre y apellido que permiten solo la admisión de letras.

## /entity
### Empleado
El manejo de la creación de la fechaCreacion se hizo con PrePersist ya que se toma la fecha del momento exacto en el que se guarda el empleado en la base de datos.

La fecha de creación se almacena en la base de datos con hora y se retorna solo la fecha.

## /exception
En este proyecto decidí trabajar de manera que a través de el servicio puedo retornar un mensaje y un HttpStatus. Además de manejar las validaciones en el DTO.

### BusinessException
### GlobalExceptionHandler

## /mapper
Este directorio es donde se encuentran las clases que sirven para convertir la entidad en dto y viceversa en caso de que el objeto que se traiga no sea nulo.
### EmpleadoMapper

## /repository
### EmpleadoRepository
	Aqui defini metodos que se van a usar en el servicio para las validaciones.

## /service

### EmpleadoService
	Contiene la definicion de los metodos crud del empleado que se van a usar en la implementacion.

### impl
Aquí se encuentra la implementación de la interfaz EmpleadoService.
#### EmpleadoServiceImpl
Tanto para el método de createEmpleado() como para el de updateEmpleado() utilice la anotación @Transactional que permite que no se almacenen datos parciales revirtiendo los cambios en caso de que se apliquen incompletos.

Para manejar la validaciones de algunos métodos estoy utilizando la clase EmpleadoValidator y usando los métodos como estáticos ya que originalmente tenia esa clase como @Component y usaba un @Autowired para acceder pero a la hora de testear la parte de servicios no se me permitía acceder a las excepciones, así que lo trabaje con métodos estáticos.

### validation
Aquí se alojan casi todas las validaciones que se utilizan en los servicios, de manera individual.

#### EmpleadoValidator

A simple vista la validación del email y el DNI pueden parecer que son iguales pero en el caso del update si se intenta cambiar algo que no es el DNI o el correo no se va a poder porque el mismo empleado se encuentra en la base de datos por eso hice una validación aparte para el update agregando la validación de que se omitan los datos del documento y email del mismo empleado permitiendo cambiar cualquier otra dato sin necesidad de cambiar estos.

# Test
En la parte de test comparte similitud en la estructura de directorios con la diferencia de que los archivos tienen la palabra Test al final.

## /controller
### EmpleadoControllerTest
	Se testeo:
	
		GET:
		1. Retorne Empleado correctamente y el status correspondiente.
		2. Retorne una lista de Empleados y el status correspondiente.
		POST:
		3. Creacion correcta del empleado y el status correspondiente.
		PUT:
		4. Modificacion correcta del empleado y el status correspondiente. 

## /mapper
### /EmpleadoMapperTest
	Se testeo:
	
	1. Conversion correcta de EmpleadoDTO (DTO) a Empleado (entidad).
	2. Conversion correcta de Empleado (entidad) a EmpleadoDTO (DTO).
	3. En caso de DTO nulo retornarlo nulo.
	4. En caso de Entidad nula retornarla nula.

## /service
### /impl
#### EmpleadoServiceImplTest
	Se testeo:
	
	Get:
	1. Si empleado no existe retorna excepcion.
	2. si solicita una lista de empleados y no hay ninguno retorna una lista vacia.
	
	Crear Empleado:
	3. Si se intenta crear un empleado cuyo email ya existe entonces retorna una excepcion.
	4.  Si se intenta crear un empleado cuyo numero de documento ya existe entonces retorna una excepcion.
	5. Si se intenta crear un empleado cuya fecha de ingreso es la posterior a la actual de ese momento retorna excepcion.
	6. Si se intenta crear un empleado cuya fecha de nacimiento es posterior a la actual de ese momento retorna excepcion.
	7. Si se intenta crear un empleado cuya edad es menor a 18 años se retorna una excepcion.
	
	Actualizar Empleado:
	8. Si se intenta actualizar un empleado cuya fecha de ingreso es posterior a la actual de ese momento retorna excepcion.
	9. Si se intenta actualizar un empleado cuya fecha de nacimiento es posterior a la actual de ese momento retorna excepcion.
	10. Si se intenta actualizar la fecha de nacimiento de un empleado cuya edad nueva da menos 18 años se retorna excepcion.
	