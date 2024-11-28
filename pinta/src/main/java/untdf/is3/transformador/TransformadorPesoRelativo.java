package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.PesoRelativo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferiblePesoRelativo;

import java.util.List;

@Mapper 
public interface TransformadorPesoRelativo {

    List<TransferiblePesoRelativo> entidadATransferible(List<PesoRelativo> pesoRelativoList);

    TransferiblePesoRelativo entidadATransferible(PesoRelativo pesoRelativo);

    @Mapping(target = "pesoRelativoId", ignore = true)
    void modificar(@MappingTarget PesoRelativo pesoRelativo, @Valid TransferiblePesoRelativo transferiblePesoRelativo);
    
}
