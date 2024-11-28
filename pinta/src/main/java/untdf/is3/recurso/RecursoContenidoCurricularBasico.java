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
import untdf.is3.servicio.ServicioContenidoCurricularBasico;
import untdf.is3.transferible.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Contenido Curricular Básico") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("contenidos-curriculares-basicos") // uri --> host:puerto/path
@Consumes(MediaType.APPLICATION_JSON) // Formato de entrada que consume
@Produces(MediaType.APPLICATION_JSON) // Formato de salida que produce
public class RecursoContenidoCurricularBasico {

    private static final String RUTA_BASE_RECURSO = "/contenidos-curriculares-basicos/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioContenidoCurricularBasico servicioContenidoCurricularBasico;

    @GET
    @Operation(summary = "Obtener todos los Contenidos Curriculares Básicos.") // Descripción
    @APIResponse(responseCode = "200",
            description = "Contenidos Curriculares Básicos recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoCurricularBasico.class)))
    public RestResponse<List<TransferibleContenidoCurricularBasico>> obtenerTodas() {

        auditoria.debug("Intentando obtener todos los Contenidos Curriculares Básicos.");
        return RestResponse.ResponseBuilder.ok(servicioContenidoCurricularBasico.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener un Contenido Curricular Básico por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Contenido Curricular Básico recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoCurricularBasico.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoCurricularBasico> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener Contenido Curricular Básico {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioContenidoCurricularBasico.obtener(id)).build();

    }

    @POST
    @Operation(summary = "Crear un nuevo Contenido Curricular Básico.")
    @APIResponse(responseCode = "201",
            description = "Contenido Curricular Básico creado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoCurricularBasico.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al crear nuevo Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al crear nuevo Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nuevo Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nuevo Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoCurricularBasico> crear(@RequestBody @Valid TransferibleCrearContenidoCurricularBasico transferibleCrearContenidoCurricularBasico){

        auditoria.debug("Intentando crear Contenido Curricular Básico.");
        TransferibleContenidoCurricularBasico contenidoCurricularBasicoNueva = servicioContenidoCurricularBasico.crear(transferibleCrearContenidoCurricularBasico);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + contenidoCurricularBasicoNueva.getContenidoCurricularBasicoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, contenidoCurricularBasicoNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar un Contenido Curricular Básico.")
    @APIResponse(responseCode = "200",
            description = "Contenido Curricular Básico modificado satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoCurricularBasico.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoCurricularBasico> modificar(@PathParam("id") int id,
                                                           @RequestBody @Valid TransferibleContenidoCurricularBasico transferibleContenidoCurricularBasico){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleContenidoCurricularBasico.getContenidoCurricularBasicoId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleContenidoCurricularBasico.getContenidoCurricularBasicoId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Contenido Curricular Básico {0}", id);
        TransferibleContenidoCurricularBasico contenidoCurricularBasicoModificada = servicioContenidoCurricularBasico.modificar(id, transferibleContenidoCurricularBasico);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + contenidoCurricularBasicoModificada.getContenidoCurricularBasicoId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, contenidoCurricularBasicoModificada)
                .location(uri)
                .build();

    }
    
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Contenido Curricular Básico por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Contenido Curricular Básico eliminado con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleContenidoCurricularBasico.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Contenido Curricular Básico.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleContenidoCurricularBasico> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar Contenido Curricular Básico {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioContenidoCurricularBasico.eliminar(id)).build();

    }

}
