package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.ActividadProfesionalReservada;
import untdf.is3.modelo.Intensidad;
import untdf.is3.transferible.TransferibleActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleIntensidad;

import java.util.List;

@Mapper 
public interface TransformadorActividadProfesionalReservada {

    List<TransferibleActividadProfesionalReservada> entidadATransferible(List<ActividadProfesionalReservada> actividadProfesionalReservadaList);

    TransferibleActividadProfesionalReservada entidadATransferible(ActividadProfesionalReservada actividadProfesionalReservada);

    @Mapping(target = "actividadProfesionalReservadaId", ignore = true)
    void modificar(@MappingTarget ActividadProfesionalReservada actividadProfesionalReservada, @Valid TransferibleActividadProfesionalReservada transferibleActividadProfesionalReservada);
    
}
