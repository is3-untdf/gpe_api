package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.ContenidoCurricularBasico;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoContenidoCurricularBasico implements PanacheRepositoryBase<ContenidoCurricularBasico, Integer> {

    @Inject
    Logger auditoria;

    public List<ContenidoCurricularBasico> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los Contenidos Curriculares Básicos desde la DB.");
        return findAll(Sort.ascending("contenidoCurricularBasicoId")).list();

    }

    public Optional<ContenidoCurricularBasico> obtener(int id) {

        auditoria.debugv("Intentando obtener Contenido Curricular Básico {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<ContenidoCurricularBasico> persistir(ContenidoCurricularBasico contenidoCurricularBasico) {

        auditoria.debug("Intentando crear nuevo Contenido Curricular Básico en la DB.");
        persist(contenidoCurricularBasico);
        return findByIdOptional(contenidoCurricularBasico.getContenidoCurricularBasicoId());

    }

    public boolean eliminar(ContenidoCurricularBasico contenidoCurricularBasico) {

        auditoria.warnf("Intentando eliminar Contenido Curricular Básico {0} en la DB.", contenidoCurricularBasico.getContenidoCurricularBasicoId());
        return deleteById(contenidoCurricularBasico.getContenidoCurricularBasicoId());

    }

}
