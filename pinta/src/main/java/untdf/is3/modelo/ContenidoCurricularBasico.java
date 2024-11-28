package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "pinta", name = "contenido_curricular_basico", uniqueConstraints = {
    @UniqueConstraint(name = "contenido_curricular_basico_nombre_key", columnNames = "nombre")
})
public class ContenidoCurricularBasico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int contenidoCurricularBasicoId;

  @NotNull
  @Size(min = 1, max = 256)
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "trayecto_formativo_id", referencedColumnName = "id")
  private TrayectoFormativo trayectoFormativoId;

  public ContenidoCurricularBasico() { }

  public ContenidoCurricularBasico(String nombre, TrayectoFormativo trayectoFormativoId) {
    this.nombre = nombre;
    this.trayectoFormativoId = trayectoFormativoId;
  }

  public int getContenidoCurricularBasicoId() {
    return contenidoCurricularBasicoId;
  }

  public void setContenidoCurricularBasicoId(int contenidoCurricularBasicoId) {
    this.contenidoCurricularBasicoId = contenidoCurricularBasicoId;
  }

  public @NotNull @Size(min = 1, max = 256) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
    this.nombre = nombre;
  }

  public TrayectoFormativo getTrayectoFormativoId() {
    return trayectoFormativoId;
  }

  public void setTrayectoFormativoId(TrayectoFormativo trayectoFormativoId) {
    this.trayectoFormativoId = trayectoFormativoId;
  }
}
