package untdf.is3.transferible;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "TransferibleExcepcion",
        description = "Implementación del estándar 'problem details' RFC7807",
        externalDocs = @ExternalDocumentation(
                url = "https://www.rfc-editor.org/rfc/rfc7807.html"
        )
)
public class TransferibleExcepcion {

    @Schema(description = "Código de status HTTP.")
    private int status;

    @Schema(description = "URL a página explicando el error (por defecto about:blank).")
    private String type;

    @Schema(description = "Mensaje al usuario.")
    private String title;

    @Schema(description = "Message al desarrollador.")
    private String detail;

    @Schema(description = "URL que dispara la excepción.")
    private String instance;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
