package com.gc.model;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String titulo;
    @ManyToMany(targetEntity = Autor.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    List<Autor> autores;
    List<String> idiomas;
    double numeroDescargas;

    public Libro() {
    }

    public Libro(String titulo, List<String> idiomas, double numeroDescargas) {
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
        this.autores = new ArrayList<>();
    }

    public Libro(DatosLibros datos) {
        this.titulo = datos.titulo();
        this.idiomas = datos.idiomas();
        this.autores = datos.autores().stream()
                .map(datosAutores -> new Autor(datosAutores.getNombre(), datosAutores.getFechaDeNacimiento(), datosAutores.getFechaDeFallecimiento()))
                .collect(Collectors.toList());
        this.numeroDescargas = datos.numeroDescargas();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Titulo = " + titulo + "\n"
                + "Autores = " + autores.stream()
                                        .map(Autor::getNombre)
                                        .collect(Collectors.joining(", ")) + "\n"
                + "Idiomas = " + idiomas.getFirst() + "\n"
                + "Numeros de descargas = " + numeroDescargas + "\n";
    }
}

