package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;

@Entity
@Table(schema = "pinta", name = "intensidad", uniqueConstraints = {
    @UniqueConstraint(name = "intensidad_nivel_key", columnNames = "nivel")
})
public class Intensidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int intensidadId;

  @NotNull
  @Min(1)
  private int nivel;

  @Size(min = 1, max = 1024)
  private String descripcion;

  public Intensidad() {
  }

  public Intensidad(int nivel, String descripcion) {
    this.nivel = nivel;
    this.descripcion = descripcion;
  }

  public int getIntensidadId() {
    return intensidadId;
  }

  public void setIntensidadId(int intensidadId) {
    this.intensidadId = intensidadId;
  }

  @NotNull
  @Min(1)
  public int getNivel() {
    return nivel;
  }

  public void setNivel(@NotNull @Min(1) int nivel) {
    this.nivel = nivel;
  }

  public @Size(min = 1, max = 1024) String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(@Size(min = 1, max = 1024) String descripcion) {
    this.descripcion = descripcion;
  }
}
