package repo;

import model.Libro;
import model.Usuario;

public class UsuarioRepo {

//metodo para tomar un libro prestado
    public boolean tomarPrestado(Usuario usuario, Libro libro){
        if(libro.estaDisponible() && !usuario.getLibrosPrestados().contains(libro)){
            libro.setDisponible(false);
            usuario.getLibrosPrestados().add(libro);
            return true; }
        return false; }

    // metodo para devolver un libro
    public boolean devolverLibro(Usuario usuario, Libro libro){
        if(usuario.getLibrosPrestados().contains(libro)){
            libro.setDisponible(true);
            usuario.getLibrosPrestados().remove(libro);
            return true;}
         return false; }

    //metodo para verificar disponibilidad de un libro
    public boolean verificarDisponible(Libro libro) {
        return libro.estaDisponible(); }

    // metodo para verificar si el usuario ya tiene prestado un libro
    public boolean tieneLibro(Usuario usuario, Libro libro){
        return usuario.getLibrosPrestados().contains(libro);
    }

    // metodo para listar todos los libros prestados
    public void listarLibrosPrestados(Usuario usuario){
        if(usuario.getLibrosPrestados().isEmpty()){
            System.out.println(usuario.getNombre() + " no tiene libros prestados.");
        } else {
            System.out.println("Libros prestados por " + usuario.getNombre() + ":");
            for(Libro libro : usuario.getLibrosPrestados()){
                System.out.println(" - " + libro);
            }
        }
    }
}
