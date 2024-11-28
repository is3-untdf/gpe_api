package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.RecomendacionCurricular;

import java.util.List;
import java.util.Optional;

@RequestScoped 
public class AccesoRecomendacionCurricular implements PanacheRepositoryBase<RecomendacionCurricular, Integer> {

    @Inject 
    Logger auditoria; 

    public List<RecomendacionCurricular> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las Recomendaciones Curriculares desde la DB.");
        return findAll(Sort.ascending("recomendacionCurricularId")).list();

    }

    public Optional<RecomendacionCurricular> obtener(int id) {

        auditoria.debugv("Intentando obtener Recomendación Curricular {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<RecomendacionCurricular> persistir(RecomendacionCurricular recomendacionCurricular) {

        auditoria.debug("Intentando crear nueva Recomendación Curricular en la DB.");
        persist(recomendacionCurricular);
        return findByIdOptional(recomendacionCurricular.getRecomendacionCurricularId());

    }

    public boolean eliminar(RecomendacionCurricular recomendacionCurricular) {

        auditoria.warnf("Intentando eliminar Recomendación Curricular {0} en la DB.", recomendacionCurricular.getRecomendacionCurricularId());
        return deleteById(recomendacionCurricular.getRecomendacionCurricularId());

    }

}
