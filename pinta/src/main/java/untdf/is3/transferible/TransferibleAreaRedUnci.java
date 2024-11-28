package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleAreaRedUnci {

    @NotNull
    @Min(1)
    @Schema
    private int areaRedUnciId;

    @NotNull
    @Size(min = 1, max = 16)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 16, required = true)
    private String sigla;

    @NotNull
    @Size(min = 1, max = 128)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 128, required = true)
    private String nombre;

    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1")
    private int trayectoFormativoId;

    public int getAreaRedUnciId() {
        return areaRedUnciId;
    }

    public void setAreaRedUnciId(int areaRedUnciId) {
        this.areaRedUnciId = areaRedUnciId;
    }

    public @NotNull @Size(min = 1, max = 16) String getSigla() {
        return sigla;
    }

    public void setSigla(@NotNull @Size(min = 1, max = 16) String sigla) {
        this.sigla = sigla;
    }

    public @NotNull @Size(min = 1, max = 128) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 128) String nombre) {
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
