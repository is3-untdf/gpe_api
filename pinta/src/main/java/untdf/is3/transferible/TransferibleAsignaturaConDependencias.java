package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.modelo.PlanEstudio;

public class TransferibleAsignaturaConDependencias {

    @Min(1)
    @NotNull
    @Schema
    private int asignaturaId;

    @NotNull
    @Size(min = 1, max = 8)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 8, required = true)
    private String codigo;

    @NotNull
    @Size(min = 1, max = 256)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 256, required = true)
    private String nombre;

    @Min(0)
    @Schema(defaultValue = "", nullable = true, description = "En horas.", minimum = "0")
    private int cargaHoraria;

    @NotNull
    @Schema(defaultValue = "1", nullable = false)
    private int planEstudioId;

    @NotNull
    @Schema(defaultValue = "1", nullable = false)
    private PlanEstudio planEstudio;

    @Min(1)
    @NotNull
    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(@Min(1) @NotNull int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public @NotNull @Size(min = 1, max = 8) String getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull @Size(min = 1, max = 8) String codigo) {
        this.codigo = codigo;
    }

    public @NotNull @Size(min = 1, max = 256) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
        this.nombre = nombre;
    }

    @Min(0)
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(@Min(0) int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @NotNull
    public int getPlanEstudioId() {
        return planEstudioId;
    }

    public void setPlanEstudioId(@NotNull int planEstudioId) {
        this.planEstudioId = planEstudioId;
    }

    public @NotNull PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(@NotNull PlanEstudio planEstudio) {
        this.planEstudio = planEstudio;
    }
}
