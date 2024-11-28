package untdf.is3.modelo;

import jakarta.persistence.*;

@Entity
@Table(schema = "pinta", name = "titulo_x_actividad_profesional_reservada",
uniqueConstraints = {
        @UniqueConstraint(name = "titulo_x_actividad_profesiona_titulo_id_actividad_profesion_key", columnNames = "(titulo_id, actividad_profesional_reservada_id")
})
public class TituloXActividadProfesionalReservada {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int tituloXActividadProfesionalReservadaId;

  @ManyToOne
  @JoinColumn(name = "titulo_id", referencedColumnName = "id")
  private Titulo tituloId;

  @ManyToOne
  @JoinColumn(name = "actividad_profesional_reservada_id", referencedColumnName = "id")
  private ActividadProfesionalReservada actividadProfesionalReservadaId;

  public TituloXActividadProfesionalReservada() {}

  public TituloXActividadProfesionalReservada(Titulo tituloId, ActividadProfesionalReservada actividadProfesionalReservadaId) {
    this.tituloId = tituloId;
    this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
  }

  public int getTituloXActividadProfesionalReservadaId() {
    return tituloXActividadProfesionalReservadaId;
  }

  public void setTituloXActividadProfesionalReservadaId(int tituloXActividadProfesionalReservadaId) {
    this.tituloXActividadProfesionalReservadaId = tituloXActividadProfesionalReservadaId;
  }

  public Titulo getTituloId() {
    return tituloId;
  }

  public void setTituloId(Titulo tituloId) {
    this.tituloId = tituloId;
  }

  public ActividadProfesionalReservada getActividadProfesionalReservadaId() {
    return actividadProfesionalReservadaId;
  }

  public void setActividadProfesionalReservadaId(ActividadProfesionalReservada actividadProfesionalReservadaId) {
    this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
  }

}
