package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoAsignatura;
import untdf.is3.acceso.AccesoContenidoMinimoPlanEstudio;
import untdf.is3.acceso.AccesoIntensidad;
import untdf.is3.acceso.AccesoPlanEstudio;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.*;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudioConDependencias;
import untdf.is3.transferible.TransferibleCrearContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudio;
import untdf.is3.transformador.TransformadorContenidoMinimoPlanEstudio;

import java.util.List;

@RequestScoped
public class ServicioContenidoMinimoPlanEstudio {

    @Inject
    Logger auditoria;

    @Inject
    AccesoContenidoMinimoPlanEstudio accesoContenidoMinimoPlanEstudio;

    @Inject
    TransformadorContenidoMinimoPlanEstudio transformadorContenidoMinimoPlanEstudio;

    @Inject
    AccesoAsignatura accesoAsignatura;

    @Inject
    AccesoIntensidad accesoIntensidad;

    @Inject
    AccesoPlanEstudio accesoPlanEstudio;

    public List<TransferibleContenidoMinimoPlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los Contenidos Mínimos de Planes de Estudio.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferible(accesoContenidoMinimoPlanEstudio.obtenerTodos());

    }

    public List<TransferibleContenidoMinimoPlanEstudioConDependencias> obtenerTodosConDependencias(){

        auditoria.debug("Intentando obtener todos los Contenidos Mínimos de Planes de Estudio y sus dependencias.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferibleConDependencias(accesoContenidoMinimoPlanEstudio.obtenerTodos());

    }

    public TransferibleContenidoMinimoPlanEstudio obtener(int id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo {0} de Plan de Estudio.", id);
        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio = accesoContenidoMinimoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo de Plan de Estudio no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferible(contenidoMinimoPlanEstudio);
    }

    public TransferibleContenidoMinimoPlanEstudioConDependencias obtenerConDependencias(int id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo {0} de Plan de Estudio.", id);
        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio = accesoContenidoMinimoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo de Plan de Estudio no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferibleConDependencias(contenidoMinimoPlanEstudio);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoMinimoPlanEstudio crear(TransferibleCrearContenidoMinimoPlanEstudio transferibleCrearContenidoMinimoPlanEstudio) {

        auditoria.debug("Intentando crear nuevo Contenido Plan de Estudio.");
        Asignatura asignatura = null;
        try {
            asignatura = accesoAsignatura.obtener(transferibleCrearContenidoMinimoPlanEstudio.getAsignaturaId())
                    .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));
        } catch (NullPointerException ignored) {}

        Intensidad intensidad = null;
        try {
            intensidad = accesoIntensidad.obtener(transferibleCrearContenidoMinimoPlanEstudio.getIntensidad())
                    .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));
        } catch (NullPointerException ignored) {}

        Exigencia exigencia = null;
        try {
            exigencia = Exigencia.fromChar(transferibleCrearContenidoMinimoPlanEstudio.getExigencia());
        } catch (NullPointerException ignored) {}

        PlanEstudio planEstudio = accesoPlanEstudio.obtener(transferibleCrearContenidoMinimoPlanEstudio.getPlanEstudioId())
                .orElseThrow(() -> new HTTPNoContentException("Plan de Estudios no existe"));

        // Verificar si el Plan de Estudios de la Asignatura se corresponde con el Plan de Estudios del Contenido Mínimo
        if(asignatura != null && planEstudio != null) {
            if(asignatura.getPlanEstudioId().getPlanEstudioId() !=
                    planEstudio.getPlanEstudioId()) {
                auditoria.errorv("El Plan de Estudio de la Asignatura {0} no se corresponde el del Contenido Mínimo {1}.",
                        asignatura.getPlanEstudioId().getPlanEstudioId(), planEstudio.getPlanEstudioId());
                throw new HTTPInternalServerErrorException("El Plan de Estudio de la Asignatura (" +
                        asignatura.getPlanEstudioId().getPlanEstudioId() +
                        ") no se corresponde el Plan de Estudios del Contenido Mínimo (" + planEstudio.getPlanEstudioId() + ").");
            }
        }

        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio = new ContenidoMinimoPlanEstudio(
                transferibleCrearContenidoMinimoPlanEstudio.getNombre(),
                asignatura,
                transferibleCrearContenidoMinimoPlanEstudio.getHorasPractica(),
                transferibleCrearContenidoMinimoPlanEstudio.getHorasTeoria(),
                exigencia == null ? null : exigencia.getIdentificador(),
                intensidad,
                planEstudio);

        try {
            accesoContenidoMinimoPlanEstudio.persistir(contenidoMinimoPlanEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Contenido Mínimo Plan de Estudio"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Contenido Mínimo de Plan de Estudio no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Contenido Mínimo de Plan de Estudio no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferible(contenidoMinimoPlanEstudio);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoMinimoPlanEstudio modificar(int id, @Valid TransferibleContenidoMinimoPlanEstudio transferibleContenidoMinimoPlanEstudio) {

        auditoria.debugv("Intentando modificar Contenido Minimo Plan Estudio {0}", id);
        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio = accesoContenidoMinimoPlanEstudio.obtener(transferibleContenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId())
                .orElseThrow(()-> new HTTPNoContentException("Contenido Minimo Plan Estudio no existe."));

        Asignatura asignatura = null;
        try {
             asignatura = accesoAsignatura.obtener(transferibleContenidoMinimoPlanEstudio.getAsignaturaId())
                    .orElseThrow(()-> new HTTPNoContentException("Asignatura no existe."));
        } catch (NullPointerException ignored) {}

        Intensidad intensidad = null;
        try {
            intensidad = accesoIntensidad.obtener(transferibleContenidoMinimoPlanEstudio.getIntensidadId())
                    .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));
        } catch (NullPointerException ignored) {}

        PlanEstudio planEstudio = accesoPlanEstudio.obtener(transferibleContenidoMinimoPlanEstudio.getPlanEstudioId())
                .orElseThrow(()-> new HTTPNoContentException("Plan de Estudios no existe."));

        // Verificar si el Plan de Estudios de la Asignatura se corresponde con el Plan de Estudios del Contenido Mínimo
        if(asignatura != null && planEstudio != null) {
            if(asignatura.getPlanEstudioId().getPlanEstudioId() !=
                    planEstudio.getPlanEstudioId()) {
                auditoria.errorv("El Plan de Estudio de la Asignatura {0} no se corresponde el del Contenido Mínimo {1}.",
                        asignatura.getPlanEstudioId().getPlanEstudioId(), planEstudio.getPlanEstudioId());
                throw new HTTPInternalServerErrorException("El Plan de Estudio de la Asignatura (" +
                        asignatura.getPlanEstudioId().getPlanEstudioId() +
                        ") no se corresponde el del Contenido Mínimo (" + planEstudio.getPlanEstudioId() + ").");
            }
        }

        auditoria.debugv("Modificando Contenido Minimo Plan Estudio {0}", contenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());
        transformadorContenidoMinimoPlanEstudio.modificar(contenidoMinimoPlanEstudio, transferibleContenidoMinimoPlanEstudio);

        contenidoMinimoPlanEstudio.setAsignaturaId(asignatura);
        contenidoMinimoPlanEstudio.setIntensidadId(intensidad);
        contenidoMinimoPlanEstudio.setPlanEstudioId(planEstudio);

        try {
            accesoContenidoMinimoPlanEstudio.persistir(contenidoMinimoPlanEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Contenido Minimo Plan Estudio"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Contenido Minimo Plan Estudio no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Contenido Minimo Plan Estudio no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferible(contenidoMinimoPlanEstudio);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoMinimoPlanEstudio eliminar(int id) {

        auditoria.debugv("Intentando obtener Contenido Mínimo Plan de Estudio {0}.", id);
        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio = accesoContenidoMinimoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo de Plan de Estudio no existe."));

        auditoria.debugv("Intentando obtener Contenido Mínimo de Plan de Estudio {0}.", contenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId());
        if(!accesoContenidoMinimoPlanEstudio.eliminar(contenidoMinimoPlanEstudio)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Contenido Mínimo de Plan de Estudio.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoMinimoPlanEstudio.entidadATransferible(contenidoMinimoPlanEstudio);

    }

}
