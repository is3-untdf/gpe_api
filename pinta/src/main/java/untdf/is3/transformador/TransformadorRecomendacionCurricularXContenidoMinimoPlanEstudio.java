package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.RecomendacionCurricularXContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio;

import java.util.List;

@Mapper
public interface TransformadorRecomendacionCurricularXContenidoMinimoPlanEstudio {

    List<TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio> entidadATransferible(List<RecomendacionCurricularXContenidoMinimoPlanEstudio> recomendacionCurricularXContenidoMinimoPlanEstudioList);

    @Mapping(target = "recomendacionCurricularId", source = "recomendacionCurricularId.recomendacionCurricularId")
    @Mapping(target = "contenidoMinimoPlanEstudioId", source = "contenidoMinimoPlanEstudioId.contenidoMinimoPlanEstudioId")
    @Mapping(target = "intensidadId", source = "intensidadId.intensidadId")
    TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio entidadATransferible(RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio);

    @Mapping(target = "intensidadId", ignore = true)
    @Mapping(target = "contenidoMinimoPlanEstudioId", ignore = true)
    @Mapping(target = "recomendacionCurricularId", ignore = true)
    void modificar(@MappingTarget RecomendacionCurricularXContenidoMinimoPlanEstudio recomendacionCurricularXContenidoMinimoPlanEstudio, @Valid TransferibleRecomendacionCurricularXContenidoMinimoPlanEstudio transferibleRecomendacionCurricularXContenidoMinimoPlanEstudio);
}
