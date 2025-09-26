package model;

import java.util.ArrayList;

public class Matricula {
    private String codigo;
    private Alumno alumnoRef;
    private Curso cursoRef;
    private ArrayList<Double> notas = new ArrayList<>();
    private static final int LIMITE_NOTAS = 3;

    public Matricula (Alumno alumnoRef, Curso cursoRef){
        this.alumnoRef = alumnoRef;
        this.cursoRef = cursoRef;
    }

    public String getCodigo() { return codigo; }
    public Alumno getAlumnoRef() { return alumnoRef; }
    public Curso getCursoRef() { return cursoRef; }
    public ArrayList<Double> getNotas() { return notas; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setAlumnoRef(Alumno alumnoRef) { this.alumnoRef = alumnoRef; }
    public void setCursoRef(Curso cursoRef) { this.cursoRef = cursoRef; }

    public void agregarNota (double nota){
        if (nota < 0 || nota > 20 ) { 
            System.out.println("La nota esta fuera de los limites");
            return;
        }
        if (notas.size() < LIMITE_NOTAS){
            notas.add(nota);
        } else {
            System.out.println("haz alcanzado el limite de " + LIMITE_NOTAS + " notas.");
        }
    }

    public double calcularPromedio(){
        if (notas.isEmpty()) return 0;
        double sumatoria = 0;
        for (int i=0; i<notas.size(); i++){
            sumatoria += notas.get(i);
        }
        return Math.round((sumatoria/notas.size()) * 100.0)/ 100.0;
    }

    public boolean isAprobado(){
        return calcularPromedio() >= 10.5;
    }

    @Override
    public String toString() {
        return "Matricula{" +
            "codigo='" + codigo + '\'' +
            ", alumno=" + alumnoRef.getNombres() + " " + alumnoRef.getApellidos() +
            ", curso=" + cursoRef.getNombre() +
            ", promedio=" + calcularPromedio() +
            '}';
    }
}