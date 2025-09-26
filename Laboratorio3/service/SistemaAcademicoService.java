package service;
import model.Alumno;
import model.Curso;
import model.Docente;
import model.Matricula;
import repo.AlumnoRepo;
import repo.CursoRepo;
import repo.DocenteRepo;
import repo.MatriculaRepo;
import java.util.*;

public class SistemaAcademicoService {
    private AlumnoRepo alumnoRepo;
    private CursoRepo cursoRepo;
    private DocenteRepo docenteRepo;
    private MatriculaRepo matriculaRepo;

    public SistemaAcademicoService(){
        alumnoRepo = new AlumnoRepo();
        cursoRepo = new CursoRepo();
        docenteRepo = new DocenteRepo();
        matriculaRepo = new MatriculaRepo();
    }

    // Metodos para Alumno
    public void agregarAlumno(String codigo, String nombres, String apellidos, String dni, int edad){
        alumnoRepo.agregarAlumno(new Alumno(codigo, nombres, apellidos, dni, edad));
    }

    public Alumno buscarAlumno(String codigo){
        return alumnoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Alumno> listarAlumnos(){
        return alumnoRepo.listarAlumnos();
    }

    // Metodos para Curso
    public void agregarCurso(String codigo, String nombre, Docente docente){
        cursoRepo.agregarCurso(new Curso(codigo,nombre,docente));
    }

    public Curso buscarCurso(String codigo){
        return cursoRepo.buscarPorCodigo(codigo);
    }

    public ArrayList<Curso> listarCursos(){
        return cursoRepo.mostrarTodos();
    }

    // Metodos para Docente
    public void agregarDocente(String dni, String nombres, String apellidos, String especialidad, int añosExperiencia){
        docenteRepo.agregarDocente(new Docente(dni,nombres,apellidos,especialidad,añosExperiencia));
    }

    public Docente buscarDocente(String codigo){
        return docenteRepo.buscarPorDni(codigo);
    }

    public ArrayList<Docente> listarDocentes(){
        return docenteRepo.mostrarTodos();
    }

    // Metodos para Matricula
    public void agregarMatricula(Alumno alumnoRef, Curso cursoRef, ArrayList<Double> notas){
        matriculaRepo.agregarMatricula(new Matricula(alumnoRef,cursoRef,notas));
    }

    public ArrayList<Matricula> buscarMatriculasPorAlumno(String codigoA){
        return matriculaRepo.buscarPorAlumno(codigoA);
    }

    public ArrayList<Matricula> buscarMatriculasPorCurso(String codigoC){
        return matriculaRepo.buscarPorCurso(codigoC);
    }

    public double obtenerPromedioAlumno(String codigoA){
        return matriculaRepo.obtenerPromedioAlumno(codigoA);
    }
}