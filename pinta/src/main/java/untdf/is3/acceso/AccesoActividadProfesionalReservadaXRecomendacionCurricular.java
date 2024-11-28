package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.ActividadProfesionalReservadaXRecomendacionCurricular;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoActividadProfesionalReservadaXRecomendacionCurricular implements PanacheRepositoryBase<ActividadProfesionalReservadaXRecomendacionCurricular, Integer> {

    @Inject
    Logger auditoria;

    public List<ActividadProfesionalReservadaXRecomendacionCurricular> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones desde la DB.");
        return findAll(Sort.ascending("actividadProfesionalReservadaXRecomendacionCurricularId")).list();

    }

    public Optional<ActividadProfesionalReservadaXRecomendacionCurricular> obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<ActividadProfesionalReservadaXRecomendacionCurricular> persistir(ActividadProfesionalReservadaXRecomendacionCurricular actividadProfesionalReservadaXRecomendacionCurricular) {

        auditoria.debug("Intentando crear nueva relación en la DB.");
        persist(actividadProfesionalReservadaXRecomendacionCurricular);
        return findByIdOptional(actividadProfesionalReservadaXRecomendacionCurricular.getActividadProfesionalReservadaXRecomendacionCurricularId());

    }

    public boolean eliminar(ActividadProfesionalReservadaXRecomendacionCurricular actividadProfesionalReservadaXRecomendacionCurricular) {

        auditoria.warnf("Intentando eliminar relación {0} en la DB.",
                actividadProfesionalReservadaXRecomendacionCurricular.getActividadProfesionalReservadaXRecomendacionCurricularId());
        return deleteById(actividadProfesionalReservadaXRecomendacionCurricular.getActividadProfesionalReservadaXRecomendacionCurricularId());

    }

}
