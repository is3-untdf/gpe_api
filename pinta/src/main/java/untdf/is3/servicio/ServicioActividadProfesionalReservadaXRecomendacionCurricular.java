package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoActividadProfesionalReservada;
import untdf.is3.acceso.AccesoRecomendacionCurricular;
import untdf.is3.acceso.AccesoActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.ActividadProfesionalReservada;
import untdf.is3.modelo.RecomendacionCurricular;
import untdf.is3.modelo.ActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transferible.TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transferible.TransferibleActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transformador.TransformadorActividadProfesionalReservadaXRecomendacionCurricular;

import java.util.List;

@RequestScoped
public class ServicioActividadProfesionalReservadaXRecomendacionCurricular {

    @Inject
    Logger auditoria;

    @Inject
    AccesoActividadProfesionalReservadaXRecomendacionCurricular accesoActividadProfesionalReservadaXRecomendacionCurricular;

    @Inject
    AccesoActividadProfesionalReservada accesoActividadProfesionalReservada;

    @Inject
    AccesoRecomendacionCurricular accesoRecomendacionCurricular;

    @Inject
    TransformadorActividadProfesionalReservadaXRecomendacionCurricular transformadorActividadProfesionalReservadaXRecomendacionCurricular;

    public List<TransferibleActividadProfesionalReservadaXRecomendacionCurricular> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones.");
        return transformadorActividadProfesionalReservadaXRecomendacionCurricular.entidadATransferible(accesoActividadProfesionalReservadaXRecomendacionCurricular.obtenerTodos());

    }

    public TransferibleActividadProfesionalReservadaXRecomendacionCurricular obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        ActividadProfesionalReservadaXRecomendacionCurricular actividadProfesionalReservadaXRecomendacionCurricular = accesoActividadProfesionalReservadaXRecomendacionCurricular.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservadaXRecomendacionCurricular.entidadATransferible(actividadProfesionalReservadaXRecomendacionCurricular);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleActividadProfesionalReservadaXRecomendacionCurricular crear(TransferibleCrearActividadProfesionalReservadaXRecomendacionCurricular transferibleCrearActividadProfesionalReservadaXRecomendacionCurricular) {

        auditoria.debug("Intentando crear nuevo relación");
        ActividadProfesionalReservada actividadProfesionalReservada =
                accesoActividadProfesionalReservada.obtener(transferibleCrearActividadProfesionalReservadaXRecomendacionCurricular.getActividadProfesionalReservadaId())
                        .orElseThrow(()-> new HTTPNoContentException("Actividad Profesional Reservada no existe."));

        RecomendacionCurricular recomendacionCurricular =
                accesoRecomendacionCurricular.obtener(transferibleCrearActividadProfesionalReservadaXRecomendacionCurricular.getRecomendacionCurricularId())
                .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular no existe."));

        ActividadProfesionalReservadaXRecomendacionCurricular
                actividadProfesionalReservadaXRecomendacionCurricular =
                new ActividadProfesionalReservadaXRecomendacionCurricular(
                        actividadProfesionalReservada, recomendacionCurricular);

        try {
            accesoActividadProfesionalReservadaXRecomendacionCurricular.persistir(actividadProfesionalReservadaXRecomendacionCurricular)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear relación."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Relación no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Relación no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservadaXRecomendacionCurricular.entidadATransferible(actividadProfesionalReservadaXRecomendacionCurricular);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleActividadProfesionalReservadaXRecomendacionCurricular eliminar(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        ActividadProfesionalReservadaXRecomendacionCurricular actividadProfesionalReservadaXRecomendacionCurricular =
                accesoActividadProfesionalReservadaXRecomendacionCurricular.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debugv("Intentando obtener relación {0}.", actividadProfesionalReservadaXRecomendacionCurricular.getActividadProfesionalReservadaXRecomendacionCurricularId());
        if(!accesoActividadProfesionalReservadaXRecomendacionCurricular.eliminar(actividadProfesionalReservadaXRecomendacionCurricular)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar relación.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorActividadProfesionalReservadaXRecomendacionCurricular.entidadATransferible(actividadProfesionalReservadaXRecomendacionCurricular);

    }

}
