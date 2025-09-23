

import java.util.Objects;

public class Alumno {
    private String codigo;
    private String nombres;
    private String apellidos;
    private String dni;
    private int edad;

    // constructor inicializado
    public Alumno(String codigo, String nombres, String apellidos, String dni, int edad) {
        this.codigo =codigo;
        this.nombres = nombres;
        this.apellidos =apellidos;
        this.dni= dni;
        this.edad =edad;
    }

    // getters y setters
    public String getCodigo() {
        return codigo;}

    public void setCodigo(String codigo) {
        this.codigo = codigo;}

    public String getNombres() {
        return nombres;}

    public void setNombres(String nombres) {
        this.nombres = nombres;}

    public String getApellidos() {
        return apellidos;}

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;}

    public String getDni() {
        return dni; }

    public void setDni(String dni) {
        this.dni = dni;}

    public int getEdad() {
        return edad;}

    public void setEdad(int edad) {
        this.edad = edad;}

    // metodo toString
    @Override
    public String toString() {
        return "Alumno{" +
        "codigo='" + codigo + '\'' +
        ", nombres='" + nombres + '\'' +
        ", apellidos='" + apellidos + '\'' +
        ", dni='" + dni + '\'' +
        ", edad=" + edad +
        '}';
    }

    // metodo equals y hashCode 
    @Override
    public boolean equals(Object o) {
         if (this == o) return true;
        if (!(o instanceof Alumno)) return false;
         Alumno alumno = (Alumno) o;
         return Objects.equals(codigo, alumno.codigo) &&
               Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, dni);
    }
}
