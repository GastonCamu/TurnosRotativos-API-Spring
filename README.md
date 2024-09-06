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
	Tanto en los put como en los create se trabajo con validaciones que se definieron en los DTO.
### ConceptoLaboralController
	En el metodo get se esta utilizando los @RequestParam para poder buscar por parametros.
### EmpleadoController
### JornadaLaboralController

## /dto
### ConceptoLaboralDTO
	Estoy usando el JsonInclude para evitar mostrar la hora minima y maxima cuando estas sean nulas.

### EmpleadoDTO
	Hay definidas diferentes validaciones entre estas están las Pattern en nombre y apellido que permiten solo la admisión de letras.
### JornadaLaboralDTO
Estoy usando el JsonInclude para evitar mostrar valores nulos en este caso se evita mostrar el id del empleado y el del concepto y si el concepto es día libre también las horas de trabajo.
## /entity
### ConceptoLaboral

### Empleado
El manejo de la creación de la fechaCreacion se hizo con PrePersist ya que se toma la fecha del momento exacto en el que se guarda el empleado en la base de datos.  
La fecha de creación se almacena en la base de datos con hora y se retorna solo la fecha.

### JornadaLaboral
Esta entidad utiliza la de Empleado y la de Concepto laboral mediante la anotación @ManyToOne para hacer una relación de muchos a uno.

## /exception
En este proyecto decidí trabajar de manera que a través de el servicio puedo retornar un mensaje y un HttpStatus. Además de manejar las validaciones en el DTO.

### BusinessException
La excepción recibe un mensaje y un status code (opcional) por defecto es un HttpStatus.BAD_REQUEST.
### GlobalExceptionHandler

## /mapper
Este directorio es donde se encuentran las clases que sirven para convertir la entidad en dto y viceversa en caso de que el objeto que se traiga no sea nulo.
### ConceptoLaboralMapper
	Cuando el concepto laboral se pasa a entidad se hace una validacion de que si la hora minima o la hora maxima son distintas de null entonces se almacena el dato. Esta validacion se realizo para poder controlar lo que se guarda.

### EmpleadoMapper
### JornadaLaboralMapper
Aquí se esta omitiendo pasar al dto los id de empleado y de concepto para evitar mostrar estos datos.

## /repository
### ConceptoLaboralRepository
### EmpleadoRepository
### JornadaLaboralRepository

## /service
Contiene la definición de los métodos crud del servicio que se va a usar en la implementación.
### ConceptoLaboralService
### EmpleadoService
### JornadaLaboralService

### impl
Aquí se encuentra la implementación de la interfaz Service.
#### ConceptoLaboralServiceImpl
Hice una validación que almacena la información según lo que haya recibido por parámetro.
#### EmpleadoServiceImpl
Tanto para el método de createEmpleado() como para el de updateEmpleado() utilice la anotación @Transactional que permite que no se almacenen datos parciales revirtiendo los cambios en caso de que se apliquen incompletos.
Para manejar la validaciones de algunos métodos estoy utilizando la clase EmpleadoValidator y usando los métodos como estáticos ya que originalmente tenia esa clase como @Component y usaba un @Autowired para acceder pero a la hora de testear la parte de servicios no se me permitía acceder a las excepciones, así que lo trabaje con métodos estáticos.
#### JornadaLaboralServiceImpl

### validation
Aquí se alojan casi todas las validaciones que se utilizan en los servicios, de manera individual.

#### EmpleadoValidator

A simple vista la validación del email y el DNI pueden parecer que son iguales pero en el caso del update si se intenta cambiar algo que no es el DNI o el correo no se va a poder porque el mismo empleado se encuentra en la base de datos por eso hice una validación aparte para el update agregando la validación de que se omitan los datos del documento y email del mismo empleado permitiendo cambiar cualquier otra dato sin necesidad de cambiar estos.

#### JornadaLaboralValidator
Actualmente para hacer las validaciones que implicaban usar la semana de la determinada fecha, considere que la semana empieza un lunes y termina un domingo.
# Test
En la parte de test comparte similitud en la estructura de directorios con la diferencia de que los archivos tienen la palabra Test al final.

## /controller
### ConceptoLaboralControllerTest
	Se testeo:
	
		GET:
		1. Retorne una todos los conceptos en caso de no usar parametros.
		2. Retornar todos los conceptos cuya id pasada por parametro.
		3. Retornar todos los conceptos cuyo nombre completo o parcial pasado por parametro.
		4. Retornar todos los conceptos cuya id y nombre completo o parcial pasados por parametro.

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
### ConceptoLaboralMapperTest
	Se testeo:
	1. Conversion correcta de ConceptoLaboral (entidad) a ConceptoLaboralDTO (DTO).
	2. Conversion correcta de ConceptoLaboralDTO (DTO) a ConceptoLaboral (entidad).
	3. En caso de DTO nulo retornarlo nulo.
	4. En caso de Entidad nula retornarla nula.

### EmpleadoMapperTest
	Se testeo:
	
	1. Conversion correcta de EmpleadoDTO (DTO) a Empleado (entidad).
	2. Conversion correcta de Empleado (entidad) a EmpleadoDTO (DTO).
	3. En caso de DTO nulo retornarlo nulo.
	4. En caso de Entidad nula retornarla nula.

## /service
### /impl
#### ConceptoLaboralServiceImplTest
	Se testeo:
	
	Get:
	1. Obtener conceptos laborales solo con el id.
	2. Obtener conceptos laborales solo con el nombre.
	3. Obtener conceptos laborales sin filtros.

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
### Validation
#### JornadaLaboralValidatorTest
	Se testeo:
	
	1. Si se intenta crear una jornada y no existe empleado asociado retorna excepcion.
	2. Si se intenta crear una jornada y se manda en horas trabajadas null en un concepto de turno normal retorna excepcion.
	3. Si se intenta crear una jornada y se excede el limite de horas diarias retorna excepcion.
	4. Si se intenta crear una jornada y se supera el limite de horas semanales retorna excepcion.
	5. Si se intenta crear una jornada y no existe concepto asociado retorna excepcion.
	6. Si se intenta crear una jornada, se valida la validacion de horas trabajadas y se retorna excepcion en caso de que no cumpla las horas el rango especificado.