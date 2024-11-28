package untdf.is3.utilidad;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import untdf.is3.excepcion.HTTPInternalServerErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestScoped
public class ExportadorCSV {

    @PersistenceContext
    EntityManager entityManager;  // Inject the EntityManager explicitly

    @ConfigProperty(name = "file.system.directorio")
    String directorioArchivosCSV;

    @Inject
    Logger auditoria;

    // Metodo generico para exportar CSV de una entidad
    public <T> String[] obtenerTodasCSV(Class<T> entityClass) {

        // yyyyMMddHHmmss
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // NombreEntidadyyyyMMddHHmmss.csv
        String nombreArchivo = entityClass.getSimpleName().concat(timestamp).concat(".csv");

        String rutaArchivo =
                directorioArchivosCSV.concat(nombreArchivo);

        // Obtener el nombre de la tabla a partir de la anotación @Table del Modelo
        String nombreTabla = getTableName(entityClass);

        // Ejecutar la función de exportar con el nombre de la tabla y la ruta (se exporta al directorio montado)
        Object resultado = entityManager.createNativeQuery("select pinta.exportar_csv" +
                        "(:nombreTabla, " +
                        ":nombreArchivo)")
                .setParameter("nombreTabla", nombreTabla)
                .setParameter("nombreArchivo", rutaArchivo)
                .getSingleResult();

        // Verificar el resultado e informar si se ha producido un error
        if (resultado == null || !(Boolean) resultado) {
            auditoria.debugv("Problemas al generar archivo .csv para {0}.", entityClass.getSimpleName());
            throw new HTTPInternalServerErrorException("Problemas al generar archivo .csv para " + entityClass.getSimpleName());
        }

        // Intentar leer el archivo (desde el directorio montado)
        try {
            auditoria.debugv("Intentando leer archivo .csv generado por la DB.");
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            return  new String[]{nombreArchivo, contenido};
        } catch (IOException e) {
            auditoria.debugv("Problemas al leer el archivo .csv generado para {0}.", entityClass.getSimpleName());
            throw new HTTPInternalServerErrorException("Problemas al leer el archivo .csv generado para " + entityClass.getSimpleName());
        }
    }

    // Metodo generico para exportar CSV de una entidad
    public <T> String[] exportarReporte(String nombreConsulta) {

        // yyyyMMddHHmmss
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // NombreEntidadyyyyMMddHHmmss.csv
        String nombreArchivo = nombreConsulta.concat(timestamp).concat(".csv");

        String rutaArchivo =
                directorioArchivosCSV.concat(nombreArchivo);

        // Ejecutar la función de exportar con el nombre de la tabla y la ruta (se exporta al directorio montado)
        Object resultado = entityManager.createNativeQuery("select pinta.exportar_consulta_csv" +
                        "(:nombreArchivo)")
                .setParameter("nombreArchivo", rutaArchivo)
                .getSingleResult();

        // Verificar el resultado e informar si se ha producido un error
        if (resultado == null || !(Boolean) resultado) {
            auditoria.debugv("Problemas al generar archivo .csv para {0}.", nombreConsulta);
            throw new HTTPInternalServerErrorException("Problemas al generar archivo .csv para " + nombreConsulta);
        }

        // Intentar leer el archivo (desde el directorio montado)
        try {
            auditoria.debugv("Intentando leer archivo .csv generado por la DB.");
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            return  new String[]{nombreArchivo, contenido};
        } catch (IOException e) {
            auditoria.debugv("Problemas al leer el archivo .csv generado para {0}.", nombreConsulta);
            throw new HTTPInternalServerErrorException("Problemas al leer el archivo .csv generado para " + nombreConsulta);
        }
    }

    // Metodo auxiliar para obtener el nombre de la tabla
    private <T> String getTableName(Class<T> entityClass) {
        // Buscar el nombre de la tabla a traves de la anotacion @Table
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            return tableAnnotation.name(); //
        } else {
            // Si no se encuentra definido en la anotacion, se retorna el nombre de la clase
            return entityClass.getSimpleName();
        }
    }
}
