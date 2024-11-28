package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferiblePesoRelativo {

    @NotNull
    @Min(1)
    @Schema
    private int pesoRelativoId;

    @NotNull
    @Size(min = 1, max = 256)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 255, required = true)
    private String descripcion;

    public TransferiblePesoRelativo() {}

    @NotNull
    @Min(1)
    public int getPesoRelativoId() {
        return pesoRelativoId;
    }

    public void setPesoRelativoId(@NotNull @Min(1) int pesoRelativoId) {
        this.pesoRelativoId = pesoRelativoId;
    }

    public @NotNull @Size(min = 1, max = 256) String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotNull @Size(min = 1, max = 256) String descripcion) {
        this.descripcion = descripcion;
    }
}
