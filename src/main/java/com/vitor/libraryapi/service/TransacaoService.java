package com.vitor.libraryapi.service;

import com.vitor.libraryapi.enums.Genero;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.model.Livro;
import com.vitor.libraryapi.repository.AutorRepository;
import com.vitor.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    public void salvarLivroComFoto() {
        // salva o livro
        // repository.save(livro)

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome do arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png")
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("5272dcc6-aa5e-4679-9522-a636af255d2d"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2001, 03, 14));
    }

    @Transactional
    public void executar() {
            // salvar o autor
            Autor autor = new Autor();
            autor.setNome("Marcelo");
            autor.setNacionalidade("Brasileiro");
            autor.setDataNascimento(LocalDate.of(2001,03,14));

            autorRepository.save(autor);

            // salva o livro
            Livro livro = new Livro();
            livro.setIsbn("01746-45435");
            livro.setPreco(BigDecimal.valueOf(300));
            livro.setGenero(Genero.BIOGRAFIA);
            livro.setTitulo("Yasuo Open");
            livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

            livro.setAutor(autor);

            livroRepository.save(livro);

            if(autor.getNome().equals("Francisca")) {
                throw new RuntimeException("Rollback!");
            }
    }
}
