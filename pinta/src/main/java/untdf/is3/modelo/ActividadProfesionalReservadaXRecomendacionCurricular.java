package untdf.is3.modelo;

import jakarta.persistence.*;

@Entity
@Table(schema = "pinta", name = "actividad_profesional_reservada_x_recomendacion_curricular",
uniqueConstraints = {
        @UniqueConstraint(name = "actividad_profesional_reserva_actividad_profesional_reserva_key", columnNames = "(actividad_profesional_reservada_id, recomendacion_curricular_id")
})
public class ActividadProfesionalReservadaXRecomendacionCurricular {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int actividadProfesionalReservadaXRecomendacionCurricularId;

  @ManyToOne
  @JoinColumn(name = "actividad_profesional_reservada_id", referencedColumnName = "id")
  private ActividadProfesionalReservada actividadProfesionalReservadaId;

  @ManyToOne
  @JoinColumn(name = "recomendacion_curricular_id", referencedColumnName = "id")
  private RecomendacionCurricular recomendacionCurricularId;

  public ActividadProfesionalReservadaXRecomendacionCurricular() {}

  public ActividadProfesionalReservadaXRecomendacionCurricular(ActividadProfesionalReservada actividadProfesionalReservadaId, RecomendacionCurricular recomendacionCurricularId) {
    this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
    this.recomendacionCurricularId = recomendacionCurricularId;
  }

  public int getActividadProfesionalReservadaXRecomendacionCurricularId() {
    return actividadProfesionalReservadaXRecomendacionCurricularId;
  }

  public void setActividadProfesionalReservadaXRecomendacionCurricularId(int actividadProfesionalReservadaXRecomendacionCurricularId) {
    this.actividadProfesionalReservadaXRecomendacionCurricularId = actividadProfesionalReservadaXRecomendacionCurricularId;
  }

  public ActividadProfesionalReservada getActividadProfesionalReservadaId() {
    return actividadProfesionalReservadaId;
  }

  public void setActividadProfesionalReservadaId(ActividadProfesionalReservada actividadProfesionalReservadaId) {
    this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
  }

  public RecomendacionCurricular getRecomendacionCurricularId() {
    return recomendacionCurricularId;
  }

  public void setRecomendacionCurricularId(RecomendacionCurricular recomendacionCurricularId) {
    this.recomendacionCurricularId = recomendacionCurricularId;
  }
}
