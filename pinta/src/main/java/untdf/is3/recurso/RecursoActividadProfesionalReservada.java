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
import untdf.is3.servicio.ServicioActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleCrearActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleIntensidad;

import java.net.URI;
import java.util.List;

@Tag(name = "Actividad Profesional Reservada") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("actividades-profesionales-reservadas") // uri --> host:puerto/path
@Consumes(MediaType.APPLICATION_JSON) // Formato de entrada que consume
@Produces(MediaType.APPLICATION_JSON) // Formato de salida que produce
public class RecursoActividadProfesionalReservada {

    private static final String RUTA_BASE_RECURSO = "/actividades-profesionales-reservadas/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioActividadProfesionalReservada servicioActividadProfesionalReservada;

    @GET // Metodo HTTP al que responde este metodo JAVA
    @Operation(summary = "Obtener todas las Actividades Profesionales Reservadas.") // Descripción
    // Respuestas de la API: codigo, descripción y clase
    @APIResponse(responseCode = "200",
            description = "Actividades Profesionales Reservadas recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservada.class)))
    // Envolver la entidad en una respuesta Rest
    public RestResponse<List<TransferibleActividadProfesionalReservada>> obtenerTodas() {

        auditoria.debug("Intentando obtener todas las Actividades Profesionales Reservadas.");
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservada.obtenerTodas()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una Actividad Profesional Reservada por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Actividad Profesional Reservada recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservada> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Actividad Profesional Reservada {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservada.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear una nueva Actividad Profesional Reservada.")
    @APIResponse(responseCode = "201",
            description = "Actividad Profesional Reservada creada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al crear nueva Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nueva Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservada> crear(@RequestBody @Valid TransferibleCrearActividadProfesionalReservada transferibleCrearActividadProfesionalReservada){

        auditoria.debug("Intentando crear Actividad Profesional Reservada.");
        TransferibleActividadProfesionalReservada actividadProfesionalReservadaNueva = servicioActividadProfesionalReservada.crear(transferibleCrearActividadProfesionalReservada);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + actividadProfesionalReservadaNueva.getActividadProfesionalReservadaId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, actividadProfesionalReservadaNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Actividad Profesional Reservada.")
    @APIResponse(responseCode = "200",
            description = "Actividad Profesional Reservada modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservada> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferibleActividadProfesionalReservada transferibleActividadProfesionalReservada){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleActividadProfesionalReservada.getActividadProfesionalReservadaId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleActividadProfesionalReservada.getActividadProfesionalReservadaId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Actividad Profesional Reservada {0}", id);
        TransferibleActividadProfesionalReservada actividadProfesionalReservadaModificada = servicioActividadProfesionalReservada.modificar(id, transferibleActividadProfesionalReservada);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + actividadProfesionalReservadaModificada.getActividadProfesionalReservadaId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, actividadProfesionalReservadaModificada)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Actividad Profesional Reservada por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Actividad Profesional Reservada eliminada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleActividadProfesionalReservada.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Actividad Profesional Reservada.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleActividadProfesionalReservada> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Actividad Profesional Reservada {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioActividadProfesionalReservada.eliminar(id)).build();

    }

}
