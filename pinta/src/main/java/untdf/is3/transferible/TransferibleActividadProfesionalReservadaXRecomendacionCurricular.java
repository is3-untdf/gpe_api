package untdf.is3.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleActividadProfesionalReservadaXRecomendacionCurricular {

    @Schema 
    private int actividadProfesionalReservadaXRecomendacionCurricularId;

    @Schema
    private int actividadProfesionalReservadaId;

    @Schema
    private int recomendacionCurricularId;

    public TransferibleActividadProfesionalReservadaXRecomendacionCurricular(int actividadProfesionalReservada, int recomendacionCurricularId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservada;
        this.recomendacionCurricularId = recomendacionCurricularId;
    }

    public int getActividadProfesionalReservadaXRecomendacionCurricularId() {
        return actividadProfesionalReservadaXRecomendacionCurricularId;
    }

    public void setActividadProfesionalReservadaXRecomendacionCurricularId(int actividadProfesionalReservadaXRecomendacionCurricularId) {
        this.actividadProfesionalReservadaXRecomendacionCurricularId = actividadProfesionalReservadaXRecomendacionCurricularId;
    }

    public int getActividadProfesionalReservadaId() {
        return actividadProfesionalReservadaId;
    }

    public void setActividadProfesionalReservadaId(int actividadProfesionalReservadaId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
    }

    public int getRecomendacionCurricularId() {
        return recomendacionCurricularId;
    }

    public void setRecomendacionCurricularId(int recomendacionCurricularId) {
        this.recomendacionCurricularId = recomendacionCurricularId;
    }
}