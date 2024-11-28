package untdf.is3.excepcion;

import jakarta.annotation.Resources;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import untdf.is3.transferible.TransferibleExcepcion;

public class MapeadorExcepciones {

    /**
     * RFC Fuente
     * <a href="https://datatracker.ietf.org/doc/html/rfc7807">Problem Details for HTTP APIs</a>
     *
     */

    @Inject
    Logger auditoria;

    @ServerExceptionMapper
    public RestResponse<TransferibleExcepcion> mapValidationException(ConstraintViolationException e,
                                                                      @Context UriInfo uriInfo) {

        auditoria.debug("mapValidationException: " + e.getMessage());
        TransferibleExcepcion cuerpo = new TransferibleExcepcion();
        cuerpo.setStatus(400);
        cuerpo.setType("https://www.rfc-editor.org/rfc/rfc2616#section-10.4.1");
        cuerpo.setDetail(e.getMessage());
        cuerpo.setInstance(uriInfo.getPath());
        cuerpo.setTitle("La solicitud no cumple con las restricciones requeridas.");
        return RestResponse.ResponseBuilder.create(RestResponse.Status.BAD_REQUEST, cuerpo).header("Warning", e.getMessage())
                .build();

    }

    @ServerExceptionMapper
    public RestResponse<TransferibleExcepcion> mapHTTPInternalServerErrorException(HTTPInternalServerErrorException e,
                                                                                   @Context UriInfo uriInfo) {

        auditoria.debug("mapHTTPInternalServerErrorException: " + e.getMessage());
        TransferibleExcepcion cuerpo = new TransferibleExcepcion();
        cuerpo.setStatus(500);
        cuerpo.setType("https://www.rfc-editor.org/rfc/rfc2616#section-10.5.1");
        cuerpo.setDetail(e.getMessage());
        cuerpo.setInstance(uriInfo.getPath());
        cuerpo.setTitle("El servidor tiene problemas para procesar la solicitud.");
        return RestResponse.ResponseBuilder.create(RestResponse.Status.INTERNAL_SERVER_ERROR, cuerpo).header("Warning", e.getMessage())
                .build();

    }

    @ServerExceptionMapper
    public RestResponse<TransferibleExcepcion> mapHTTPEntityExistsException(HTTPEntityExistsException e,
                                                                                   @Context UriInfo uriInfo) {

        auditoria.debug("mapHTTPEntityExistsException: " + e.getMessage());
        TransferibleExcepcion cuerpo = new TransferibleExcepcion();
        cuerpo.setStatus(409);
        cuerpo.setType("https://www.rfc-editor.org/rfc/rfc2616#section-10.4.10");
        cuerpo.setDetail(e.getMessage());
        cuerpo.setInstance(uriInfo.getPath());
        cuerpo.setTitle("Problemas con creación o actualización de la entidad.");
        return RestResponse.ResponseBuilder.create(RestResponse.Status.CONFLICT, cuerpo).header("Warning", e.getMessage())
                .build();

    }

    @ServerExceptionMapper
    public RestResponse<TransferibleExcepcion> mapHTTPNoContentException(HTTPNoContentException e,
                                                                            @Context UriInfo uriInfo) {

        auditoria.debug("mapHTTPNoContentException: " + e.getMessage());
        TransferibleExcepcion cuerpo = new TransferibleExcepcion();
        cuerpo.setStatus(204);
        cuerpo.setType("https://www.rfc-editor.org/rfc/rfc2616#section-10.2.5");
        cuerpo.setDetail(e.getMessage());
        cuerpo.setInstance(uriInfo.getPath());
        cuerpo.setTitle("Problemas al obtener la entidad.");
        return RestResponse.ResponseBuilder.create(RestResponse.Status.NO_CONTENT, cuerpo).header("Warning", e.getMessage())
                .build();

    }

}
