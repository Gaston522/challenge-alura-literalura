package com.gc.menus;

import com.gc.model.DatosLibros;

public class SubMenus {

    public void confirmar(MenuPrincipal menuPrincipal) {
        System.out.println("""
                Estas seguro que desea salir?
                1- Si
                2- No
                """);
        if (menuPrincipal.sc.hasNextInt()) {
            var opcion = menuPrincipal.sc.nextInt();
            menuPrincipal.sc.nextLine();

            if (opcion == 1) System.out.println("Saliendo...");
            else if (opcion == 2) menuPrincipal.menu();
        }else {
            System.out.println(menuPrincipal.OPCION_INVALIDA);
            menuPrincipal.sc.next();
            confirmar(menuPrincipal);
        }
    }

    public void volverOSalir(MenuPrincipal menuPrincipal) {
        System.out.println("""
                Desea volver o salir:
                1- Volver
                2- Salir
                """);

        if (menuPrincipal.sc.hasNextInt()) {
            var opcion = menuPrincipal.sc.nextInt();
            menuPrincipal.sc.nextLine();

            if (opcion == 1)menuPrincipal.menu();
            else if (opcion == 2) confirmar(menuPrincipal);
            else {
                System.out.println(menuPrincipal.OPCION_INVALIDA);
                volverOSalir(menuPrincipal);
            }
        }else {
            System.out.println(menuPrincipal.OPCION_INVALIDA);
            menuPrincipal.sc.next();
            volverOSalir(menuPrincipal);
        }
    }

    public void guardar(MenuPrincipal menuPrincipal, DatosLibros datosLibros) {
        System.out.println("""
                Desea guardar en la base de datos?
                1- Si
                2- No
                """);

        if (menuPrincipal.sc.hasNextInt()) {
            var opcion = menuPrincipal.sc.nextInt();
            menuPrincipal.sc.nextLine();

            if (opcion == 1) {
                menuPrincipal.librosService.crearLibro(datosLibros);
                volverOSalir(menuPrincipal);
            }
            else if (opcion == 2) volverOSalir(menuPrincipal);
            else {
                System.out.println(menuPrincipal.OPCION_INVALIDA);
                guardar(menuPrincipal, datosLibros);
            }
        }else {
            System.out.println(menuPrincipal.OPCION_INVALIDA);
            menuPrincipal.sc.next();
            volverOSalir(menuPrincipal);
        }
    }
}
