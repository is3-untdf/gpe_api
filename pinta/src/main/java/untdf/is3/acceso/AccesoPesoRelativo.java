package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.PesoRelativo;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoPesoRelativo implements PanacheRepositoryBase<PesoRelativo, Integer> {

    @Inject
    Logger auditoria;

    public List<PesoRelativo> obtenerTodas(){

        auditoria.debug("Intentando obtener todos los Pesos Relativos desde la DB.");
        return findAll(Sort.ascending("pesoRelativoId")).list();

    }

    public Optional<PesoRelativo> obtener(int id) {

        auditoria.debugv("Intentando obtener Peso Relativo {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<PesoRelativo> persistir(PesoRelativo pesoRelativo) {

        auditoria.debug("Intentando crear nuevo Peso Relativo en la DB.");
        persist(pesoRelativo);
        return findByIdOptional(pesoRelativo.getPesoRelativoId());

    }

    public boolean eliminar(PesoRelativo pesoRelativo) {

        auditoria.warnf("Intentando eliminar Peso Relativo {0} en la DB.", pesoRelativo.getPesoRelativoId());
        return deleteById(pesoRelativo.getPesoRelativoId());

    }

}
