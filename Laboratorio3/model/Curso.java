package model;

public class Curso {
    private String codigo;
    private String nombre;
    private Docente docente; // relación con la clase Docente

    public Curso(String codigo, String nombre, Docente docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.docente = docente;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }

    @Override
    public String toString() {
        return "Curso{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", docente=" + docente + // se imprimirá usando el toString() de Docente
                '}';
    }
}
