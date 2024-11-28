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
import untdf.is3.servicio.ServicioPlanEstudio;
import untdf.is3.transferible.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Plan de Estudio")
@RequestScoped
@Path("planes-de-estudio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoPlanEstudio {

    private static final String RUTA_BASE_RECURSO = "/planes-de-estudio/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioPlanEstudio servicioPlanEstudio;

    @GET
    @Operation(summary = "Obtener todos los Planes de Estudio.")
    @APIResponse(responseCode = "200",
            description = "Planes de estudio recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    public RestResponse<List<TransferiblePlanEstudio>> obtenerTodos() {

        auditoria.debug("Intentando obtener todos los Planes de Estudio.");
        return RestResponse.ResponseBuilder.ok(servicioPlanEstudio.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Plan de Estudio recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePlanEstudio> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Plan de Estudio {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioPlanEstudio.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Plan de Estudio.")
    @APIResponse(responseCode = "201",
            description = "Plan de Estudio creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al crear nuevo Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nuevo Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePlanEstudio> crear(@RequestBody @Valid TransferibleCrearPlanEstudio transferibleCrearPlanEstudio){

        auditoria.debug("Intentando crear Plan de Estudio.");
        TransferiblePlanEstudio planEstudioNuevo = servicioPlanEstudio.crear(transferibleCrearPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + planEstudioNuevo.getPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, planEstudioNuevo)
                .location(uri)
                .build();

    }

    @POST
    @Path("clonar")
    @Operation(summary = "Clonar un Plan de Estudio.")
    @APIResponse(responseCode = "201",
            description = "Plan de Estudio clonado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas al clonar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al clonar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al clonar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePlanEstudio> clonar(@RequestBody @Valid TransferibleClonarPlanEstudio transferibleClonarPlanEstudio){

        auditoria.debug("Intentando clonar Plan de Estudio.");
        TransferiblePlanEstudio planEstudioNuevo = servicioPlanEstudio.clonar(transferibleClonarPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + planEstudioNuevo.getPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, planEstudioNuevo)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar un Plan de Estudio.")
    @APIResponse(responseCode = "200",
            description = "Plan de Estudio modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePlanEstudio> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferiblePlanEstudio transferiblePlanEstudio){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferiblePlanEstudio.getPlanEstudioId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferiblePlanEstudio.getPlanEstudioId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Plan de Estudio {0}", id);
        TransferiblePlanEstudio planEstudioModificada = servicioPlanEstudio.modificar(id, transferiblePlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + planEstudioModificada.getPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, planEstudioModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Plan de Estudio eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferiblePlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferiblePlanEstudio> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Plan de Estudio {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioPlanEstudio.eliminar(id)).build();

    }

}
