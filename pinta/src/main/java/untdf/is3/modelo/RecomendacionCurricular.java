package untdf.is3.modelo;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Check;
import untdf.is3.utilidad.ValidarExigencia;

@Entity
@Table(schema = "pinta", name = "recomendacion_curricular", uniqueConstraints = {
    @UniqueConstraint(name = "recomendacion_curricular_codigo_key", columnNames = "codigo"),
    @UniqueConstraint(name = "recomendacion_curricular_nombre_key", columnNames = "nombre")
})
public class RecomendacionCurricular {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int recomendacionCurricularId;

  @NotNull
  @Min(1)
  private int codigo;

  @NotNull
  @Size(min = 1, max = 256)
  private String nombre;

  @ValidarExigencia
  private Character exigencia;

  @ManyToOne
  @JoinColumn(name = "intensidad_id", referencedColumnName = "id")
  private Intensidad intensidadId;

  @ManyToOne
  @JoinColumn(name = "peso_relativo_id", referencedColumnName = "id")
  private PesoRelativo pesoRelativoId;

  @ManyToOne
  @JoinColumn(name = "area_red_unci_id", referencedColumnName = "id")
  private AreaRedUnci areaRedUnciId;

  public RecomendacionCurricular() {}

  public RecomendacionCurricular(int codigo, String nombre, Character exigencia, Intensidad intensidadId, PesoRelativo pesoRelativoId, AreaRedUnci areaRedUnciId) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.exigencia = exigencia;
    this.intensidadId = intensidadId;
    this.pesoRelativoId = pesoRelativoId;
    this.areaRedUnciId = areaRedUnciId;
  }

  public int getRecomendacionCurricularId() {
    return recomendacionCurricularId;
  }

  public void setRecomendacionCurricularId(int recomendacionCurricularId) {
    this.recomendacionCurricularId = recomendacionCurricularId;
  }

  @NotNull
  @Min(1)
  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(@NotNull @Min(1) int codigo) {
    this.codigo = codigo;
  }

  public @NotNull @Size(min = 1, max = 256) String getNombre() {
    return nombre;
  }

  public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
    this.nombre = nombre;
  }

  public Character getExigencia() {
    return exigencia;
  }

  public void setExigencia(@ValidarExigencia Character exigencia) {
    this.exigencia = exigencia;
  }

  public Intensidad getIntensidadId() {
    return intensidadId;
  }

  public void setIntensidadId(Intensidad intensidadId) {
    this.intensidadId = intensidadId;
  }

  public PesoRelativo getPesoRelativoId() {
    return pesoRelativoId;
  }

  public void setPesoRelativoId(PesoRelativo pesoRelativoId) {
    this.pesoRelativoId = pesoRelativoId;
  }

  public AreaRedUnci getAreaRedUnciId() {
    return areaRedUnciId;
  }

  public void setAreaRedUnciId(AreaRedUnci areaRedUnciId) {
    this.areaRedUnciId = areaRedUnciId;
  }
}
