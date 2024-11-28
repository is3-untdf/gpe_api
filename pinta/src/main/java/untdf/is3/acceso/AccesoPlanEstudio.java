package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.jboss.logging.Logger;
import untdf.is3.modelo.PlanEstudio;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoPlanEstudio implements PanacheRepositoryBase<PlanEstudio, Integer> {

    @Inject
    Logger auditoria;

    @Inject
    EntityManager entityManager;

    public List<PlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los planes de estudio desde la DB.");
        return findAll(Sort.ascending("planEstudioId")).list();

    }

    public Optional<PlanEstudio> obtener(int id) {

        auditoria.debugv("Intentando obtener plan de estudio {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<PlanEstudio> persistir(PlanEstudio planEstudio) {

        auditoria.debug("Intentando crear nuevo plan de estudios en la DB.");
        persist(planEstudio);
        return findByIdOptional(planEstudio.getPlanEstudioId());

    }

    public boolean eliminar(PlanEstudio planEstudio) {

        auditoria.warnf("Intentando eliminar plan de estudio {0} en la DB.", planEstudio.getPlanEstudioId());
        return deleteById(planEstudio.getPlanEstudioId());

    }

    public void clonar(PlanEstudio viejoPlanEstudio, PlanEstudio nuevoPlanEstudio) {
        entityManager.createNativeQuery("CALL pinta.clonar_plan_estudio(:viejoPlanEstudioId, :nuevoPlanEstudioId)")
                .setParameter("viejoPlanEstudioId", viejoPlanEstudio.getPlanEstudioId())
                .setParameter("nuevoPlanEstudioId", nuevoPlanEstudio.getPlanEstudioId())
                .executeUpdate();
    }



}
