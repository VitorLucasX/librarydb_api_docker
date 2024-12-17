package com.vitor.libraryapi.controller.dto;

import com.vitor.libraryapi.enums.Genero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        Genero genero,
        BigDecimal preco,
        AutorDTO autorDTO
        ) {
}
