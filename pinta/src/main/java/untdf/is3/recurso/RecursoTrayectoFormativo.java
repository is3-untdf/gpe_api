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
import untdf.is3.servicio.ServicioTrayectoFormativo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTrayectoFormativo;
import untdf.is3.transferible.TransferibleCrearTrayectoFormativo;
import untdf.is3.transferible.TransferibleExcepcion;

import java.net.URI;
import java.util.List;

@Tag(name = "Trayecto Formativo") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("trayecto-formativo") // uri --> host:puerto/path
@Consumes(MediaType.APPLICATION_JSON) // Formato de entrada que consume
@Produces(MediaType.APPLICATION_JSON) // Formato de salida que produce
public class RecursoTrayectoFormativo {

    private static final String RUTA_BASE_RECURSO = "/trayecto-formativo/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioTrayectoFormativo servicioTrayectoFormativo;

    @GET
    @Operation(summary = "Obtener todos los Trayectos Formativos.") // Descripción
    @APIResponse(responseCode = "200",
            description = "Trayectos Formativos recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTrayectoFormativo.class)))
    public RestResponse<List<TransferibleTrayectoFormativo>> obtenerTodas() {

        auditoria.debug("Intentando obtener todos los Trayectos Formativos.");
        return RestResponse.ResponseBuilder.ok(servicioTrayectoFormativo.obtenerTodas()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Trayecto Formativo por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Trayecto Formativo recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTrayectoFormativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTrayectoFormativo> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Trayecto Formativo {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTrayectoFormativo.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Trayecto Formativo.")
    @APIResponse(responseCode = "201",
            description = "Trayecto Formativo creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleTrayectoFormativo.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nuevo Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTrayectoFormativo> crear(@RequestBody @Valid TransferibleCrearTrayectoFormativo transferibleCrearTrayectoFormativo){

        auditoria.debug("Intentando crear Trayecto Formativo.");
        TransferibleTrayectoFormativo trayectoFormativoNueva = servicioTrayectoFormativo.crear(transferibleCrearTrayectoFormativo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + trayectoFormativoNueva.getTrayectoFormativoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, trayectoFormativoNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar un Trayecto Formativo.")
    @APIResponse(responseCode = "200",
            description = "Trayecto Formativo modificado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleTrayectoFormativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTrayectoFormativo> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferibleTrayectoFormativo transferibleTrayectoFormativo){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleTrayectoFormativo.getTrayectoFormativoId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleTrayectoFormativo.getTrayectoFormativoId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Trayecto Formativo {0}", id);
        TransferibleTrayectoFormativo trayectoFormativoModificada = servicioTrayectoFormativo.modificar(id, transferibleTrayectoFormativo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + trayectoFormativoModificada.getTrayectoFormativoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, trayectoFormativoModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Trayecto Formativo por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Trayecto Formativo eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTrayectoFormativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Trayecto Formativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTrayectoFormativo> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Trayecto Formativo {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTrayectoFormativo.eliminar(id)).build();

    }

}
