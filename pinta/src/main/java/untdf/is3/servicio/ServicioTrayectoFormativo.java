package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoTrayectoFormativo;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.TrayectoFormativo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTrayectoFormativo;
import untdf.is3.transferible.TransferibleCrearTrayectoFormativo;
import untdf.is3.transformador.TransformadorTrayectoFormativo;

import java.util.List;

@RequestScoped
public class ServicioTrayectoFormativo {

    @Inject
    Logger auditoria;

    @Inject
    AccesoTrayectoFormativo accesoTrayectoFormativo;

    @Inject
    TransformadorTrayectoFormativo transformadorTrayectoFormativo;

    public List<TransferibleTrayectoFormativo> obtenerTodas(){

        auditoria.debug("Intentando obtener todos los Trayectos Formativos.");
        return transformadorTrayectoFormativo.entidadATransferible(accesoTrayectoFormativo.obtenerTodas());

    }

    public TransferibleTrayectoFormativo obtener(int id) {

        auditoria.debugv("Intentando obtener Trayecto Formativo {0}.", id);
        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTrayectoFormativo.entidadATransferible(trayectoFormativo);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTrayectoFormativo crear(TransferibleCrearTrayectoFormativo transferibleCrearTrayectoFormativo) {

        auditoria.debug("Intentando crear nuevo Trayecto Formativo");
        TrayectoFormativo trayectoFormativo =
                new TrayectoFormativo(transferibleCrearTrayectoFormativo.getSigla(),
                        transferibleCrearTrayectoFormativo.getNombre(),
                        transferibleCrearTrayectoFormativo.getHorasMinimas());

        try {
            accesoTrayectoFormativo.persistir(trayectoFormativo)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Trayecto Formativo"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Trayecto Formativo no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Trayecto Formativo no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTrayectoFormativo.entidadATransferible(trayectoFormativo);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTrayectoFormativo modificar(int id, @Valid TransferibleTrayectoFormativo transferibleTrayectoFormativo) {

        auditoria.debugv("Intentando modificar Trayecto Formativo {0}", id);
        TrayectoFormativo asignatura = accesoTrayectoFormativo.obtener(transferibleTrayectoFormativo.getTrayectoFormativoId())
                .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        auditoria.debugv("Modificando Trayecto Formativo {0}", asignatura.getTrayectoFormativoId());
        transformadorTrayectoFormativo.modificar(asignatura, transferibleTrayectoFormativo);

        try {
            accesoTrayectoFormativo.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Trayecto Formativo"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Trayecto Formativo no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Trayecto Formativo no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTrayectoFormativo.entidadATransferible(asignatura);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTrayectoFormativo eliminar(int id) {

        auditoria.debugv("Intentando obtener Trayecto Formativo {0}.", id);
        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        auditoria.debugv("Intentando obtener Trayecto Formativo {0}.", trayectoFormativo.getTrayectoFormativoId());
        if(!accesoTrayectoFormativo.eliminar(trayectoFormativo)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Trayecto Formativo.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorTrayectoFormativo.entidadATransferible(trayectoFormativo);

    }

}
