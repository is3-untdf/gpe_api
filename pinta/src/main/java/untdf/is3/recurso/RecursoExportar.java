package untdf.is3.recurso;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.modelo.*;
import untdf.is3.servicio.ServicioAsignatura;
import untdf.is3.servicio.ServicioContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudioConDependencias;
import untdf.is3.transferible.TransferibleExcepcion;
import untdf.is3.utilidad.ExportadorCSV;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
@Tag(name = "Exportar") // Etiqueta de identificación en Swagger
@RequestScoped
@Path("exportar") // uri --> host:puerto/path
public class RecursoExportar {

    private static final String RUTA_BASE_RECURSO_CSV = "/exportar/csv/";
//    private static final String RUTA_BASE_RECURSO_ASIGNATURAS = "/reportes/asignaturas/";
//    private static final String RUTA_BASE_RECURSO_CONTENIDOS_MINIMOS = "/reportes/contenidos-minimos/";
//    private static final String RUTA_BASE_RECURSO_CONTENIDOS_PLANES_ESTUDIO = "/reportes/planes-estudio/";
//
    @Inject
    Logger auditoria;
//
//    @Inject
//    Template menu;
//
//    @Inject
//    Template asignaturas;
//
//    @Inject
//    Template asignatura;
//
//    @Inject
//    Template asignaturasYEstadisticas;
//
//    @Inject
//    Template contenidosMinimos;
//
//    @Inject
//    Template csv;
//
//    @Inject
//    ServicioAsignatura servicioAsignatura;
//
//    @Inject
//    ServicioContenidoMinimoPlanEstudio servicioContenidoMinimoPlanEstudio;
//
    @Inject
    ExportadorCSV exportadorCSV;
//
//    @Deprecated
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    @Consumes(MediaType.TEXT_HTML)
//    @Transactional(Transactional.TxType.REQUIRED)
//    public TemplateInstance index(){
//
//        return menu.data("Algo", "ALGOOGOGOOGG");
//
//    }
//
//    @Deprecated
//    @GET
//    @Path("contenidos-minimos")
//    @Produces(MediaType.TEXT_HTML)
//    @Transactional(Transactional.TxType.REQUIRED)
//    public TemplateInstance getContenidosMinimos() {
//
//        List<TransferibleContenidoMinimoPlanEstudioConDependencias> listaContenidosMinimos =
//                servicioContenidoMinimoPlanEstudio.obtenerTodosConDependencias();
//
//        // Group by asignaturaId
//        Map<Integer, List<TransferibleContenidoMinimoPlanEstudioConDependencias>> agrupado = listaContenidosMinimos.stream()
//                .collect(Collectors.groupingBy(TransferibleContenidoMinimoPlanEstudioConDependencias::getAsignaturaId));
//
//        // Prepare data for template
//        List<Map<String, Object>> contenidosAgrupadosPorAsignatura = new ArrayList<>();
//
//        for (Map.Entry<Integer, List<TransferibleContenidoMinimoPlanEstudioConDependencias>> entry : agrupado.entrySet()) {
//            List<TransferibleContenidoMinimoPlanEstudioConDependencias> contenidos = entry.getValue();
//
//            // Calculate totals while handling nulls
//            int totalHorasTeoria = contenidos.stream()
//                    .mapToInt(contenido -> Optional.ofNullable(contenido.getHorasTeoria()).orElse(0))
//                    .sum();
//
//            int totalHorasPractica = contenidos.stream()
//                    .mapToInt(contenido -> Optional.ofNullable(contenido.getHorasPractica()).orElse(0))
//                    .sum();
//
//            // Add to the grouped list
//            Map<String, Object> grupo = new HashMap<>();
//            grupo.put("contenidos", contenidos);
//            grupo.put("totalHorasTeoria", totalHorasTeoria);
//            grupo.put("totalHorasPractica", totalHorasPractica);
//            grupo.put("asignaturaNombre", contenidos.get(0).getAsignatura().getNombre()); // Add subject name
//
//            contenidosAgrupadosPorAsignatura.add(grupo);
//        }
//
//        // Return the template with the required data
//        return contenidosMinimos.data("contenidosAgrupadosPorAsignatura", contenidosAgrupadosPorAsignatura)
//                .data("desde", "Respuesta REST con QUTE INTEGRADO")
//                .data("detalle", "Esta metodologia ES LA QUE VA no xige redireccion y permite inyectar servicios")
//                .data("rutaContenidos", URI.create(RUTA_BASE_RECURSO_CONTENIDOS_MINIMOS))
//                .data("rutaAsignaturas", URI.create(RUTA_BASE_RECURSO_ASIGNATURAS))
//                .data("rutaPlanesEstudio", URI.create(RUTA_BASE_RECURSO_CONTENIDOS_PLANES_ESTUDIO))
//                .data("planEstudioId", listaContenidosMinimos.get(0).getPlanEstudioId());
//    }
//
////    @GET
////    @Path("csv/asignaturas")
////    @Produces("text/csv")
////    public Response obtenerCSV() {
////
////        InputStream csvStream = new ByteArrayInputStream(servicioAsignatura.obtenerTodasCSV().getBytes(StandardCharsets.UTF_8));
////
////        return Response.ok(csvStream)
////                .header("Content-Disposition", "attachment; filename=\"asignaturas.csv\"")
////                .build();
////    }
//
//    @GET
//    @Path("csv")
//    @Transactional(Transactional.TxType.REQUIRED)
//    public TemplateInstance getCSVs() {
//
//        return csv.data("rutaBaseCSVs", RUTA_BASE_RECURSO_CSV);
//
//    }

