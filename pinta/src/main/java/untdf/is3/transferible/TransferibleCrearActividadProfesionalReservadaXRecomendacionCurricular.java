package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular {

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int actividadProfesionalReservadaId;

    @NotNull
    @Min(1)
    @Schema(defaultValue = "", nullable = false, minimum = "1", required = true)
    private int recomendacionCurricularId;

    public TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular() { }

    public TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular(int actividadProfesionalReservadaId, int recomendacionCurricularId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
        this.recomendacionCurricularId = recomendacionCurricularId;
    }

    @NotNull
    @Min(1)
    public int getActividadProfesionalReservadaId() {
        return actividadProfesionalReservadaId;
    }

    public void setActividadProfesionalReservadaId(@NotNull @Min(1) int actividadProfesionalReservadaId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
    }

    @NotNull
    @Min(1)
    public int getRecomendacionCurricularId() {
        return recomendacionCurricularId;
    }

    public void setRecomendacionCurricularId(@NotNull @Min(1) int recomendacionCurricularId) {
        this.recomendacionCurricularId = recomendacionCurricularId;
    }
}
