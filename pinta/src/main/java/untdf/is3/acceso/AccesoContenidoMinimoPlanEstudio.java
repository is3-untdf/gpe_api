package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.ContenidoMinimoPlanEstudio;
import untdf.is3.modelo.ContenidoMinimoPlanEstudio;

import java.util.List;
import java.util.Optional;

@RequestScoped 
public class AccesoContenidoMinimoPlanEstudio implements PanacheRepositoryBase<ContenidoMinimoPlanEstudio, Integer> {

    @Inject 
    Logger auditoria; 

    public List<ContenidoMinimoPlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los Contenidos Mínimos de Plan Estudio desde la DB.");
        return findAll(Sort.ascending("contenidoMinimoPlanEstudioId")).list();

    }

    public Optional<ContenidoMinimoPlanEstudio> obtener(int id) {

        auditoria.debugv("Intentando obtener contenido Mínimo de Plan Estudio {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<ContenidoMinimoPlanEstudio> persistir(ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio) {

        auditoria.debug("Intentando crear nuevo Contenido Mínimo Plan Estudio en la DB.");
        persist(contenidoMinimoPlanEstudio);
        return findByIdOptional(contenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());

    }

    public boolean eliminar(ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio) {

        auditoria.warnf("Intentando eliminar Contenido Mínimo Plan de Estudio {0} en la DB.", contenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());
        return deleteById(contenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());

    }

}
