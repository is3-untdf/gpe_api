package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.ContenidoMinimoPlanEstudio;
import untdf.is3.modelo.RecomendacionCurricular;
import untdf.is3.modelo.RecomendacionCurricularXContenidoMinimoPlanEstudio;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoRecomendacionCurricularXContenidoMinimoPlanEstudio implements PanacheRepositoryBase<RecomendacionCurricularXContenidoMinimoPlanEstudio, Integer> {

    @Inject
    Logger auditoria;

    public List<RecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones desde la DB.");
        return findAll(Sort.ascending("recomendacionCurricularXContenidoMinimoPlanEstudioId")).list();

    }

    public Optional<RecomendacionCurricularXContenidoMinimoPlanEstudio> obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public List<RecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerPorRecomendacionCurricular(RecomendacionCurricular id) {

        auditoria.debugv("Intentando obtener por Recomendación Curricular {0} desde la DB.", id);
        return find("recomendacionCurricularId", id).list();

    }

    public List<RecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerPorContenidoMinimo(ContenidoMinimoPlanEstudio id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo Plan de Estudio {0} desde la DB.", id);
        return find("contenidoMinimoPlanEstudioId", id).list();

    }

    public Optional<RecomendacionCurricularXContenidoMinimoPlanEstudio> persistir(RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio) {

        auditoria.debug("Intentando crear nueva relación en la DB.");
        persist(recomendacionCurricularXContenidoMinimoPlanEstudio);
        return findByIdOptional(recomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());

    }

    public boolean eliminar(RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio) {

        auditoria.warnf("Intentando eliminar relación {0} en la DB.",
                recomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());
        return deleteById(recomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());

    }

}
