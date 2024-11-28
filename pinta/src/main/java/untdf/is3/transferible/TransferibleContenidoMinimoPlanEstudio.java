package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.utilidad.ValidarExigencia;

public class TransferibleContenidoMinimoPlanEstudio {

    @NotNull
    @Min(1)
    @Schema
    private int contenidoMinimoPlanEstudioId;

    @NotNull
    @Size(min = 1, max = 256)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 256, required = true)
    private String nombre;

    @Min(1)
    @Schema(defaultValue = "", nullable = true)
    private Integer asignaturaId;

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

    @NotNull
    @Schema(defaultValue = "1", nullable = false)
    private int planEstudioId;

    @NotNull
    @Min(1)
    public int getContenidoMinimoPlanEstudioId() {
        return contenidoMinimoPlanEstudioId;
    }

    public void setContenidoMinimoPlanEstudioId(@NotNull @Min(1) int contenidoMinimoPlanEstudioId) {
        this.contenidoMinimoPlanEstudioId = contenidoMinimoPlanEstudioId;
    }

    public @NotNull @Size(min = 1, max = 256) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
        this.nombre = nombre;
    }

    public @Min(1) Integer getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(@Min(1) Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
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

    @NotNull
    public int getPlanEstudioId() {
        return planEstudioId;
    }

    public void setPlanEstudioId(@NotNull int planEstudioId) {
        this.planEstudioId = planEstudioId;
    }

}
