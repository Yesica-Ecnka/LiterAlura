package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosResultado;
import com.aluracursos.literalura.model.Titulo;
import com.aluracursos.literalura.repositorio.repositorioDelAutor;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private String URL_BOOKS = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private repositorioDelAutor repositorioDelAutor;
    Scanner teclado = new Scanner(System.in);

    public Principal(repositorioDelAutor repositorioDelAutor){
        this.repositorioDelAutor = repositorioDelAutor;
    }

    public void mostrarMenu() {
        int opcion = 0;
        boolean bucleW = true;
        String menu = """
                    *****************************************************
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Listar Libros por autor
                    7 - Listar 10 más descargados registrados
                    0 - Salir
                    *****************************************************
                    """;
        while (bucleW) {
            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        mostrarLibrosRegistrados();
                        break;
                    case 3:
                        mostrarAutores();
                        break;
                    case 4:
                        mostrarChequeDeAño();
                        break;
                    case 5:
                        mostrarMenuDeIdiomas();
                        break;
                    case 6:
                        buscarTitulosPorNombre();
                        break;
                    case 7:
                        mostrarMasDescargados();
                        break;
                    case 0:
                        bucleW = false;
                        break;
                    default:
                        System.out.println("Opción no válida. :/");
                }

            } catch (Exception e) {
                System.out.println("Error! Intente de nuevo, por favor." + e.getMessage());
                teclado.nextLine();
            }
        }
    }

    private void mostrarMasDescargados() {
        List<Titulo> titulosTemp = repositorioDelAutor.masDescargados();
        titulosTemp.forEach(System.out::println); //usando forEach
    }

    private void buscarTitulosPorNombre() {
        System.out.println("Escribe el nombre del autor, por favor :] ");
        String nombreAutor = teclado.nextLine();
        List<Titulo> titulosTemp = repositorioDelAutor.tituloPorAutor(nombreAutor);
        if (titulosTemp.isEmpty()){
            System.out.println("Sin resultados, lo lamento :/ ");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarMenuDeIdiomas() {
        // Listado de idiomas existentes :)
        System.out.println("""
                -----------------------------
                es: Español
                pt: Portugués
                fr: Francés
                en: Inglés
                -----------------------------
                """);
        System.out.println("Ingrese el idioma para buscar los libros, por favor: ");
        String entrada = teclado.nextLine();
        List<Titulo>  titulosTemp = repositorioDelAutor.listaTitulosPorIdioma(entrada);
        if (titulosTemp.isEmpty()){
            System.out.println("Sin resultados, lo lamento :/ ");
        }
        titulosTemp.forEach(System.out::println);
    }

//este es para mostrar a los cool autores
    private void mostrarAutores() {
        repositorioDelAutor.findAll().forEach(System.out::println);
    }

    private void mostrarChequeDeAño() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar, por favor");
        Integer year = teclado.nextInt();
        teclado.nextLine();

        List<Autor>  autorsTemp = repositorioDelAutor.liveYearCheck(year);
        if (autorsTemp.isEmpty()){
            System.out.println("Sin resultados");
        }
        autorsTemp.forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        repositorioDelAutor.todosLosTitulos().forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException{
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String nombreT = teclado.nextLine();
        DatosResultado consulta = buscarLibro(nombreT);
        Optional<Titulo> titulo = consulta.resultados().stream()
                .map(r -> new Titulo(
                        r.titulo(),
                        r.idiomas(),
                        r.numeroDeDescargas(),
                        r.autores()
                )).
                findFirst();
        if (titulo.isPresent()) {
            Titulo tituloTemp = titulo.get();
            Autor autorTemp = tituloTemp.getAutors();
            if (repositorioDelAutor.buscarTituloPorNombre(tituloTemp.getTitulo()).isPresent()) {
                System.out.println("No se puede registrar este libro más de una vez");
            } else if (repositorioDelAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).isPresent()) {
                tituloTemp.setAutors(repositorioDelAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).get());
                tituloTemp.getAutors().addTitulo(tituloTemp);
                repositorioDelAutor.save(tituloTemp.getAutors());
                System.out.println(tituloTemp);
            } else {
                titulo.get().getAutors().addTitulo(titulo.get());
                repositorioDelAutor.save(titulo.get().getAutors());
                System.out.println(tituloTemp);
            }

        } else {
            System.out.println("Titulo no encontrado");
        }

    }

    private DatosResultado buscarLibro(String titulo) throws IOException, InterruptedException {
        return convierteDatos.convertir(new String(URL_BOOKS.concat(URL_BOOKS+"?search="+titulo.replace(" ","+"))), DatosResultado.class);
    }


}
