package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleIntensidad {

    @Min(1)
    @NotNull
    @Schema 
    private int intensidadId;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, required = true)
    private int nivel;

    @Size(min = 1, max = 1024)
    @Schema(defaultValue = "", nullable = true, minLength = 1, maxLength = 1024)
    private String descripcion;

    public TransferibleIntensidad() {}

    @Min(1)
    @NotNull
    public int getIntensidadId() {
        return intensidadId;
    }

    public void setIntensidadId(@Min(1) @NotNull int intensidadId) {
        this.intensidadId = intensidadId;
    }

    @NotNull
    @Min(1)
    public int getNivel() {
        return nivel;
    }

    public void setNivel(@NotNull @Min(1) int nivel) {
        this.nivel = nivel;
    }

    public @Size(min = 1, max = 1024) String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(min = 1, max = 1024) String descripcion) {
        this.descripcion = descripcion;
    }
}
