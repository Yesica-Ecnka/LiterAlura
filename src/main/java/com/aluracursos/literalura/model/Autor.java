package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;
    @OneToMany(mappedBy = "autors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Titulo> titulos = new ArrayList<>();
//constructor vacío
    public Autor() {
    }
//constructor lleno
    public Autor(DatosDelAutor datosDelAutor, Titulo titulo){
        this.nombre = datosDelAutor.nombre();
        this.fechaDeNacimiento = datosDelAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosDelAutor.fechaDeMuerte();
        this.titulos.add(titulo);
    }

    public void addTitulo(Titulo titulo){
        this.titulos.add(titulo);
    }

    public Autor(DatosDelAutor datosDelAutor){
        this.nombre = datosDelAutor.nombre();
        this.fechaDeNacimiento = datosDelAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosDelAutor.fechaDeMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    //sobreescribiendo
    @Override
    public String toString() {
        return """
                Autor -- %s
                Fecha de Nacimiento -- %d
                Libros -- %s
                """.formatted(this.nombre, this.fechaDeNacimiento, this.fechaDeMuerte,
                this.titulos.stream().map(Titulo::getTitulo).toList().toString());
    }


}
