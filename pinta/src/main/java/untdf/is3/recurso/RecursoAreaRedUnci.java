package untdf.is3.recurso;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.servicio.ServicioAreaRedUnci;
import untdf.is3.transferible.TransferibleAreaRedUnci;
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleCrearAreaRedUnci;
import untdf.is3.transferible.TransferibleExcepcion;

import java.net.URI;
import java.util.List;

@Tag(name = "Área Red UNCI") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("areas-red-unci") // uri --> host:puerto/path
@Consumes(MediaType.APPLICATION_JSON) // Formato de entrada que consume
@Produces(MediaType.APPLICATION_JSON) // Formato de salida que produce
public class RecursoAreaRedUnci {

    private static final String RUTA_BASE_RECURSO = "/areas-red-unci/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioAreaRedUnci servicioAreaRedUnci;

    @GET
    @Operation(summary = "Obtener todas las Áreas Red UNCI.") // Descripción
    @APIResponse(responseCode = "200",
            description = "Áreas Red UNCI recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAreaRedUnci.class)))
    public RestResponse<List<TransferibleAreaRedUnci>> obtenerTodas() {

        auditoria.debug("Intentando obtener todas las Áreas Red UNCI.");
        return RestResponse.ResponseBuilder.ok(servicioAreaRedUnci.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Área Red UNCI por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Área Red UNCI recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAreaRedUnci.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAreaRedUnci> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Área Red UNCI {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioAreaRedUnci.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear una nueva Área Red UNCI.")
    @APIResponse(responseCode = "201",
            description = "Área Red UNCI creada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleAreaRedUnci.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al crear nueva Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nueva Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nueva Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAreaRedUnci> crear(@RequestBody @Valid TransferibleCrearAreaRedUnci transferibleCrearAreaRedUnci){

        auditoria.debug("Intentando crear Área Red UNCI.");
        TransferibleAreaRedUnci areaRedUnciNueva = servicioAreaRedUnci.crear(transferibleCrearAreaRedUnci);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + areaRedUnciNueva.getAreaRedUnciId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, areaRedUnciNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar un Área Red UNCI.")
    @APIResponse(responseCode = "200",
            description = "Área Red UNCI modificado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleAreaRedUnci.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAreaRedUnci> modificar(@PathParam("id") int id,
                                                                         @RequestBody @Valid TransferibleAreaRedUnci transferibleAreaRedUnci){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleAreaRedUnci.getAreaRedUnciId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleAreaRedUnci.getAreaRedUnciId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Área Red UNCI {0}", id);
        TransferibleAreaRedUnci areaRedUnciModificada = servicioAreaRedUnci.modificar(id, transferibleAreaRedUnci);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + areaRedUnciModificada.getAreaRedUnciId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, areaRedUnciModificada)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Área Red UNCI por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Área Red UNCI eliminada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAreaRedUnci.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Área Red UNCI.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAreaRedUnci> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Área Red UNCI {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioAreaRedUnci.eliminar(id)).build();

    }

}
