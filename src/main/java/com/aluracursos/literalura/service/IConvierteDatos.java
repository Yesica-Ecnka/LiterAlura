package com.aluracursos.literalura.service;

public interface IConvierteDatos {
    <T> T convertir(String json, Class<T> clase);
}
