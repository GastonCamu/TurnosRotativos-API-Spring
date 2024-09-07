package ApiRest.TurnosRotativos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="concepto_laboral")
public class ConceptoLaboral {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "laborable")
    private Boolean laborable;

    @Column(name = "hs_minimo")
    private Integer hsMinimo;

    @Column(name = "hs_maximo")
    private Integer hsMaximo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getLaborable() {
        return laborable;
    }

    public void setLaborable(Boolean laborable) {
        this.laborable = laborable;
    }

    public Integer getHsMinimo() {
        return hsMinimo;
    }

    public void setHsMinimo(Integer hsMinimo) {
        this.hsMinimo = hsMinimo;
    }

    public Integer getHsMaximo() {
        return hsMaximo;
    }

    public void setHsMaximo(Integer hsMaximo) {
        this.hsMaximo = hsMaximo;
    }
}
