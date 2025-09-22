package model;

import java.util.Objects;

public class Docente {
    private String dni;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private int aniosExperiencia;

    public Docente(String dni, String nombres, String apellidos, String especialidad, int aniosExperiencia) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getEspecialidad() { return especialidad; }
    public int getAniosExperiencia() { return aniosExperiencia; }

    @Override public String toString() {
        return apellidos + ", " + nombres + " (DNI " + dni + ", " + especialidad + ", " + aniosExperiencia + " años)";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Docente)) return false;
        Docente d = (Docente) o;
        return Objects.equals(dni, d.dni);
    }
    @Override public int hashCode() { return Objects.hash(dni); }
}


package repo;

import model.Docente;
import util.Validations;

import java.util.ArrayList;

public class DocenteRepo {
    private final ArrayList<Docente> data = new ArrayList<>();

    public void add(Docente d) {
        Validations.notBlank(d.getDni(), "dni");
        if (findByDni(d.getDni()) != null) throw new IllegalArgumentException("DNI de docente repetido");
        data.add(d);
}
