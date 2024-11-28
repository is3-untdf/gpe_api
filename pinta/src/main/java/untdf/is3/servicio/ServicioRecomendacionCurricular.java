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
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleRecomendacionCurricular;
import untdf.is3.transferible.TransferibleCrearRecomendacionCurricular;
import untdf.is3.transformador.TransformadorRecomendacionCurricular;

import java.util.List;

@RequestScoped
public class ServicioRecomendacionCurricular {

    @Inject
    Logger auditoria;

    @Inject
    AccesoRecomendacionCurricular accesoRecomendacionCurricular;

    @Inject
    TransformadorRecomendacionCurricular transformadorRecomendacionCurricular;

    @Inject
    AccesoAsignatura accesoAsignatura;

    @Inject
    AccesoIntensidad accesoIntensidad;

    @Inject
    AccesoPesoRelativo accesoPesoRelativo;

    @Inject
    AccesoAreaRedUnci accesoAreaRedUnci;

    public List<TransferibleRecomendacionCurricular> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las Recomendaciones Curriculares.");
        return transformadorRecomendacionCurricular.entidadATransferible(accesoRecomendacionCurricular.obtenerTodos());

    }

    public TransferibleRecomendacionCurricular obtener(int id) {

        auditoria.debugv("Intentando obtener Recomendación Curricular {0} de Plan de Estudio.", id);
        RecomendacionCurricular recomendacionCurricular = accesoRecomendacionCurricular.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular de Plan de Estudio no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricular.entidadATransferible(recomendacionCurricular);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricular crear(TransferibleCrearRecomendacionCurricular transferibleCrearRecomendacionCurricular) {

        auditoria.debug("Intentando crear nueva Recomendación Curricular.");
        Exigencia exigencia = Exigencia.fromChar(transferibleCrearRecomendacionCurricular.getExigencia());

        Intensidad intensidad = accesoIntensidad.obtener(transferibleCrearRecomendacionCurricular.getIntensidadId())
                .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));

        PesoRelativo pesoRelativo = accesoPesoRelativo.obtener(transferibleCrearRecomendacionCurricular.getPesoRelativoId())
                .orElseThrow(()-> new HTTPNoContentException("Peso Relativo no existe."));

        AreaRedUnci areaRedUnci = accesoAreaRedUnci.obtener(transferibleCrearRecomendacionCurricular.getAreaRedUnciId())
                .orElseThrow(()-> new HTTPNoContentException("Área Red UNCI no existe."));

        RecomendacionCurricular recomendacionCurricular = new RecomendacionCurricular(
                transferibleCrearRecomendacionCurricular.getCodigo(),
                transferibleCrearRecomendacionCurricular.getNombre(),
                exigencia.getIdentificador(),
                intensidad,
                pesoRelativo,
                areaRedUnci);

        try {
            accesoRecomendacionCurricular.persistir(recomendacionCurricular)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Recomendación Curricular."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Recomendación Curricular no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Recomendación Curricular no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricular.entidadATransferible(recomendacionCurricular);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricular modificar(int id, @Valid TransferibleRecomendacionCurricular transferibleRecomendacionCurricular) {

        auditoria.debugv("Intentando modificar Recomendación Curricular {0}", id);
        RecomendacionCurricular recomendacionCurricular = accesoRecomendacionCurricular.obtener(transferibleRecomendacionCurricular.getRecomendacionCurricularId())
                .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular no existe."));

        Exigencia exigencia = Exigencia.fromChar(transferibleRecomendacionCurricular.getExigencia());

        Intensidad intensidad = accesoIntensidad.obtener(transferibleRecomendacionCurricular.getIntensidadId())
                .orElseThrow(()-> new HTTPNoContentException("Intensidad no existe."));

        PesoRelativo pesoRelativo = accesoPesoRelativo.obtener(transferibleRecomendacionCurricular.getPesoRelativoId())
                .orElseThrow(()-> new HTTPNoContentException("Peso Relativo no existe."));

        AreaRedUnci areaRedUnci = accesoAreaRedUnci.obtener(transferibleRecomendacionCurricular.getAreaRedUnciId())
                .orElseThrow(()-> new HTTPNoContentException("Área Red UNCI no existe."));

        auditoria.debugv("Modificando Recomendación Curricular {0}", recomendacionCurricular.getRecomendacionCurricularId());
        transformadorRecomendacionCurricular.modificar(recomendacionCurricular, transferibleRecomendacionCurricular);

        recomendacionCurricular.setExigencia(exigencia.getIdentificador());
        recomendacionCurricular.setIntensidadId(intensidad);
        recomendacionCurricular.setPesoRelativoId(pesoRelativo);
        recomendacionCurricular.setAreaRedUnciId(areaRedUnci);

        try {
            accesoRecomendacionCurricular.persistir(recomendacionCurricular)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Recomendación Curricular"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Recomendación Curricular no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Recomendación Curricular no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricular.entidadATransferible(recomendacionCurricular);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRecomendacionCurricular eliminar(int id) {

        auditoria.debugv("Intentando obtener Recomendación Curricular {0}.", id);
        RecomendacionCurricular recomendacionCurricular = accesoRecomendacionCurricular.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Recomendación Curricular no existe."));

        auditoria.debugv("Intentando obtener Recomendación Curricular {0}.", recomendacionCurricular.getRecomendacionCurricularId());
        if(!accesoRecomendacionCurricular.eliminar(recomendacionCurricular)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Contenido Mínimo de Plan de Estudio.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorRecomendacionCurricular.entidadATransferible(recomendacionCurricular);

    }

}
