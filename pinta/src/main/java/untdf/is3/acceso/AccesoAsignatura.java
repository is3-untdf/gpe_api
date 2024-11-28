package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Table;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.modelo.Asignatura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Esta clase debe implementar esta interfaz para saber que entidad del modelo
// debe ser capaz de mapear desde la BD (a traves de Hibernate, el ORM)
@RequestScoped // Solo existe en memoria durante la petición
public class AccesoAsignatura implements PanacheRepositoryBase<Asignatura, Integer> {

    @ConfigProperty(name = "file.system.directorio")
    String directorioArchivosCSV;

    @Inject // Injectar (¿importar dinamicamente?)
    Logger auditoria; // Clase manejadora de logs

    public List<Asignatura> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las asignaturas desde la DB.");
        return findAll(Sort.ascending("asignaturaId")).list(); // retorno de la DB a lista

    }

    public Optional<Asignatura> obtener(int id) {

        auditoria.debugv("Intentando obtener asignatura {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<Asignatura> persistir(Asignatura asignatura) {

        auditoria.debug("Intentando crear nueva asignatura en la DB.");
        persist(asignatura);
        return findByIdOptional(asignatura.getAsignaturaId());

    }

    public boolean eliminar(Asignatura asignatura) {

        auditoria.warnf("Intentando eliminar asignatura {0} en la DB.", asignatura.getAsignaturaId());
        return deleteById(asignatura.getAsignaturaId());

    }

}
