package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity
public class Titulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(500)", unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDeDesacarga;
    @ManyToOne
    private Autor autors;

    public Titulo(){
    }

    public Titulo(String titulo, List<String> idioma, Integer numeroDeDesacarga, List<DatosDelAutor> autors){
       this.titulo = titulo;
       this.idioma = idioma.get(0);
       this.numeroDeDesacarga = numeroDeDesacarga;
       this.autors = new Autor(autors.get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDesacarga() {
        return numeroDeDesacarga;
    }

    public void setNumeroDeDesacarga(Integer numeroDeDesacarga) {
        this.numeroDeDesacarga = numeroDeDesacarga;
    }

    public Autor getAutors() {
        return autors;
    }

    public void setAutors(Autor autors) {
        this.autors = autors;
    }
//sobreescribiendo
    @Override
    public String toString() {
        return """
                ************* LIBRO *************
                Titulo -- %s
                Autor -- %s
                Idioma -- %s
                Descargas -- $d
                *********************************
                """.formatted(this.titulo, this.autors.getNombre(), this.idioma, this.numeroDeDesacarga);

    }

}
