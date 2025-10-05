package app;

import service.BibliotecaService;
import repo.*;

public class Main {
    public static void main(String[] args) {
        
        LibroRepo catalogo = new LibroRepo();
        UsuarioRepo usuarios = new UsuarioRepo();
        
        BibliotecaService servicio = new BibliotecaService(catalogo, usuarios);
    
        boolean salir = false;
        
        while (!salir) {
            servicio.mostrarMenu();
            int opcion = servicio.leerOpcion();
            
            switch (opcion) {
                case 1: catalogo.agregarLibro(); break;
                case 2: usuarios.registrarUsuario(); break;
                case 3: servicio.prestarLibro(); break;
                case 4: servicio.devolverLibro(); break;
                case 5: catalogo.mostrarTodos(); break;
                case 6: catalogo.mostrarDisponibles(); break;
                case 7: usuarios.mostrarTodos(); break;
                case 8: servicio.consultarLibrosDeUsuario(); break;
                case 9: catalogo.buscarYMostrarPorIsbn(); break;
                case 0: salir = true; System.out.println("\n¡Gracias por usar el sistema de biblioteca!"); break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}