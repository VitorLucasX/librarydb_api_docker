package com.vitor.libraryapi.controller.mappers;

import com.vitor.libraryapi.controller.dto.AutorDTO;
import com.vitor.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDto(Autor autor);
}
