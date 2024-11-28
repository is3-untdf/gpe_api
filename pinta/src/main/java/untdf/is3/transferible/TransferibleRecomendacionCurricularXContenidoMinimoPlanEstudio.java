package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.utilidad.ValidarExigencia;

public class TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio {

    @Schema 
    private int recomendacionCurricularXContenidoMinimoPlanEstudioId;

    @Schema
    private int recomendacionCurricularId;

    @Schema
    private int contenidoMinimoPlanEstudioId;

    @Min(1)
    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private Integer horasPractica;

    @Min(1)
    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private Integer horasTeoria;

    @ValidarExigencia
    @Schema(defaultValue = "O", nullable = true)
    private Character exigencia;

    @Min(1)
    @Schema(defaultValue = "", nullable = true)
    private Integer intensidadId;

    @Schema(defaultValue = "", nullable = true)
    private String observaciones;

    public int getRecomendacionCurricularXContenidoMinimoPlanEstudioId() {
        return recomendacionCurricularXContenidoMinimoPlanEstudioId;
    }

    public void setRecomendacionCurricularXContenidoMinimoPlanEstudioId(int recomendacionCurricularXContenidoMinimoPlanEstudioId) {
        this.recomendacionCurricularXContenidoMinimoPlanEstudioId = recomendacionCurricularXContenidoMinimoPlanEstudioId;
    }

    public int getRecomendacionCurricularId() {
        return recomendacionCurricularId;
    }

    public void setRecomendacionCurricularId(int recomendacionCurricularId) {
        this.recomendacionCurricularId = recomendacionCurricularId;
    }

    public int getContenidoMinimoPlanEstudioId() {
        return contenidoMinimoPlanEstudioId;
    }

    public void setContenidoMinimoPlanEstudioId(int contenidoMinimoPlanEstudioId) {
        this.contenidoMinimoPlanEstudioId = contenidoMinimoPlanEstudioId;
    }

    public @Min(1) Integer getHorasPractica() {
        return horasPractica;
    }

    public void setHorasPractica(@Min(1) Integer horasPractica) {
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

    public @Min(1) Integer getIntensidadId() {
        return intensidadId;
    }

    public void setIntensidadId(@Min(1) Integer intensidadId) {
        this.intensidadId = intensidadId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}