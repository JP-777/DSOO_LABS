package service;
import model.*;
import repo.*;
import java.util.Scanner;

public class BibliotecaService {
    private LibroRepo libroRepo;
    private UsuarioRepo usuarioRepo;

    // Constructor con inyección de dependencias
    public BibliotecaService(LibroRepo libroRepo, UsuarioRepo usuarioRepo) {
        this.libroRepo = libroRepo;
        this.usuarioRepo = usuarioRepo;
    }
    
    public void prestarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== PRÉSTAMO DE LIBRO ===");
        System.out.print("ID de usuario: ");
        String idUsuario = scanner.nextLine();
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        
        Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
        Libro libro = libroRepo.buscarPorIsbn(isbn);
        
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        
        if (!libro.estaDisponible()) {
            System.out.println("El libro no está disponible.");
            return;
        }
        
        if (usuarioRepo.tomarPrestado(usuario, libro)) {
            System.out.println("✓ Préstamo exitoso: " + libro.getTitulo() + 
                             " para " + usuario.getNombre());
        } else {
            System.out.println("No se pudo realizar el préstamo.");
        }
    }
    
    public void devolverLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== DEVOLUCIÓN DE LIBRO ===");
        System.out.print("ID de usuario: ");
        String idUsuario = scanner.nextLine();
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        
        Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
        Libro libro = libroRepo.buscarPorIsbn(isbn);
        
        if (usuario == null || libro == null) {
            System.out.println("Usuario o libro no encontrado.");
            return;
        }
        
        if (usuarioRepo.devolverLibro(usuario, libro)) {
            System.out.println("Devolución exitosa: " + libro.getTitulo());
        } else {
            System.out.println("El usuario no tiene este libro prestado.");
        }
    }
    
    public void consultarLibrosDeUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nID de usuario: ");
        String idUsuario = scanner.nextLine();
        
        Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        
        System.out.println("\n===== LIBROS DE " + usuario.getNombre().toUpperCase() + " =====");
        if (usuario.getLibrosPrestados().isEmpty()) {
            System.out.println("No tiene libros prestados.");
        } else {
            for (Libro libro : usuario.getLibrosPrestados()) {
                System.out.println("  - " + libro.getTitulo() + " (" + libro.getISBN() + ")");
            }
        }
        System.out.println("===============================\n");
    }
    
    public void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE BIBLIOTECA     ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Agregar libro al catálogo          ║");
        System.out.println("║  2. Registrar nuevo usuario            ║");
        System.out.println("║  3. Prestar libro                      ║");
        System.out.println("║  4. Devolver libro                     ║");
        System.out.println("║  5. Mostrar todos los libros           ║");
        System.out.println("║  6. Mostrar libros disponibles         ║");
        System.out.println("║  7. Mostrar todos los usuarios         ║");
        System.out.println("║  8. Consultar libros de un usuario     ║");
        System.out.println("║  9. Buscar libro por ISBN              ║");
        System.out.println("║  0. Salir                              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }
    
    public int leerOpcion() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}