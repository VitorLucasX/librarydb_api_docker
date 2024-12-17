package com.vitor.libraryapi.controller;

import com.vitor.libraryapi.controller.dto.AutorDTO;
import com.vitor.libraryapi.controller.dto.CadastroLivroDTO;
import com.vitor.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.vitor.libraryapi.controller.mappers.LivroMapper;
import com.vitor.libraryapi.model.Livro;
import com.vitor.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
// http://localhost:8080/livros
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO) {
        Livro livro = livroMapper.toEntity(livroDTO);
        livroService.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarLivroPorId(
            @PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarLivroPorId(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletarPorId(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
