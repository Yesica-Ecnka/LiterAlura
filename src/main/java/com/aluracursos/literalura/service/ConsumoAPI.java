package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//La conexión con la URL de la biblioteca
public class ConsumoAPI {
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse response;

    public ConsumoAPI() {
        client = HttpClient.newHttpClient();
    }
    //Se uso throws para no usar Try/Catch
    public String consultar(String url) throws IOException, InterruptedException {
        request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }
}

