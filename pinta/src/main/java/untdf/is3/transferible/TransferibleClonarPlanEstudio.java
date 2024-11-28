package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleClonarPlanEstudio {

    @NotNull
    @Size(min = 1, max = 64)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 64, required = true)
    private String nombre;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int planEstudioId;

    public @NotNull @Size(min = 1, max = 64) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 64) String nombre) {
        this.nombre = nombre;
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
