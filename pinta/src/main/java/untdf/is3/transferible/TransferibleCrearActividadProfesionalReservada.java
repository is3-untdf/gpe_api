package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearActividadProfesionalReservada {

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int codigo;

    @NotNull
    @Size(min = 1, max = 16)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 16, required = true)
    private String sigla;

    @NotNull
    @Size(min = 1, max = 1024)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 1024, required = true)
    private String descripcion;

    @NotNull
    @Min(1)
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull @Min(1) int codigo) {
        this.codigo = codigo;
    }

    public @NotNull @Size(min = 1, max = 16) String getSigla() {
        return sigla;
    }

    public void setSigla(@NotNull @Size(min = 1, max = 16) String sigla) {
        this.sigla = sigla;
    }

    public @NotNull @Size(min = 1, max = 1024) String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotNull @Size(min = 1, max = 1024) String descripcion) {
        this.descripcion = descripcion;
    }
}
