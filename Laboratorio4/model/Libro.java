package model;
 public class Libro{
    private String titulo;
    private String autor;
    private String ISBN;
    private boolean disponible;
    
    public Libro(String titulo, String autor, String ISBN, boolean disponible){
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.disponible = disponible;
    }

    public String getTitulo (){return titulo;}
    public String getAutor (){return autor;}
    public String getISBN (){return ISBN;}
    public boolean estaDisponible (){return disponible;}
    
 }
// Para la clase Usuario se hizo uso de la disponibilidad del libro
    //Para el metodo accesor usar isDisponible()
    //Para el metodo mutador usar getDisponible()