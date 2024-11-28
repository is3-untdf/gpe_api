package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "pinta", name = "plan_estudio", uniqueConstraints = {
    @UniqueConstraint(name = "plan_estudio_nombre_key", columnNames = "nombre")
})
public class PlanEstudio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int planEstudioId;

  @NotNull
  @Size(min = 1, max = 64)
  private String nombre;

  public PlanEstudio() {
  }

  public PlanEstudio(String nombre) {
    this.nombre = nombre;
  }

  public int getPlanEstudioId() {
    return planEstudioId;
  }

  public void setPlanEstudioId(int planEstudioId) {
    this.planEstudioId = planEstudioId;
  }

  public @NotNull @Size(min = 1, max = 64) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 64) String nombre) {
    this.nombre = nombre;
  }
}
