package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoActividadProfesionalReservada;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.ActividadProfesionalReservada;
import untdf.is3.modelo.Intensidad;
import untdf.is3.transferible.TransferibleActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleCrearActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transformador.TransformadorActividadProfesionalReservada;

import java.util.List;

@RequestScoped
public class ServicioActividadProfesionalReservada {

    @Inject
    Logger auditoria;

    @Inject
    AccesoActividadProfesionalReservada accesoActividadProfesionalReservada;

    @Inject
    TransformadorActividadProfesionalReservada transformadorActividadProfesionalReservada;

    public List<TransferibleActividadProfesionalReservada> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las Actividades Profesionales Reservadas.");
        return transformadorActividadProfesionalReservada.entidadATransferible(accesoActividadProfesionalReservada.obtenerTodas());

    }

    public TransferibleActividadProfesionalReservada obtener(int id) {

        auditoria.debugv("Intentando obtener Actividad Profesional Reservada {0}.", id);
        ActividadProfesionalReservada actividadProfesionalReservada = accesoActividadProfesionalReservada.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Actividad Profesional Reservada no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservada.entidadATransferible(actividadProfesionalReservada);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleActividadProfesionalReservada crear(TransferibleCrearActividadProfesionalReservada transferibleCrearActividadProfesionalReservada) {

        auditoria.debug("Intentando crear nueva actividadProfesionalReservada");
        ActividadProfesionalReservada actividadProfesionalReservada =
                new ActividadProfesionalReservada(
                        transferibleCrearActividadProfesionalReservada.getCodigo(),
                        transferibleCrearActividadProfesionalReservada.getSigla(),
                        transferibleCrearActividadProfesionalReservada.getDescripcion());

        try {
            accesoActividadProfesionalReservada.persistir(actividadProfesionalReservada)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Actividad Profesional Reservada"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Actividad Profesional Reservada no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Actividad Profesional Reservada no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservada.entidadATransferible(actividadProfesionalReservada);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleActividadProfesionalReservada modificar(int id, @Valid TransferibleActividadProfesionalReservada transferibleActividadProfesionalReservada) {

        auditoria.debugv("Intentando modificar Actividad Profesional Reservada {0}", id);
        ActividadProfesionalReservada asignatura = accesoActividadProfesionalReservada.obtener(transferibleActividadProfesionalReservada.getActividadProfesionalReservadaId())
                .orElseThrow(()-> new HTTPNoContentException("Actividad Profesional Reservada no existe."));

        auditoria.debugv("Modificando Actividad Profesional Reservada {0}", asignatura.getActividadProfesionalReservadaId());
        transformadorActividadProfesionalReservada.modificar(asignatura, transferibleActividadProfesionalReservada);

        try {
            accesoActividadProfesionalReservada.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Actividad Profesional Reservada"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Actividad Profesional Reservada no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Actividad Profesional Reservada no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservada.entidadATransferible(asignatura);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleActividadProfesionalReservada eliminar(int id) {

        auditoria.debugv("Intentando obtener Actividad Profesional Reservada {0}.", id);
        ActividadProfesionalReservada actividadProfesionalReservada = accesoActividadProfesionalReservada.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Actividad Profesional Reservada no existe."));

        auditoria.debugv("Intentando obtener Actividad Profesional Reservada {0}.", actividadProfesionalReservada.getActividadProfesionalReservadaId());
        if(!accesoActividadProfesionalReservada.eliminar(actividadProfesionalReservada)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Actividad Profesional Reservada.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservada.entidadATransferible(actividadProfesionalReservada);

    }

}
