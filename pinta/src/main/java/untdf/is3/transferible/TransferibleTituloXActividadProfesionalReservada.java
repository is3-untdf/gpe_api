package untdf.is3.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleTituloXActividadProfesionalReservada {

    @Schema 
    private int tituloXActividadProfesionalReservadaId;

    @Schema
    private int tituloId;

    @Schema
    private int actividadProfesionalReservadaId;

    public int getTituloXActividadProfesionalReservadaId() {
        return tituloXActividadProfesionalReservadaId;
    }

    public void setTituloXActividadProfesionalReservadaId(int tituloXActividadProfesionalReservadaId) {
        this.tituloXActividadProfesionalReservadaId = tituloXActividadProfesionalReservadaId;
    }

    public int getTituloId() {
        return tituloId;
    }

    public void setTituloId(int tituloId) {
        this.tituloId = tituloId;
    }

    public int getActividadProfesionalReservadaId() {
        return actividadProfesionalReservadaId;
    }

    public void setActividadProfesionalReservadaId(int actividadProfesionalReservadaId) {
        this.actividadProfesionalReservadaId = actividadProfesionalReservadaId;
    }
}