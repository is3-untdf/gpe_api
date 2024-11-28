package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.Asignatura;
import untdf.is3.modelo.Intensidad;
import untdf.is3.transferible.TransferibleAsignatura;
import untdf.is3.transferible.TransferibleIntensidad;

import java.util.List;

@Mapper 
public interface TransformadorIntensidad {

    List<TransferibleIntensidad> entidadATransferible(List<Intensidad> intensidadList);

    TransferibleIntensidad entidadATransferible(Intensidad intensidad);

    @Mapping(target = "intensidadId", ignore = true)
    void modificar(@MappingTarget Intensidad intensidad, @Valid TransferibleIntensidad transferibleIntensidad);

}
