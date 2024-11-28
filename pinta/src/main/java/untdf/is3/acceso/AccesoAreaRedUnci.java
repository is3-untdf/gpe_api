package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.AreaRedUnci;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoAreaRedUnci implements PanacheRepositoryBase<AreaRedUnci, Integer> {

    @Inject
    Logger auditoria;

    public List<AreaRedUnci> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las Áreas Red UNCI desde la DB.");
        return findAll(Sort.ascending("areaRedUnciId")).list();

    }

    public Optional<AreaRedUnci> obtener(int id) {

        auditoria.debugv("Intentando obtener Área Red UNCI {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<AreaRedUnci> persistir(AreaRedUnci areaRedUnci) {

        auditoria.debug("Intentando crear nuevo Área Red UNCI en la DB.");
        persist(areaRedUnci);
        return findByIdOptional(areaRedUnci.getAreaRedUnciId());

    }

    public boolean eliminar(AreaRedUnci areaRedUnci) {

        auditoria.warnf("Intentando eliminar Área Red UNCI {0} en la DB.", areaRedUnci.getAreaRedUnciId());
        return deleteById(areaRedUnci.getAreaRedUnciId());

    }

}
