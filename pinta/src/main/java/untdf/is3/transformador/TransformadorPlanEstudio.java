package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.Asignatura;
import untdf.is3.modelo.PlanEstudio;
import untdf.is3.transferible.TransferibleAsignatura;
import untdf.is3.transferible.TransferiblePlanEstudio;

import java.util.List;

@Mapper
public interface TransformadorPlanEstudio {

    List<TransferiblePlanEstudio> entidadATransferible(List<PlanEstudio> planEstudioList);

    TransferiblePlanEstudio entidadATransferible(PlanEstudio planEstudio);

    @Mapping(target = "planEstudioId", ignore = true)
    void modificar(@MappingTarget PlanEstudio planEstudio, @Valid TransferiblePlanEstudio transferiblePlanEstudio);
    
}
