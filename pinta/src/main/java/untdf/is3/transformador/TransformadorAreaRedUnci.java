package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.AreaRedUnci;
import untdf.is3.modelo.ContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleAreaRedUnci;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudio;

import java.util.List;

@Mapper
public interface TransformadorAreaRedUnci {

    List<TransferibleAreaRedUnci> entidadATransferible(List<AreaRedUnci> areaRedUnciList);

    @Mapping(target = "trayectoFormativoId", source = "trayectoFormativoId.trayectoFormativoId")
    TransferibleAreaRedUnci entidadATransferible(AreaRedUnci areaRedUnci);

    @Mapping(target = "trayectoFormativoId", ignore = true)
    @Mapping(target = "areaRedUnciId", ignore = true)
    void modificar(@MappingTarget AreaRedUnci areaRedUnci, @Valid TransferibleAreaRedUnci transferibleAreaRedUnci);

}
