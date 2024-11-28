package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoTitulo;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.Titulo;
import untdf.is3.transferible.TransferibleCrearTitulo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTitulo;
import untdf.is3.transformador.TransformadorTitulo;

import java.util.List;

@RequestScoped
public class ServicioTitulo {

    @Inject
    Logger auditoria;

    @Inject
    AccesoTitulo accesoTitulo;

    @Inject
    TransformadorTitulo transformadorTitulo;

    public List<TransferibleTitulo> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los Títulos.");
        return transformadorTitulo.entidadATransferible(accesoTitulo.obtenerTodos());

    }

    public TransferibleTitulo obtener(int id) {

        auditoria.debugv("Intentando obtener Título {0}.", id);
        Titulo titulo = accesoTitulo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Título no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTitulo.entidadATransferible(titulo);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTitulo crear(TransferibleCrearTitulo transferibleCrearTitulo) {

        auditoria.debug("Intentando crear nuevo Título");
        Titulo titulo = new Titulo(transferibleCrearTitulo.getNombre());

        try {
            accesoTitulo.persistir(titulo)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Título."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Título no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Título no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTitulo.entidadATransferible(titulo);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTitulo modificar(int id, @Valid TransferibleTitulo transferibleTitulo) {

        auditoria.debugv("Intentando modificar Título {0}", id);
        Titulo asignatura = accesoTitulo.obtener(transferibleTitulo.getTituloId())
                .orElseThrow(()-> new HTTPNoContentException("Título no existe."));

        auditoria.debugv("Modificando Título {0}", asignatura.getTituloId());
        transformadorTitulo.modificar(asignatura, transferibleTitulo);

        try {
            accesoTitulo.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Título"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Titulo no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Titulo no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTitulo.entidadATransferible(asignatura);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTitulo eliminar(int id) {

        auditoria.debugv("Intentando obtener Título {0}.", id);
        Titulo titulo = accesoTitulo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Título no existe."));

        auditoria.debugv("Intentando obtener Título {0}.", titulo.getTituloId());
        if(!accesoTitulo.eliminar(titulo)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Título.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTitulo.entidadATransferible(titulo);

    }

}
