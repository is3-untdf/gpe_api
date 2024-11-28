package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleTitulo {

    @NotNull
    @Min(1)
    @Schema 
    private int tituloId;

    @NotNull
    @Size(min = 1, max = 512)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 512, required = true)
    private String nombre;

    @NotNull
    @Min(1)
    public int getTituloId() {
        return tituloId;
    }

    public void setTituloId(@NotNull @Min(1) int tituloId) {
        this.tituloId = tituloId;
    }

    public @NotNull @Size(min = 1, max = 512) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 512) String nombre) {
        this.nombre = nombre;
    }
}