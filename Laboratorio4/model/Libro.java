package model;
public class Libro{
    private String titulo; 
    private String autor;
    private String ISBN;
    private boolean disponible; 
    //constuctor con parámetros para inicializarlo una vez creado
    public Libro(String titulo, String autor, String ISBN, boolean disponible){
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.disponible = disponible;
    }
    //constructor vacío para crear objeto sin inicializarlo todavía
    //Se debe llenar en el orden preestablecido de los atributos
    public Libro(){
        this("", "", "", true);
    }
    //Constructor para cuando el libro es de autor anónimo
    public Libro(String titulo, String ISBN, boolean disponible){
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.disponible = disponible;
    }
    public String getTitulo (){return titulo;}
    public String getAutor (){return autor;}
    public String getISBN (){return ISBN;}
    public boolean estaDisponible (){return disponible;}
    public void setTitulo(String titulo){this.titulo = titulo;}
    public void setAutor(String autor){this.autor = autor;}
    public void setISBN(String ISBN){this.ISBN = ISBN;}
    public void setDisponible(boolean disponible){this.disponible = disponible;}
    @Override
    public String toString(){
        return "Título: "+titulo+", Autor: "+autor+", ISBN: "+ISBN+", Disponibilidad: "+(disponible);
    }
 }
// Para la clase Usuario se hizo uso de la disponibilidad del libro
    //Para el metodo accesor usar isDisponible()
    //Para el metodo mutador usar getDisponible()