package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTitulos(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosDelAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("ownload_count") Integer numeroDeDescargas
) {
}
