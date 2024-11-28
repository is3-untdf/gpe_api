package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.utilidad.ValidarExigencia;

public class TransferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio {

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int recomendacionCurricularId;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
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

    @NotNull
    @Min(1)
    public int getRecomendacionCurricularId() {
        return recomendacionCurricularId;
    }

    public void setRecomendacionCurricularId(@NotNull @Min(1) int recomendacionCurricularId) {
        this.recomendacionCurricularId = recomendacionCurricularId;
    }

    @NotNull
    @Min(1)
    public int getContenidoMinimoPlanEstudioId() {
        return contenidoMinimoPlanEstudioId;
    }

    public void setContenidoMinimoPlanEstudioId(@NotNull @Min(1) int contenidoMinimoPlanEstudioId) {
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
