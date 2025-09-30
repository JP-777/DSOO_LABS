package model;
import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private ArrayList<Libro> librosPrestados;

    public Usuario(String nombre){
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
    }

    public String getNombre(){ return nombre; }
    public ArrayList<Libro> getLibrosPrestados() { return librosPrestados; }

    public void tomarPrestado (Libro libro){
        libro.setDisponible (false);
        librosPrestados.add(libro);
    }

    public void devolverLibro(Libro libro){
        libro.setDisponible(true);
        librosPrestados.remove(libro);
    }

    public boolean verificarDisponible (Libro libro) {
        return libro.isDisponible();
    }
}