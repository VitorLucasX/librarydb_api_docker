package com.vitor.libraryapi.repository;

import com.vitor.libraryapi.enums.Genero;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001,03,14));

        var autorSave = repository.save(autor);
        System.out.println("Autor salvo: " + autorSave);
    }

@Test
public void atualizarTest(){
    var id = UUID.fromString("06301151-ec2b-432c-9441-0e60cff18a05");

    Optional<Autor> possivelAutor = repository.findById(id);

    if(possivelAutor.isPresent()){

        Autor autorEncontrado =  possivelAutor.get();
        System.out.println("Dados do Autor:");
        System.out.println(autorEncontrado);

        autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

        repository.save(autorEncontrado);

    }
}

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("aa8ca482-2384-4b8c-b0de-0ec31f8cc9ed");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("45af1ac7-ae13-481e-97f6-f7956944b3c9");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1999,03,14));

        Livro livro = new Livro();
        livro.setIsbn("21746-25435");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(Genero.MISTERIO);
        livro.setTitulo("New Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("21746-25435");
        livro2.setPreco(BigDecimal.valueOf(300));
        livro2.setGenero(Genero.MISTERIO);
        livro2.setTitulo("New Livro 22");
        livro2.setDataPublicacao(LocalDate.of(1980, 1, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        //livroRepository.saveAll(autor.getLivros());

    }
}
