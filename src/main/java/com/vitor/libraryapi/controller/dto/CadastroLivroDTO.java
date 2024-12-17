package com.vitor.libraryapi.controller.dto;

import com.vitor.libraryapi.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(

        @NotBlank(message = "Campo obrigatório")
        @ISBN
        String isbn,

        @NotBlank(message = "Campo obrigatório")
        String titulo,

        @NotNull(message = "Campo obrigatório")
        @Past(message = "Nao pode ser uma data futura")
        LocalDate dataPublicacao,

        Genero genero,

        BigDecimal preco,

        @NotNull(message = "Campo obrigatório")
        UUID idAutor
        ) {
}
