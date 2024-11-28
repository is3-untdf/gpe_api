package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.ContenidoCurricularBasico;
import untdf.is3.modelo.RecomendacionCurricular;
import untdf.is3.transferible.TransferibleContenidoCurricularBasico;
import untdf.is3.transferible.TransferibleRecomendacionCurricular;

import java.util.List;

@Mapper
public interface TransformadorRecomendacionCurricular {

    List<TransferibleRecomendacionCurricular> entidadATransferible(List<RecomendacionCurricular> recomendacionCurricularList);

    @Mapping(target = "intensidadId", source = "intensidadId.intensidadId")
    @Mapping(target = "pesoRelativoId", source = "pesoRelativoId.pesoRelativoId")
    @Mapping(target = "areaRedUnciId", source = "areaRedUnciId.areaRedUnciId")
    TransferibleRecomendacionCurricular entidadATransferible(RecomendacionCurricular recomendacionCurricular);

    @Mapping(target = "exigencia", ignore = true)
    @Mapping(target = "intensidadId", ignore = true)
    @Mapping(target = "pesoRelativoId", ignore = true)
    @Mapping(target = "areaRedUnciId", ignore = true)
    @Mapping(target = "recomendacionCurricularId", ignore = true)
    void modificar(@MappingTarget RecomendacionCurricular recomendacionCurricular, @Valid TransferibleRecomendacionCurricular transferibleRecomendacionCurricular);
    
}
