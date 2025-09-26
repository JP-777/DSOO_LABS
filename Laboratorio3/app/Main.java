package app;

import service.SistemaAcademicoService;
import model.*;
import repo.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final Scanner sc = new Scanner(System.in);
    public static SistemaAcademicoService sistema;

    public static void main(String[] args) {
        AlumnoRepo almRepo = new AlumnoRepo();
        DocenteRepo dctRepo = new DocenteRepo();
        CursoRepo crsRepo = new CursoRepo();
        MatriculaRepo mtrRepo = new MatriculaRepo();
        sistema = new SistemaAcademicoService(almRepo, dctRepo, crsRepo, mtrRepo);
        menu(sistema);
    }

    public static void menu(SistemaAcademicoService sistema) {

        System.out.println("<-----Bienvenido al Sistema académico----->");
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Registrar alumno.");
        System.out.println("2. Registrar docente.");
        System.out.println("3. Registrar curso.");
        System.out.println("4. Matricular alumno en curso.");
        System.out.println("5. Registrar notas de los alumnos por curso.");
        System.out.println("6. Calcular el promedio ponderado de cada alumno.");
        System.out.println("7. Mostrar el alumno con el promedio más alto");
        System.out.println("8. Listar los alumnos aprobados y desaprobados por curso.");
        System.out.println("9. Salir.");
        System.out.print("Ingrese el número de la opción que desea realizar: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                registrarAlumno();
                break;
            case 2:
                registrarDocente();
                break;
            case 3:
                registrarCurso();
                break;
            case 4:
                matricular();
                break;
            case 5:
                registrarNotas();
                break;
            case 6:

            case 7:
                System.out.print("Ingrese el código del alumno: ");
                String codProm = sc.nextLine();
                double promedio = sistema.obtenerPromedioAlumno(codProm);
                System.out.println("Promedio del alumno: " + promedio);
                break;
            case 8:
                double mejorProm = -1;
                Alumno mejorAlumno = null;
                for (Alumno a : sistema.listarAlumnos()) {
                    double prom = sistema.obtenerPromedioAlumno(a.getCodigo());
                    if (prom > mejorProm) {
                        mejorProm = prom;
                        mejorAlumno = a;
                    }
                }
                if (mejorAlumno != null)
                    System.out.println("Alumno con mayor promedio: " + mejorAlumno + " con " + mejorProm);
                else
                    System.out.println("No hay alumnos registrados.");
                break;
            case 9:
                listarAlumnosAprobadosPorCurso();
                break;
            case 10:
                System.out.println("Gracias por usar el sistema. :)");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void registrarAlumno() {
        System.out.print("Ingrese el código del alumno: ");
        String codigo = sc.nextLine();
        System.out.print("Ingrese los nombres del alumno: ");
        String nombres = sc.nextLine();
        System.out.print("Ingrese los apellidos del alumno: ");
        String apellidos = sc.nextLine();
        System.out.print("Ingrese el DNI del alumno: ");
        String dni = sc.nextLine();
        System.out.print("Ingrese la edad del alumno: ");
        int edad = sc.nextInt();
        sistema.agregarAlumno(codigo, nombres, apellidos, dni, edad);
        System.out.println("Alumno registrado exitosamente.");
    }

    private static void registrarDocente() {
        System.out.print("Ingrese el DNI del docente: ");
        String dniDocente = sc.nextLine();
        System.out.print("Ingrese los nombres del docente: ");
        String nombresDocente = sc.nextLine();
        System.out.print("Ingrese los apellidos del docente: ");
        String apellidosDocente = sc.nextLine();
        System.out.print("Ingrese la especialidad del docente: ");
        String especialidad = sc.nextLine();
        System.out.print("Ingrese los años de experiencia del docente: ");
        int añosExperiencia = sc.nextInt();
        sistema.agregarDocente(dniDocente, nombresDocente, apellidosDocente, especialidad, añosExperiencia);
        System.out.println("Docente registrado exitosamente.");
    }

    private static void registrarCurso() {
        System.out.print("Ingrese el código del curso: ");
        String codigoCurso = sc.nextLine();
        System.out.print("Ingrese el nombre del curso: ");
        String nombreCurso = sc.nextLine();
        System.out.print("Ingrese el DNI del docente que impartirá el curso: ");
        String dniDocenteCurso = sc.nextLine();
        Docente docente = sistema.buscarDocente(dniDocenteCurso);
        if (docente != null) {
            sistema.agregarCurso(codigoCurso, nombreCurso, docente);
            System.out.println("Curso registrado exitosamente.");
        } else {
            System.out.println("No se encontró un docente con ese DNI.");
        }
    }

    private static void matricular() {
        System.out.print("Ingrese el código del alumno: ");
        String codigoAlumno = sc.nextLine();
        System.out.print("Ingrese el código del curso: ");
        String codigoCursoMat = sc.nextLine();
        sistema.agregarMatricula(codigoAlumno, codigoCursoMat);
        System.out.println("Matrícula registrada exitosamente.");
    }

    private static void registrarNotas() {
        System.out.print("Ingrese el código del alumno: ");
        String codigoAlumno = sc.nextLine();
        System.out.print("Ingrese el código del curso: ");
        String codigoCurso = sc.nextLine();
        for (int i = 1; i <= 3; i++) {
            System.out.print("Ingrese la nota " + i + ": ");
            while (!sc.hasNextDouble()) {
                System.out.println("Entrada inválida. Ingrese un número.");
                sc.next(); // descarta entrada inválida
            }
            double nuevaNota = sc.nextDouble();
            sc.nextLine(); // limpiar newline
            sistema.agregarNotaAlumnoCurso(codigoAlumno, codigoCurso, nuevaNota);
            System.out.println("Nota " + i + " registrada/actualizada.");
        }
    }

    private static void listarAlumnosAprobadosPorCurso() {
        System.out.print("Ingrese el código del curso: ");
        String codigoCurso = sc.nextLine();
        ArrayList<Matricula> matriculasCurso = sistema.buscarMatriculasPorCurso(codigoCurso);
        if (matriculasCurso.isEmpty()) {
            System.out.println("No hay matrículas para este curso.");
            return;
        }
        System.out.println("Alumnos aprobados:");
        for (Matricula m : matriculasCurso) {
            if (m.isAprobado()) {
                System.out.println(m.getAlumnoRef() + " - Promedio: " + m.calcularPromedio());
            }
        }
        System.out.println("Alumnos desaprobados:");
        for (Matricula m : matriculasCurso) {
            if (!m.isAprobado()) {
                System.out.println(m.getAlumnoRef() + " - Promedio: " + m.calcularPromedio());
            }
        }
    }
}
