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
import untdf.is3.servicio.ServicioRecomendacionCurricularXContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio;

import java.net.URI;
import java.util.List;

@Tag(name = "Relación Recomendación Curricular x Contenido Mínimo Plan Estudio")
@RequestScoped
@Path("recomendaciones-curriculares-x-contenidos-minimos-planes-estudio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoRecomendacionCurricularXContenidoMinimoPlanEstudio {

    private static final String RUTA_BASE_RECURSO = "/recomendaciones-curriculares-x-contenidos-minimos-planes-estudio/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioRecomendacionCurricularXContenidoMinimoPlanEstudio servicioRecomendacionCurricularXContenidoMinimoPlanEstudio;

    @GET
    @Operation(summary = "Obtener todas las relaciones entre Recomendaciones Curriculares y Contenidos Mínimos de Planes Estudio.")
    @APIResponse(responseCode = "200",
            description = "Relaciones recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    public RestResponse<List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio>> obtenerTodos() {

        auditoria.debug("Intentando obtener todas las relaciones.");
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una relación entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.obtener(id)).build();

    }

    @GET
    @Path("recomendaciones-curriculares/{id}")
    @Operation(summary = "Obtener una relación entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio por el identificador de la Recomendación Curricular.")
    @APIResponse(responseCode = "200",
            description = "Relación recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio>> obtenerPorRecomendacionCurricular(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener relación de acuerdo a Recomendacion Curricular {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerPorRecomendacionCurricular(id)).build();

    }

    @GET
    @Path("contenidos-minimos-planes-estudio/{id}")
    @Operation(summary = "Obtener una relación entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio por el identificador del Contenido Mínimo.")
    @APIResponse(responseCode = "200",
            description = "Relación recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio>> obtenerPorContenidoMinimo(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener relación de acuerdo a Contenido Mínimo {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerPorContenidoMinimo(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nueva relación entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio.")
    @APIResponse(responseCode = "201",
            description = "Relación creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nueva relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nueva relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> crear(@RequestBody @Valid TransferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio){

        auditoria.debug("Intentando crear relación.");
        TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio nuevaRelacion = servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.crear(transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + nuevaRelacion.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, nuevaRelacion)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio.")
    @APIResponse(responseCode = "200",
            description = "Relación modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al modificar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> modificar(@PathParam("id") Integer id,
                                                                                                  @RequestBody @Valid TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio){


        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debug("Intentando modificar relación.");
        TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio relacion =
                servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.modificar(id,
                        transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + relacion.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, relacion)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una relación entre Recomendación Curricular y Contenido Mínimo de Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioRecomendacionCurricularXContenidoMinimoPlanEstudio.eliminar(id)).build();

    }

}
