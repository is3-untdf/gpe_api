package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoContenidoCurricularBasico;
import untdf.is3.acceso.AccesoTrayectoFormativo;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.AreaRedUnci;
import untdf.is3.modelo.ContenidoCurricularBasico;
import untdf.is3.modelo.TrayectoFormativo;
import untdf.is3.transferible.TransferibleAreaRedUnci;
import untdf.is3.transferible.TransferibleCrearContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;
import untdf.is3.transformador.TransformadorContenidoCurricularBasico;

import java.util.List;

@RequestScoped
public class ServicioContenidoCurricularBasico {

    @Inject
    Logger auditoria;

    @Inject
    AccesoContenidoCurricularBasico accesoContenidoCurricularBasico;

    @Inject
    TransformadorContenidoCurricularBasico transformadorContenidoCurricularBasico;

    @Inject
    AccesoTrayectoFormativo accesoTrayectoFormativo;

    public List<TransferibleContenidoCurricularBasico> obtenerTodos(){

        auditoria.debug("Intentando obtener todos los Contenidos Curriculares Básicos.");
        return transformadorContenidoCurricularBasico.entidadATransferible(accesoContenidoCurricularBasico.obtenerTodos());

    }

    public TransferibleContenidoCurricularBasico obtener(int id) {

        auditoria.debugv("Intentando obtener Contenido Curricular Básico {0}.", id);
        ContenidoCurricularBasico contenidoCurricularBasico = accesoContenidoCurricularBasico.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Contenido Curricular Básico no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoCurricularBasico.entidadATransferible(contenidoCurricularBasico);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoCurricularBasico crear(TransferibleCrearContenidoCurricularBasico transferibleCrearContenidoCurricularBasico) {

        auditoria.debug("Intentando crear nuevo Contenido Curricular Básico");
        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo
                .obtener(transferibleCrearContenidoCurricularBasico.getTrayectoFormativoId())
                    .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        ContenidoCurricularBasico contenidoCurricularBasico =
                new ContenidoCurricularBasico(
                        transferibleCrearContenidoCurricularBasico.getNombre(),
                        trayectoFormativo);

        try {
            accesoContenidoCurricularBasico.persistir(contenidoCurricularBasico)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Contenido Curricular Básico."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Contenido Curricular Básico no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Contenido Curricular Básico no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoCurricularBasico.entidadATransferible(contenidoCurricularBasico);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoCurricularBasico modificar(int id, @Valid TransferibleContenidoCurricularBasico transferibleContenidoCurricularBasico) {

        auditoria.debugv("Intentando modificar Contenido Curricular Básico {0}", id);
        ContenidoCurricularBasico contenidoCurricularBasico = accesoContenidoCurricularBasico.obtener(transferibleContenidoCurricularBasico.getContenidoCurricularBasicoId())
                .orElseThrow(()-> new HTTPNoContentException("Contenido Curricular Básico no existe."));

        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo
                .obtener(transferibleContenidoCurricularBasico.getTrayectoFormativoId())
                .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        auditoria.debugv("Modificando Contenido Curricular Básico {0}", contenidoCurricularBasico.getContenidoCurricularBasicoId());
        transformadorContenidoCurricularBasico.modificar(contenidoCurricularBasico, transferibleContenidoCurricularBasico);

        contenidoCurricularBasico.setTrayectoFormativoId(trayectoFormativo);

        try {
            accesoContenidoCurricularBasico.persistir(contenidoCurricularBasico)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Contenido Curricular Básico"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Contenido Curricular Básico no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Contenido Curricular Básico no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoCurricularBasico.entidadATransferible(contenidoCurricularBasico);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleContenidoCurricularBasico eliminar(int id) {

        auditoria.debugv("Intentando obtener Contenido Curricular Básico {0}.", id);
        ContenidoCurricularBasico contenidoCurricularBasico = accesoContenidoCurricularBasico.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Contenido Curricular Básico no existe."));

        auditoria.debugv("Intentando obtener Contenido Curricular Básico {0}.", contenidoCurricularBasico.getContenidoCurricularBasicoId());
        if(!accesoContenidoCurricularBasico.eliminar(contenidoCurricularBasico)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Contenido Curricular Básico.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorContenidoCurricularBasico.entidadATransferible(contenidoCurricularBasico);

    }

}
