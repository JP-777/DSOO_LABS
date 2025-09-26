package model;
import java.util.Objects;
public class Docente {
    private String dni;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private int añosExperiencia;
    public Docente(String dni, String nombres, String apellidos, String especialidad, int añosExperiencia) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getEspecialidad() { return especialidad; }
    public int getAñosExperiencia() { return añosExperiencia; }
    public void setDni(String dni) { this.dni = dni; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad;}
    public void setAñosExperiencia(int añosExperiencia) { this.añosExperiencia = añosExperiencia; }
    @Override public String toString() {
        return apellidos + ", " + nombres + " (DNI " + dni + ", " + especialidad + ", " + añosExperiencia + " años)";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Docente)) return false;
        Docente d = (Docente) o;
        return Objects.equals(dni, d.dni);
    }
    @Override public int hashCode() { return Objects.hash(dni); }
}
