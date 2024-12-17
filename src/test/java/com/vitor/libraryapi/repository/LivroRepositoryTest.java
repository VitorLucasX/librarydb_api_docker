package com.vitor.libraryapi.repository;

import com.vitor.libraryapi.enums.Genero;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("21746-25435");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(Genero.CIENCIA);
        livro.setTitulo("Ciencias UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("41a9cfcf-1831-4baa-98ac-472be537abbe"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("21746-25435");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(Genero.BIOGRAFIA);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Uniqua");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001,03,14));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("21746-25435");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(Genero.BIOGRAFIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001,03,14));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorELivro() {
        UUID id = UUID.fromString("4788a6d7-d8d1-4ceb-9ecf-c728cdec94b1");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("06301151-ec2b-432c-9441-0e60cff18a05");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("4788a6d7-d8d1-4ceb-9ecf-c728cdec94b1");
        repository.deleteById(id);
    }

    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("6a976a12-c7b6-4bd4-9b91-552a9da67f61");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("17017672-d4a2-47a8-b83b-4b5dc74cd335");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    @Transactional
    void listarLivrosAutor() {
        UUID id = UUID.fromString("06301151-ec2b-432c-9441-0e60cff18a05");
        var autor = autorRepository.findById(id).get();

        // buscar os livros do autor
        List<Livro> livrosLista = repository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = repository.findByTitulo("UFO");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbn() {
        List<Livro> lista = repository.findByIsbn("21746-25435");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloAndPreco() {
        var preco = BigDecimal.valueOf(100);
        String tituloPesquisa = "UFO";

        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloOrIsbn() {
        String isbn = "21746-25435";
        String tituloPesquisa = "UFO";

        List<Livro> lista = repository.findByTituloOrIsbnOrderByTitulo(isbn, tituloPesquisa);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL() {
        var resultado = repository.listarTodosOrdenadosPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresComQueryJPQL() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosDistintosComQueryJPQL() {
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileirosComQueryJPQL() {
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam() {
        var resultado = repository.findByGenero(Genero.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }
    @Test
    void listarPorGeneroPositionalQueryParam() {
        var resultado = repository.findByGeneroPositionalParameters(Genero.MISTERIO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletarLivroPorGenero() {
        repository.deleteByGenero(Genero.CIENCIA);
    }

    @Test
    void atualizarDataLivro() {
        repository.updateDataPublicacao(LocalDate.of(1950,03,01));
    }
}