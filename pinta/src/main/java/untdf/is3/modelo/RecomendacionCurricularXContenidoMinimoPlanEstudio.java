package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Check;
import untdf.is3.utilidad.ValidarExigencia;

@Entity
@Table(schema = "pinta", name = "recomendacion_curricular_x_contenido_minimo_plan_estudio",
uniqueConstraints = {
        @UniqueConstraint(name = "recomendacion_curricular_x_co_recomendacion_curricular_id_c_key",
                columnNames = {"recomendacion_curricular_id", "contenido_minimo_plan_estudio_id"})
})
public class RecomendacionCurricularXContenidoMinimoPlanEstudio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int recomendacionCurricularXContenidoMinimoPlanEstudioId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "recomendacion_curricular_id", referencedColumnName = "id")
  private RecomendacionCurricular recomendacionCurricularId;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "contenido_minimo_plan_estudio_id", referencedColumnName = "id")
  private ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudioId;

  // Atributos de la relación
  @Check(name = "mayor a cero", constraints = "recomendacion_curricular_x_contenido_minim_horas_practica_check")
  @Column(name = "horas_practica")
  private Integer horasPractica;

  @Min(1)
  @Check(name = "mayor a cero", constraints = "recomendacion_curricular_x_contenido_minimo__horas_teoria_check")
  @Column(name = "horas_teoria")
  private Integer horasTeoria;

  @ValidarExigencia
  private Character exigencia;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "intensidad_id", referencedColumnName = "id")
  private Intensidad intensidadId;

  private String observaciones;

  public RecomendacionCurricularXContenidoMinimoPlanEstudio() {}

  public RecomendacionCurricularXContenidoMinimoPlanEstudio(RecomendacionCurricular recomendacionCurricularId, ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudioId, Integer horasPractica, Integer horasTeoria, Character exigencia, Intensidad intensidadId, String observaciones) {
    this.recomendacionCurricularId = recomendacionCurricularId;
    this.contenidoMinimoPlanEstudioId = contenidoMinimoPlanEstudioId;
    this.horasPractica = horasPractica;
    this.horasTeoria = horasTeoria;
    this.exigencia = exigencia;
    this.intensidadId = intensidadId;
    this.observaciones = observaciones;
  }

  public int getRecomendacionCurricularXContenidoMinimoPlanEstudioId() {
    return recomendacionCurricularXContenidoMinimoPlanEstudioId;
  }

  public void setRecomendacionCurricularXContenidoMinimoPlanEstudioId(int recomendacionCurricularXContenidoMinimoPlanEstudioId) {
    this.recomendacionCurricularXContenidoMinimoPlanEstudioId = recomendacionCurricularXContenidoMinimoPlanEstudioId;
  }

  public @NotNull RecomendacionCurricular getRecomendacionCurricularId() {
    return recomendacionCurricularId;
  }

  public void setRecomendacionCurricularId(@NotNull RecomendacionCurricular recomendacionCurricularId) {
    this.recomendacionCurricularId = recomendacionCurricularId;
  }

  public @NotNull ContenidoMinimoPlanEstudio getContenidoMinimoPlanEstudioId() {
    return contenidoMinimoPlanEstudioId;
  }

  public void setContenidoMinimoPlanEstudioId(@NotNull ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudioId) {
    this.contenidoMinimoPlanEstudioId = contenidoMinimoPlanEstudioId;
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

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
