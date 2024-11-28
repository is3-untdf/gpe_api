package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

@Entity
@Table(schema = "pinta", name = "actividad_profesional_reservada",
    uniqueConstraints = {
        @UniqueConstraint(name = "actividad_profesional_reservada_codigo_key", columnNames = "codigo"),
        @UniqueConstraint(name = "actividad_profesional_reservada_sigla_key", columnNames = "sigla")
    })
public class ActividadProfesionalReservada {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int actividadProfesionalReservadaId;

  @NotNull
  @Min(1)
  @Check(name = "mayor a cero", constraints = "actividad_profesional_reservada_codigo_check")
  private int codigo;

  @NotNull
  @Size(min = 1, max = 16)
  private String sigla;

  @NotNull
  @Size(min = 1, max = 1024)
  private String descripcion;

  public ActividadProfesionalReservada() {
  }

  public ActividadProfesionalReservada(int codigo, String sigla, String descripcion) {
    this.codigo = codigo;
    this.sigla = sigla;
    this.descripcion = descripcion;
  }

  public int getActividadProfesionalReservadaId() {
    return actividadProfesionalReservadaId;
  }

  public void setActividadProfesionalReservadaId(int actividadProfesionalReservadaId) {
    this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
  }

  @NotNull
  @Min(1)
  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(@NotNull @Min(1) int codigo) {
    this.codigo = codigo;
  }

  public @NotNull @Size(min = 1, max = 16) String getSigla() {
    return sigla;
  }

  public void setSigla(@NotNull @Size(min = 1, max = 16) String sigla) {
    this.sigla = sigla;
  }

  public @NotNull @Size(min = 1, max = 1024) String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(@NotNull @Size(min = 1, max = 1024) String descripcion) {
    this.descripcion = descripcion;
  }
}
