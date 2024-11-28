package untdf.is3.servicio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import untdf.is3.acceso.AccesoPesoRelativo;
import untdf.is3.excepcion.HTTPEntityExistsException;
import untdf.is3.excepcion.HTTPInternalServerErrorException;
import untdf.is3.excepcion.HTTPNoContentException;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.PesoRelativo;
import untdf.is3.transferible.TransferibleCrearPesoRelativo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferiblePesoRelativo;
import untdf.is3.transformador.TransformadorPesoRelativo;

import java.util.List;

@RequestScoped
public class ServicioPesoRelativo {

    @Inject
    Logger auditoria;

    @Inject
    AccesoPesoRelativo accesoPesoRelativo;

    @Inject
    TransformadorPesoRelativo transformadorPesoRelativo;

    public List<TransferiblePesoRelativo> obtenerTodas(){

        auditoria.debug("Intentando obtener todos los Pesos Relativos.");
        return transformadorPesoRelativo.entidadATransferible(accesoPesoRelativo.obtenerTodas());

    }

    public TransferiblePesoRelativo obtener(int id) {

        auditoria.debugv("Intentando obtener Peso Relativo {0}.", id);
        PesoRelativo pesoRelativo = accesoPesoRelativo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Peso Relativo no existe."));

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPesoRelativo.entidadATransferible(pesoRelativo);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePesoRelativo crear(TransferibleCrearPesoRelativo transferibleCrearPesoRelativo) {

        auditoria.debug("Intentando crear nuevo Peso Relativo");
        PesoRelativo pesoRelativo = new PesoRelativo(transferibleCrearPesoRelativo.getDescripcion());

        try {
            accesoPesoRelativo.persistir(pesoRelativo)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al crear Peso Relativo"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Peso Relativo no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Peso Relativo no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPesoRelativo.entidadATransferible(pesoRelativo);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePesoRelativo modificar(int id, @Valid TransferiblePesoRelativo transferiblePesoRelativo) {

        auditoria.debugv("Intentando modificar Peso Relativo {0}", id);
        PesoRelativo asignatura = accesoPesoRelativo.obtener(transferiblePesoRelativo.getPesoRelativoId())
                .orElseThrow(()-> new HTTPNoContentException("Peso Relativo no existe."));

        auditoria.debugv("Modificando Peso Relativo {0}", asignatura.getPesoRelativoId());
        transformadorPesoRelativo.modificar(asignatura, transferiblePesoRelativo);

        try {
            accesoPesoRelativo.persistir(asignatura)
                    .orElseThrow( () -> new HTTPInternalServerErrorException("Problemas al modificar Peso Relativo"));
        } catch (ConstraintViolationException e) {
            auditoria.error("Peso Relativo no cumple con las restricciones de la DB: " + e.getMessage());
            throw new HTTPEntityExistsException("Peso Relativo no cumple con las restricciones de la DB: " + e.getMessage());
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPesoRelativo.entidadATransferible(asignatura);

    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public TransferiblePesoRelativo eliminar(int id) {

        auditoria.debugv("Intentando obtener Peso Relativo {0}.", id);
        PesoRelativo pesoRelativo = accesoPesoRelativo.obtener(id)
                .orElseThrow(()-> new HTTPNoContentException("Peso Relativo no existe."));

        auditoria.debugv("Intentando eliminar Peso Relativo {0}.", pesoRelativo.getPesoRelativoId());
        if(!accesoPesoRelativo.eliminar(pesoRelativo)) {
            throw new HTTPInternalServerErrorException("Problemas al eliminar Peso Relativo.");
        }

        auditoria.debug("Mapeando entidad a transferible.");
        return transformadorPesoRelativo.entidadATransferible(pesoRelativo);

    }

}
