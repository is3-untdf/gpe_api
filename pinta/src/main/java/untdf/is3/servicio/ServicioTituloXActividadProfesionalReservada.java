package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoActividadProfesionalReservada;
import untdf.is3.acceso.AccesoTitulo;
import untdf.is3.acceso.AccesoTituloXActividadProfesionalReservada;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.ActividadProfesionalReservada;
import untdf.is3.modelo.Titulo;
import untdf.is3.modelo.TituloXActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleCrearTituloXActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleTituloXActividadProfesionalReservada;
import untdf.is3.transformador.TransformadorTituloXActividadProfesionalReservada;

import java.util.List;

@RequestScoped
public class ServicioTituloXActividadProfesionalReservada {

    @Inject
    Logger auditoria;

    @Inject
    AccesoTituloXActividadProfesionalReservada accesoTituloXActividadProfesionalReservada;

    @Inject
    AccesoTitulo accesoTitulo;

    @Inject
    AccesoActividadProfesionalReservada accesoActividadProfesionalReservada;

    @Inject
    TransformadorTituloXActividadProfesionalReservada transformadorTituloXActividadProfesionalReservada;

    public List<TransferibleTituloXActividadProfesionalReservada> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones.");
        return transformadorTituloXActividadProfesionalReservada.entidadATransferible(accesoTituloXActividadProfesionalReservada.obtenerTodos());

    }

    public TransferibleTituloXActividadProfesionalReservada obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada = accesoTituloXActividadProfesionalReservada.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTituloXActividadProfesionalReservada.entidadATransferible(tituloXActividadProfesionalReservada);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTituloXActividadProfesionalReservada crear(TransferibleCrearTituloXActividadProfesionalReservada transferibleCrearTituloXActividadProfesionalReservada) {

        auditoria.debug("Intentando crear nuevo relación");
        Titulo titulo = accesoTitulo.obtener(transferibleCrearTituloXActividadProfesionalReservada.getTituloId())
                .orElseThrow(()-> new HTTPNoContentException("Título no existe."));

        ActividadProfesionalReservada actividadProfesionalReservada =
                accesoActividadProfesionalReservada.obtener(
                        transferibleCrearTituloXActividadProfesionalReservada.getActividadProfesionalReservadaId())
                        .orElseThrow(()-> new HTTPNoContentException("Actividad Profesional Reservada no existe."));

        TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada =
                new TituloXActividadProfesionalReservada(titulo, actividadProfesionalReservada);

        try {
            accesoTituloXActividadProfesionalReservada.persistir(tituloXActividadProfesionalReservada)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear relación."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Relación no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Relación no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTituloXActividadProfesionalReservada.entidadATransferible(tituloXActividadProfesionalReservada);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTituloXActividadProfesionalReservada eliminar(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada =
                accesoTituloXActividadProfesionalReservada.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debugv("Intentando obtener relación {0}.", tituloXActividadProfesionalReservada.getTituloXActividadProfesionalReservadaId());
        if(!accesoTituloXActividadProfesionalReservada.eliminar(tituloXActividadProfesionalReservada)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar relación.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTituloXActividadProfesionalReservada.entidadATransferible(tituloXActividadProfesionalReservada);

    }

}
