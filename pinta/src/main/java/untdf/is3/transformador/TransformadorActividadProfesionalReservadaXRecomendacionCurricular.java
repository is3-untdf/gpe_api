package untdf.is3.transformador;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import untdf.is3.modelo.ActividadProfesionalReservadaXRecomendacionCurricular;
import untdf.is3.transferible.TransferibleActividadProfesionalReservadaXRecomendacionCurricular;

import java.util.List;

@Mapper
public interface TransformadorActividadProfesionalReservadaXRecomendacionCurricular {

    List<TransferibleActividadProfesionalReservadaXRecomendacionCurricular> entidadATransferible(List<ActividadProfesionalReservadaXRecomendacionCurricular> actividadProfesionalReservadaXRecomendacionCurricularList);

    @Mapping(target = "actividadProfesionalReservadaId", source = "actividadProfesionalReservadaId.actividadProfesionalReservadaId")
    @Mapping(target = "recomendacionCurricularId", source = "recomendacionCurricularId.recomendacionCurricularId")
    TransferibleActividadProfesionalReservadaXRecomendacionCurricular entidadATransferible(ActividadProfesionalReservadaXRecomendacionCurricular actividadProfesionalReservadaXRecomendacionCurricular);

}
