package com.gc.model;

import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int nacimiento;
    private int fallecimiento;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Libro> libro;

    public Autor(){
    }

    public Autor(String nombre, int nacimiento, int fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    public Autor(DatosAutores d, List<Libro> libro) {
        this.nombre = d.getNombre();
        this.nacimiento = d.getFechaDeNacimiento();
        this.fallecimiento = d.getFechaDeFallecimiento();
        this.libro = libro;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(int fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> l) {
        this.libro = l;
    }

    @Override
    public String toString() {
        return "Nombre = " + nombre + "\n"
                + "Fecha de nacimiento = " + nacimiento + "\n"
                + "Fecha de fallecimiento = " + fallecimiento + "\n"
                + "Libros registrados = " + libro.stream()
                                                    .map(Libro::getTitulo)
                                                    .collect(Collectors.joining(", "))+ "\n";
    }

    public void agregarLibro(Libro l){
        this.libro.add(l);
        l.getAutores().add(this);
    }
}
