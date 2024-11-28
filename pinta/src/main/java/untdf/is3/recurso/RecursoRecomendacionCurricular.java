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
import untdf.is3.servicio.ServicioRecomendacionCurricular;
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleRecomendacionCurricular;
import untdf.is3.transferible.TransferibleCrearRecomendacionCurricular;
import untdf.is3.transferible.TransferibleExcepcion;

import java.net.URI;
import java.util.List;

@Tag(name = "Recomendación Curricular Red UNCI")
@RequestScoped
@Path("recomendacion-curricular")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoRecomendacionCurricular {

    private static final String RUTA_BASE_RECURSO = "/recomendacion-curricular/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioRecomendacionCurricular servicioRecomendacionCurricular;

    @GET
    @Operation(summary = "Obtener todas las Recomendaciones Curriculares.")
    @APIResponse(responseCode = "200",
            description = "Recomendaciones Curriculares recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricular.class)))
    public RestResponse<List<TransferibleRecomendacionCurricular>> obtenerTodos() {

        auditoria.debug("Intentando obtener todas las Recomendaciones Curriculares.");
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricular.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una Recomendaciones Curriculares por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Recomendaciones Curriculares recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Recomendaciones Curriculares.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricular> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Recomendaciones Curriculares {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricular.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear una nueva Recomendación Curricular.")
    @APIResponse(responseCode = "201",
            description = "Recomendación Curricular creada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al crear nueva Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nueva Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricular> crear(@RequestBody @Valid TransferibleCrearRecomendacionCurricular transferibleCrearRecomendacionCurricular){

        auditoria.debug("Intentando crear Recomendación Curricular.");
        TransferibleRecomendacionCurricular recomendacionCurricularNuevo = servicioRecomendacionCurricular.crear(transferibleCrearRecomendacionCurricular);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + recomendacionCurricularNuevo.getRecomendacionCurricularId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, recomendacionCurricularNuevo)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Recomendación Curricular.")
    @APIResponse(responseCode = "200",
            description = "Recomendación Curricular modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricular> modificar(@PathParam("id") int id,
                                                                         @RequestBody @Valid TransferibleRecomendacionCurricular transferibleRecomendacionCurricular){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleRecomendacionCurricular.getRecomendacionCurricularId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleRecomendacionCurricular.getRecomendacionCurricularId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Recomendación Curricular {0}", id);
        TransferibleRecomendacionCurricular recomendacionCurricularModificada = servicioRecomendacionCurricular.modificar(id, transferibleRecomendacionCurricular);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + recomendacionCurricularModificada.getRecomendacionCurricularId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, recomendacionCurricularModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Recomendación Curricular por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Recomendación Curricular eliminada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Recomendación Curricular.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricular> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Recomendación Curricular {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricular.eliminar(id)).build();

    }

}
