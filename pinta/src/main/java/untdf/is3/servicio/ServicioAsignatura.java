package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoAsignatura;
import untdf.is3.acceso.AccesoPlanEstudio;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Asignatura;
import untdf.is3.modelo.PlanEstudio;
import untdf.is3.transferible.TransferibleAsignatura;
import untdf.is3.transferible.TransferibleAsignaturaConDependencias;
import untdf.is3.transferible.TransferibleCrearAsignatura;
import untdf.is3.transformador.TransformadorAsignatura;

import java.util.List;

@RequestScoped
public class ServicioAsignatura {

    @Inject
    Logger auditoria;

    @Inject
    AccesoAsignatura accesoAsignatura;

    @Inject
    TransformadorAsignatura transformadorAsignatura;

    @Inject
    AccesoPlanEstudio accesoPlanEstudio;

    public List<TransferibleAsignatura> obtenerTodas(){

        auditoria.debug("Intentando obtener todas las asignaturas.");
        return transformadorAsignatura.entidadATransferible(accesoAsignatura.obtenerTodas());

    }

    public List<TransferibleAsignaturaConDependencias> obtenerTodasConDependencias(){

        auditoria.debug("Intentando obtener todas las asignaturas.");
        return transformadorAsignatura.entidadATransferibleConDependencias(accesoAsignatura.obtenerTodas());

    }

    public TransferibleAsignatura obtener(int id) {

        auditoria.debugv("Intentando obtener asignatura {0}.", id);
        Asignatura asignatura = accesoAsignatura.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAsignatura.entidadATransferible(asignatura);
    }

    public TransferibleAsignaturaConDependencias obtenerConDependencias(int id) {

        auditoria.debugv("Intentando obtener asignatura {0}.", id);
        Asignatura asignatura = accesoAsignatura.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAsignatura.entidadATransferibleConDependencias(asignatura);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAsignatura crear(TransferibleCrearAsignatura transferibleCrearAsignatura) {

        PlanEstudio planEstudio = accesoPlanEstudio.obtener(transferibleCrearAsignatura.getPlanEstudioId()
                ).orElseThrow(()-> new HTTPNoContentException("Plan de Estudios no existe."));

        auditoria.debug("Intentando crear nueva asignatura");
        Asignatura asignatura = new Asignatura(transferibleCrearAsignatura.getCodigo(),
                transferibleCrearAsignatura.getNombre(),
                transferibleCrearAsignatura.getCargaHoraria(), planEstudio);

        try {
            accesoAsignatura.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Asignatura"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Asignatura no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Asignatura no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAsignatura.entidadATransferible(asignatura);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAsignatura modificar(int id, @Valid TransferibleAsignatura transferibleAsignatura) {

        auditoria.debugv("Intentando modificar Asignatura {0}", id);
        Asignatura asignatura = accesoAsignatura.obtener(transferibleAsignatura.getAsignaturaId())
                .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));

        PlanEstudio planEstudio = accesoPlanEstudio.obtener(transferibleAsignatura.getPlanEstudioId())
                .orElseThrow(()-> new HTTPNoContentException("Plan de estudio no existe."));

        auditoria.debugv("Modificando Asignatura {0}", asignatura.getAsignaturaId());
        transformadorAsignatura.modificar(asignatura, transferibleAsignatura);

        asignatura.setPlanEstudioId(planEstudio);

        try {
            accesoAsignatura.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Asignatura"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Asignatura no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Asignatura no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAsignatura.entidadATransferible(asignatura);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAsignatura eliminar(int id) {

        auditoria.debugv("Intentando obtener asignatura {0}.", id);
        Asignatura asignatura = accesoAsignatura.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));

        auditoria.debugv("Intentando obtener asignatura {0}.", asignatura.getAsignaturaId());
        if(!accesoAsignatura.eliminar(asignatura)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Asignatura.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAsignatura.entidadATransferible(asignatura);

    }


}
