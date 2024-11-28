package untdf.is3.transferible;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearPlanEstudio {

    @NotNull
    @Size(min = 1, max = 64)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 64, required = true)
    private String nombre;

    public @NotNull @Size(min = 1, max = 64) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 64) String nombre) {
        this.nombre = nombre;
    }
}
