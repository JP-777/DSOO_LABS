package model;
import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String id;
    private ArrayList<Libro> librosPrestados;

    public Usuario(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.librosPrestados = new ArrayList<>();
    }

    // Constructor sobrecargado - solo nombre (genera ID automático)
    public Usuario(String nombre) {
        this(nombre, "USR" + System.currentTimeMillis());
    }

    public String getNombre(){ return nombre; }
    public String getId() { return id; }
    public ArrayList<Libro> getLibrosPrestados() { return librosPrestados; }

    public boolean tieneLibro(Libro libro) {
        return librosPrestados.contains(libro);
    }

    public int cantidadLibrosPrestados() {
        return librosPrestados.size();
    }

    @Override
    public String toString() {
        return "Usuario: " + nombre + " | ID: " + id + 
               " | Libros prestados: " + librosPrestados.size();
    }
}