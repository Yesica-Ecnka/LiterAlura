package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ConvierteDatos implements IConvierteDatos {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertir(String json, Class<T> clase) { //usando datos generics
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
