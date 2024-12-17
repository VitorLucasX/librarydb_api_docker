package com.vitor.libraryapi.controller;

import com.vitor.libraryapi.controller.dto.AutorDTO;
import com.vitor.libraryapi.controller.mappers.AutorMapper;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
// http://localhost:8080/autores
public class AutorController implements GenericController {

    @Autowired
    private AutorService autorService;

    private final AutorMapper autorMapper;

    // -> SALVAR AUTOR
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);
        // http://localhost:8080/autores/"06301151-ec2b-432c-9441-0e60cff18a05"
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    // -> BUSCAR AUTOR POR ID
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO autorDTO = autorMapper.toDto(autor);
                    return ResponseEntity.ok(autorDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // -> DELETAR AUTOR
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    // -> BUSCAR LISTA DE AUTORES
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autorMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // -> ATUALIZAR AUTOR POR ID
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @Valid @RequestBody AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }


}
