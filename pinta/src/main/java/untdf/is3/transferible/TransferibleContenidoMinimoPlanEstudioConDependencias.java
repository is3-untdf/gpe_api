package untdf.is3.transferible;

import io.quarkus.qute.TemplateExtension;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.modelo.Asignatura;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.PlanEstudio;
import untdf.is3.utilidad.ValidarExigencia;

import java.util.List;
import java.util.Objects;

public class TransferibleContenidoMinimoPlanEstudioConDependencias {

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
    private int asignaturaId;

    @Min(1)
    @Schema(defaultValue = "", nullable = true)
    private Asignatura asignatura;

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

    @Min(1)
    @Schema(defaultValue = "", nullable = true)
    private Intensidad intensidad;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false)
    private int planEstudioId;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false)
    private PlanEstudio planEstudio;

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

    @Min(1)
    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(@Min(1) int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public @Min(1) Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(@Min(1) Asignatura asignatura) {
        this.asignatura = asignatura;
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

    public @Min(1) Intensidad getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(@Min(1) Intensidad intensidad) {
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

    public @NotNull @Min(1) PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(@NotNull @Min(1) PlanEstudio planEstudio) {
        this.planEstudio = planEstudio;
    }
}
