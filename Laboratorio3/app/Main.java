package app;
import service.SistemaAcademicoService;
import model.Alumno;
import model.Curso;
import model.Docente;
import model.Matricula;
import java.util.Scanner;
 public class Main{
    public static void main(String[] args){
        SistemaAcademicoService sistema = new SistemaAcademicoService();
        menu(sistema);
    }

    public static void menu(SistemaAcademicoService sistema){
        Scanner sc = new Scanner(System.in);
        System.out.println("<-----Bienvenido al Sistema académico----->");
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Registrar alumno.");
        System.out.println("2. Registrar docente.");
        System.out.println("3. Registrar curso.");
        System.out.println("4. Matricular alumno en curso.");
        System.out.println("6. Registrar notas de los alumnos por curso.");
        System.out.println("7. Calcular el promedio ponderado de cada alumno.");
        System.out.println("8. Mostrar el alumno con el promedio más alto");
        System.out.println("9. Listar los alumnos aprobados y desaprobados por curso.");
        System.out.println("10. Salir.");
        System.out.print("Ingrese el número de la opción que desea realizar: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch(opcion){
            case 1:
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
                break;
            case 2:
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
                break;
            case 3:
                System.out.print("Ingrese el código del curso: ");
                String codigoCurso = sc.nextLine();
                System.out.print("Ingrese el nombre del curso: ");
                String nombreCurso = sc.nextLine();
                System.out.print("Ingrese el DNI del docente que impartirá el curso: ");
                String dniDocenteCurso = sc.nextLine();
                Docente docente = sistema.buscarDocente(dniDocenteCurso);
                if(docente != null){
                    sistema.agregarCurso(codigoCurso, nombreCurso, docente);
                    System.out.println("Curso registrado exitosamente.");
                } else {
                    System.out.println("No se encontró un docente con ese DNI.");
                }
                break;
            case 4:
                System.out.print("Ingrese el código del alumno: ");
                String codigoAlumno = sc.nextLine();
                System.out.print("Ingrese el código del curso: ");
                String codigoCursoMat = sc.nextLine();
                System.out.print("Ingrese la nota inicial: ");
                double notaInicial = sc.nextDouble();
                sc.nextLine();
                /* *
                sistema.agregarMatricula(codigoAlumno, codigoCursoMat);
                System.out.println(Matrícula registrada exitosamente.");
                break;
                */
            case 5:
                System.out.println("Lista de alumnos:");
                for (Alumno a : sistema.listarAlumnos()) 
                    System.out.println(a);
            break;
            case 6:
                System.out.print("Ingrese el código del alumno: ");
                String codAluNota = sc.nextLine();
                System.out.print("Ingrese el código del curso: ");
                String codCurNota = sc.nextLine();
                System.out.print("Ingrese la nueva nota: ");
                double nuevaNota = sc.nextDouble();
                sc.nextLine();

    //      sistema.agregarMatricula(codAluNota, codCurNota, nuevaNota);
                System.out.println("Nota registrada/actualizada.");
                break;
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
                System.out.print("Ingrese el código del curso: ");
                String codCursoCheck = sc.nextLine();
                System.out.println("Aprobados:");
                /*for (Matricula m : sistema.buscarMatriculasPorCurso(codCursoCheck)) {
                    if (m.getNota() >= 11) 
                        System.out.println(m);
                }
                System.out.println(" Desaprobados:");
                for (Matricula m : sistema.buscarMatriculasPorCurso(codCursoCheck)) {
                    if (m.getNota() < 11) 
                        System.out.println(m);
                }*/
                break;
            case 10:
                System.out.println("Gracias por usar el sistema. :)");
                break;
            default:
                    System.out.println("Opción no válida.");
        }
    }
}

