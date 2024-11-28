package untdf.is3.transformador;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import untdf.is3.modelo.Intensidad;
import untdf.is3.modelo.Titulo;
import untdf.is3.transferible.TransferibleIntensidad;
import untdf.is3.transferible.TransferibleTitulo;

import java.util.List;

@Mapper
public interface TransformadorTitulo {

    List<TransferibleTitulo> entidadATransferible(List<Titulo> tituloList);

    TransferibleTitulo entidadATransferible(Titulo titulo);

    @Mapping(target = "tituloId", ignore = true)
    void modificar(@MappingTarget Titulo titulo, @Valid TransferibleTitulo transferibleTitulo);
    
}