    @GET
    @Path("csv/{entidad}")
    @Produces("text/csv")
    @APIResponse(responseCode = "200",
            description = "Exporte de CSV generado con éxito.")
    @APIResponse(responseCode = "500",
            description = "Problemas al exportar CSV.",
            content = @Content(schema = @Schema(implementation = TransferibleExcepcion.class)))
    public Response obtenerCSVGenerico(@PathParam("entidad") String entidad) {

        String[] archivo = {entidad, entidad};

        switch (entidad) {
            case "actividades-profesionales-reservadas" :
                archivo = exportadorCSV.obtenerTodasCSV(ActividadProfesionalReservada.class); break;
            case "asignaturas" :
                archivo = exportadorCSV.obtenerTodasCSV(Asignatura.class); break;
            case "contenidos-curriculares-basicos" :
                archivo =
                        exportadorCSV.obtenerTodasCSV(ContenidoCurricularBasico.class); break;
            case "contenido-minimos-de-planes-de-estudio" :
                archivo = exportadorCSV.obtenerTodasCSV(ContenidoMinimoPlanEstudio.class); break;
            case "intensidades" :
                archivo =
                        exportadorCSV.obtenerTodasCSV(Intensidad.class); break;
            case "pesos-relativos" :
                archivo = exportadorCSV.obtenerTodasCSV(PesoRelativo.class); break;
            case "planes-de-estudio" :
                archivo = exportadorCSV.obtenerTodasCSV(PlanEstudio.class); break;
            case "recomendacion-curricular" :
                archivo = exportadorCSV.obtenerTodasCSV(RecomendacionCurricular.class); break;
            case "trayecto-formativo" :
                archivo =
                        exportadorCSV.obtenerTodasCSV(TrayectoFormativo.class); break;
            case "titulos" :
                archivo = exportadorCSV.obtenerTodasCSV(Titulo.class); break;
            case "areas-red-unci" :
                archivo =
                        exportadorCSV.obtenerTodasCSV(AreaRedUnci.class); break;
            case "planes-de-estudio-general" :
                archivo =
                        exportadorCSV.exportarReporte("planes-de-estudio-general"); break;
            default: archivo[0] = entidad ; break;
        }

        if(archivo[0].equals(entidad)) {
            auditoria.debugv("La entidad {0} no existe.", entidad);
            throw new HTTPInternalServerErrorException("La entidad " + entidad + " no existe.");
        }

        InputStream csvStream = new ByteArrayInputStream(archivo[1].getBytes(StandardCharsets.UTF_8));

        return Response.ok(csvStream)
                .header("Content-Disposition", "attachment; filename=\"" + archivo[0] + "\"")
                .build();
    }

//    @Deprecated
//    @GET
//    @Path("asignaturas")
//    @Produces(MediaType.TEXT_HTML)
//    @Transactional(Transactional.TxType.REQUIRED)
//    //public TemplateInstance getAsignaturas(@QueryParam("carga-horaria") Integer cargaHoraria) {
//    public TemplateInstance getAsignaturas() {
//
//        return asignaturas.data("asignaturas", servicioAsignatura.obtenerTodas())
//                .data("ruta", URI.create(RUTA_BASE_RECURSO_ASIGNATURAS));
//
//    }
//
//    @Deprecated
//    @GET
//    @Path("asignaturas-estadisticas")
//    @Produces(MediaType.TEXT_HTML)
//    @Transactional(Transactional.TxType.REQUIRED)
//    //public TemplateInstance getAsignaturas(@QueryParam("carga-horaria") Integer cargaHoraria) {
//    public TemplateInstance getAsignaturasYEstadisticas() {
//
//        // Promedio
//        // Quartiles
//        // Porcentajes
//
//        return asignaturasYEstadisticas.data("asignaturas", servicioAsignatura.obtenerTodas())
//                .data("desde", "Respuesta REST con QUTE INTEGRADO").data("detalle", "Esta metodologia ES LA QUE VS, " +
//                        "no xige redireccion y permite inyectar servicios").data("ruta", URI.create(RUTA_BASE_RECURSO_ASIGNATURAS));
//
//    }
//
//    @Deprecated
//    @GET
//    @Path("asignaturas/{id}")
//    @Produces(MediaType.TEXT_HTML)
//    @Transactional(Transactional.TxType.REQUIRED)
//    //public TemplateInstance getAsignaturas(@QueryParam("carga-horaria") Integer cargaHoraria) {
//    public TemplateInstance getAsignatura(@PathParam("id") Integer id) {
//
//        return asignatura.data("asignatura", servicioAsignatura.obtener(id)).data("ruta", URI.create(RUTA_BASE_RECURSO_ASIGNATURAS));
//
//    }
//
//    @Deprecated
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    //public TemplateInstance getAsignaturas(@QueryParam("carga-horaria") Integer cargaHoraria) {
//    public Response generarRedireccion() {
//
//        return Response.temporaryRedirect(URI.create(RUTA_BASE_RECURSO_ASIGNATURAS.concat("html-asignaturas"))).build();
//
//    }
//
//    @Deprecated
//    @GET
//    @Path("html-asignaturas")
//    public Response asignaturasReporte() {
//
//        return Response.ok(asignaturas.data("desde", "Redireccion").data("asignaturas",
//                servicioAsignatura.obtenerTodas()).data("detalle",
//                "Se podría redireccionar cualquier petición get a la ruta y mostrarlo sin explicitar las peticiones " +
//                        "ni revelar los endpoints. Se devuelve un string (excelente)" +
//                        ".")).build();
//
//    }

}
