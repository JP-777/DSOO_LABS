package model;

import java.util.ArrayList;

public class Matricula {
    private String idMatricula;
    private Alumno alumnoRef;
    private Curso cursoRef;
    private ArrayList<Double> notas;
    private static final int LIMITE_NOTAS = 3;

}