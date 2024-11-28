package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "pinta", name = "area_red_unci",
    uniqueConstraints = {
        @UniqueConstraint(name = "area_red_unci_nombre_key", columnNames = "nombre"),
        @UniqueConstraint(name = "area_red_unci_sigla_key", columnNames = "sigla")
    })
public class AreaRedUnci {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int areaRedUnciId;

  @NotNull
  @Size(min = 1, max = 16)
  private String sigla;

  @NotNull
  @Size(min = 1, max = 128)
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "trayecto_formativo_id", referencedColumnName = "id")
  private TrayectoFormativo trayectoFormativoId;

  public AreaRedUnci() { }

  public AreaRedUnci(String sigla, String descripcion, TrayectoFormativo trayectoFormativo) {
    this.sigla = sigla;
    this.nombre = descripcion;
    this.trayectoFormativoId = trayectoFormativo;
  }

  public int getAreaRedUnciId() {
    return areaRedUnciId;
  }

  public void setAreaRedUnciId(int areaRedUnciId) {
    this.areaRedUnciId = areaRedUnciId;
  }

  public @NotNull @Size(min = 1, max = 16) String getSigla() {
    return sigla;
  }

  public void setSigla(@NotNull @Size(min = 1, max = 16) String sigla) {
    this.sigla = sigla;
  }

  public @NotNull @Size(min = 1, max = 128) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 128) String nombre) {
    this.nombre = nombre;
  }

  public TrayectoFormativo getTrayectoFormativoId() {
    return trayectoFormativoId;
  }

  public void setTrayectoFormativoId(TrayectoFormativo trayectoFormativoId) {
    this.trayectoFormativoId = trayectoFormativoId;
  }
}
