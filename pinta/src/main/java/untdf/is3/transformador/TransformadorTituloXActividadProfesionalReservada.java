package untdf.is3.transformador;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import untdf.is3.modelo.Titulo;
import untdf.is3.modelo.TituloXActividadProfesionalReservada;
import untdf.is3.transferible.TransferibleTitulo;
import untdf.is3.transferible.TransferibleTituloXActividadProfesionalReservada;

import java.util.List;

@Mapper
public interface TransformadorTituloXActividadProfesionalReservada {

    List<TransferibleTituloXActividadProfesionalReservada> entidadATransferible(List<TituloXActividadProfesionalReservada> tituloXActividadProfesionalReservadaList);

    @Mapping(target = "tituloId", source = "tituloId.tituloId")
    @Mapping(target = "actividadProfesionalReservadaId", source = "actividadProfesionalReservadaId.actividadProfesionalReservadaId")
    TransferibleTituloXActividadProfesionalReservada entidadATransferible(TituloXActividadProfesionalReservada tituloXActividadProfesionalReservada);

}
