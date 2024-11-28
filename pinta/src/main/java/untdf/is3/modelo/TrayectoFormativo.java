package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

@Entity
@Table(schema = "pinta", name = "trayecto_formativo",
    uniqueConstraints = {
        @UniqueConstraint(name = "trayecto_formativo_nombre_key", columnNames = "nombre"),
        @UniqueConstraint(name = "trayecto_formativo_sigla_key", columnNames = "sigla")
    })
public class TrayectoFormativo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int trayectoFormativoId;

  @NotNull
  @Size(min = 1, max = 16)
  private String sigla;

  @NotNull
  @Size(min = 1, max = 128)
  private String nombre;

  @NotNull
  @Min(1)
  @Check(name = "mayor a cero", constraints = "trayecto_formativo_horas_minimas_check")
  @Column(name = "horas_minimas")
  private int horasMinimas;

  public TrayectoFormativo() { }

  public TrayectoFormativo(String sigla, String nombre, int horasMinimas) {
    this.sigla = sigla;
    this.nombre = nombre;
    this.horasMinimas = horasMinimas;
  }

  public int getTrayectoFormativoId() {
    return trayectoFormativoId;
  }

  public void setTrayectoFormativoId(int trayectoFormativoId) {
    this.trayectoFormativoId = trayectoFormativoId;
  }

  public @NotNull @Size(min = 1, max = 16) String getSigla() {
    return sigla;
  }

  public void setSigla(@NotNull @Size(min = 1, max = 16) String sigla) {
    this.sigla = sigla;
  }

  public @NotNull @Size(min = 1, max = 64) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 64) String nombre) {
    this.nombre = nombre;
  }

  @NotNull
  @Min(1)
  public int getHorasMinimas() {
    return horasMinimas;
  }

  public void setHorasMinimas(@NotNull @Min(1) int horasMinimas) {
    this.horasMinimas = horasMinimas;
  }
}
