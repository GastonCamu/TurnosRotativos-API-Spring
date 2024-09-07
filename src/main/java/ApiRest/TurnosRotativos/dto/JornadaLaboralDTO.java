package ApiRest.TurnosRotativos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JornadaLaboralDTO {

    private Long id;
    private Integer nroDocumento;
    private String nombreCompleto;

    @NotNull(message = "fecha es obligatorio.")
    private LocalDate fecha;

    private String concepto;
    private Integer hsTrabajadas;

    @NotNull(message = "idEmpleado es obligatorio.")
    private Long idEmpleado;

    @NotNull(message = "idConcepto es obligatorio.")
    private Long idConcepto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getHsTrabajadas() {
        return hsTrabajadas;
    }

    public void setHsTrabajadas(Integer hsTrabajadas) {
        this.hsTrabajadas = hsTrabajadas;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Long getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Long idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
