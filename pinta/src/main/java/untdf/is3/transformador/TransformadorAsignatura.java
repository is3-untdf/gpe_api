package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import untdf.is3.modelo.Asignatura;
import untdf.is3.transferible.TransferibleAsignatura;
import untdf.is3.transferible.TransferibleAsignaturaConDependencias;

import java.util.List;

@Mapper // Indica que es un mapeador
public interface TransformadorAsignatura {

    //@Mapping(target = "planEstudioId", source = "planEstudioId.planEstudioId")
    List<TransferibleAsignatura> entidadATransferible(List<Asignatura> asignaturas);

    @Mapping(target = "planEstudioId", source = "planEstudioId.planEstudioId")
    TransferibleAsignatura entidadATransferible(Asignatura asignatura);

    List<TransferibleAsignaturaConDependencias> entidadATransferibleConDependencias(List<Asignatura> asignaturas);

    @Mapping(target = "planEstudio", source = "planEstudioId")
    @Mapping(target = "planEstudioId", source = "planEstudioId.planEstudioId")
    TransferibleAsignaturaConDependencias entidadATransferibleConDependencias(Asignatura asignatura);

    @Mapping(target = "asignaturaId", ignore = true)
    @Mapping(target = "planEstudioId", ignore = true)
    void modificar(@MappingTarget Asignatura asignatura, @Valid TransferibleAsignatura transferibleAsignatura);



}
