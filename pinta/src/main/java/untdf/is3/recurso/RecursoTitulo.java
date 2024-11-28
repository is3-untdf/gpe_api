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
import untdf.is3.servicio.ServicioTitulo;
import untdf.is3.transferible.TransferibleCrearTitulo;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTitulo;

import java.net.URI;
import java.util.List;

@Tag(name = "Título")
@RequestScoped
@Path("titulos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoTitulo {

    private static final String RUTA_BASE_RECURSO = "/titulo/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioTitulo servicioTitulo;

    @GET
    @Operation(summary = "Obtener todos los Títulos.")
    @APIResponse(responseCode = "200",
            description = "Títulos recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTitulo.class)))
    public RestResponse<List<TransferibleTitulo>> obtenerTodos() {

        auditoria.debug("Intentando obtener todos los Títulos.");
        return RestResponse.ResponseBuilder.ok(servicioTitulo.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Título por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Título recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTitulo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTitulo> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Título {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTitulo.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Título.")
    @APIResponse(responseCode = "201",
            description = "Título creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleTitulo.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nuevo Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nuevo Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTitulo> crear(@RequestBody @Valid TransferibleCrearTitulo transferibleCrearTitulo){

        auditoria.debug("Intentando crear Título.");
        TransferibleTitulo tituloNuevo = servicioTitulo.crear(transferibleCrearTitulo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + tituloNuevo.getTituloId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, tituloNuevo)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar un Título.")
    @APIResponse(responseCode = "200",
            description = "Título modificado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleTitulo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTitulo> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferibleTitulo transferibleTitulo){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleTitulo.getTituloId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleTitulo.getTituloId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Título {0}", id);
        TransferibleTitulo tituloModificada = servicioTitulo.modificar(id, transferibleTitulo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + tituloModificada.getTituloId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, tituloModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Título por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Título eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleTitulo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Título.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleTitulo> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Título {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioTitulo.eliminar(id)).build();

    }

}
