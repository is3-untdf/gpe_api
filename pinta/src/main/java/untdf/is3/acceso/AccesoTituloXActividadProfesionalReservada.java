package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.Titulo;
import untdf.is3.modelo.TituloXActividadProfesionalReservada;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoTituloXActividadProfesionalReservada implements PanacheRepositoryBase<TituloXActividadProfesionalReservada, Integer> {

    @Inject
    Logger auditoria;

    public List<TituloXActividadProfesionalReservada> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones desde la DB.");
        return findAll(Sort.ascending("tituloXActividadProfesionalReservadaId")).list();

    }

    public Optional<TituloXActividadProfesionalReservada> obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<TituloXActividadProfesionalReservada> persistir(TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada) {

        auditoria.debug("Intentando crear nueva relación en la DB.");
        persist(tituloXActividadProfesionalReservada);
        return findByIdOptional(tituloXActividadProfesionalReservada.getTituloXActividadProfesionalReservadaId());

    }

    public boolean eliminar(TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada) {

        auditoria.warnf("Intentando eliminar relación {0} en la DB.",
                tituloXActividadProfesionalReservada.getActividadProfesionalReservadaId());
        return deleteById(tituloXActividadProfesionalReservada.getTituloXActividadProfesionalReservadaId());

    }

}
