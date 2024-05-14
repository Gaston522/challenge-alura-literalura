package com.gc.service;

import com.gc.model.*;
import com.gc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrosService {

    @Autowired
    private LibroRepository libroRepo;

    @Autowired
    private AutorRepository autorRepo;

    public void crearLibro(DatosLibros datosLibros){

        Libro libroRegistrado = libroRepo.findByTitulo(datosLibros.titulo());
        Autor autorRegistrado = autorRepo.findByNombre(datosLibros.autores().getFirst().getNombre());

        if (libroRegistrado == null && autorRegistrado == null) {
            Libro libro = new Libro(datosLibros);
            libroRepo.save(libro);
            System.out.println("Guardado con 111111");
        }else if(autorRegistrado != null){
            Libro libroSinAutor = new Libro(datosLibros.titulo(), datosLibros.idiomas(), datosLibros.numeroDescargas());
            autorRegistrado.agregarLibro(libroSinAutor);
            autorRepo.save(autorRegistrado);
            System.out.println("Guardado con 222222");
        }else if(libroRegistrado != null){
            System.out.println("El libro ya estaba registrado");
        }
    }

    public List<Libro> listarLibros(){
        return libroRepo.findAll();
    }

    public List<Autor> listarAutores(){
        return autorRepo.findAll();
    }
}
