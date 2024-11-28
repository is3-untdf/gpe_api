package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.TrayectoFormativo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTrayectoFormativo;

import java.util.List;

@Mapper 
public interface TransformadorTrayectoFormativo {

    List<TransferibleTrayectoFormativo> entidadATransferible(List<TrayectoFormativo> trayectoFormativoList);

    TransferibleTrayectoFormativo entidadATransferible(TrayectoFormativo trayectoFormativo);

    @Mapping(target = "trayectoFormativoId", ignore = true)
    void modificar(@MappingTarget TrayectoFormativo trayectoFormativo, @Valid TransferibleTrayectoFormativo transferibleTrayectoFormativo);
    
}
