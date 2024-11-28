package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.AreaRedUnci;
import untdf.is3.modelo.ContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleAreaRedUnci;
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;

import java.util.List;

@Mapper
public interface TransformadorContenidoCurricularBasico {

    List<TransferibleContenidoCurricularBasico> entidadATransferible(List<ContenidoCurricularBasico> contenidoCurricularBasicoList);

    @Mapping(target = "trayectoFormativoId", source = "trayectoFormativoId.trayectoFormativoId")
    TransferibleContenidoCurricularBasico entidadATransferible(ContenidoCurricularBasico contenidoCurricularBasico);

    @Mapping(target = "trayectoFormativoId", ignore = true)
    @Mapping(target = "contenidoCurricularBasicoId", ignore = true)
    void modificar(@MappingTarget ContenidoCurricularBasico contenidoCurricularBasico, @Valid TransferibleContenidoCurricularBasico transferibleContenidoCurricularBasico);
    
}
