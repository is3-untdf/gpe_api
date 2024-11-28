package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "pinta", name = "titulo", uniqueConstraints = {
    @UniqueConstraint(name = "titulo_nombre_key", columnNames = "nombre")
})
public class Titulo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int tituloId;

  @NotNull
  @Size(min = 1, max = 512)
  private String nombre;

  public Titulo() {
  }

  public Titulo(String nombre) {
    this.nombre = nombre;
  }

  public int getTituloId() {
    return tituloId;
  }

  public void setTituloId(int tituloId) {
    this.tituloId = tituloId;
  }

  public @NotNull @Size(min = 1, max = 512) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 512) String nombre) {
    this.nombre = nombre;
  }
}
