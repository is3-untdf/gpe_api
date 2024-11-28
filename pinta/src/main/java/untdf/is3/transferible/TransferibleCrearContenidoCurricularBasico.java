package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearContenidoCurricularBasico {

    @NotNull
    @Size(min = 1, max = 256)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 256, required = true)
    private String nombre;

    @Min(1)
    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private int trayectoFormativoId;

    public @NotNull @Size(min = 1, max = 256) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
        this.nombre = nombre;
    }

    @Min(1)
    public int getTrayectoFormativoId() {
        return trayectoFormativoId;
    }

    public void setTrayectoFormativoId(@Min(1) int trayectoFormativoId) {
        this.trayectoFormativoId = trayectoFormativoId;
    }
}
