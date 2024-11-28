package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoAreaRedUnci;
import untdf.is3.acceso.AccesoTrayectoFormativo;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.*;
import untdf.is3.transferible.TransferibleAreaRedUnci;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleCrearAreaRedUnci;
import untdf.is3.transformador.TransformadorAreaRedUnci;

import java.util.List;

@RequestScoped
public class ServicioAreaRedUnci {

    @Inject
    Logger auditoria;

    @Inject
    AccesoAreaRedUnci accesoAreaRedUnci;

    @Inject
    TransformadorAreaRedUnci transformadorAreaRedUnci;

    @Inject
    AccesoTrayectoFormativo accesoTrayectoFormativo;

    public List<TransferibleAreaRedUnci> obtenerTodos(){

        auditoria.debug("Intentando obtener todas las Áreas Red UNCI.");
        return transformadorAreaRedUnci.entidadATransferible(accesoAreaRedUnci.obtenerTodos());

    }

    public TransferibleAreaRedUnci obtener(int id) {

        auditoria.debugv("Intentando obtener Área Red UNCI {0}.", id);
        AreaRedUnci areaRedUnci = accesoAreaRedUnci.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Área Red UNCI no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAreaRedUnci.entidadATransferible(areaRedUnci);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAreaRedUnci crear(TransferibleCrearAreaRedUnci transferibleCrearAreaRedUnci) {

        auditoria.debug("Intentando crear nuevo Área Red UNCI");
        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo
                .obtener(transferibleCrearAreaRedUnci.getTrayectoFormativoId())
                    .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        AreaRedUnci areaRedUnci =
                new AreaRedUnci(transferibleCrearAreaRedUnci.getSigla(),
                        transferibleCrearAreaRedUnci.getNombre(),
                        trayectoFormativo);

        try {
            accesoAreaRedUnci.persistir(areaRedUnci)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Área Red UNCI."));
        } catch (ConstraintViolationException e) {
            auditoria.error("Área Red UNCI no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Área Red UNCI no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAreaRedUnci.entidadATransferible(areaRedUnci);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAreaRedUnci modificar(int id, @Valid TransferibleAreaRedUnci transferibleAreaRedUnci) {

        auditoria.debugv("Intentando modificar Area Red UNCI {0}", id);
        AreaRedUnci areaRedUnci = accesoAreaRedUnci.obtener(transferibleAreaRedUnci.getAreaRedUnciId())
                .orElseThrow(()-> new HTTPNoContentException("Area Red UNCI no existe."));

        TrayectoFormativo trayectoFormativo = accesoTrayectoFormativo
                .obtener(transferibleAreaRedUnci.getTrayectoFormativoId())
                .orElseThrow(()-> new HTTPNoContentException("Trayecto Formativo no existe."));

        auditoria.debugv("Modificando Area Red UNCI {0}", areaRedUnci.getAreaRedUnciId());
        transformadorAreaRedUnci.modificar(areaRedUnci, transferibleAreaRedUnci);

        areaRedUnci.setTrayectoFormativoId(trayectoFormativo);

        try {
            accesoAreaRedUnci.persistir(areaRedUnci)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Area Red UNCI"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Area Red UNCI no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Area Red UNCI no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAreaRedUnci.entidadATransferible(areaRedUnci);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleAreaRedUnci eliminar(int id) {

        auditoria.debugv("Intentando obtener Área Red UNCI {0}.", id);
        AreaRedUnci areaRedUnci = accesoAreaRedUnci.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Área Red UNCI no existe."));

        auditoria.debugv("Intentando obtener Área Red UNCI {0}.", areaRedUnci.getAreaRedUnciId());
        if(!accesoAreaRedUnci.eliminar(areaRedUnci)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Área Red UNCI.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorAreaRedUnci.entidadATransferible(areaRedUnci);

    }

}
