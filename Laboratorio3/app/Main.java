package app;

import service.SistemaAcademicoService;
import model.*;
import repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static SistemaAcademicoService sistema;

    public static void main(String[] args) {
        AlumnoRepo alumnoRepo   = new AlumnoRepo();
        DocenteRepo docenteRepo = new DocenteRepo();
        CursoRepo cursoRepo     = new CursoRepo();
        MatriculaRepo matrRepo  = new MatriculaRepo();

        sistema = new SistemaAcademicoService(alumnoRepo, docenteRepo, cursoRepo, matrRepo);

        int op;
        do {
            menu();
            op = leerEntero("Opción: ");
            try {
                switch (op) {
                    case 1 -> registrarAlumno();
                    case 2 -> registrarDocente();
                    case 3 -> registrarCurso();
                    case 4 -> matricularAlumno();
                    case 5 -> registrarNotas();
                    case 6 -> promedioAlumnoEnCurso();
                    case 7 -> mostrarMejorAlumno();
                    case 8 -> listarAprobadosDesaprobados();
                    case 9 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        } while (op != 9);
    }

    private static void menu() {
        System.out.println("=== Sistema Académico ===");
        System.out.println("1) Registrar alumno");
        System.out.println("2) Registrar docente");
        System.out.println("3) Registrar curso (asociar a docente)");
        System.out.println("4) Matricular alumno en curso");
        System.out.println("5) Registrar 3 notas [0–20]");
        System.out.println("6) Calcular promedio de un alumno en un curso");
        System.out.println("7) Mostrar alumno con mejor promedio general");
        System.out.println("8) Listar aprobados y desaprobados por curso");
        System.out.println("9) Salir");
    }

    private static void registrarAlumno() {
        System.out.println("Registro de Alumno");
        String codigo = leerTexto("Código único: ");
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String dni = leerTexto("DNI: ");
        int edad = leerEntero("Edad: ");
        sistema.agregarAlumno(codigo, nombres, apellidos, dni, edad);
        System.out.println("Alumno registrado.");
    }

    private static void registrarDocente() {
        System.out.println("Registro de Docente");
        String dni = leerTexto("DNI (único): ");
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String esp = leerTexto("Especialidad: ");
        int anios = leerEntero("Años de experiencia: ");
        sistema.agregarDocente(dni, nombres, apellidos, esp, anios);
        System.out.println("Docente registrado.");
    }

    private static void registrarCurso() {
        if (sistema.listarDocentes().isEmpty()) {
            System.out.println("Primero registre docentes.");
            return;
        }
        System.out.println("Docentes disponibles:");
        for (Docente d : sistema.listarDocentes()) System.out.println(" - " + d);

        String codigo = leerTexto("Código del curso: ");
        String nombre = leerTexto("Nombre del curso: ");
        String dniDoc = leerTexto("DNI del docente responsable: ");
        sistema.agregarCurso(codigo, nombre, dniDoc);
        System.out.println("Curso registrado.");
    }

    private static void matricularAlumno() {
        if (sistema.listarAlumnos().isEmpty() || sistema.listarCursos().isEmpty()) {
            System.out.println("Debe existir al menos 1 alumno y 1 curso.");
            return;
        }
        System.out.println("Alumnos:");
        for (Alumno a : sistema.listarAlumnos()) System.out.println(" - " + a);
        String codAlm = leerTexto("Código del alumno: ");

        System.out.println("Cursos:");
        for (Curso c : sistema.listarCursos()) System.out.println(" - " + c);
        String codCrs = leerTexto("Código del curso: ");

        sistema.agregarMatricula(codAlm, codCrs);
        System.out.println("Matrícula registrada.");
    }

    private static void registrarNotas() {
        String codAlm = leerTexto("Código del alumno: ");
        String codCrs = leerTexto("Código del curso: ");

        List<Double> notas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            notas.add(leerDoubleEnRango("Nota " + i + " [0-20]: ", 0, 20));
        }
        sistema.registrarNotas(codAlm, codCrs, notas);
        System.out.println("Notas registradas.");
    }

    private static void promedioAlumnoEnCurso() {
        String codAlm = leerTexto("Código del alumno: ");
        String codCrs = leerTexto("Código del curso: ");
        double p = sistema.promedioDe(codAlm, codCrs);
        System.out.printf("Promedio: %.2f → %s%n", p, (p >= 11 ? "APROBADO" : "DESAPROBADO"));
    }

    private static void mostrarMejorAlumno() {
        var entry = sistema.mejorAlumno();
        if (entry == null) {
            System.out.println("Aún no hay promedios registrados.");
        } else {
            System.out.printf("Mejor alumno: %s con promedio general %.2f%n",
                    entry.getKey(), entry.getValue());
        }
    }

    private static void listarAprobadosDesaprobados() {
        String codCrs = leerTexto("Código del curso: ");
        var map = sistema.aprobadosYDesaprobadosPorCurso(codCrs);
        System.out.println("-- Aprobados --");
        for (Matricula m : map.get("aprobados")) {
            System.out.printf("%s -> prom=%.2f%n", m.getAlumnoRef(), m.calcularPromedio());
        }
        System.out.println("-- Desaprobados --");
        for (Matricula m : map.get("desaprobados")) {
            System.out.printf("%s -> prom=%.2f%n", m.getAlumnoRef(), m.calcularPromedio());
        }
    }

    private static String leerTexto(String label) {
        System.out.print(label);
        String s = sc.nextLine().trim();
        while (s.isEmpty()) {
            System.out.print("No puede estar vacío. " + label);
            s = sc.nextLine().trim();
        }
        return s;
    }

    private static int leerEntero(String label) {
        while (true) {
            System.out.print(label);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Ingrese un entero válido."); }
        }
    }

    private static double leerDoubleEnRango(String label, double min, double max) {
        while (true) {
            System.out.print(label);
            try {
                double v = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (v < min || v > max) throw new IllegalArgumentException();
                return v;
            } catch (Exception e) {
                System.out.printf("Ingrese un número en [%.1f, %.1f].%n", min, max);
            }
        }
    }
}
