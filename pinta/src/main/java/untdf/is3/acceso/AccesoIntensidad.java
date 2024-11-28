package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.Intensidad;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoIntensidad implements PanacheRepositoryBase<Intensidad, Integer> {

    @Inject
    Logger auditoria;

    public List<Intensidad> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las intensidades desde la DB.");
        return findAll(Sort.ascending("intensidadId")).list();

    }

    public Optional<Intensidad> obtener(int id) {

        auditoria.debugv("Intentando obtener intensidad {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<Intensidad> persistir(Intensidad intensidad) {

        auditoria.debug("Intentando crear nueva intensidad en la DB.");
        persist(intensidad);
        return findByIdOptional(intensidad.getIntensidadId());

    }

    public boolean eliminar(Intensidad intensidad) {

        auditoria.warnf("Intentando eliminar intensidad {0} en la DB.", intensidad.getIntensidadId());
        return deleteById(intensidad.getIntensidadId());

    }

}
