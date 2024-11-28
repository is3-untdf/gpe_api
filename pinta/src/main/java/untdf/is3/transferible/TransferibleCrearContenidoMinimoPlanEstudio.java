package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.utilidad.ValidarExigencia;

public class TransferibleCrearContenidoMinimoPlanEstudio {

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

    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private Integer horasTeoria;

    @ValidarExigencia
    @Schema(defaultValue = "", nullable = true)
    private Character exigencia;

    @Min(1)
    @Schema(defaultValue = "", nullable = true)
    private Integer intensidad;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, required = true)
    private int planEstudioId;

    public TransferibleCrearContenidoMinimoPlanEstudio() {}

    public TransferibleCrearContenidoMinimoPlanEstudio(String nombre, Integer asignaturaId, Integer horasPractica, Integer horasTeoria, Character exigencia, Integer intensidad, int planEstudioId) {
        this.nombre = nombre;
        this.asignaturaId = asignaturaId;
        this.horasPractica = horasPractica;
        this.horasTeoria = horasTeoria;
        this.exigencia = exigencia;
        this.intensidad = intensidad;
        this.planEstudioId = planEstudioId;
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

    public Integer getHorasTeoria() {
        return horasTeoria;
    }

    public void setHorasTeoria(Integer horasTeoria) {
        this.horasTeoria = horasTeoria;
    }

    public Character getExigencia() {
        return exigencia;
    }

    public void setExigencia(Character exigencia) {
        this.exigencia = exigencia;
    }

    public @Min(1) Integer getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(@Min(1) Integer intensidad) {
        this.intensidad = intensidad;
    }

    @NotNull
    @Min(1)
    public int getPlanEstudioId() {
        return planEstudioId;
    }

    public void setPlanEstudioId(@NotNull @Min(1) int planEstudioId) {
        this.planEstudioId = planEstudioId;
    }
}
