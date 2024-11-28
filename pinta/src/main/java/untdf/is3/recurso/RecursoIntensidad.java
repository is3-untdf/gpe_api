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
import untdf.is3.servicio.ServicioIntensidad;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleCrearIntensidad;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferiblePlanEstudio;

import java.net.URI;
import java.util.List;

@Tag(name = "Intensidad")
@RequestScoped
@Path("intensidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoIntensidad {

    private static final String RUTA_BASE_RECURSO = "/intensidad/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioIntensidad servicioIntensidad;

    @GET
    @Operation(summary = "Obtener todas las Intensidades.")
    @APIResponse(responseCode = "200",
            description = "Intensidades recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleIntensidad.class)))
    public RestResponse<List<TransferibleIntensidad>> obtenerTodas() {

        auditoria.debug("Intentando obtener todas las intensidades.");
        return RestResponse.ResponseBuilder.ok(servicioIntensidad.obtenerTodas()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una Intensidad por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Intensidad recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleIntensidad.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleIntensidad> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener intensidad {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioIntensidad.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear una nueva Intensidad.")
    @APIResponse(responseCode = "201",
            description = "Intensidad creada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleIntensidad.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nueva Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nueva Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleIntensidad> crear(@RequestBody @Valid TransferibleCrearIntensidad transferibleCrearIntensidad){

        auditoria.debug("Intentando crear Intensidad.");
        TransferibleIntensidad intensidadNueva = servicioIntensidad.crear(transferibleCrearIntensidad);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + intensidadNueva.getIntensidadId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, intensidadNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Intensidad.")
    @APIResponse(responseCode = "200",
            description = "Intensidad modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleIntensidad.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleIntensidad> modificar(@PathParam("id") int id,
                                                           @RequestBody @Valid TransferibleIntensidad transferibleIntensidad){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleIntensidad.getIntensidadId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleIntensidad.getIntensidadId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Intensidad {0}", id);
        TransferibleIntensidad intensidadModificada = servicioIntensidad.modificar(id, transferibleIntensidad);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + intensidadModificada.getIntensidadId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, intensidadModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Intensidad por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Intensidad eliminada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleIntensidad.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Intensidad.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleIntensidad> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar intensidad {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioIntensidad.eliminar(id)).build();

    }

}
