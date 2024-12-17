package com.vitor.libraryapi.service;

import com.vitor.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.repository.AutorRepository;
import com.vitor.libraryapi.repository.LivroRepository;
import com.vitor.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    // SALVAR AUTOR
    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    // ATUALIZAR AUTOR
    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException(
                    "Para atualizar e necessario que o autor ja esteja salvo na base."
            );
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    // BUSCAR AUTOR POR ID
    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    // DELETAR AUTOR POR ID
    public void deletar(Autor autor) {
        if(possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Nao é permitido excluir um Autor que possui livros cadastrados!"
            );
        }
        autorRepository.delete(autor);
    }

    // BUSCAR AUTOR POR PARAMETROS OU TODOS
    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if(nome != null & nacionalidade != null) {
            autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null) {
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    // OLHAR SE O AUTOR POSSUI LIVRO
    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }


}
