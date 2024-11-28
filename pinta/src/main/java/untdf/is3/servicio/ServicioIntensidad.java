package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoIntensidad;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.PlanEstudio;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleCrearIntensidad;
import untdf.is3.transferible.TransferiblePlanEstudio;
import untdf.is3.transformador.TransformadorIntensidad;

import java.util.List;

@RequestScoped
public class ServicioIntensidad {

    @Inject
    Logger auditoria;

    @Inject
    AccesoIntensidad accesoIntensidad;

    @Inject
    TransformadorIntensidad transformadorIntensidad;

    public List<TransferibleIntensidad> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las intensidades.");
        return transformadorIntensidad.entidadATransferible(accesoIntensidad.obtenerTodas());

    }

    public TransferibleIntensidad obtener(int id) {

        auditoria.debugv("Intentando obtener intensidad {0}.", id);
        Intensidad intensidad = accesoIntensidad.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorIntensidad.entidadATransferible(intensidad);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleIntensidad crear(TransferibleCrearIntensidad transferibleCrearIntensidad) {

        auditoria.debug("Intentando crear nueva Intensidad");
        Intensidad intensidad = new Intensidad(transferibleCrearIntensidad.getNivel(),
                transferibleCrearIntensidad.getDescripcion());

        try {
            accesoIntensidad.persistir(intensidad)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Intensidad"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Intensidad no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Intensidad no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorIntensidad.entidadATransferible(intensidad);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleIntensidad modificar(int id, @Valid TransferibleIntensidad transferibleIntensidad) {

        auditoria.debugv("Intentando modificar Intensidad {0}", id);
        Intensidad asignatura = accesoIntensidad.obtener(transferibleIntensidad.getIntensidadId())
                .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));

        auditoria.debugv("Modificando Intensidad {0}", asignatura.getIntensidadId());
        transformadorIntensidad.modificar(asignatura, transferibleIntensidad);

        try {
            accesoIntensidad.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Intensidad"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Intensidad no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Intensidad no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorIntensidad.entidadATransferible(asignatura);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleIntensidad eliminar(int id) {

        auditoria.debugv("Intentando obtener intensidad {0}.", id);
        Intensidad intensidad = accesoIntensidad.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));

        auditoria.debugv("Intentando eliminar intensidad {0}.", intensidad.getIntensidadId());
        if(!accesoIntensidad.eliminar(intensidad)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Intensidad.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorIntensidad.entidadATransferible(intensidad);

    }

}
