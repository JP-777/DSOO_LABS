package repo;
import model.Libro;
import java.util.ArrayList;
import java.util.Scanner;

public class LibroRepo {
    private ArrayList<Libro> libros;
    private Scanner scanner;

    public LibroRepo(Scanner scanner) {
        this.libros = new ArrayList<>();
        this.scanner = scanner;
    }

    // Método con interacción para agregar un libro
    public void agregarLibro() {
        System.out.println("\n=== AGREGAR NUEVO LIBRO ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        
        Libro libro = new Libro(titulo, autor, isbn);
        libros.add(libro);
        System.out.println("✓ Libro agregado exitosamente.");
    }

    // Método con interacción para buscar y mostrar libro por ISBN
    public void buscarYMostrarPorIsbn() {
        System.out.print("\nISBN del libro: ");
        String isbn = scanner.nextLine();
        Libro libro = buscarPorIsbn(isbn);
        
        if (libro != null) {
            System.out.println("\n✓ Libro encontrado:");
            System.out.println(libro);
        } else {
            System.out.println("\n❌ Libro no encontrado.");
        }
    }

    // Método interno para buscar libro (usado por otros métodos)
    public Libro buscarPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getISBN().equalsIgnoreCase(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // Método con interacción para mostrar todos los libros
    public void mostrarTodos() {
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros en el catálogo.");
            return;
        }
        
        System.out.println("\n========== CATÁLOGO DE LIBROS ==========");
        for (int i = 0; i < libros.size(); i++) {
            System.out.println((i + 1) + ". " + libros.get(i));
        }
        System.out.println("=========================================\n");
    }
    
    // Método con interacción para mostrar libros disponibles
    public void mostrarDisponibles() {
        System.out.println("\n===== LIBROS DISPONIBLES =====");
        boolean hayDisponibles = false;
        for (Libro libro : libros) {
            if (libro.estaDisponible()) {
                System.out.println("  " + libro);
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay libros disponibles actualmente.");
        }
        System.out.println("==============================\n");
    }
}