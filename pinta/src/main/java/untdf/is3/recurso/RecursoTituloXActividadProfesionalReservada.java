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
import untdf.is3.servicio.ServicioTituloXActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleCrearTituloXActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleTituloXActividadProfesionalReservada;

import java.net.URI;
import java.util.List;

@Tag(name = "Relación Título x Actividad Profesional Reservada")
@RequestScoped
@Path("titulo-x-actividad-profesional-reservada")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoTituloXActividadProfesionalReservada {

    private static final String RUTA_BASE_RECURSO = "/titulo-x-actividad-profesional-reservada/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioTituloXActividadProfesionalReservada servicioTituloXActividadProfesionalReservada;

    @GET
    @Operation(summary = "Obtener todas las relaciones entre Títulos y Actividades Profesionales Reservadas.")
    @APIResponse(responseCode = "200",
            description = "Relaciones recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTituloXActividadProfesionalReservada.class)))
    public RestResponse<List<TransferibleTituloXActividadProfesionalReservada>> obtenerTodos() {

        auditoria.debug("Intentando obtener todas las relaciones.");
        return RestResponse.ResponseBuilder.ok(servicioTituloXActividadProfesionalReservada.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una relación entre Título y Actividad Profesional Reservada por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTituloXActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTituloXActividadProfesionalReservada> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTituloXActividadProfesionalReservada.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nueva relación entre Título y Actividad Profesional Reservada.")
    @APIResponse(responseCode = "201",
            description = "Relación creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleTituloXActividadProfesionalReservada.class)))
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
    public RestResponse<TransferibleTituloXActividadProfesionalReservada> crear(@RequestBody @Valid TransferibleCrearTituloXActividadProfesionalReservada transferibleCrearTituloXActividadProfesionalReservada){

        auditoria.debug("Intentando crear relación.");
        TransferibleTituloXActividadProfesionalReservada nuevaRelacion = servicioTituloXActividadProfesionalReservada.crear(transferibleCrearTituloXActividadProfesionalReservada);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + nuevaRelacion.getTituloXActividadProfesionalReservadaId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, nuevaRelacion)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una relación entre Título y Actividad Profesional Reservada por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Relación eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTituloXActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar relación.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTituloXActividadProfesionalReservada> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar relación {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTituloXActividadProfesionalReservada.eliminar(id)).build();

    }

}
