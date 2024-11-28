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
import untdf.is3.servicio.ServicioAsignatura;
import untdf.is3.transferible.TransferibleAsignatura;
import untdf.is3.transferible.TransferibleAsignaturaConDependencias;
import untdf.is3.transferible.TransferibleCrearAsignatura;
import untdf.is3.transferible.TransferibleExcepcion;

import java.net.URI;
import java.util.InputMismatchException;
import java.util.List;

@Tag(name = "Asignatura") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("asignaturas") // uri --> host:puerto/path
@Consumes(MediaType.APPLICATION_JSON) // Formato de entrada que consume
@Produces(MediaType.APPLICATION_JSON) // Formato de salida que produce
public class RecursoAsignatura {

    private static final String RUTA_BASE_RECURSO = "/asignatura/";

    @Inject
    Logger auditoria;

    @Inject
    ServicioAsignatura servicioAsignatura;

    @GET // Metodo HTTP al que responde este metodo JAVA
    @Operation(summary = "Obtener todas las Asignaturas.") // Descripción
    // Respuestas de la API: codigo, descripción y clase
    @APIResponse(responseCode = "200",
            description = "Asignaturas recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignatura.class)))
    // Envolver la entidad en una respuesta Rest
    public RestResponse<List<TransferibleAsignatura>> obtenerTodas() {

        auditoria.debug("Intentando obtener todas las asignaturas.");
        return RestResponse.ResponseBuilder.ok(servicioAsignatura.obtenerTodas()).build();

    }

    @GET // Metodo HTTP al que responde este metodo JAVA
    @Path("dependencias")
    @Operation(summary = "Obtener todas las Asignaturas con sus dependencias.") // Descripción
    // Respuestas de la API: codigo, descripción y clase
    @APIResponse(responseCode = "200",
            description = "Asignaturas recuperadas con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignaturaConDependencias.class)))
    // Envolver la entidad en una respuesta Rest
    public RestResponse<List<TransferibleAsignaturaConDependencias>> obtenerTodasConDependencias() {

        auditoria.debug("Intentando obtener todas las asignaturas.");
        return RestResponse.ResponseBuilder.ok(servicioAsignatura.obtenerTodasConDependencias()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Obtener una Asignatura por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Asignatura recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignatura.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAsignatura> obtener(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener asignatura {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioAsignatura.obtener(id)).build();

    }

    @GET
    @Path("{id}/dependencias")
    @Operation(summary = "Obtener una Asignatura por su identificador, con sus dependencias.")
    @APIResponse(responseCode = "200",
            description = "Asignatura recuperada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignaturaConDependencias.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al recuperar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAsignaturaConDependencias> obtenerConDependencias(@PathParam("id") int id) {

        auditoria.debugv("Intentando obtener asignatura {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioAsignatura.obtenerConDependencias(id)).build();

    }

    @POST
    @Operation(summary = "Crear una nueva Asignatura.")
    @APIResponse(responseCode = "201",
            description = "Asignatura creada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignatura.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al crear nueva Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al crear nueva Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al crear nueva Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAsignatura> crear(@RequestBody @Valid TransferibleCrearAsignatura transferibleCrearAsignatura){

        auditoria.debug("Intentando crear Asignatura.");
        TransferibleAsignatura asignaturaNueva = servicioAsignatura.crear(transferibleCrearAsignatura);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + asignaturaNueva.getAsignaturaId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, asignaturaNueva)
                .location(uri)
                .build();

    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Modificar una Asignatura.")
    @APIResponse(responseCode = "200",
            description = "Asignatura modificada satisfactoriamente.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignatura.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al modificar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "400",
            description = "Problemas de validación de los campos al modificar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "409",
            description = "Problemas al modificar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al modificar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAsignatura> modificar(@PathParam("id") int id,
                                                          @RequestBody @Valid TransferibleAsignatura transferibleAsignatura){

        auditoria.debugv("Verificando argumentos de la petición.");
        if(!(id == transferibleAsignatura.getAsignaturaId())) {
            auditoria.debugv("El ID del Path ({0}) y el ID de Body ({1}) deben ser iguales.",
                    id, transferibleAsignatura.getAsignaturaId());
            throw new HTTPInternalServerErrorException("El ID del Path y el ID de Body deben ser iguales.");
        }

        auditoria.debugv("Intentando modificar Asignatura {0}", id);
        TransferibleAsignatura asignaturaModificada = servicioAsignatura.modificar(id, transferibleAsignatura);

        // Crear URI específica
        URI uri = URI.create(RUTA_BASE_RECURSO + asignaturaModificada.getAsignaturaId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.OK, asignaturaModificada)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Asignatura por su identificador.")
    @APIResponse(responseCode = "200",
            description = "Asignatura eliminada con éxito.",
            content = @Content(schema = @Schema(implementation = TransferibleAsignatura.class)))
    @APIResponse(responseCode = "204",
            description = "Problemas al eliminar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    @APIResponse(responseCode = "500",
            description = "Problemas al eliminar Asignatura.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public RestResponse<TransferibleAsignatura> eliminar(@PathParam("id") int id) {

        auditoria.debugv("Intentando eliminar asignatura {0}.", id);
        return RestResponse.ResponseBuilder.ok(servicioAsignatura.eliminar(id)).build();

    }

}
