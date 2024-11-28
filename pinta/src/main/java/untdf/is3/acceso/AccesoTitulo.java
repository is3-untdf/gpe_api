package untdf.is3.acceso;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import untdf.is3.modelo.Titulo;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoTitulo implements PanacheRepositoryBase<Titulo, Integer> {

    @Inject
    Logger auditoria;

    public List<Titulo> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los títulos desde la DB.");
        return findAll(Sort.ascending("tituloId")).list();

    }

    public Optional<Titulo> obtener(int id) {

        auditoria.debugv("Intentando obtener titulo {0} desde la DB.", id);
        return findByIdOptional(id);

    }

    public Optional<Titulo> persistir(Titulo titulo) {

        auditoria.debug("Intentando crear nuevo titulo en la DB.");
        persist(titulo);
        return findByIdOptional(titulo.getTituloId());

    }

    public boolean eliminar(Titulo titulo) {

        auditoria.warnf("Intentando eliminar titulo {0} en la DB.", titulo.getTituloId());
        return deleteById(titulo.getTituloId());

    }

}
