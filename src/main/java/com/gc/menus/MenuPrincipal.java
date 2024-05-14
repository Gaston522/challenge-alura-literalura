package com.gc.menus;

import com.gc.model.*;
import com.gc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MenuPrincipal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    public static final String OPCION_INVALIDA = "Opcion invalida";
    private ConsumirAPi ca = new ConsumirAPi();
    private ConvierteDatos cd = new ConvierteDatos();
    public Scanner sc = new Scanner(System.in);
    private SubMenus subMenus = new SubMenus();

    @Autowired
    public LibrosService librosService;


    public MenuPrincipal() {
    }

    public void menu() {
        System.out.println("""
                
                *********************************************************
                Elige una opcion:
                1- Buscar por titulo
                2- Buscar por autor
                3- Mostrar top 10 de libros mas descargados
                4- Listar libros registrados
                5- Listar autores registrados
                6- Listar autores vivos en un determinado año registrados
                7- Listar libros por lenguaje registrados
                                
                0- Salir
                **********************************************************
                """);

        if (sc.hasNextInt()) {
            var opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) buscarTituloOAutorEnApi(opcion);
            else if (opcion == 2) buscarTituloOAutorEnApi(opcion);
            else if (opcion == 3) topDescargas();
            else if (opcion == 4) mostrarLibrosDB();
            else if (opcion == 5) mostrarAutoresDB();
            else if (opcion == 6) autoresEnEpocaDB();
            else if (opcion == 7) mostrarLibrosPorLenguajeDB();
            else if (opcion == 0) subMenus.confirmar(this);
            else {
                System.out.println(OPCION_INVALIDA);
                menu();
            }
        } else {
            System.out.println(OPCION_INVALIDA);
            sc.next();
            menu();
        }
    }

    public void buscarTituloOAutorEnApi(int opcion) {
        String palabra;
        if(opcion == 1)palabra = "libro";
        else palabra = "autor";

        System.out.println("\n" + "Ingrese el nombre del " + palabra + " que desea buscar" + "\n");

        var tituloLibro = sc.nextLine();
        var json = ca.obtenerLibros(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
        var datosBusqueda = cd.obtenerDatos(json, Libros.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {

            DatosLibros datosLibros = libroBuscado.get();

            if (opcion == 1) {
                System.out.println("\n" + "***************** Libro Encontrado *****************" + "\n"
                        + "Titulo: " + libroBuscado.get().titulo() + "\n"
                        + "Autores: " + libroBuscado.get().autores() + "\n"
                        + "Idioma: " + libroBuscado.get().idiomas().getFirst()
                        + "\n" + "****************************************************" + "\n");
            }else {
                System.out.println("\n" + "***************** Autor Encontrado *****************" + "\n"
                        +"Autores: " + libroBuscado.get().autores() + "\n"
                        + "Titulo: " + libroBuscado.get().titulo() + "\n"
                        + "Idioma: " + libroBuscado.get().idiomas().getFirst()
                        + "\n" + "****************************************************" + "\n");
            }

            subMenus.guardar(this, datosLibros);
        } else {
            System.out.println("\n" + "No se pudo encontrar el " + palabra + " ingresado" + "\n");
            subMenus.volverOSalir(this);
        }
    }

    private void topDescargas(){
        var json = ca.obtenerLibros(URL_BASE + "?popular");
        var top = cd.obtenerDatos(json, Libros.class);

        List<Libro> topLibro = top.resultados().stream()
                .map(Libro::new)
                .collect(Collectors.toList());

        System.out.println("************************** Libros registrados **************************"+"\n"
                            + topLibro.stream().limit(10)
                                    .map(Libro::toString)
                                    .collect(Collectors.joining("\n"))
                            + "****************************************************");

        subMenus.volverOSalir(this);
    }

    private void mostrarLibrosDB() {
        librosService.listarLibros().stream().forEach(System.out::println);
        subMenus.volverOSalir(this);
    }

    private void mostrarAutoresDB() {
        librosService.listarAutores().stream().forEach(System.out::println);
        subMenus.volverOSalir(this);
    }

    private void autoresEnEpocaDB() {
        List<Autor> autor = librosService.listarAutores();

        System.out.println("\n" + "Ingresa el año" + "\n");

        if (sc.hasNextInt()) {
            var opcion = sc.nextInt();

            List<Autor> autoresFiltrados = autor.stream()
                    .filter(a -> a.getFallecimiento() > opcion
                            && opcion > a.getNacimiento() )
                    .collect(Collectors.toList());

            autoresFiltrados.forEach(System.out::println);

            if(autoresFiltrados.size() == 0) System.out.println("No se encontraron autores en ese año");
            subMenus.volverOSalir(this);
        }else {
            System.out.println(OPCION_INVALIDA);
            autoresEnEpocaDB();
        }
    }

    private void mostrarLibrosPorLenguajeDB() {
        System.out.println("""
                Ingrise el idioma para buscar los libros:
                                
                es- español
                en- ingles
                pr- portugues
                fr- frances
                                
                0- Volver
                """);

        var opcion = sc.next();
        sc.nextLine();

        if (opcion.equals("es"))filtrarPorIdioma("es");
        else if (opcion.equals("en"))filtrarPorIdioma("en");
        else if (opcion.equals("pr"))filtrarPorIdioma("pr");
        else if (opcion.equals("fr"))filtrarPorIdioma("es");
        else if (opcion.equals("0"))menu();
        else {
            System.out.println(OPCION_INVALIDA);
            mostrarLibrosPorLenguajeDB();
        }
    }

    public void filtrarPorIdioma(String idioma){
        List<Libro> librosEs = librosService.listarLibros()
                .stream().filter(libro -> libro.getIdiomas().getFirst()
                        .equals(idioma))
                .collect(Collectors.toList());

        if(librosEs.isEmpty()) System.out.println("No se encontraron libros de ese idioma");
        else librosEs.forEach(System.out::println);
        subMenus.volverOSalir(this);
    }
}
