package ApiRest.TurnosRotativos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class EmpleadoDTO {

    private Long id;
    @NotNull(message = "nroDocumento es obligatorio.")

    private Integer nroDocumento;

    @NotBlank(message = "nombre es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Solo se permiten letras en el campo 'nombre'")
    private String nombre;

    @NotBlank(message = "apellido es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Solo se permiten letras en el campo 'apellido'")
    private String apellido;

    @NotBlank(message = "email es obligatorio.")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @NotNull(message = "fecha de nacimiento es obligatorio.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "fecha de ingreso es obligatorio.")
    private LocalDate fechaIngreso;


    private LocalDate fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
