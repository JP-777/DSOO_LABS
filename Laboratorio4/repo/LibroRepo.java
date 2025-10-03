package repo;

import model.Libro;
import java.util.ArrayList;

//clase repositorio que gestiona los libros
public class LibroRepo {
    private ArrayList<Libro> libros;

    public LibroRepo() {
        this.libros = new ArrayList<>();
    }

    // metodo para agregar un libro
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    // metodo para buscar libro
    public Libro buscarPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getISBN().equalsIgnoreCase(isbn)) {
                return libro;
            }
        }
        return null; // si no encuentra
    }

    // Método para buscar libros por título
    public ArrayList<Libro> buscarPorTitulo(String titulo) {
        ArrayList<Libro> encontrados = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                encontrados.add(libro);
            }
        }
        return encontrados;
    }

    // Método para obtener todos los libros
    public ArrayList<Libro> obtenerTodos() {
        return new ArrayList<>(libros);
    }

    // Método para obtener solo libros disponibles
    public ArrayList<Libro> obtenerDisponibles() {
        ArrayList<Libro> disponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.estaDisponible()) {
                disponibles.add(libro);
            }
        }
        return disponibles;
    }
}