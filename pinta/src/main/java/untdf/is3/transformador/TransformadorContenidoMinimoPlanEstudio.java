package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.ContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudio;
import untdf.is3.transferible.TransferibleContenidoMinimoPlanEstudioConDependencias;

import java.util.List;

@Mapper
public interface TransformadorContenidoMinimoPlanEstudio {

    List<TransferibleContenidoMinimoPlanEstudio> entidadATransferible(List<ContenidoMinimoPlanEstudio> contenidoMinimoPlanEstudioList);

    List<TransferibleContenidoMinimoPlanEstudioConDependencias> entidadATransferibleConDependencias(List<ContenidoMinimoPlanEstudio> contenidoMinimoPlanEstudios);

    @Mapping(target = "intensidadId", source = "intensidadId.intensidadId")
    @Mapping(target = "intensidad", source = "intensidadId")
    @Mapping(target = "asignaturaId", source = "asignaturaId.asignaturaId")
    @Mapping(target = "asignatura", source = "asignaturaId")
    @Mapping(target = "planEstudioId", source = "planEstudioId.planEstudioId")
    @Mapping(target = "planEstudio", source = "planEstudioId")
    TransferibleContenidoMinimoPlanEstudioConDependencias entidadATransferibleConDependencias(ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio);

    @Mapping(target = "asignaturaId", source = "asignaturaId.asignaturaId")
    @Mapping(target = "intensidadId", source = "intensidadId.intensidadId")
    @Mapping(target = "planEstudioId", source = "planEstudioId.planEstudioId")
    TransferibleContenidoMinimoPlanEstudio entidadATransferible(ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio);

    @Mapping(target = "asignaturaId", ignore = true)
    @Mapping(target = "intensidadId", ignore = true)
    @Mapping(target = "contenidoMinimoPlanEstudioId", ignore = true)
    @Mapping(target = "planEstudioId", ignore = true)
    void modificar(@MappingTarget ContenidoMinimoPlanEstudio contenidoMinimoPlanEstudio, @Valid TransferibleContenidoMinimoPlanEstudio transferibleContenidoMinimoPlanEstudio);


}
