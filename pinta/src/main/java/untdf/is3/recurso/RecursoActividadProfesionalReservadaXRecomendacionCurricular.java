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
import untdf.is3.servicio.ServicioActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transferible.TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleActividadProfesionalReservadaXRecomendacionCurricular;

import java.net.URI;
import java.util.List;

@Tag(name = "Relación Actividad Profesional Reservada x Recomendación Curricular")
@RequestScoped
@Path("actividades-profesionales-reservadas-x-recomendaciones-curriculares")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoActividadProfesionalReservadaXRecomendacionCurricular {

    private static final String RUTA_BASE_RECURSO = "/actividades-profesionales-reservadas-x-recomendaciones-curriculares/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioActividadProfesionalReservadaXRecomendacionCurricular servicioActividadProfesionalReservadaXRecomendacionCurricular;

    @GET
    @Operation(summary = "Obtener todas las relaciones entre Actividades Profesionales Reservadas y Recomendaciones Curriculares.")
    @APIResponse(responseCode = "200",
            description = "Relaciones recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservadaXRecomendacionCurricular.class)))
    public RestResponse<List<TransferibleActividadProfesionalReservadaXRecomendacionCurricular>> obtenerTodos() {

        auditoria.debug("Intentando obtener todas las relaciones.");
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservadaXRecomendacionCurricular.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una relación entre Actividad Profesional Reservada y Recomendación Curricular por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservadaXRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservadaXRecomendacionCurricular> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservadaXRecomendacionCurricular.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nueva relación entre Actividad Profesional Reservada y Recomendación Curricular.")
    @APIResponse(responseCode = "201",
            description = "Relación creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservadaXRecomendacionCurricular.class)))
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
    public RestResponse<TransferibleActividadProfesionalReservadaXRecomendacionCurricular> crear(@RequestBody @Valid TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular transferibleCrearActividadProfesionalReservadaXRecomendacionCurricular){

        auditoria.debug("Intentando crear relación.");
        TransferibleActividadProfesionalReservadaXRecomendacionCurricular nuevaRelacion = servicioActividadProfesionalReservadaXRecomendacionCurricular.crear(transferibleCrearActividadProfesionalReservadaXRecomendacionCurricular);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + nuevaRelacion.getActividadProfesionalReservadaXRecomendacionCurricularId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, nuevaRelacion)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una relación entre Actividad Profesional Reservada y Recomendación Curricular por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservadaXRecomendacionCurricular.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservadaXRecomendacionCurricular> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservadaXRecomendacionCurricular.eliminar(id)).build();

    }

}
