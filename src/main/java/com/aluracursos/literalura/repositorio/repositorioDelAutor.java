package com.aluracursos.literalura.repositorio;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface repositorioDelAutor extends JpaRepository<Autor, Long> {

    @Query("SELECT t FROM Autor a JOIN a.titulos t WHERE a.nombre = :nombreAutor")
    List<Titulo> porTitulo(String nombreAutor);

    @Query("SELECT t FROM Autor a JOIN a.titulos t WHERE a.nombre ILIKE %:nombreAutor%")
    List<Titulo> tituloPorAutor(String nombreAutor);

    @Query("SELECT t FROM Titulo t")
    List<Titulo> todosLosTitulos();

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :year AND a.fechaDeMuerte >= :year")
    List<Autor> liveYearCheck(Integer year);

    @Query("SELECT DISTINCT t.idioma FROM Titulo t")
    List<String> listaIdiomas();

    @Query("SELECT t FROM Titulo t WHERE t.idioma =:acronimo")
    List<Titulo> listaTitulosPorIdioma(String acronimo);

    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.nombre = %:nombreAutor%")
    Optional<Autor> buscarPorNombreAutor(String nombreAutor);

    @Query("SElECT t FROM Titulo t WHERE t.titulo = %:nombreTitulo%")
    Optional<Titulo> buscarTituloPorNombre(String nombreTitulo);

    @Query("SELECT t FROM Titulo t ORDER BY t.numeroDeDesacarga DESC LIMIT 10")
    List<Titulo> masDescargados();

}
