package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferiblePlanEstudio {

    @Min(1)
    @NotNull
    @Schema // Metadata para la respuesta rest
    private int planEstudioId;

    @NotNull
    @Size(min = 1, max = 64)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 64, required = true)
    private String nombre;

    @Min(1)
    @NotNull
    public int getPlanEstudioId() {
        return planEstudioId;
    }

    public void setPlanEstudioId(@Min(1) @NotNull int planEstudioId) {
        this.planEstudioId = planEstudioId;
    }

    public @NotNull @Size(min = 1, max = 64) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 64) String nombre) {
        this.nombre = nombre;
    }
}