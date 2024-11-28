package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoPlanEstudio;
import untdf.is3.acceso.AccesoPlanEstudio;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Asignatura;
import untdf.is3.modelo.PlanEstudio;
import untdf.is3.transferible.*;
import untdf.is3.transferible.TransferiblePlanEstudio;
import untdf.is3.transformador.TransformadorPlanEstudio;
import untdf.is3.transformador.TransformadorPlanEstudio;

import java.util.List;

@RequestScoped
public class ServicioPlanEstudio {

    @Inject
    Logger auditoria;

    @Inject
    AccesoPlanEstudio accesoPlanEstudio;

    @Inject
    TransformadorPlanEstudio transformadorPlanEstudio;

    public List<TransferiblePlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los planes de estudio.");
        return transformadorPlanEstudio.entidadATransferible(accesoPlanEstudio.obtenerTodos());

    }

    public TransferiblePlanEstudio obtener(int id) {

        auditoria.debugv("Intentando obtener plan de estudio {0}.", id);
        PlanEstudio planEstudio = accesoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Plan de estudio no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPlanEstudio.entidadATransferible(planEstudio);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePlanEstudio crear(TransferibleCrearPlanEstudio transferibleCrearPlanEstudio) {

        auditoria.debug("Intentando crear nuevo plan de estudio");
        PlanEstudio planEstudio = new PlanEstudio(transferibleCrearPlanEstudio.getNombre());

        try {
            accesoPlanEstudio.persistir(planEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear plan de estudio"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Plan de estudio no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Plan de estudio no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPlanEstudio.entidadATransferible(planEstudio);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePlanEstudio clonar(@Valid TransferibleClonarPlanEstudio transferibleClonarPlanEstudio) {

        auditoria.debugv("Intentando clonar plan de estudio {0}", transferibleClonarPlanEstudio.getPlanEstudioId());
        PlanEstudio planEstudioAClonar = accesoPlanEstudio.obtener(transferibleClonarPlanEstudio.getPlanEstudioId())
                .orElseThrow(()-> new HTTPNoContentException("Plan de Estudios a clonar no existe."));

        PlanEstudio planEstudio = new PlanEstudio(transferibleClonarPlanEstudio.getNombre());

        // Persistir nuevo Plan de Estudio
        try {
            accesoPlanEstudio.persistir(planEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al clonar plan de estudio"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Plan de estudio no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Plan de estudio no cumple con las restricciones de la DB: " + e.getMessage());
        }

        // Clonar Plan de Estudio
        try {
            auditoria.debugv("Clonando Plan de Estudio.");
            accesoPlanEstudio.clonar(planEstudioAClonar, planEstudio);
        } catch (Exception e) {
            auditoria.debugv("Problemas al clonar Plan de Estudio {0}", planEstudioAClonar.getPlanEstudioId());
            throw  new HTTPInternalServerErrorException("Problemas al clonar Plan de Estudio " + planEstudioAClonar.getPlanEstudioId());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPlanEstudio.entidadATransferible(planEstudio);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePlanEstudio modificar(int id, @Valid TransferiblePlanEstudio transferiblePlanEstudio) {

        auditoria.debugv("Intentando modificar Plan Estudio {0}", id);
        PlanEstudio asignatura = accesoPlanEstudio.obtener(transferiblePlanEstudio.getPlanEstudioId())
                .orElseThrow(()-> new HTTPNoContentException("Plan Estudio no existe."));

        auditoria.debugv("Modificando Plan Estudio {0}", asignatura.getPlanEstudioId());
        transformadorPlanEstudio.modificar(asignatura, transferiblePlanEstudio);

        try {
            accesoPlanEstudio.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Plan Estudio"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Plan Estudio no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Plan Estudio no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPlanEstudio.entidadATransferible(asignatura);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePlanEstudio eliminar(int id) {

        auditoria.debugv("Intentando obtener plan de estudio {0}.", id);
        PlanEstudio planEstudio = accesoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Plan de estudio no existe."));

        auditoria.debugv("Intentando obtener plan de estudio {0}.", planEstudio.getPlanEstudioId());
        if(!accesoPlanEstudio.eliminar(planEstudio)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar plan de estudio.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPlanEstudio.entidadATransferible(planEstudio);

    }

}
