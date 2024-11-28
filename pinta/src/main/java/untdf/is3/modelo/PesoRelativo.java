package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "pinta", name = "peso_relativo", uniqueConstraints = {
    @UniqueConstraint(name = "peso_relativo_descripcion_key", columnNames = "descripcion")
})
public class PesoRelativo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int pesoRelativoId;

  @NotNull
  @Size(min = 1, max = 256)
  private String descripcion;

  public PesoRelativo() {
  }

  public PesoRelativo(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getPesoRelativoId() {
    return pesoRelativoId;
  }

  public void setPesoRelativoId(int pesoRelativoId) {
    this.pesoRelativoId = pesoRelativoId;
  }

  public @NotNull @Size(min = 1, max = 256) String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(@NotNull @Size(min = 1, max = 256) String descripcion) {
    this.descripcion = descripcion;
  }

}
