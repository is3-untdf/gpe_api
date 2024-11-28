package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.*;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.*;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio;
import untdf.is3.transformador.TransformadorRecomendacionCurricularXContenidoMinimoPlanEstudio;

import java.util.List;

@RequestScoped
public class ServicioRecomendacionCurricularXContenidoMinimoPlanEstudio {

    @Inject
    Logger auditoria;

    @Inject
    AccesoRecomendacionCurricularXContenidoMinimoPlanEstudio accesoRecomendacionCurricularXContenidoMinimoPlanEstudio;

    @Inject
    AccesoRecomendacionCurricular accesoRecomendacionCurricular;

    @Inject
    AccesoContenidoMinimoPlanEstudio accesoContenidoMinimoPlanEstudio;

    @Inject
    AccesoIntensidad accesoIntensidad;

    @Inject
    TransformadorRecomendacionCurricularXContenidoMinimoPlanEstudio transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio;

    public List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las relaciones.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerTodos());

    }

    public TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio obtener(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio = accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);
    }

    public List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerPorRecomendacionCurricular(int id) {

        RecomendacionCurricular recomendacionCurricular = accesoRecomendacionCurricular.obtener(id)
                        .orElseThrow(()-> new HTTPNoContentException("Recomendacion Curricular no existe."));

        auditoria.debugv("Intentando obtener relación de acuerdo a Recomendacion Curricular {0}.", id);
        List<RecomendacionCurricularXContenidoMinimoPlanEstudio> recomendacionCurricularXContenidoMinimoPlanEstudio =
                accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerPorRecomendacionCurricular(recomendacionCurricular);

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);
    }

    public List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> obtenerPorContenidoMinimo(int id) {

        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio =
                accesoContenidoMinimoPlanEstudio.obtener(id)
                        .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo Plan de Estudio no existe."));

        auditoria.debugv("Intentando obtener relación de acuerdo a Contenido Mínimo {0}.", id);
        List<RecomendacionCurricularXContenidoMinimoPlanEstudio> recomendacionCurricularXContenidoMinimoPlanEstudio =
                accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtenerPorContenidoMinimo(contenidoMinimoPlanEstudio);

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio crear(TransferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio) {

        auditoria.debug("Intentando crear nuevo relación");
        RecomendacionCurricular recomendacionCurricular =
                accesoRecomendacionCurricular.obtener(transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularId())
                .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular no existe."));

        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio =
                accesoContenidoMinimoPlanEstudio.obtener(
                        transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId())
                        .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo Plan de Estudio no existe."));

        Intensidad intensidad = null;
        try {
            intensidad = accesoIntensidad.obtener(transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getIntensidadId())
                    .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));
        } catch (NullPointerException ignored) {}

        Exigencia exigencia = null;
        try {
            exigencia = Exigencia.fromChar(transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getExigencia());
        } catch (NullPointerException ignored) {}

        RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio =
                new RecomendacionCurricularXContenidoMinimoPlanEstudio(
                        recomendacionCurricular,
                        contenidoMinimoPlanEstudio,
                        transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getHorasTeoria(),
                        transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getHorasTeoria(),
                        exigencia == null ? null : exigencia.getIdentificador(),
                        intensidad,
                        transferibleCrearRecomendacionCurricularXContenidoMinimoPlanEstudio.getObservaciones());

        try {
            accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.persistir(recomendacionCurricularXContenidoMinimoPlanEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear relación."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Relación no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Relación no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio modificar(int id,
                          @Valid TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio) {

        RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio =
                accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtener(id)
                                .orElseThrow(()-> new HTTPNoContentException(
                                        "Relación no existe."));

        auditoria.debugv("Intentando modificar relación {0}", id);
        RecomendacionCurricular recomendacionCurricular =
                accesoRecomendacionCurricular.obtener(transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularId())
                        .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular no existe."));

        ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio =
                accesoContenidoMinimoPlanEstudio.obtener(
                                transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getContenidoMinimoPlanEstudioId())
                        .orElseThrow(()-> new HTTPNoContentException("Contenido Mínimo Plan de Estudio no existe."));

        Intensidad intensidad = null;
        try {
            intensidad = accesoIntensidad.obtener(transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getIntensidadId())
                    .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));
        } catch (NullPointerException ignored) {}

//        Exigencia exigencia = null;
//        try {
//            exigencia = Exigencia.fromChar(transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getExigencia());
//        } catch (NullPointerException ignored) {}


        auditoria.debugv("Modificando relación {0}",
                recomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());
        transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.modificar(
                recomendacionCurricularXContenidoMinimoPlanEstudio,
                transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio);

        recomendacionCurricularXContenidoMinimoPlanEstudio.setObservaciones(transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio.getObservaciones());
        recomendacionCurricularXContenidoMinimoPlanEstudio.setIntensidadId(intensidad);

        try {
            accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.persistir(recomendacionCurricularXContenidoMinimoPlanEstudio)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Relación."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Relación no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Relación no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio eliminar(int id) {

        auditoria.debugv("Intentando obtener relación {0}.", id);
        RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio =
                accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Relación no existe."));

        auditoria.debugv("Intentando obtener relación {0}.", recomendacionCurricularXContenidoMinimoPlanEstudio.getRecomendacionCurricularXContenidoMinimoPlanEstudioId());
        if(!accesoRecomendacionCurricularXContenidoMinimoPlanEstudio.eliminar(recomendacionCurricularXContenidoMinimoPlanEstudio)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar relación.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricularXContenidoMinimoPlanEstudio.entidadATransferible(recomendacionCurricularXContenidoMinimoPlanEstudio);

    }

}
