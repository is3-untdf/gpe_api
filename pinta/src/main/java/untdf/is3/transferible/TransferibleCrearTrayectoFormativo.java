package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearTrayectoFormativo {

    @NotNull
    @Size(min = 1, max = 16)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 16, required = true)
    private String sigla;

    @NotNull
    @Size(min = 1, max = 128)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 128, required = true)
    private String nombre;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int horasMinimas;

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

    @NotNull
    @Min(1)
    public int getHorasMinimas() {
        return horasMinimas;
    }

    public void setHorasMinimas(@NotNull @Min(1) int horasMinimas) {
        this.horasMinimas = horasMinimas;
    }
}
