package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;
import untdf.is3.utilidad.ValidarExigencia;

@Entity
@Table(schema = "pinta", name = "contenido_minimo_plan_estudio", uniqueConstraints = {
    @UniqueConstraint(name = "contenido_minimo_plan_estudio_id_plan_estudios_id_key", columnNames = {"id, plan_estudios_id"})
})
public class ContenidoMinimoPlanEstudio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int contenidoMinimoPlanEstudioId;

  @NotNull
  @Size(min = 1, max = 256)
  private String nombre;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "asignatura_id", referencedColumnName = "id")
  private Asignatura asignaturaId;

  @Check(name = "mayor a cero", constraints = "contenido_minimo_plan_estudio_horas_practica_check")
  @Column(name = "horas_practica")
  private Integer horasPractica;

  @Min(1)
  @Check(name = "mayor a cero", constraints = "contenido_minimos_plan_estudio_horas_teoria_check")
  @Column(name = "horas_teoria")
  private Integer horasTeoria;

  @ValidarExigencia
  private Character exigencia;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "intensidad_id", referencedColumnName = "id")
  private Intensidad intensidadId;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "plan_estudios_id", referencedColumnName = "id")
  private PlanEstudio planEstudioId;

  public ContenidoMinimoPlanEstudio() {
  }

  public ContenidoMinimoPlanEstudio(String nombre, Asignatura asignaturaId, Integer horasPractica, Integer horasTeoria, Character exigencia, Intensidad intensidadId, PlanEstudio planEstudioId) {
    this.nombre = nombre;
    this.asignaturaId = asignaturaId;
    this.horasPractica = horasPractica;
    this.horasTeoria = horasTeoria;
    this.exigencia = exigencia;
    this.intensidadId = intensidadId;
    this.planEstudioId = planEstudioId;
  }

  public int getContenidoMinimoPlanEstudioId() {
    return contenidoMinimoPlanEstudioId;
  }

  public void setContenidoMinimoPlanEstudioId(int contenidoMinimoPlanEstudioId) {
    this.contenidoMinimoPlanEstudioId = contenidoMinimoPlanEstudioId;
  }

  public @NotNull @Size(min = 1, max = 256) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
    this.nombre = nombre;
  }

  public Asignatura getAsignaturaId() {
    return asignaturaId;
  }

  public void setAsignaturaId(Asignatura asignaturaId) {
    this.asignaturaId = asignaturaId;
  }

  public Integer getHorasPractica() {
    return horasPractica;
  }

  public void setHorasPractica(Integer horasPractica) {
    this.horasPractica = horasPractica;
  }

  public @Min(1) Integer getHorasTeoria() {
    return horasTeoria;
  }

  public void setHorasTeoria(@Min(1) Integer horasTeoria) {
    this.horasTeoria = horasTeoria;
  }

  public Character getExigencia() {
    return exigencia;
  }

  public void setExigencia(Character exigencia) {
    this.exigencia = exigencia;
  }

  public Intensidad getIntensidadId() {
    return intensidadId;
  }

  public void setIntensidadId(Intensidad intensidadId) {
    this.intensidadId = intensidadId;
  }

  public @NotNull PlanEstudio getPlanEstudioId() {
    return planEstudioId;
  }

  public void setPlanEstudioId(@NotNull PlanEstudio planEstudioId) {
    this.planEstudioId = planEstudioId;
  }

}
