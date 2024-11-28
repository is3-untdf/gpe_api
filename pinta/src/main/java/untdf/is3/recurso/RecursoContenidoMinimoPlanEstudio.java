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
import untdf.is3.servicio.ServicioContenidoMinimoPlanEstudio;
import untdf.is3.transferible.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Contenido Mínimo de Plan de Estudio")
@RequestScoped
@Path("contenido-minimos-de-planes-de-estudio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecursoContenidoMinimoPlanEstudio {

    private static final String RUTA_BASE_RECURSO = "/contenido-minimos-de-planes-de-estudio/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioContenidoMinimoPlanEstudio servicioContenidoMinimoPlanEstudio;

    @GET
    @Operation(summary = "Obtener todos los Contenidos Mínimos de Planes de Estudio.")
    @APIResponse(responseCode = "200",
            description = "Contenidos Mínimos de Planes de Estudio recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    public RestResponse<List<TransferibleContenidoMinimoPlanEstudio>> obtenerTodos() {

        auditoria.debug("Intentando obtener todos los Contenidos Mínimos de Planes de Estudio.");
        return RestResponse.ResponseBuilder.ok(servicioContenidoMinimoPlanEstudio.obtenerTodos()).build();

    }

    @GET
    @Path("dependencias")
    @Operation(summary = "Obtener todos los Contenidos Mínimos de Planes de Estudio y sus dependencias.")
    @APIResponse(responseCode = "200",
            description = "Contenidos Mínimos de Planes de Estudio recuperados con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudioConDependencias.class)))
    public RestResponse<List<TransferibleContenidoMinimoPlanEstudioConDependencias>> obtenerTodosConDependencias() {

        auditoria.debug("Intentando obtener todos los Contenidos Mínimos de Planes de Estudio y sus dependencias.");
        return RestResponse.ResponseBuilder.ok(servicioContenidoMinimoPlanEstudio.obtenerTodosConDependencias()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Contenido Mínimo de Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Contenido Mínimo de Plan de Estudio recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoMinimoPlanEstudio> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo de Plan de Estudio {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioContenidoMinimoPlanEstudio.obtener(id)).build();

    }

    @GET
    @Path("{id}/dependencias")
    @Operation(summary = "Obtener un Contenido Mínimo de Plan de Estudio por su identificador, con sus dependencias.")
    @APIResponse(responseCode = "200",
            description = "Contenido Mínimo de Plan de Estudio recuperado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoMinimoPlanEstudioConDependencias> obtenerConDependencias(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo de Plan de Estudio {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioContenidoMinimoPlanEstudio.obtenerConDependencias(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Contenido Mínimo de Plan de Estudio.")
    @APIResponse(responseCode = "201",
            description = "Contenido Mínimo de Plan de Estudio creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al crear nuevo Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al crear nuevo Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nuevo Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoMinimoPlanEstudio> crear(@RequestBody @Valid TransferibleCrearContenidoMinimoPlanEstudio transferibleCrearContenidoMinimoPlanEstudio){

        auditoria.debug("Intentando crear Contenido Mínimo de Plan de Estudio.");
        TransferibleContenidoMinimoPlanEstudio contenidoMinimoPlanEstudioNuevo = servicioContenidoMinimoPlanEstudio.crear(transferibleCrearContenidoMinimoPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + contenidoMinimoPlanEstudioNuevo.getContenidoMinimoPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, contenidoMinimoPlanEstudioNuevo)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Contenido Minimo Plan Estudio.")
    @APIResponse(responseCode = "200",
            description = "Contenido Minimo Plan Estudio modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Contenido Minimo Plan Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Contenido Minimo Plan Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Contenido Minimo Plan Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Contenido Minimo Plan Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoMinimoPlanEstudio> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferibleContenidoMinimoPlanEstudio transferibleContenidoMinimoPlanEstudio){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleContenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleContenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Contenido Minimo Plan Estudio {0}", id);
        TransferibleContenidoMinimoPlanEstudio contenidoMinimoPlanEstudioModificada = servicioContenidoMinimoPlanEstudio.modificar(id, transferibleContenidoMinimoPlanEstudio);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + contenidoMinimoPlanEstudioModificada.getContenidoMinimoPlanEstudioId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, contenidoMinimoPlanEstudioModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Contenido Mínimo de Plan de Estudio por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Contenido Mínimo de Plan de Estudio eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoMinimoPlanEstudio.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Contenido Mínimo de Plan de Estudio.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoMinimoPlanEstudio> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Contenido Mínimo de Plan de Estudio {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioContenidoMinimoPlanEstudio.eliminar(id)).build();

    }

}
