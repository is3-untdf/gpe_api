package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

@Entity // Entidad que se persiste
@Table(schema = "pinta", name = "asignatura", // Nombre de esquema y db
    uniqueConstraints = {
        @UniqueConstraint(name = "asignatura_id_plan_estudios_id_key", columnNames = {"id", "plan_estudios_id"}),
    })
public class Asignatura {

  @Id // Identificador en DB
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id") // Explicitar mapeo de nombre distinto entre DB y API
  private int asignaturaId;

  @NotNull
  @Size(min = 1, max = 8)
  private String codigo;

  @NotNull
  @Size(min = 1, max = 256)
  private String nombre;

  @Min(0)
  @Check(name = "mayor o igual a cero", constraints = "asignatura_carga_horaria_check")
  @Column(name = "carga_horaria")
  private int cargaHoraria;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "plan_estudios_id", referencedColumnName = "id")
  private PlanEstudio planEstudioId;

  public Asignatura() {
  }

  public Asignatura(String codigo, String nombre, int cargaHoraria, PlanEstudio planEstudioId) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.cargaHoraria = cargaHoraria;
    this.planEstudioId = planEstudioId;
  }

  public int getAsignaturaId() {
    return asignaturaId;
  }

  public void setAsignaturaId(int asignaturaId) {
    this.asignaturaId = asignaturaId;
  }

  public @NotNull @Size(min = 1, max = 8) String getCodigo() {
    return codigo;
  }

  public void setCodigo(@NotNull @Size(min = 1, max = 8) String codigo) {
    this.codigo = codigo;
  }

  public @NotNull @Size(min = 1, max = 256) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
    this.nombre = nombre;
  }

  @Min(0)
  public int getCargaHoraria() {
    return cargaHoraria;
  }

  public void setCargaHoraria(@Min(0) int cargaHoraria) {
    this.cargaHoraria = cargaHoraria;
  }

  public @NotNull PlanEstudio getPlanEstudioId() {
    return planEstudioId;
  }

  public void setPlanEstudioId(@NotNull PlanEstudio planEstudioId) {
    this.planEstudioId = planEstudioId;
  }
}
