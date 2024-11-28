package untdf.is3.transferible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import untdf.is3.utilidad.ValidarExigencia;

public class TransferibleRecomendacionCurricular {

    @NotNull
    @Min(1)
    @Schema
    private int recomendacionCurricularId;

    @NotNull
    @Schema(defaultValue = "", nullable = false, required = true)
    private int codigo;

    @NotNull
    @Size(min = 1, max = 256)
    @Schema(defaultValue = "", nullable = false, minLength = 1, maxLength = 256, required = true)
    private String nombre;

    @ValidarExigencia
    @Schema(defaultValue = "O", nullable = true, required = true)
    private Character exigencia;

    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private int intensidadId;

    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private int pesoRelativoId;

    @Schema(defaultValue = "", nullable = true, minimum = "1")
    private int areaRedUnciId;

    public TransferibleRecomendacionCurricular() {}

    @NotNull
    @Min(1)
    public int getRecomendacionCurricularId() {
        return recomendacionCurricularId;
    }

    public void setRecomendacionCurricularId(@NotNull @Min(1) int recomendacionCurricularId) {
        this.recomendacionCurricularId = recomendacionCurricularId;
    }

    @NotNull
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull int codigo) {
        this.codigo = codigo;
    }

    public @NotNull @Size(min = 1, max = 256) String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull @Size(min = 1, max = 256) String nombre) {
        this.nombre = nombre;
    }

    public Character getExigencia() {
        return exigencia;
    }

    public void setExigencia(Character exigencia) {
        this.exigencia = exigencia;
    }

    public int getIntensidadId() {
        return intensidadId;
    }

    public void setIntensidadId(int intensidadId) {
        this.intensidadId = intensidadId;
    }

    public int getPesoRelativoId() {
        return pesoRelativoId;
    }

    public void setPesoRelativoId(int pesoRelativoId) {
        this.pesoRelativoId = pesoRelativoId;
    }

    public int getAreaRedUnciId() {
        return areaRedUnciId;
    }

    public void setAreaRedUnciId(int areaRedUnciId) {
        this.areaRedUnciId = areaRedUnciId;
    }
}
