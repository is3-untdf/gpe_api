package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.TrayectoFormativo;

import java.util.List;
import java.util.Optional;

@RequestScoped 
public class AccesoTrayectoFormativo implements PanacheRepositoryBase<TrayectoFormativo, Integer> {

    @Inject 
    Logger auditoria; 

    public List<TrayectoFormativo> obtenerTodas(){

        auditoria.debug("Intentando obtener todos los Trayectos Formativos desde la DB.");
        return findAll(Sort.ascending("trayectoFormativoId")).list();

    }

    public Optional<TrayectoFormativo> obtener(int id) {

        auditoria.debugv("Intentando obtener Trayecto Formativo {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<TrayectoFormativo> persistir(TrayectoFormativo trayectoFormativo) {

        auditoria.debug("Intentando crear nueva Trayecto Formativo en la DB.");
        persist(trayectoFormativo);
        return findByIdOptional(trayectoFormativo.getTrayectoFormativoId());

    }

    public boolean eliminar(TrayectoFormativo trayectoFormativo) {

        auditoria.warnf("Intentando eliminar Trayecto Formativo {0} en la DB.", trayectoFormativo.getTrayectoFormativoId());
        return deleteById(trayectoFormativo.getTrayectoFormativoId());

    }

}
