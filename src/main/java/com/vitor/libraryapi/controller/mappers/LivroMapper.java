package com.vitor.libraryapi.controller.mappers;

import com.vitor.libraryapi.controller.dto.CadastroLivroDTO;
import com.vitor.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.vitor.libraryapi.model.Livro;
import com.vitor.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);

    @Mapping(target = "autorDTO", source = "autor")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}