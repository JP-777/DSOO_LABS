package repo;

import model.Libro;
import java.util.ArrayList;

//clase repositorio que gestiona los libros
public class LibroRepo {
    private final ArrayList<Libro> libros = new ArrayList<>();

    //metodo para agregar un libro
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    //metodo para buscar libro
    public Libro buscarPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equalsIgnoreCase(isbn)) {
                return libro;
            }
        }
        return null; //si no encuentra
    }

    //metodo para mostrar todos los libros
    public ArrayList<Libro> mostrarTodos() {
        return new ArrayList<>(libros);
    }
}