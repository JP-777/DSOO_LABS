package model;

public class Paciente {
    private int codigo;
    private String nombre;
    private int edad;
    private int dni;

    public Paciente(int codigo, String nombre, int edad, int dni) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public int getDni() { return dni; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDni(int dni) { this.dni = dni; }

    @Override
    public String toString() {
        return "Paciente{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", documento='" + dni + '\'' +
                '}';
    }
}