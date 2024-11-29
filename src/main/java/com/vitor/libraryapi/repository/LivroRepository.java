package com.vitor.libraryapi.repository;

import com.vitor.libraryapi.enums.Genero;
import com.vitor.libraryapi.model.Autor;
import com.vitor.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // SELECT * FROM livro WHERE id_autor = 'saquivfhg23j3hjfdvhf'
    List<Livro> findByAutor(Autor autor);

    // SELECT * FROM livro WHERE titulo = 'UFO'
    List<Livro> findByTitulo(String titulo);

    // SELECT * FROM livro WHERE isbn = '183723-3747284'
    List<Livro> findByIsbn(String isbn);

    // SELECT * FROM livro WHERE titulo = 'UFO' AND preco = 100
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // SELECT * FROM livro WHERE titulo = 'UFO' OR isbn = '823348-328548'
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    // SELECT * FROM livro WHERE data_publicacao BETWEEN 10_02_2024 AND 10_04_2024
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> Referencia as entidades e as propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloAndPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileiro'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") Genero genero,
            @Param("paramOrdenacao") String nomePropriedade
    );

    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(Genero genero, String nomePropriedade);

}
