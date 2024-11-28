package untdf.is3.recurso;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
import untdf.is3.servicio.ServicioPesoRelativo;
import untdf.is3.transferible.TransferibleCrearPesoRelativo;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferiblePesoRelativo;

import java.net.URI;
import java.util.List;

@Tag(name = "Peso Relativo")
@RequestScoped
@Path("pesos-relativos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoPesoRelativo {

    private static final String RUTA_BASE_RECURSO = "/pesos-relativos/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioPesoRelativo servicioPesoRelativo;

    @GET
    @Operation(summary = "Obtener todas las Pesos Relativos.")
    @APIResponse(responseCode = "200",
            description = "Pesos Relativos recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePesoRelativo.class)))
    public RestResponse<List<TransferiblePesoRelativo>> obtenerTodas() {

        auditoria.debug("Intentando obtener todas las Pesos Relativos.");
        return RestResponse.ResponseBuilder.ok(servicioPesoRelativo.obtenerTodas()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Peso Relativo por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Peso Relativo recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePesoRelativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePesoRelativo> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Peso Relativo {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioPesoRelativo.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Peso Relativo.")
    @APIResponse(responseCode = "201",
            description = "Peso Relativo creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferiblePesoRelativo.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al crear nuevo Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nuevo Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePesoRelativo> crear(@RequestBody @Valid TransferibleCrearPesoRelativo transferibleCrearPesoRelativo){

        auditoria.debug("Intentando crear Peso Relativo.");
        TransferiblePesoRelativo pesoRelativoNueva = servicioPesoRelativo.crear(transferibleCrearPesoRelativo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + pesoRelativoNueva.getPesoRelativoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, pesoRelativoNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Peso Relativo.")
    @APIResponse(responseCode = "200",
            description = "Peso Relativo modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferiblePesoRelativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePesoRelativo> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferiblePesoRelativo transferiblePesoRelativo){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferiblePesoRelativo.getPesoRelativoId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferiblePesoRelativo.getPesoRelativoId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Peso Relativo {0}", id);
        TransferiblePesoRelativo pesoRelativoModificada = servicioPesoRelativo.modificar(id, transferiblePesoRelativo);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + pesoRelativoModificada.getPesoRelativoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, pesoRelativoModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Peso Relativo por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Peso Relativo eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePesoRelativo.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Peso Relativo.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePesoRelativo> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Peso Relativo {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioPesoRelativo.eliminar(id)).build();

    }

}
