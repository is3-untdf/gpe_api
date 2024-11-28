package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearTituloXActividadProfesionalReservada {

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minLength = 1, required = true)
    private int tituloId;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minLength = 1, required = true)
    private int actividadProfesionalReservadaId;

    @NotNull
    @Min(1)
    public int getTituloId() {
        return tituloId;
    }

    public void setTituloId(@NotNull @Min(1) int tituloId) {
        this.tituloId = tituloId;
    }

    @NotNull
    @Min(1)
    public int getActividadProfesionalReservadaId() {
        return actividadProfesionalReservadaId;
    }

    public void setActividadProfesionalReservadaId(@NotNull @Min(1) int actividadProfesionalReservadaId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
    }
}
