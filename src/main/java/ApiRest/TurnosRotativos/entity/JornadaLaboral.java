package ApiRest.TurnosRotativos.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "jornada_laboral")
public class JornadaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_concepto_laboral", nullable = false)
    private ConceptoLaboral conceptoLaboral;

    private LocalDate fecha;
    private Integer hsTrabajadas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ConceptoLaboral getConceptoLaboral() {
        return conceptoLaboral;
    }

    public void setConceptoLaboral(ConceptoLaboral conceptoLaboral) {
        this.conceptoLaboral = conceptoLaboral;
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
}
